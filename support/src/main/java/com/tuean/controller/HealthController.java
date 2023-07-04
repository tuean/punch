package com.tuean.controller;

import com.tuean.entity.request.TestRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @RequestMapping(value = "/health", method = RequestMethod.POST)
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
