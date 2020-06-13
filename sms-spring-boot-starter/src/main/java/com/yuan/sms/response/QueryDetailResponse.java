package com.yuan.sms.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yuan
 */
public class QueryDetailResponse extends SmsResponse{
    @SerializedName("TotalCount")
    private int totalCount;
    @SerializedName("SmsSendDetailDTOs")
    private DetailBean detailBean;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public DetailBean getDetailBean() {
        return detailBean;
    }

    public void setDetailBean(DetailBean detailBean) {
        this.detailBean = detailBean;
    }

    public static class DetailBean {
        @SerializedName("SmsSendDetailDTO")
        private List<SmsDetail> detailList;

        public List<SmsDetail> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<SmsDetail> detailList) {
            this.detailList = detailList;
        }

        public static class SmsDetail {

            @SerializedName("TemplateCode")
            private String templateCode;
            @SerializedName("ReceiveDate")
            private String receiveDate;
            @SerializedName("PhoneNum")
            private String phoneNum;
            @SerializedName("Content")
            private String content;
            @SerializedName("SendStatus")
            private int sendStatus;
            @SerializedName("SendDate")
            private String sendDate;
            @SerializedName("ErrCode")
            private String rrrCode;

            public String getTemplateCode() {
                return templateCode;
            }

            public void setTemplateCode(String templateCode) {
                this.templateCode = templateCode;
            }

            public String getReceiveDate() {
                return receiveDate;
            }

            public void setReceiveDate(String receiveDate) {
                this.receiveDate = receiveDate;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getSendStatus() {
                return sendStatus;
            }

            public void setSendStatus(int sendStatus) {
                this.sendStatus = sendStatus;
            }

            public String getSendDate() {
                return sendDate;
            }

            public void setSendDate(String sendDate) {
                this.sendDate = sendDate;
            }

            public String getRrrCode() {
                return rrrCode;
            }

            public void setRrrCode(String rrrCode) {
                this.rrrCode = rrrCode;
            }
        }
    }
}
