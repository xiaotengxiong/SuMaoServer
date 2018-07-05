package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.common.BatchDeleteException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.ErrorMessageResult;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.aliyun.mns.common.MNSConstants.*;

public class ErrorReceiptHandleListDeserializer extends XMLDeserializer<Exception> {
    @Override
    public Exception deserialize(InputStream stream) throws Exception {

        // byte[] bytes = new byte[1024];
        // while(stream.read(bytes, 0, stream.available())>0){
        // System.out.println(new String(bytes));
        // }
        Document doc = getDocmentBuilder().parse(stream);
        Exception ret = null;
        Element root = doc.getDocumentElement();
        
        if (root != null) {
            String rootName = root.getNodeName();
            
            if (rootName == ERROR_LIST_TAG) {
                NodeList list = doc.getElementsByTagName(ERROR_TAG);
                if (list != null && list.getLength() > 0) {
                    Map<String, ErrorMessageResult> results = new HashMap<String, ErrorMessageResult>();
        
                    for (int i = 0; i < list.getLength(); i++) {
                        String receiptHandle = parseReceiptHandle((Element) list.item(i));
                        ErrorMessageResult result = parseErrorResult((Element) list.item(i));
                        results.put(receiptHandle, result);
        
                    }
                    ret = new BatchDeleteException(results);
                }
            }
            else if (rootName == ERROR_TAG) {
                String code = safeGetElementContent(root, ERROR_CODE_TAG, "");
                String message = safeGetElementContent(root, ERROR_MESSAGE_TAG, "");
                String requestId = safeGetElementContent(root, ERROR_REQUEST_ID_TAG, "");
                String hostId = safeGetElementContent(root, ERROR_HOST_ID_TAG, "");
                ret = new ServiceException(message, null, code, requestId, hostId);
            }
        }
        return ret;
    }

    private String parseReceiptHandle(Element root) {
        return safeGetElementContent(root, RECEIPT_HANDLE_TAG,
                null);
    }

    private ErrorMessageResult parseErrorResult(Element root) {
        ErrorMessageResult result = new ErrorMessageResult();
        String errorCode = safeGetElementContent(root, MESSAGE_ERRORCODE_TAG,
                null);
        result.setErrorCode(errorCode);

        String errorMessage = safeGetElementContent(root,
                MESSAGE_ERRORMESSAGE_TAG, null);
        result.setErrorMessage(errorMessage);
        return result;
    }
}
