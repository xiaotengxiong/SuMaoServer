package com.aliyun.mns.client;

import com.aliyun.mns.model.Message;

/*
 * interface for check if the transaction is executed successfully.
 */
public interface TransactionChecker {
    
    /*
     * check if the transaction related to the message is success.
     */
    public boolean checkTransactionStatus(Message message);
}
