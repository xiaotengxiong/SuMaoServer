package com.aliyun.mns.common;

import com.aliyun.mns.model.ErrorMessageResult;

import java.util.Map;

public class BatchDeleteException extends ServiceException {
    /**
     *
     */
    private static final long serialVersionUID = -7705861423905005565L;
    private Map<String, ErrorMessageResult> errorMessages;

    public BatchDeleteException(Map<String, ErrorMessageResult> errorMsgs) {
        this.errorMessages = errorMsgs;
    }

    public Map<String, ErrorMessageResult> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, ErrorMessageResult> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
