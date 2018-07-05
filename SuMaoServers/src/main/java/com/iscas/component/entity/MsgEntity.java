package com.iscas.component.entity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/4
 * Time: 14:14
 */
public class MsgEntity<T> {

    private String hostMac;
    private int type;
    private String imei;
    private List<T> msg;
    private String latdm;
    private String lngdm;

    public String getHostMac() {
        return hostMac;
    }

    public void setHostMac(String hostMac) {
        this.hostMac = hostMac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<T> getMsg() {
        return msg;
    }

    public void setMsg(List<T> msg) {
        this.msg = msg;
    }

    public String getLatdm() {
        return latdm;
    }

    public void setLatdm(String latdm) {
        this.latdm = latdm;
    }

    public String getLngdm() {
        return lngdm;
    }

    public void setLngdm(String lngdm) {
        this.lngdm = lngdm;
    }
}
