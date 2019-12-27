package com.sona.transmittable.ops;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 运维跟踪上下文, 通过 TransmittableThreadLocal 传递 requestId
 */
public class TraceContext {

    private static TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();

    public static void setRequestId(String requestId) {
        context.set(requestId);
    }

    public static String getRequestId() {
        return context.get();
    }

    public static void remove() {
        context.remove();
    }
}
