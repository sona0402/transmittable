package com.sona.transmittable.ops;

import java.util.UUID;

/**
 * 运维跟踪工具类
 */
public final class TraceUtils {
    public static String getRequestId() {
        return String.format("r%s", UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
