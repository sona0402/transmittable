package com.sona.transmittable.web;

import com.sona.transmittable.ops.TraceContext;
import com.sona.transmittable.util.TaskExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/request")
public class RequestTestController {

    private static final Logger log = LogManager.getFormatterLogger(RequestTestController.class);


    @GetMapping(path = "/test")
    public String test() {

        log.info(Thread.currentThread() + "father:------>" + TraceContext.getRequestId());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread() + "son:------>" + TraceContext.getRequestId());
            }

        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread() + "son2:------>" + TraceContext.getRequestId());
            }

        };

        TaskExecutor.submit(runnable);
        TaskExecutor.submit(runnable2);


        return "OK";
    }
}
