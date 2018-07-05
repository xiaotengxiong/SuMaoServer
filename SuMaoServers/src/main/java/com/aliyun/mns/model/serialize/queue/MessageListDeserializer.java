package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.model.ErrorMessageResult;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.*;

public class MessageListDeserializer extends XMLDeserializer<List<Message>> {
    @Override
    public List<Message> deserialize(InputStream stream) throws Exception {

        // byte[] bytes = new byte[1024];
        // while(stream.read(bytes, 0, stream.available())>0){
        // System.out.println(new String(bytes));
        // }
        Document doc = getDocmentBuilder().parse(stream);
        return deserialize(doc);

    }

    public List<Message> deserialize(Document doc) {
        NodeList list = doc.getElementsByTagName(MESSAGE_TAG);
        if (list != null && list.getLength() > 0) {
            List<Message> results = new ArrayList<Message>();

            for (int i = 0; i < list.getLength(); i++) {
                Message msg = parseMessage((Element) list.item(i));
                results.add(msg);
            }
            return results;
        }
        return null;
    }

    private ErrorMessageResult parseErrorMessageResult(Element root) {
        ErrorMessageResult result = new ErrorMessageResult();
        String errorCode = safeGetElementContent(root, MESSAGE_ERRORCODE_TAG,
                null);
        result.setErrorCode(errorCode);

        String errorMessage = safeGetElementContent(root,
                MESSAGE_ERRORMESSAGE_TAG, null);
        result.setErrorMessage(errorMessage);
        return result;
    }

    private Message parseMessage(Element root) throws ClientException {
        Message message = new Message();

        String messageId = safeGetElementContent(root, MESSAGE_ID_TAG, null);
        if (messageId == null) {
            message.setErrorMessage(parseErrorMessageResult(root));
            return message;
        }

        message.setMessageId(messageId);
        String messageBody = safeGetElementContent(root, MESSAGE_BODY_TAG, null);
        if (messageBody != null) {
            // try {
            message.setMessageBody(messageBody, Message.MessageBodyType.RAW_STRING);
            // } catch (UnsupportedEncodingException e) {
            // throw new RuntimeException("Not support enconding:"
            // + DEFAULT_CHARSET);
            // }
        }

        String messageBodyMD5 = safeGetElementContent(root,
                MESSAGE_BODY_MD5_TAG, null);
        message.setMessageBodyMD5(messageBodyMD5);

        String receiptHandle = safeGetElementContent(root, RECEIPT_HANDLE_TAG,
                null);
        message.setReceiptHandle(receiptHandle);

        String enqueTime = safeGetElementContent(root, ENQUEUE_TIME_TAG, null);
        if (enqueTime != null)
            message.setEnqueueTime(new Date(Long.parseLong(enqueTime)));

        String nextVisibleTime = safeGetElementContent(root,
                NEXT_VISIBLE_TIME_TAG, null);
        if (nextVisibleTime != null)
            message.setNextVisibleTime(new Date(Long.parseLong(nextVisibleTime)));

        String firstDequeueTime = safeGetElementContent(root,
                FIRST_DEQUEUE_TIME_TAG, null);
        if (firstDequeueTime != null)
            message.setFirstDequeueTime(new Date(
                    Long.parseLong(firstDequeueTime)));

        String dequeueCount = safeGetElementContent(root, DEQUEUE_COUNT_TAG,
                null);
        if (dequeueCount != null)
            message.setDequeueCount(Integer.parseInt(dequeueCount));

        String priority = safeGetElementContent(root, PRIORITY_TAG,
                null);
        if (priority != null)
            message.setPriority(Integer.parseInt(priority));

        return message;
    }
}
