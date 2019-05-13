package com.ls.stockforecast.config.feign;

import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yaochenglong
 * @date 2018/11/14
 *
 * feign拦截器
 * 设置header中的Authorization
 */
@Configuration
public class FeignConfiguration implements RequestInterceptor{

    private Logger log = LoggerFactory.getLogger(FeignConfiguration.class);

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isNotEmpty(authorization)){
            template.header("Authorization", authorization);
        }
    }


    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public feign.Logger.Level feignLoggerLevel(){
        return feign.Logger.Level.BASIC;
    }
}
