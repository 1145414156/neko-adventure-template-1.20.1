package com.nekoadventure.client;

import net.minecraft.text.Text;

import java.util.function.Supplier;

/**
 * 获取“显示完整道具池”按键名称的桥接类
 * 因为main代码不能直接引用client端的类，所以由client端入口注入真正的实现
 * 在非客户端环境（如专用服务器）下默认返回 Alt
 * 这个类由AI辅助完成
 */

public final class ShowItemPoolKeyHelper {
    private static Supplier<Text> keyNameSupplier = () -> Text.literal("Alt");

    private ShowItemPoolKeyHelper() {}

    public static void setKeyNameSupplier(Supplier<Text> supplier) {
        keyNameSupplier = supplier;
    }

    public static Text getKeyName() {
        return keyNameSupplier.get();
    }
}
