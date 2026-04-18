package com.nekoadventure.client;

import java.util.function.BooleanSupplier;

/**
 * 判断玩家是否按住Shift的桥接类
 * 因为main代码不能直接引用client端的类，所以由client端入口注入真正的实现
 * 在非客户端环境（如专用服务器）下默认返回false
 */
public final class ShiftKeyHelper {
    private static BooleanSupplier shiftDownSupplier = () -> false;

    private ShiftKeyHelper() {}

    public static void setShiftDownSupplier(BooleanSupplier supplier) {
        shiftDownSupplier = supplier;
    }

    public static boolean isShiftDown() {
        return shiftDownSupplier.getAsBoolean();
    }
}
