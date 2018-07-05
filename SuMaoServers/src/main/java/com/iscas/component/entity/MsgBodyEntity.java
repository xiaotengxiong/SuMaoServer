package com.iscas.component.entity;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/4
 * Time: 16:21
 */
public class MsgBodyEntity {
    private String payload;
    private String messagetype;
    private String messageid;
    private String timestamp;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
