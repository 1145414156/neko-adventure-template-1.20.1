package com.nekoadventure.network.mob;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

//该类由AI完成
public class ClientFloorShakeHandler {
    private static final float RISE_SPEED = 0.5F;
    private static final int HOLD_TICKS = 5;
    private static final int MAX_BLOCKS = 512;

    private static final List<BouncingBlock> BOUNCING_BLOCKS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    private static class BouncingBlock {
        final BlockPos pos;
        final BlockState state;
        final float maxHeight;
        float offsetY;
        int holdTicks;
        boolean falling;

        BouncingBlock(BlockPos pos, BlockState state, float minHeight, float maxHeight) {
            this.pos = pos;
            this.state = state;
            float actualMin = Math.min(minHeight, maxHeight);
            float actualMax = Math.max(minHeight, maxHeight);
            this.maxHeight = actualMin + RANDOM.nextFloat() * (actualMax - actualMin);
        }
    }

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(FloorShakeNetworking.CHANNEL,
                (client, handler, buf, responseSender) -> {
                    BlockPos center = buf.readBlockPos();
                    double radius = buf.readDouble();
                    float minHeight = buf.readFloat();
                    float maxHeight = buf.readFloat();
                    client.execute(() -> startEffect(client, center, radius, minHeight, maxHeight));
                });
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
        WorldRenderEvents.AFTER_ENTITIES.register(ClientFloorShakeHandler::render);
    }
    private static void startEffect(MinecraftClient client, BlockPos center, double radius,
                                    float minHeight, float maxHeight) {
        List<BouncingBlock> blocks = new ArrayList<>();
        World world = client.world;
        if (world == null) {
            return;
        }
        int r = (int) Math.ceil(radius);
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                if (dx * dx + dz * dz > radius * radius) {
                    continue;
                }
                BlockPos pos = center.add(dx, 0, dz);
                BlockState state = world.getBlockState(pos);
                if (state.isAir() || state.getRenderType() == BlockRenderType.INVISIBLE) {
                    continue;
                }
                if (state.getHardness(world, pos) < 0) {
                    continue;
                }
                blocks.add(new BouncingBlock(pos, state, minHeight, maxHeight));
            }
        }
        Random random = new Random();
        for (int i = 0; i < blocks.size() / 4 * 3 && !blocks.isEmpty(); i++) {
            int randomIndex = random.nextInt(blocks.size());
            blocks.remove(randomIndex);
        }
        BOUNCING_BLOCKS.addAll(blocks);
        if (BOUNCING_BLOCKS.size() > MAX_BLOCKS) {
            BOUNCING_BLOCKS.sort(Comparator.comparingDouble(rb -> rb.pos.getSquaredDistance(center)));
            BOUNCING_BLOCKS.subList(MAX_BLOCKS, BOUNCING_BLOCKS.size()).clear();
        }
    }

    private static void tick() {
        BOUNCING_BLOCKS.removeIf(rb -> {
            if (rb.falling) {
                rb.offsetY = Math.max(0, rb.offsetY - RISE_SPEED);
                return rb.offsetY <= 0;
            }
            if (rb.offsetY < rb.maxHeight) {
                rb.offsetY = Math.min(rb.maxHeight, rb.offsetY + RISE_SPEED);
                return false;
            }
            rb.holdTicks++;
            if (rb.holdTicks > HOLD_TICKS) {
                rb.falling = true;
            }
            return false;
        });
    }

    private static void render(WorldRenderContext context) {
        if (BOUNCING_BLOCKS.isEmpty()) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        World world = context.world();
        Camera camera = context.camera();
        if (world == null || camera == null) {
            return;
        }
        client.gameRenderer.getLightmapTextureManager().enable();

        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();

        for (BouncingBlock rb : BOUNCING_BLOCKS) {
            if (!isWithinRenderDistance(client, rb)) {
                continue;
            }
            BlockState state = world.getBlockState(rb.pos);
            if (state.isAir() || state.getRenderType() == BlockRenderType.INVISIBLE) {
                continue;
            }
            matrices.push();
            matrices.translate(
                    rb.pos.getX() - camera.getPos().x,
                    rb.pos.getY() + rb.offsetY - camera.getPos().y,
                    rb.pos.getZ() - camera.getPos().z);
            int light = WorldRenderer.getLightmapCoordinates(world, state, rb.pos.up(1));
            int skyLight = (light >> 20) & 0xF;
            int blockLight = (light >> 4) & 0xF;
            if (blockLight < 8) {
                blockLight = 8;
            }
            light = (skyLight << 20) | (blockLight << 4);

            client.getBlockRenderManager().renderBlockAsEntity(
                    state, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);

            matrices.pop();
        }

        vertexConsumers.draw();

        client.gameRenderer.getLightmapTextureManager().disable();
    }

    private static boolean isWithinRenderDistance(MinecraftClient client, BouncingBlock rb) {
        if (client.player == null) {
            return false;
        }
        double dx = client.player.getX() - rb.pos.getX();
        double dz = client.player.getZ() - rb.pos.getZ();
        return dx * dx + dz * dz < 64 * 64;
    }
}
