package com.aliyun.mns.model;

import com.google.gson.Gson;

public class MessageAttributes implements BaseAttributes {
    private MailAttributes mailAttributes;

    @Override
    public AttributesValidationResult validate() {
        AttributesValidationResult result;
        if (mailAttributes != null)
        {
            result = mailAttributes.validate();
            if (!result.isSuccess())
            {
                return result;
            }
        }

        result = new AttributesValidationResult();
        result.setSuccess(true);
        return result;
    }

    public MailAttributes getMailAttributes() {
        return mailAttributes;
    }

    public void setMailAttributes(MailAttributes mailAttributes) {
        this.mailAttributes = mailAttributes;
    }
}
