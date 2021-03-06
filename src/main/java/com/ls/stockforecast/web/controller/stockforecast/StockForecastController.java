package com.ls.stockforecast.web.controller.stockforecast;

import com.fasterxml.jackson.databind.JsonNode;
import com.ls.stockforecast.core.web.response.ErrorBody;
import com.ls.stockforecast.entity.model.base.stockforecast.StockInfo;
import com.ls.stockforecast.service.stockforecast.StockInfoService;
import com.ls.stockforecast.service.stockforecast.TecentQuoteService;
import com.ls.stockforecast.service.stockforecast.XueqiuQuoteService;
import com.ls.stockforecast.utils.DateUtils;
import com.ls.stockforecast.utils.constant.ErrorCode;
import com.ls.stockforecast.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by sheng.li on 2019/5/10.
 */
@RestController
public class StockForecastController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StockForecastController.class);

    private static int STARTYEAR = 1990;

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private TecentQuoteService tecentQuoteService;
    @Autowired
    private XueqiuQuoteService xueqiuQuoteService;

    @PostMapping(value = "/tecent/quote")
    public ResponseEntity insertTecentQuote(@RequestBody JsonNode param, HttpServletRequest request) {
        String type = param.get("type")==null?null:param.get("type").asText();
        if(!"date".equals(type) && !"scode".equals(type)) {
            return handleResult(new ErrorBody(ErrorCode.ERROR_400, "参数错误"));
        }
        if("date".equals(type)) {
            String date = param.get("date") == null ? null : param.get("date").asText();
            if (DateUtils.getFormatDate(date, DateUtils.DATE_PATTERN_) == null)
                return handleResult(new ErrorBody(ErrorCode.ERROR_400, "参数错误"));
            String error = tecentQuoteService.insertQuoteByDate(date);
            if (StringUtils.isNotEmpty(error)) {
                logger.error(error);
            }
        } else if("scode".equals(type)) {
            String scode = param.get("scode") == null ? null : param.get("scode").asText();
            String mktcode = param.get("mktcode") == null ? null : param.get("mktcode").asText();
            String year = param.get("year") == null ? null : param.get("year").asText();
            if(StringUtils.isNotEmpty(year)) {
                String error = tecentQuoteService.insertQuoteByScodeAndYear(scode, mktcode, year);
                if (StringUtils.isNotEmpty(error)) {
                    logger.error(error);
                }
            } else {
                int endYear = Integer.parseInt(DateUtils.getFormatDateStr(new Date(), DateUtils.DATE_YEAR));
                for(int i=endYear;i>=STARTYEAR;i--) {
                    String error = tecentQuoteService.insertQuoteByScodeAndYear(scode, mktcode, i+"");
                    if (StringUtils.isNotEmpty(error)) {
                        logger.error(error);
                    }
                }
            }
        }
        return handleResult();
    }

    @PostMapping(value = "/tecent/year-quote")
    public ResponseEntity insertYearTecentQuote(@RequestBody JsonNode param, HttpServletRequest request) {
        String year = param.get("year") == null ? null : param.get("year").asText();
        List<StockInfo> stockInfoList = stockInfoService.selectAll();
        for(StockInfo stockInfo : stockInfoList) {
            String error = tecentQuoteService.insertQuoteByScodeAndYear(stockInfo.getScode(), stockInfo.getMktcode(), year);
            if (StringUtils.isNotEmpty(error)) {
                logger.error(error);
            }
        }
        return handleResult();
    }

    @PostMapping(value = "/xueqiu/year-quote")
    public ResponseEntity insertYearXueqiuQuote(@RequestBody JsonNode param, HttpServletRequest request) {
        String year = param.get("year") == null ? null : param.get("year").asText();
        List<StockInfo> stockInfoList = stockInfoService.selectAll();
        for(StockInfo stockInfo : stockInfoList) {
            String error = xueqiuQuoteService.insertQuoteByScodeAndYear(stockInfo.getScode(), stockInfo.getMktcode(), year);
            if (StringUtils.isNotEmpty(error)) {
                logger.error(error);
            }
        }
        // test
//        String error = xueqiuQuoteService.insertQuoteByScodeAndYear("600000", "sh", year);
//        if (StringUtils.isNotEmpty(error)) {
//            logger.error(error);
//        }
        return handleResult();
    }

    @PostMapping(value = "/xueqiu/all-quote")
    public ResponseEntity insertAllXueqiuQuote(@RequestBody JsonNode param, HttpServletRequest request) {
        String scode = param.get("scode") == null ? null : param.get("scode").asText();
        StockInfo stockInfo = stockInfoService.selectByScode(scode);
        if(stockInfo==null) return handleResult(new ErrorBody(ErrorCode.ERROR_400, "股票代码错误"));
        int endYear = Integer.parseInt(DateUtils.getFormatDateStr(new Date(), DateUtils.DATE_YEAR));
        for(int i=endYear;i>=STARTYEAR;i--) {
            String error = xueqiuQuoteService.insertQuoteByScodeAndYear(stockInfo.getScode(), stockInfo.getMktcode(), i+"");
            if (StringUtils.isNotEmpty(error)) {
                logger.error(error);
            }
        }
        return handleResult();
    }
}
