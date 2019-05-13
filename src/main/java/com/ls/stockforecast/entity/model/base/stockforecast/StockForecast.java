package com.ls.stockforecast.entity.model.base.stockforecast;

import java.util.Date;

public class StockForecast {
    private Integer id;

    private String scode;

    private Integer mktcode;

    private Integer rank;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode == null ? null : scode.trim();
    }

    public Integer getMktcode() {
        return mktcode;
    }

    public void setMktcode(Integer mktcode) {
        this.mktcode = mktcode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}