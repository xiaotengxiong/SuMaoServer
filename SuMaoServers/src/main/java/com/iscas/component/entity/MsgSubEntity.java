package com.iscas.component.entity;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/4
 * Time: 14:16
 */
public class MsgSubEntity {
    private String macId;
    private String actionType;
    private String cardId;
    private String actionTime;
    private String msgId;

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
