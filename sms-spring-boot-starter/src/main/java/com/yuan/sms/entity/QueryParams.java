package com.yuan.sms.entity;

import java.time.LocalDate;

public class QueryParams {
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 短信发送日期,支持查询最近30天的记录
     * 格式为yyyyMMdd,例如20181225
     */
    private LocalDate date;
    /**
     * 分页查看发送记录,指定每页显示的短信记录数量
     * 取值范围为1~50
     */
    private int pageSize;
    /**
     * 分页查看发送记录，指定发送记录的的当前页码
     */
    private int currentPage;
    /**
     * 发送回执ID,即发送流水号.调用发送接口SendSms或SendBatchSms发送短信时,返回值中的BizId字段
     */
    private String bizId;

    public QueryParams(String phoneNumber, LocalDate date, int pageSize, int currentPage) {
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
