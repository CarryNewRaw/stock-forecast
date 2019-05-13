package com.ls.stockforecast.web.controller.stockforecast;

import com.fasterxml.jackson.databind.JsonNode;
import com.ls.stockforecast.core.web.response.ErrorBody;
import com.ls.stockforecast.service.stockforecast.StockQuoteService;
import com.ls.stockforecast.utils.DateUtils;
import com.ls.stockforecast.utils.constant.ErrorCode;
import com.ls.stockforecast.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sheng.li on 2019/5/10.
 */
@RestController
public class StockForecastController extends BaseController {
    @Autowired
    private StockQuoteService stockQuoteService;

    @PostMapping(value = "/quote")
    public ResponseEntity insertQuote(@RequestBody JsonNode param, HttpServletRequest request) {
        String scode = param.get("scode")==null?"":param.get("scode").asText();
        String date = param.get("date")==null?"":param.get("date").asText();
        if(DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_)==null)
            return handleResult(new ErrorBody(ErrorCode.ERROR_400, "参数错误"));
        stockQuoteService.insertDailyQuote();
        return handleResult();
    }
}
