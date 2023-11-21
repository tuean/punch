package com.tuean.controller;

import com.tuean.annotation.*;
import com.tuean.entity.request.TestRequest;
import com.tuean.helper.context.Ctx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.tuean.config.enums.HttpMethod.POST;

@Ctx
@Api
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @ApiJson(path = "/health", method = POST)
    public String health(@RequestParam("a") Integer a, @RequestParam("b") Integer b,
                         @RequestBody TestRequest testRequest,
                         @RequestHeader("token") String token) {
        logger.info("a:{}", a);
        logger.info("b:{}", b);
        logger.info("token:{}", token);
        logger.info("testRequest:{}", testRequest);
        return "ok";
    }


}
