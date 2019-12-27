package com.sona.transmittable.interceptor;

import com.sona.transmittable.ops.Log;
import com.sona.transmittable.ops.TraceContext;
import com.sona.transmittable.ops.TraceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 运维跟踪拦截器
 *
 * @author renfakai
 * @since 2019/12/27
 */
public class TraceInterceptor extends HandlerInterceptorAdapter {
    /**
     * 如果在上游header里面传递requestID使用上游requestId
     */
    private static final String HEADER_REQUEST_ID = "X-Request-Id";

    private static final String KEY_LOGGER_REQUEST_ID = "requestId";

    private static final Logger logger = LogManager.getFormatterLogger(TraceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Log log = new Log();
        log.title = "请求开始";

        String requestId = getRequestId(request);
        TraceContext.setRequestId(requestId);

        ThreadContext.put(KEY_LOGGER_REQUEST_ID, requestId);

        log.requestId = requestId;
        log.setTag("access", "start");
        log.setField("method", request.getMethod());
        log.setField("uri", request.getRequestURI());

        logger.info(log.toJson());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Log log = new Log();
        log.title = "请求结束";
        log.requestId = TraceContext.getRequestId();
        log.setTag("access", "end");

        logger.info(log.toJson());
    }

    private String getRequestId(HttpServletRequest request) {

        String requestId = request.getHeader(HEADER_REQUEST_ID);

        if (requestId == null) {

            requestId = request.getHeader(HEADER_REQUEST_ID.toLowerCase());

            if (requestId == null) {
                requestId = TraceUtils.getRequestId();
            }

        }

        return requestId;
    }
}
