package com.ls.stockforecast.core.web.response;

/**
 * @author yaochenglong
 * @date 2018/10/26
 *
 * 逻辑执行失败后的返回体
 *
 */
public class ErrorBody implements ResponseBody{
    /*错误代码*/
    private String errcode;
    /*错误信息*/
    private String errmsg;


    public ErrorBody() {
    }

    public ErrorBody(String errcode) {
        this.errcode = errcode;
    }

    public ErrorBody(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
