package com.aliyun.mns.client;


import com.aliyun.mns.client.impl.topic.*;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.*;
import com.aliyun.mns.model.request.topic.*;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CloudTopic {
    /**
     * log4j object
     */
    public static Logger logger = Logger.getLogger(CloudTopic.class);
    /**
     * object connect to MNS service
     */
    private ServiceClient serviceClient;
    /**
     * topic url, ie: http://uid.mns.region.aliyuncs.com/topics/topicName
     */
    private String topicURL;
    /**
     * object content user auth info
     */
    private ServiceCredentials credentials;
    /**
     * user mns endpoint, ie: http://uid.mns.region.aliyuncs.com/
     */
    private URI endpoint;

    private String accountId;
    private String region;

    /**
     * @param topicName,   topic name
     * @param client,      ServiceClient object
     * @param credentials, ServiceCredentials object
     * @param endpoint,    user mns endpoint, ie: http://uid.mns.region.aliyuncs.com/
     */
    protected CloudTopic(String topicName, ServiceClient client,
                         ServiceCredentials credentials, URI endpoint) {
        this.serviceClient = client;
        this.credentials = credentials;
        this.endpoint = endpoint;

        String host = endpoint.getHost();
        String[] hostPieces = host.split("\\.");
        this.accountId = hostPieces[0];
        this.region = hostPieces[2].split("-internal")[0];

        String uri = endpoint.toString();
        if (!uri.endsWith("/"))
            uri += "/";
        uri += MNSConstants.TPOIC_PREFIX + topicName;
        this.topicURL = uri;
    }

    /**
     * get topic name from topic url
     *
     * @return topic name
     */
    private String getTopicName() {
        String topicName = null;
        if (topicURL.startsWith(this.endpoint.toString())) {
            topicName = topicURL
                    .substring(this.endpoint.toString().length() + 1 + MNSConstants.TPOIC_PREFIX.length());
        }

        // erase start "/"
        while (topicName != null && topicName.trim().length() > 0
                && topicName.startsWith("/")) {
            topicName = topicName.substring(1);
        }

        if (topicName == null || topicName.trim().length() == 0) {
            logger.warn("topic name is null or empty");
            throw new NullPointerException("Topic Name can not be null.");
        }

        return topicName;
    }

    /**
     * get topic url
     *
     * @return topic url
     */
    public String getTopicURL() {
        return topicURL;
    }

    /**
     * create topic with default topic meta
     *
     * @return topic url
     */
    public String create() {
        String topicName = this.getTopicName();
        TopicMeta meta = new TopicMeta();
        meta.setTopicName(topicName);
        meta.setTopicURL(this.topicURL);
        return create(meta);
    }

    /**
     * create topic with special topic meta
     *
     * @param meta, topic meta data
     * @return topic url
     */
    public String create(TopicMeta meta) {
        CreateTopicAction action = new CreateTopicAction(serviceClient, credentials, endpoint);
        CreateTopicRequest request = new CreateTopicRequest();
        request.setRequestPath(this.topicURL);
        String topicName = getTopicName();
        if (meta == null) {
            meta = new TopicMeta();
            meta.setTopicName(topicName);
            meta.setTopicURL(this.topicURL);
            logger.debug("topic meta is null, we use default meta");
        }

        if (meta.getTopicName() == null || meta.getTopicName().trim().length() == 0) {
            meta.setTopicName(topicName);
            meta.setTopicURL(this.topicURL);
            logger.debug("topic name in meta is null or empty, we get it from topic url");
        }

        if (!meta.getTopicName().equals(topicName)) {
            logger.warn("TopicName conflict between meta topic name and  topic url offered");
            throw new ClientException("TopicName conflict between meta topic name and  topic url offered");
        }

        request.setTopicMeta(meta);
        request.setRequestPath(MNSConstants.TPOIC_PREFIX + topicName);
        return action.execute(request);

    }

    /**
     * async set topic attribute with given meta and callback object
     *
     * @param meta,     tpoic meta data
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<Void> asyncSetAttribute(TopicMeta meta, AsyncCallback<Void> callback) {
        SetTopicAttrAction action = new SetTopicAttrAction(serviceClient, credentials, endpoint);
        SetTopicAttrRequest request = new SetTopicAttrRequest();
        request.setTopicMeta(meta);
        request.setRequestPath(MNSConstants.QUEUE_PREFIX + meta.getTopicName());
        return action.execute(request, callback);
    }

    /**
     * get topic attribute
     *
     * @return topic meta data
     */
    public TopicMeta getAttribute() {
        GetTopicAttrAction action = new GetTopicAttrAction(serviceClient, credentials, endpoint);
        GetTopicAttrRequest request = new GetTopicAttrRequest();
        request.setRequestPath(topicURL);
        TopicMeta meta = action.execute(request);
        meta.setTopicURL(topicURL);
        return meta;
    }

    /**
     * set tpoic attribute with given meta
     *
     * @param meta, topic meta data
     */
    public void setAttribute(TopicMeta meta) {
        SetTopicAttrAction action = new SetTopicAttrAction(serviceClient, credentials, endpoint);
        SetTopicAttrRequest request = new SetTopicAttrRequest();
        request.setTopicMeta(meta);
        request.setRequestPath(MNSConstants.TPOIC_PREFIX + meta.getTopicName());
        action.execute(request);
    }

    /**
     * async get topic attribute
     *
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<TopicMeta> asyncGetAttribute(AsyncCallback<TopicMeta> callback) {
        GetTopicAttrAction action = new GetTopicAttrAction(serviceClient, credentials, endpoint);
        GetTopicAttrRequest request = new GetTopicAttrRequest();
        request.setRequestPath(topicURL);
        return action.execute(request, callback);
    }

    /**
     * delete topic
     */
    public void delete() {
        DeleteTopicAction action = new DeleteTopicAction(serviceClient, credentials, endpoint);
        DeleteTopicRequest request = new DeleteTopicRequest();
        request.setRequestPath(topicURL);
        action.execute(request);
    }

    /**
     * async delete topic
     *
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<Void> asyncDelete(AsyncCallback<Void> callback) {
        DeleteTopicAction action = new DeleteTopicAction(serviceClient, credentials, endpoint);
        DeleteTopicRequest request = new DeleteTopicRequest();
        request.setRequestPath(topicURL);
        return action.execute(request, callback);
    }

    /**
     * subscribe this topic
     *
     * @param meta, SubscriptionMeta data
     * @return, subscription url
     */
    public String subscribe(SubscriptionMeta meta) {
        SubscribeRequest request = new SubscribeRequest();
        SubscribeAction action = new SubscribeAction(serviceClient, credentials, endpoint);
        request.setMeta(meta);
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + meta.getSubscriptionName());
        String url = action.execute(request);
        return url;
    }

    /**
     * async subscribe this topic
     *
     * @param meta,     SubscriptionMeta data
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<String> asyncSubscribe(SubscriptionMeta meta, AsyncCallback<String> callback) {
        SubscribeRequest request = new SubscribeRequest();
        SubscribeAction action = new SubscribeAction(serviceClient, credentials, endpoint);
        request.setMeta(meta);
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + meta.getSubscriptionName());
        return action.execute(request, callback);
    }

    /**
     * set subscription attribute
     *
     * @param meta, SubscriptionMeta data
     */
    public void setSubscriptionAttr(SubscriptionMeta meta) {
        SetSubscriptionAttrRequest request = new SetSubscriptionAttrRequest();
        SetSubscriptionAttrAction action = new SetSubscriptionAttrAction(serviceClient, credentials, endpoint);
        request.setMeta(meta);
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + meta.getSubscriptionName());
        action.execute(request);
    }

    /**
     * async set subscription attribute
     *
     * @param meta,     SubscriptionMeta data
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<Void> asyncSetSubscriptionAttr(SubscriptionMeta meta, AsyncCallback<Void> callback) {
        SetSubscriptionAttrRequest request = new SetSubscriptionAttrRequest();
        SetSubscriptionAttrAction action = new SetSubscriptionAttrAction(serviceClient, credentials, endpoint);
        request.setMeta(meta);
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + meta.getSubscriptionName());
        return action.execute(request, callback);
    }

    /**
     * get subscription attribute
     *
     * @param subscriptionName, subscription name
     * @return SubscriptionMeta data
     */
    public SubscriptionMeta getSubscriptionAttr(String subscriptionName) {
        if (!Utils.checkSubscriptionName(subscriptionName)) {
            logger.warn("check subscription name fail");
            throw new IllegalArgumentException("subscription name error");
        }
        GetSubscriptionAttrRequest request = new GetSubscriptionAttrRequest();
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + subscriptionName);
        GetSubscriptionAttrAction action = new GetSubscriptionAttrAction(serviceClient, credentials, endpoint);
        return action.execute(request);
    }

    /**
     * async get subscription attribute
     *
     * @param subscriptionName, subscription name
     * @param callback,         user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<SubscriptionMeta> asyncGetSubscriptionAttr(String subscriptionName, AsyncCallback<SubscriptionMeta> callback) {
        if (!Utils.checkSubscriptionName(subscriptionName)) {
            logger.warn("check subscription name fail");
            throw new IllegalArgumentException("subscription name error");
        }
        GetSubscriptionAttrRequest request = new GetSubscriptionAttrRequest();
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + subscriptionName);
        GetSubscriptionAttrAction action = new GetSubscriptionAttrAction(serviceClient, credentials, endpoint);
        return action.execute(request, callback);
    }

    /**
     * unsubscribe this topic
     *
     * @param subscriptionName, subscription name
     */
    public void unsubscribe(String subscriptionName) {
        if (!Utils.checkSubscriptionName(subscriptionName)) {
            logger.warn("check subscription name fail");
            throw new IllegalArgumentException("subscription name error");
        }
        UnsubscribeRequest request = new UnsubscribeRequest();
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + subscriptionName);

        UnsubscribeAction action = new UnsubscribeAction(serviceClient, credentials, endpoint);
        action.execute(request);
    }

    /**
     * async unsubscribe
     *
     * @param subscriptionName, subscription name
     * @param callback,         user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<Void> asyncUnsubscribe(String subscriptionName, AsyncCallback<Void> callback) {
        if (!Utils.checkSubscriptionName(subscriptionName)) {
            logger.warn("check subscription name fail");
            throw new IllegalArgumentException("subscription name error");
        }
        UnsubscribeRequest request = new UnsubscribeRequest();
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION + "/" + subscriptionName);

        UnsubscribeAction action = new UnsubscribeAction(serviceClient, credentials, endpoint);
        return action.execute(request, callback);
    }

    /**
     * list topic subscription
     *
     * @param prefix,    subscription name prefis
     * @param marker,    subscription start marker
     * @param retNumber, return number
     * @param withMeta,  true return full SubscriptionMeta, false return only subscription url
     * @return SubscriptionMeta list
     */
    private PagingListResult<SubscriptionMeta> listSubscriptions(String prefix, String marker,
                                                                 Integer retNumber, boolean withMeta) {
        ListSubscriptionRequest request = new ListSubscriptionRequest();
        ListSubscriptionAction action = new ListSubscriptionAction(serviceClient, credentials, endpoint);
        request.setRequestPath(topicURL + "/" + MNSConstants.SUBSRIPTION);
        request.setMarker(marker);
        request.setPrefix(prefix);
        request.setMaxRet(retNumber);
        request.setWithMeta(withMeta);
        return action.execute(request);
    }

    /**
     * list topic subscription
     *
     * @param prefix,    subscription name prefis
     * @param marker,    subscription start marker
     * @param retNumber, return number
     * @return SubscriptionMeta list
     */
    public PagingListResult<SubscriptionMeta> listSubscriptions(String prefix, String marker, Integer retNumber) {
        return listSubscriptions(prefix, marker, retNumber, true);
    }

    /**
     * list topic subscription
     *
     * @param prefix,    subscription name prefis
     * @param marker,    subscription start marker
     * @param retNumber, return number
     * @return subscription url list
     */
    public PagingListResult<String> listSubscriptionUrls(String prefix, String marker, Integer retNumber) {
        PagingListResult<SubscriptionMeta> list = listSubscriptions(prefix, marker, retNumber, false);
        PagingListResult<String> result = null;
        if (list != null && list.getResult() != null) {
            List<String> tmp = new ArrayList<String>();
            for (SubscriptionMeta meta : list.getResult()) {
                tmp.add(meta.getSubscriptionURL());
            }
            result = new PagingListResult<String>();
            result.setResult(tmp);
            result.setMarker(list.getMarker());
        }
        return result;
    }

    /**
     * generate queue endpoint for subscription
     */
    public String generateQueueEndpoint(String queueName) {
        return "acs:mns:" + this.region + ":" + this.accountId + ":queues/" + queueName;
    }

    /**
     * generate mail endpoint for subscription
     */
    public String generateMailEndpoint(String mailAddress) {
        return "mail:directmail:" + mailAddress;
    }

    /**
     * publish message to topic
     *
     * @param msg, message，这里可以使用RawTopicMessage跟Base64TopicMessage作为向服务发消息的结构。
     *             Base64TopicMessage会将消息体进行base64编码。
     *             RawTopicMessage发送的数据是明文可读的串，我们不做任何改动。
     *             如果你是用Base64TopicMessage发送消息的，那么在endpoint端收到消息时，
     *             需要额外做一次base64解码，才能跟你发送的消息相匹配。
     *
     *        如果接收端包含了邮箱,请使用publishMessage(RawTopicMessage, MessageAttributes)
     * @return message
     */
    public TopicMessage publishMessage(TopicMessage msg) {
        PublishMessageRequest request = new PublishMessageRequest();
        request.setMessage(msg);
        PublishMessageAction action = new PublishMessageAction(serviceClient, credentials, endpoint);
        request.setRequestPath(topicURL + "/" + MNSConstants.LOCATION_MESSAGES);
        return action.execute(request);
    }

    /**
     * publish raw message to topic
     *
     * @param msg, RawTopicMessage发送的数据是明文可读的串，我们不做任何改动。
     *
     *             如果接收端是邮箱,那么这里的msg就是邮件正文.
     * @param messageAttributes 如果希望被推送到邮箱,那么attributes需要包含发送邮件所必须的几个属性
     * @return message
     */
    public TopicMessage publishMessage(RawTopicMessage msg, MessageAttributes messageAttributes) {
        AttributesValidationResult result = messageAttributes.validate();
        if (!result.isSuccess())
        {
            throw new ClientException(result.getMessage());
        }

        PublishMessageRequest request = new PublishMessageRequest();
        request.setMessage(msg);
        request.setMessageAttributes(messageAttributes);
        PublishMessageAction action = new PublishMessageAction(serviceClient, credentials, endpoint);
        request.setRequestPath(topicURL + "/" + MNSConstants.LOCATION_MESSAGES);
        return action.execute(request);
    }

    /**
     * async  publish message, we will do base64 encode for message body before publish it to MNS server.
     * so, when you receive this message, you should do base64 decode before use it.
     *
     * @param msg,      message，这里可以使用RawTopicMessage跟Base64TopicMessage作为向服务发消息的结构。
     *                  但我们推荐使用Base64TopicMessage，它会将消息体进行base64编码后再发送数据。
     *                  RawTopicMessage发送的数据是明文可读的串，我们不做任何改动。
     *                  如果你是用Base64TopicMessage发送消息的，那么在endpoint端收到的消息，
     *                  需要额外做一次base64解码，才能跟你发送的消息相匹配。
     * @param callback, user callback object
     * @return AsyncResult, you can wait result by AsyncResult if you want to do this
     */
    public AsyncResult<TopicMessage> asyncPublishMessage(TopicMessage msg, AsyncCallback<TopicMessage> callback) {
        PublishMessageRequest request = new PublishMessageRequest();
        request.setMessage(msg);
        PublishMessageAction action = new PublishMessageAction(serviceClient, credentials, endpoint);
        request.setRequestPath(topicURL + "/" + MNSConstants.LOCATION_MESSAGES);
        return action.execute(request, callback);
    }
}
