package com.iscas.component.services.hotel_extension.bean;

import com.iscas.component.core.PagerModel;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:15
 */
public class HotelExtension extends PagerModel {
    private String id;
    private String deviceName;
    private String macId;
    private String hotelNo;
    private String hotelName;
    private String roomNo;
    private String status;
    private String region;
    private String ext;
    private String createTime;
    private String delStatus;
    private String memo;
    private String actionTime;
    private String lngdm;
    private String latdm;
    private String cardId;
    private String subMacId;
    private String hostMac;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getLngdm() {
        return lngdm;
    }

    public void setLngdm(String lngdm) {
        this.lngdm = lngdm;
    }

    public String getLatdm() {
        return latdm;
    }

    public void setLatdm(String latdm) {
        this.latdm = latdm;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSubMacId() {
        return subMacId;
    }

    public void setSubMacId(String subMacId) {
        this.subMacId = subMacId;
    }

    public String getHostMac() {
        return hostMac;
    }

    public void setHostMac(String hostMac) {
        this.hostMac = hostMac;
    }
}
