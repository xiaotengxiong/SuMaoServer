package com.aliyun.mns.client;

import com.aliyun.mns.model.Message;

/*
 * interface for do the transaction.
 */
public interface TransactionOperations {
    
    /*
     * do the transaction related to the message.
     */
    public boolean doTransaction(Message message);
}
