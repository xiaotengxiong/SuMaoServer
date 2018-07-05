package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.common.BatchSendException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.*;

public class ErrorMessageListDeserializer extends XMLDeserializer<Exception> {
    @Override
    public Exception deserialize(InputStream stream) throws Exception {

//       byte[] bytes = new byte[1024];
//       while(stream.read(bytes, 0, stream.available())>0){
//       System.out.println(new String(bytes));
//       }
        Document doc = getDocmentBuilder().parse(stream);

        Exception ret = null;
        Element root = doc.getDocumentElement();
        
        if (root != null) {
            String rootName = root.getNodeName();
            
            if (rootName == MESSAGE_LIST_TAG) {
                List<Message> msgs = new MessageListDeserializer().deserialize(doc);
                if (msgs != null) {
                    ret = new BatchSendException(msgs);
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
}