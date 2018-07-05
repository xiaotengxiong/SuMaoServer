package com.aliyun.mns.client;

import com.aliyun.mns.client.impl.queue.*;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.request.queue.*;

import java.net.URI;
import java.util.List;

public final class CloudQueue {
    private ServiceClient serviceClient;
    private String queueURL;
    private ServiceCredentials credentials;
    private URI endpoint;

    protected CloudQueue(String queueURL, ServiceClient client,
                         ServiceCredentials credentials, URI endpoint) {
        this.serviceClient = client;
        this.credentials = credentials;
        this.endpoint = endpoint;


        if (queueURL == null || queueURL.equals("")) {
            throw new NullPointerException(
                    "QueueURL parameter can not be empty.");
        }
        String uri = endpoint.toString();
        if (queueURL.startsWith(uri)) {
            this.queueURL = queueURL;
        } else {
            if (!uri.endsWith("/")) {
                uri += "/";
            }

            if (queueURL != null) {
                uri += MNSConstants.QUEUE_PREFIX + queueURL;
            }
            this.queueURL = uri;
        }

    }

    /**
     * 创建队列，使用默认属性
     *
     * @return 队列的URL
     * @throws ServiceException
     * @throws ClientException
     */
    public String create() throws ServiceException, ClientException {
        return create(null);
    }

    /**
     * 创建队列，队列属性由参数queueMeta设置
     *
     * @param queueMeta, queue meta data
     * @return 队列的URL
     * @throws ServiceException
     * @throws ClientException
     */
    public String create(QueueMeta queueMeta) throws ServiceException,
            ClientException {

        CreateQueueAction action = new CreateQueueAction(serviceClient,
                credentials, endpoint);
        String queueName = drillQueueName();
        CreateQueueRequest request = new CreateQueueRequest();
        if (queueMeta == null || queueMeta.getQueueName() == null) {
            queueMeta = new QueueMeta();
            queueMeta.setQueueName(queueName);
        } else {
            if (queueMeta.getQueueName() == null
                    || queueMeta.getQueueName().isEmpty()) {
                queueMeta.setQueueName(queueName);
            } else {
                if (!queueName.equals(queueMeta.getQueueName())) {
                    throw new ClientException(
                            "QueueName conflict between meta queue name and  queue url offered.");
                }
            }
        }

        request.setRequestPath(MNSConstants.QUEUE_PREFIX + queueMeta.getQueueName());
        request.setQueueMeta(queueMeta);
        return action.execute(request);
    }

    private String drillQueueName() {
        String queueName = null;
        if (queueURL.startsWith(this.endpoint.toString())) {
            queueName = queueURL
                    .substring(this.endpoint.toString().length() + 1 + MNSConstants.QUEUE_PREFIX.length());
        }

        // erase start "/"
        while (queueName != null && queueName.trim().length() > 0
                && queueName.startsWith("/")) {
            queueName = queueName.substring(1);
        }

        if (queueName == null || queueName.trim().length() == 0) {
            throw new NullPointerException("Queue Name can not be null.");
        }

        return queueName;
    }

    /**
     * 删除队列
     *
     * @throws ServiceException
     * @throws ClientException
     */
    public void delete() throws ServiceException, ClientException {
        DeleteQueueAction action = new DeleteQueueAction(serviceClient,
                credentials, endpoint);
        DeleteQueueRequest request = new DeleteQueueRequest();
        request.setRequestPath(queueURL);
        action.execute(request);
    }

    /**
     * 获取队列的属性
     *
     * @return 队列属性
     * @throws ServiceException
     * @throws ClientException
     */
    public QueueMeta getAttributes() throws ServiceException, ClientException {
        GetQueueAttrAction action = new GetQueueAttrAction(serviceClient,
                credentials, endpoint);
        GetQueueAttrRequest request = new GetQueueAttrRequest();
        request.setRequestPath(queueURL);
        QueueMeta meta = action.execute(request);
        meta.setQueueURL(queueURL);
        return meta;
    }

    /**
     * 设置队列属性
     *
     * @param queueMeta, queue meta data
     * @throws ClientException
     * @throws ServiceException
     */
    public void setAttributes(QueueMeta queueMeta) throws ClientException,
            ServiceException {
        SetQueueAttrAction action = new SetQueueAttrAction(serviceClient,
                credentials, endpoint);
        SetQueueAttrRequest request = new SetQueueAttrRequest();
        request.setQueueMeta(queueMeta);
        request.setRequestPath(MNSConstants.QUEUE_PREFIX + queueMeta.getQueueName());
        action.execute(request);
    }

    /**
     * 查看队列消息
     *
     * @return 查找到的消息
     * @throws ServiceException
     * @throws ClientException  消息不存在时返回null
     */
    public Message peekMessage() throws ServiceException, ClientException {
        PeekMessageAction action = new PeekMessageAction(serviceClient,
                credentials, endpoint);
        PeekMessageRequest request = new PeekMessageRequest();
        request.setRequestPath(queueURL);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 异步查看队列消息
     *
     * @param callback 异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Message> asyncPeekMessage(AsyncCallback<Message> callback)
            throws ClientException {
        PeekMessageAction action = new PeekMessageAction(serviceClient,
                credentials, endpoint);
        PeekMessageRequest request = new PeekMessageRequest();
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 批量查看队列消息
     *
     * @param batchSize 本次最多查看消息的条数
     * @return 查找到的消息
     * @throws ServiceException
     * @throws ClientException  消息不存在时返回null
     */
    public List<Message> batchPeekMessage(int batchSize) throws ServiceException,
            ClientException {
        BatchPeekMessageAction action = new BatchPeekMessageAction(
                serviceClient, credentials, endpoint);
        BatchPeekMessageRequest request = new BatchPeekMessageRequest();
        request.setBatchSize(batchSize);
        request.setRequestPath(queueURL);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 异步批量查看消息
     *
     * @param batchSize 本次最多查看消息的条数
     * @param callback
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<List<Message>> asyncBatchPeekMessage(int batchSize,
                                                            AsyncCallback<List<Message>> callback) throws ClientException {
        BatchPeekMessageAction action = new BatchPeekMessageAction(serviceClient,
                credentials, endpoint);
        BatchPeekMessageRequest request = new BatchPeekMessageRequest();
        request.setBatchSize(batchSize);
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 改变消息的不可见时间
     *
     * @param receiptHandle     消息句柄
     * @param visibilityTimeout 消息不可见时间，单位是秒
     * @return 新的消息句柄
     * @throws ServiceException
     * @throws ClientException
     */
    public String changeMessageVisibilityTimeout(String receiptHandle,
                                                 int visibilityTimeout) throws ServiceException, ClientException {
        ChangeVisibilityTimeoutAction action = new ChangeVisibilityTimeoutAction(
                serviceClient, credentials, endpoint);
        ChangeVisibilityTimeoutRequest request = new ChangeVisibilityTimeoutRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        request.setVisibilityTimeout(visibilityTimeout);
        return action.execute(request);
    }

    /**
     * 异步改变消息的不可见时间
     *
     * @param receiptHandle     待改变消息的句柄
     * @param visibilityTimeout 新的消息不可见时间，单位是秒
     * @param callback          异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<String> asyncChangeMessageVisibilityTimeout(
            String receiptHandle, int visibilityTimeout,
            AsyncCallback<String> callback) throws ClientException {
        ChangeVisibilityTimeoutAction action = new ChangeVisibilityTimeoutAction(
                serviceClient, credentials, endpoint);
        ChangeVisibilityTimeoutRequest request = new ChangeVisibilityTimeoutRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        request.setVisibilityTimeout(visibilityTimeout);
        return action.execute(request, callback);
    }
    
    /**
     * 改变消息的不可见时间
     *
     * @param receiptHandle     消息句柄
     * @param visibilityTimeout 消息不可见时间，单位是秒
     * @return 新的消息，保含消息句柄和下次可见时间
     * @throws ServiceException
     * @throws ClientException
     */
    public Message changeMessageVisibility(String receiptHandle,
                                                 int visibilityTimeout) throws ServiceException, ClientException {
        ChangeVisibilityAction action = new ChangeVisibilityAction(
                serviceClient, credentials, endpoint);
        ChangeVisibilityTimeoutRequest request = new ChangeVisibilityTimeoutRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        request.setVisibilityTimeout(visibilityTimeout);
        return action.execute(request);
    }

    /**
     * 异步改变消息的不可见时间
     *
     * @param receiptHandle     待改变消息的句柄
     * @param visibilityTimeout 新的消息不可见时间，单位是秒
     * @param callback          异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Message> asyncChangeMessageVisibility(
            String receiptHandle, int visibilityTimeout,
            AsyncCallback<Message> callback) throws ClientException {
        ChangeVisibilityAction action = new ChangeVisibilityAction(
                serviceClient, credentials, endpoint);
        ChangeVisibilityTimeoutRequest request = new ChangeVisibilityTimeoutRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        request.setVisibilityTimeout(visibilityTimeout);
        return action.execute(request, callback);
    }

    /**
     * 获取队列中的消息
     *
     * @return 返回队列中的一个消息
     * @throws ServiceException
     * @throws ClientException  在队列中没有消息的时候返回null
     */
    public Message popMessage() throws ServiceException, ClientException {
        ReceiveMessageAction action = new ReceiveMessageAction(serviceClient,
                credentials, endpoint);
        ReceiveMessageRequest request = new ReceiveMessageRequest();
        request.setRequestPath(queueURL);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 获取队列中的消息
     *
     * @param waitSeconds 长轮询等待时间，单位是秒
     * @return 队列中的一个消息
     * @throws ServiceException
     * @throws ClientException  队列中没有消息的时候返回null
     */
    public Message popMessage(int waitSeconds) throws ServiceException, ClientException {
        ReceiveMessageAction action = new ReceiveMessageAction(serviceClient,
                credentials, endpoint);
        ReceiveMessageRequest request = new ReceiveMessageRequest();
        request.setRequestPath(queueURL);
        request.setWaitSeconds(waitSeconds);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 异步获取队列中的消息
     *
     * @param callback 异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Message> asyncPopMessage(AsyncCallback<Message> callback)
            throws ClientException {
        ReceiveMessageAction action = new ReceiveMessageAction(serviceClient,
                credentials, endpoint);
        ReceiveMessageRequest request = new ReceiveMessageRequest();
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 异步获取队列中的消息
     *
     * @param waitSeconds 长轮询等待时间，单位是秒
     * @param callback    异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Message> asyncPopMessage(int waitSeconds, AsyncCallback<Message> callback)
            throws ClientException {
        ReceiveMessageAction action = new ReceiveMessageAction(serviceClient,
                credentials, endpoint);
        ReceiveMessageRequest request = new ReceiveMessageRequest();
        request.setRequestPath(queueURL);
        request.setWaitSeconds(waitSeconds);
        return action.execute(request, callback);
    }

    /**
     * 批量获取队列中的消息
     *
     * @param batchSize 本次最多获取消息的条数
     * @return 消息列表
     * @throws ServiceException
     * @throws ClientException  队列中没有消息的时候返回null
     */
    public List<Message> batchPopMessage(int batchSize) throws ServiceException, ClientException {
        BatchReceiveMessageAction action = new BatchReceiveMessageAction(serviceClient,
                credentials, endpoint);
        BatchReceiveMessageRequest request = new BatchReceiveMessageRequest();
        request.setBatchSize(batchSize);
        request.setRequestPath(queueURL);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 批量获取队列中的消息
     *
     * @param batchSize   本次最多获取消息的条数
     * @param waitSeconds 长轮询等待时间，单位是秒
     * @return 消息列表
     * @throws ServiceException
     * @throws ClientException  队列中没有消息的时候返回null
     */
    public List<Message> batchPopMessage(int batchSize, int waitSeconds)
            throws ServiceException, ClientException {
        BatchReceiveMessageAction action = new BatchReceiveMessageAction(serviceClient,
                credentials, endpoint);
        BatchReceiveMessageRequest request = new BatchReceiveMessageRequest();
        request.setBatchSize(batchSize);
        request.setWaitSeconds(waitSeconds);
        request.setRequestPath(queueURL);
        try {
            return action.execute(request);
        } catch (ServiceException e) {
            if (!isMessageNotExist(e)) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 异步批量获取队列中的消息
     *
     * @param callback 异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<List<Message>> asyncBatchPopMessage(int batchSize,
                                                           AsyncCallback<List<Message>> callback) throws ClientException {
        BatchReceiveMessageAction action = new BatchReceiveMessageAction(serviceClient,
                credentials, endpoint);
        BatchReceiveMessageRequest request = new BatchReceiveMessageRequest();
        request.setBatchSize(batchSize);
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 异步批量获取队列中的消息
     *
     * @param waitSeconds 长轮询等待时间，单位是秒
     * @param callback    异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<List<Message>> asyncBatchPopMessage(int batchSize, int waitSeconds,
                                                           AsyncCallback<List<Message>> callback) throws ClientException {
        BatchReceiveMessageAction action = new BatchReceiveMessageAction(serviceClient,
                credentials, endpoint);
        BatchReceiveMessageRequest request = new BatchReceiveMessageRequest();
        request.setBatchSize(batchSize);
        request.setWaitSeconds(waitSeconds);
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 删除消息
     *
     * @param receiptHandle 消息句柄
     * @throws ServiceException
     * @throws ClientException
     */
    public void deleteMessage(String receiptHandle) throws ServiceException,
            ClientException {
        DeleteMessageAction action = new DeleteMessageAction(serviceClient,
                credentials, endpoint);
        DeleteMessageRequest request = new DeleteMessageRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        action.execute(request);
    }

    /**
     * 异步删除消息
     *
     * @param receiptHandle 消息句柄
     * @param callback      异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Void> asyncDeleteMessage(String receiptHandle,
                                                AsyncCallback<Void> callback) throws ClientException {
        DeleteMessageAction action = new DeleteMessageAction(serviceClient,
                credentials, endpoint);
        DeleteMessageRequest request = new DeleteMessageRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandle(receiptHandle);
        return action.execute(request, callback);
    }

    /**
     * 批量删除消息
     *
     * @param receiptHandles 消息句柄列表
     * @throws ServiceException
     * @throws ClientException
     */
    public void batchDeleteMessage(List<String> receiptHandles)
            throws ServiceException, ClientException {
        BatchDeleteMessageAction action = new BatchDeleteMessageAction(serviceClient,
                credentials, endpoint);
        BatchDeleteMessageRequest request = new BatchDeleteMessageRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandles(receiptHandles);
        action.execute(request);
    }

    /**
     * @param receiptHandles 消息句柄列表
     * @param callback       异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Void> asyncBatchDeleteMessage(List<String> receiptHandles,
                                                     AsyncCallback<Void> callback) throws ClientException {
        BatchDeleteMessageAction action = new BatchDeleteMessageAction(serviceClient,
                credentials, endpoint);
        BatchDeleteMessageRequest request = new BatchDeleteMessageRequest();
        request.setRequestPath(queueURL);
        request.setReceiptHandles(receiptHandles);
        return action.execute(request, callback);
    }

    /**
     * 发送消息, 消息体在发送到服务端前，我们会对消息体进行一次base64编码，如果你使用本SDK发送，
     * 但用其他方式接收时，需要确认，接收方有无对消息体进行base64解码。
     *
     * @param message 待发送的消息
     * @return 发送成功的消息
     * @throws ServiceException
     * @throws ClientException
     */
    public Message putMessage(Message message) throws ServiceException,
            ClientException {
        SendMessageAction action = new SendMessageAction(serviceClient,
                credentials, endpoint);
        SendMessageRequest request = new SendMessageRequest();
        request.setMessage(message);
        request.setRequestPath(queueURL);
        return action.execute(request);
    }

    /**
     * 异步发送消息, 消息体在发送到服务端前，我们会对消息体进行一次base64编码，如果你使用本SDK发送，
     * 但用其他方式接收时，需要确认，接收方有无对消息体进行base64解码。
     *
     * @param message  待发送的消息
     * @param callback 异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<Message> asyncPutMessage(Message message,
                                                AsyncCallback<Message> callback) throws ClientException {
        SendMessageAction action = new SendMessageAction(serviceClient,
                credentials, endpoint);
        SendMessageRequest request = new SendMessageRequest();
        request.setMessage(message);
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    /**
     * 批量发送消息, 消息体在发送到服务端前，我们会对消息体进行一次base64编码，如果你使用本SDK发送，
     * 但用其他方式接收时，需要确认，接收方有无对消息体进行base64解码。
     *
     * @param messages 待发送的消息
     * @return 发送成功的消息
     * @throws ServiceException
     * @throws ClientException
     */
    public List<Message> batchPutMessage(List<Message> messages) throws ServiceException,
            ClientException {
        BatchSendMessageAction action = new BatchSendMessageAction(serviceClient,
                credentials, endpoint);
        BatchSendMessageRequest request = new BatchSendMessageRequest();
        request.setMessages(messages);
        request.setRequestPath(queueURL);
        return action.execute(request);
    }

    /**
     * 异步批量发送消息, 消息体在发送到服务端前，我们会对消息体进行一次base64编码，如果你使用本SDK发送，
     * 但用其他方式接收时，需要确认，接收方有无对消息体进行base64解码。
     *
     * @param messages 待发送的消息
     * @param callback 异步回调对象
     * @return 异步结果调用句柄
     * @throws ClientException
     */
    public AsyncResult<List<Message>> asyncBatchPutMessage(List<Message> messages,
                                                           AsyncCallback<List<Message>> callback) throws ClientException {
        BatchSendMessageAction action = new BatchSendMessageAction(serviceClient,
                credentials, endpoint);
        BatchSendMessageRequest request = new BatchSendMessageRequest();
        request.setMessages(messages);
        request.setRequestPath(queueURL);
        return action.execute(request, callback);
    }

    public String getQueueURL() {
        return queueURL;
    }

    public boolean isMessageNotExist(ServiceException e) {
        return "MessageNotExist".equals(e.getErrorCode());
    }
}
