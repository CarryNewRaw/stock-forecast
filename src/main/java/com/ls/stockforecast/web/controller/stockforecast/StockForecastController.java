package com.ls.stockforecast.web.controller.stockforecast;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sheng.li on 2019/5/10.
 */
@RestController
public class StockForecastController {
    @GetMapping(value = "/lstest")
    public String lstest(HttpServletRequest request) {
        return "ok";
    }
}
