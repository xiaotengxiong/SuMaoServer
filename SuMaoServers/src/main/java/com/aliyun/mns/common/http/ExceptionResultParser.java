package com.aliyun.mns.common.http;

import java.io.IOException;

import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.parser.JAXBResultParser;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.common.utils.IOUtils;
import com.aliyun.mns.model.ErrorMessage;

public class ExceptionResultParser implements ResultParser<Exception> {
    @Override
    public Exception parse(ResponseMessage response) throws ResultParseException {
        assert response != null;

        if (response.isSuccessful()) {
            return null;
        }

        ServiceException result = null;
        String content = null;
        try {
            content = IOUtils.readStreamAsString(response.getContent(), "UTF-8");
        } catch (IOException e) {
            return new ServiceException(e.getMessage(), e);
        }

        try {
            // 使用jaxb common parser
            JAXBResultParser d = new JAXBResultParser(ErrorMessage.class);
            Object obj = d.parse(content);
            if (obj instanceof ErrorMessage) {
                ErrorMessage err = (ErrorMessage) obj;
                result = new ServiceException(err.Message, null, err.Code, err.RequestId, err.HostId);
            }
        } catch (Exception e) {
            // now treat it as unknown formats
            result = new ServiceException(content, null, "InternalServerError", null, null);
        }

        return result;
    }

}
