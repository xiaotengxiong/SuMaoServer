package com.iscas.component.services.card_type.bean;

import com.iscas.component.core.PagerModel;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:15
 */
public class CardType extends PagerModel {
    private String id;
    private String cardNo;
    private String positionNo;
    private String positionName;
    private String createTime;
    private String delStatus;
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
}
