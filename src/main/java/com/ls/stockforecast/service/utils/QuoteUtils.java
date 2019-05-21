package com.ls.stockforecast.service.utils;

import com.ls.stockforecast.feignclient.TecentClient;
import com.ls.stockforecast.feignclient.XueqiuClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class QuoteUtils {
    private QuoteUtils() {}
    private static QuoteUtils quoteUtils;

    @Resource
    private TecentClient tecentClient;
    @Resource
    private XueqiuClient xueqiuClient;

    @PostConstruct
    private void init() {
        quoteUtils = this;
        quoteUtils.tecentClient = this.tecentClient;
        quoteUtils.xueqiuClient = this.xueqiuClient;
    }

    public static TecentClient getTecentClient() {
        return quoteUtils.tecentClient;
    }

    public static XueqiuClient getXueqiuClient() {
        return quoteUtils.xueqiuClient;
    }
}