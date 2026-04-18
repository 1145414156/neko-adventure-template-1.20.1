package com.nekoadventure.block.blockentity.maze;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.block.maze.GateBlock;
import com.nekoadventure.block.maze.MazeRoomStageBlock;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.item.soulItem.AbstractSoulItem;
import com.nekoadventure.other.mazeApart.ClearRoomIssueRewardManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;

//这个类是整个迷宫的核心
//作用是
//1.在玩家进入这个方块的范围时，给玩家添加战斗效果
//2.将初等合法的房间上方方块转换成MazeStructure方块
//3.在清理房间后的奖励发放与清除外壳基岩
public class MazeBlockEntity extends AbstractMazeBlockEntity{
    public MazeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.MAZE_BLOCK, pos, state);
    }

    // 检测范围内是否有怪物
    private boolean hasMonstersInRange() {
        Box detectionBox = createDetectionBox();
        if (world != null) {
            List<HostileEntity> monsters = world.getEntitiesByClass(
                    HostileEntity.class,
                    detectionBox,
                    monster -> monster.isAlive() && !monster.isRemoved()
            );
            return !monsters.isEmpty();
        }
        return false;
    }
    private Box createDetectionBox() {
        if (pos == null) return new Box(0, 0, 0, 0, 0, 0);
        int range = detectRoomDistance(world,pos)-1;
        double minX = pos.getX() - range-1;
        double minY = pos.getY() - 3;
        double minZ = pos.getZ() - range-1;
        double maxX = pos.getX() + range+1;
        double maxY = pos.getY() + 15;
        double maxZ = pos.getZ() + range+1;
        return new Box(minX, minY, minZ, maxX, maxY, maxZ);
    }



    private void removeErrorMaze(World world, BlockPos pos) {
        int detectRange=detectRoomDistance(world,pos);
        if (world instanceof  ServerWorld serverWorld) {
            if (isMazeDimension(serverWorld)) {
                if (detectMazeBlockAround(world,pos)) {
                        for (int c=detectRange;c>=-detectRange;c--){
                            for (int d=detectRange;d>=-detectRange;d--){
                                for (int e=15;e>=0;e--){
                                    world.setBlockState(new BlockPos(pos.getX()+c, pos.getY()+e, pos.getZ()+d),Blocks.AIR.getDefaultState());
                                }
                            }
                        }
                }
            }
            else {
                for (int c=detectRange+1;c>=-detectRange-1;c--){
                    for (int d=detectRange+1;d>=-detectRange-1;d--){
                        for (int e=15;e>=0;e--){
                            world.setBlockState(new BlockPos(pos.getX()+c, pos.getY()+e, pos.getZ()+d),Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    private void givePlayerEffectInRange(World world) {
        if (hasMonstersInRange()){
            List<PlayerEntity> players = world.getEntitiesByClass(
                    PlayerEntity.class,
                    createDetectionBox(),
                    player -> player.isAlive() && !player.isSpectator()
            );
            for (PlayerEntity player : players) {
                if (!player.isCreative()&&!player.isSpectator()) {
                    player.addStatusEffect(new StatusEffectInstance
                            (ModStatusEffects.MAZE_CURSE,5,1,true,false));
                }
            }
        }
    }

    private boolean detectMazeBlockAround(World world,BlockPos pos){
            for (int a=-1;a<=1;a++){
                for (int b=-1;b<=1;b++){
                        if (!(b==0&&a==0)) {
                            if (!world.getBlockState(new BlockPos(pos.getX()+a, pos.getY(), pos.getZ()+b)).equals(Blocks.BEDROCK.getDefaultState())) {
                                return  false;
                            }
                        }
                }
            }
        return world.getBlockState(pos.up(1)).getBlock().equals(Blocks.AIR);
    }

//这个方法是给其他所有涉及到迷宫结构的方块用来检测迷宫半径的
//这个方法有点违反Java的类的结构了qwq，为了省事嘛
    public static int detectRoomDistance(World world, BlockPos pos) {
        for (int i=0;i<=48;i++){
            if (world.getBlockState(new BlockPos(pos.getX()+i,pos.getY(),pos.getZ())).getBlock() instanceof GateBlock) {
                return i;
            }
        }
        return 8;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (world.isClient) return;

        if (world instanceof ServerWorld serverWorld) {
            if (isMazeDimension(serverWorld)) {
                givePlayerEffectInRange(world);
                removeErrorMaze(world, pos);

                //将初等合法的房间上方方块转换成MazeStructure方块
                if (world.getBlockState(pos.up(1)).getBlock().equals(Blocks.BEDROCK)) {
                    world.setBlockState(pos.up(1), ModBlocks.MAZE_STRUCTURE_BLOCK.getDefaultState());
                }
                //奖励发放
                if (!hasMonstersInRange()&&world.getBlockState(pos.up(2)).getBlock().equals(Blocks.AIR)) {
                    if (world.getBlockState(pos.up(1)).equals(ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,1))) {
                        giveRewardForPlayers(world, pos);
                        removeBedrockForWorld(world, pos.up(1));
                    }

                }
            }
            else {
                removeErrorMaze(world,pos);
            }
        }
    }

    private void giveRewardForPlayers(World world, BlockPos pos) {
        List<PlayerEntity> players = world.getEntitiesByClass(
                PlayerEntity.class,
                createDetectionBox(),
                player -> player.isAlive() && !player.isSpectator()
        );
        if (!players.isEmpty()) {
            for (PlayerEntity player:players) {
                player.removeStatusEffect(ModStatusEffects.MAZE_CURSE);
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (stack.getItem() instanceof AbstractSoulItem soulItem) {
                        soulItem.addCharged(stack, 1);
                    }
                }
                player.sendMessage(Text.of("房间清理完成！"),true);
                ClearRoomIssueRewardManager clearRoomIssueRewardManager=new ClearRoomIssueRewardManager();
                clearRoomIssueRewardManager.apply(player, world);
            }
        }
        world.setBlockState(pos.up(1),ModBlocks.MAZE_ROOM_STAGE_BLOCK.getDefaultState().with(MazeRoomStageBlock.MAZE_STAGE,2));
        world.setBlockState(pos,Blocks.BEDROCK.getDefaultState());
    }

    private void removeBedrockForWorld(World world, BlockPos center) {
        int range = detectRoomDistance(world, center);
        if (range <= 0) {
            return;
        }
        int ceilingY = getCeilingHeight(world, center);
        Direction[] directions = {
                Direction.EAST,
                Direction.WEST,
                Direction.SOUTH,
                Direction.NORTH
        };
        for (Direction direction : directions) {
            BlockPos gatePos = center.add(
                    direction.getOffsetX() * range,
                    0,
                    direction.getOffsetZ() * range
            );

            if (world.getBlockState(gatePos).getBlock() == ModBlocks.GATE_BLOCK) {
                removeBedrockOnWall(world, center, direction, range, ceilingY);
            }
        }
    }
    private int getCeilingHeight(World world, BlockPos center) {
        int maxHeight = 64;
        for (int y = 0; y < maxHeight; y++) {
            BlockPos checkPos = center.add(0, y, 0);
            if (world.getBlockState(checkPos).getBlock() == Blocks.BEDROCK) {
                return y;
            }
        }
        return 1;
    }

    private void removeBedrockOnWall(World world, BlockPos center, Direction direction, int range, int ceilingY) {
        int dx = direction.getOffsetX();
        int dz = direction.getOffsetZ();
        BlockPos gatePos = center.add(dx * range, 0, dz * range);
        // 获取替换源方块位置：大门方块往中心方向移动1格，再向上5格
        BlockPos sourcePos = gatePos.add(-dx, 5, -dz);
        BlockState sourceState = world.getBlockState(sourcePos);
        if (sourceState.isAir()) {
            return;
        }
        BlockPos innerPos = gatePos.add(-dx, 0, -dz);

        // 计算左右方向向量（垂直于当前方向）
        Direction leftDirection = direction.rotateYCounterclockwise();
        Direction rightDirection = direction.rotateYClockwise();
        BlockPos[] positionsToReplace = {
                innerPos.add(leftDirection.getOffsetX(), 0, leftDirection.getOffsetZ()),
                innerPos,
                innerPos.add(rightDirection.getOffsetX(), 0, rightDirection.getOffsetZ())
        };
        for (BlockPos pos : positionsToReplace) {
            BlockState currentState = world.getBlockState(pos);
            if (currentState.equals(Blocks.BEDROCK.getDefaultState())) {
                world.setBlockState(pos, sourceState);
            }
        }
        int fixedX = dx != 0 ? dx * range : 0;
        int fixedZ = dz != 0 ? dz * range : 0;
        for (int y = 0; y <= ceilingY-1; y++) {
            for (int offset = -range+1; offset <=range-1; offset++) {
                BlockPos wallPos;

                if (dx != 0) {
                    wallPos = center.add(fixedX, y, offset);
                } else {
                    wallPos = center.add(offset, y, fixedZ);
                }

                // 检查当前方块是否为非空气方块
                BlockState currentState = world.getBlockState(wallPos);
                if (y>0&&y<5&&Math.abs(offset)<=1){
                    world.setBlockState(wallPos,Blocks.AIR.getDefaultState());
                }
                else if (!currentState.isAir()) {
                    // 将非空气方块替换为源方块
                    world.setBlockState(wallPos, sourceState);
                }
            }
        }

    }


    private boolean isMazeDimension(ServerWorld world) {
        TagKey<DimensionType> isMazeTag = TagKey.of(
                RegistryKeys.DIMENSION_TYPE,
                new Identifier(NekoAdventure.MOD_ID, "is_maze")
        );
        return world.getDimensionEntry().isIn(isMazeTag);
    }
}
