package com.aliyun.mns.model.request.account;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.AccountAttributes;

public class SetAccountAttributesRequest extends AbstractRequest {
    private AccountAttributes accountAttributes;

    public AccountAttributes getAccountAttributes() {
        return accountAttributes;
    }

    public void setAccountAttributes(AccountAttributes accountAttributes) {
        this.accountAttributes = accountAttributes;
    }
}
