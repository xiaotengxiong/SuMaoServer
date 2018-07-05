package com.aliyun.mns.client;

import com.aliyun.mns.client.impl.account.GetAccountAttributesAction;
import com.aliyun.mns.client.impl.account.SetAccountAttributesAction;
import com.aliyun.mns.client.impl.queue.CreateQueueAction;
import com.aliyun.mns.client.impl.queue.ListQueueAction;
import com.aliyun.mns.client.impl.topic.ListTopicAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ClientConfiguration;
import com.aliyun.mns.common.http.DefaultServiceClient;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.request.account.GetAccountAttributesRequest;
import com.aliyun.mns.model.request.account.SetAccountAttributesRequest;
import com.aliyun.mns.model.request.queue.CreateQueueRequest;
import com.aliyun.mns.model.request.queue.GetQueueAttrRequest;
import com.aliyun.mns.model.request.queue.ListQueueRequest;
import com.aliyun.mns.model.request.topic.ListTopicRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DefaultMNSClient implements MNSClient {
    private static Log log = LogFactory.getLog(DefaultMNSClient.class);
    private static String OPERATION_LOG_QUEUE_POSTFIX = "-operation-log-queue";

    // MNS 服务的地址。
    private URI endpoint;

    // 访问MNS服务的client
    private ServiceClient serviceClient;

    // 用户身份信息。
    private ServiceCredentials credentials = new ServiceCredentials();

    /**
     * 使用指定的MNS Endpoint构造一个新的{@link MNSClient}对象。
     *
     * @param endpoint  MNS服务的Endpoint。
     * @param accessId  访问MNS的Access ID。
     * @param accessKey 访问MNS的Access Key。
     */
    public DefaultMNSClient(String endpoint, String accessId, String accessKey) {
        this(endpoint, accessId, accessKey, null);
    }

    /**
     * 使用指定的MNS Endpoint和配置构造一个新的{@link MNSClient}对象。
     *
     * @param endpoint  MNS服务的Endpoint。
     * @param accessId  访问MNS的Access ID。
     * @param accessKey 访问MNS的Access Key。
     * @param config    客户端配置 {@link ClientConfiguration}。
     */
    public DefaultMNSClient(String endpoint, String accessId, String accessKey,
                            ClientConfiguration config) {
        setEndpoint(endpoint);
        this.credentials = new ServiceCredentials(accessId, accessKey);
        if (config == null) {
            config = new ClientConfiguration();
            config.setExceptContinue(false);
        }

        this.serviceClient = new DefaultServiceClient(config);

        if (log.isDebugEnabled()) {
            log.debug("initiated MNSClientImpl,accessId=" + accessId
                    + ",accessKey=" + accessKey + ", endpoint=" + endpoint);
        }
    }

    protected DefaultMNSClient(ServiceCredentials credentials,
                               ServiceClient serviceClient, String endpoint) {
        this.serviceClient = serviceClient;
        this.credentials = credentials;
        setEndpoint(endpoint);
    }

    @Override
    public void close() {
        if (serviceClient != null) {
            this.serviceClient.close();
        }
    }

    @Override
    public void open() {
        if (serviceClient != null) {
            this.serviceClient.open();
        }
    }

    @Override
    public boolean isOpen() {
        if (serviceClient != null) {
            return this.serviceClient.isOpen();
        } else {
            return false;
        }
    }

    @Override
    public void SetAccountAttributes(AccountAttributes accountAttributes) throws ServiceException, ClientException {
        SetAccountAttributesAction action = new SetAccountAttributesAction(serviceClient,
                credentials, endpoint);
        SetAccountAttributesRequest request = new SetAccountAttributesRequest();
        request.setAccountAttributes(accountAttributes);
        action.execute(request);
    }

    @Override
    public AccountAttributes GetAccountAttributes() throws ServiceException, ClientException {
        GetAccountAttributesAction action = new GetAccountAttributesAction(serviceClient,
                credentials, endpoint);
        GetAccountAttributesRequest request = new GetAccountAttributesRequest();
        return action.execute(request);
    }

    @Override
    public CloudQueue getQueueRef(String queueName) {
        return new CloudQueue(queueName, this.serviceClient, this.credentials,
                this.endpoint);
    }

    private void setEndpoint(String endpoint) throws IllegalArgumentException {
        this.endpoint = Utils.getHttpURI(endpoint);
    }

    public void addHeader(String key, String value) {
        serviceClient.addHeader(key, value);
    }

    public AsyncResult<String> createQueueAsync(QueueMeta queueMeta,
                                                AsyncCallback<String> callback) {
        CreateQueueAction action = new CreateQueueAction(serviceClient,
                credentials, endpoint);
        CreateQueueRequest request = new CreateQueueRequest();
        request.setRequestPath(MNSConstants.QUEUE_PREFIX + queueMeta.getQueueName());
        request.setQueueMeta(queueMeta);
        return action.execute(request, callback);
    }

    /**
     * 创建队列
     *
     * @param queueMeta 队列属性
     * @return CloudQueue object
     * @throws ClientException
     * @throws ServiceException
     */
    public CloudQueue createQueue(QueueMeta queueMeta) throws ClientException,
            ServiceException {
        CloudQueue queue = new CloudQueue(queueMeta.getQueueName(), this.serviceClient, this.credentials, this.endpoint);
        queue.create(queueMeta);
        return queue;
    }
    
    /**
     * Get reference to a transaction queue object by given queue name.
     * note: it will not do create queue operation and assumes the queue
     * has already be create properly.
     * 
     * @param queueName
     * @return TransactionQueue object
     */
    @Override
    public TransactionQueue getTransQueueRef(String queueName, TransactionChecker checker) {
        CloudQueue queue = new CloudQueue(queueName, this.serviceClient, this.credentials,
                this.endpoint);
        CloudQueue opLogQueue = new CloudQueue(queueName + OPERATION_LOG_QUEUE_POSTFIX, this.serviceClient, this.credentials,
                this.endpoint);
        return new TransactionQueue(queue, opLogQueue, checker, TransactionQueue.DEFAULT_LIFE_TIME_IN_SECONDS,
            TransactionQueue.DEFAULT_DELAY＿TIME_IN_SECONDS);
    }

    /**
     * Creates transaction queue.
     * 
     * @param queueMeta queue properties.
     * @param checker TransactionChecker to check the transaction message status.
     * @param lifeTime message life time
     * @param delayTime message delay time
     * @throws ClientException
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta, TransactionChecker checker, long lifeTime, long delayTime) throws ClientException,
           ServiceException {
        if (delayTime <= 0 || lifeTime <= 0)
        {
            throw new IllegalArgumentException("delayTime(" + delayTime 
                    + ") or lifetime(" + lifeTime +") should be bigger than 0");
        }

        CloudQueue queue = new CloudQueue(queueMeta.getQueueName(), this.serviceClient, this.credentials, this.endpoint);
        queueMeta.setMessageRetentionPeriod(lifeTime);
        queueMeta.setDelaySeconds(delayTime);
        queue.create(queueMeta);

        //create operation log queue.
        QueueMeta opLogQueueMeta = new QueueMeta();
        opLogQueueMeta.setQueueName(queueMeta.getQueueName() + OPERATION_LOG_QUEUE_POSTFIX);
        opLogQueueMeta.setPollingWaitSeconds(queueMeta.getPollingWaitSeconds());

        CloudQueue opLogQueue = new CloudQueue(opLogQueueMeta.getQueueName(),
                this.serviceClient, this.credentials, this.endpoint);
        opLogQueue.create(opLogQueueMeta);

        TransactionQueue transQueue = new TransactionQueue(queue, opLogQueue, checker, lifeTime, delayTime);
        return transQueue;
    }

    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param queueMeta queue properties.
     * @param checker TransactionChecker to check the transaction message status.
     * @throws ClientException
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta, TransactionChecker checker) throws ClientException,
           ServiceException {
        return this.createTransQueue(queueMeta, checker, TransactionQueue.DEFAULT_LIFE_TIME_IN_SECONDS,
                TransactionQueue.DEFAULT_DELAY＿TIME_IN_SECONDS);
    }

    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param queueMeta queue properties.
     * @param checker TransactionChecker to check the transaction message status.
     * @throws ClientException
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta) throws ClientException,
           ServiceException {
        return this.createTransQueue(queueMeta, null, TransactionQueue.DEFAULT_LIFE_TIME_IN_SECONDS,
                TransactionQueue.DEFAULT_DELAY＿TIME_IN_SECONDS);
    }
    
    /**
     * Creates topic for pull.
     *
     * @param topicMeta topic properties.
     * @param queueNameList the queue name list which are going to be endpoint of new created topic.
     * @param needCreateQueue flag to indicate that if we need to create the queue in the queueNameList.
     * @param queueMetaTemplate the queueMeta template used to create queues in queueNamelist if need.
     * @throws ClientException,ServiceException,IllegalArgumentException
     */
    public CloudPullTopic createPullTopic(TopicMeta topicMeta, Vector<String> queueNameList, boolean needCreateQueue, QueueMeta queueMetaTemplate)
        throws ClientException, ServiceException
    {
        if(queueNameList == null || queueNameList.size() <= 0)
        {
            throw new IllegalArgumentException("queueNameList should not be null or empty.");
        }
        Vector<CloudQueue> queueList = new Vector<CloudQueue>();
        if (needCreateQueue)
        {
            QueueMeta queueMeta = null;
            if(queueMetaTemplate != null)
            {
                queueMeta = queueMetaTemplate;
            }else
            {
                queueMeta = new QueueMeta();
                queueMeta.setPollingWaitSeconds(30);
                //add some default settings;
            }
            for(int i = 0; i < queueNameList.size(); i++)
            {
                String queueName = queueNameList.get(i);
                queueMeta.setQueueName(queueName);
                CloudQueue queue = new CloudQueue(queueName, this.serviceClient, this.credentials, this.endpoint);
                queue.create(queueMeta);
                queueList.add(queue);
            }
        }
        else
        {
            for(int i = 0; i < queueNameList.size(); i++)
            {
                String queueName = queueNameList.get(i);
                CloudQueue queue = new CloudQueue(queueName, this.serviceClient, this.credentials,
                        this.endpoint);
                queueList.add(queue);
            }
        }
        CloudTopic rawTopic = this.createTopic(topicMeta);
        return new CloudPullTopic(rawTopic, queueNameList, queueList);
    }

    /**
     * Creates topic for pull.
     *
     * @param topicMeta topic properties.
     * @param queueNameList the queue name list which are going to be endpoint of new created topic.
     * @throws ClientException,ServiceException,IllegalArgumentException
     */
    public CloudPullTopic createPullTopic(TopicMeta topicMeta, Vector<String> queueNameList) throws ClientException, ServiceException
    {
        return this.createPullTopic(topicMeta, queueNameList, false, null);
    }

    /**
     * 列举队列
     *
     * @param prefix    队列名前缀
     * @param marker    列举的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return 返回队列URL列表及marker
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<String> listQueueURL(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException {
        PagingListResult<String> results = new PagingListResult<String>();
        PagingListResult<QueueMeta> list = this.listQueue(prefix, marker,
                retNumber, false);
        if (list != null && list.getResult() != null) {
            List<String> queues = new ArrayList<String>();
            for (QueueMeta meta : list.getResult()) {
                queues.add(meta.getQueueURL());
            }
            if (list.getMarker() != null) {
                results.setMarker(list.getMarker());
            }
            results.setResult(queues);
            return results;
        }
        return results;
    }

    /**
     * 列举队列
     *
     * @param prefix    队列名前缀
     * @param marker    列举的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return 返回队列属性列表及marker
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<QueueMeta> listQueue(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException {
        return this.listQueue(prefix, marker, retNumber, true);
    }

    private PagingListResult<QueueMeta> listQueue(String prefix, String marker,
                                                  Integer retNumber, boolean withMeta) throws ClientException,
            ServiceException {
        ListQueueAction action = new ListQueueAction(serviceClient,
                credentials, endpoint);
        ListQueueRequest request = new ListQueueRequest();
        request.setRequestPath("/queues");
        request.setMarker(marker);
        request.setPrefix(prefix);
        request.setMaxRet(retNumber);
        request.setWithMeta(withMeta);
        return action.execute(request);
    }

    /**
     * 根据Topic的URL创建CloudTopic对象，后于后续对改对象的创建、查询等
     *
     * @param topicName topic name
     * @return CloudTpoic对象
     */
    public CloudTopic getTopicRef(String topicName) {
        return new CloudTopic(topicName, this.serviceClient, this.credentials, this.endpoint);
    }

    /**
     * 根据Topic的meta数据，创建CloudTopic对象，用于后续的消息发送等
     *
     * @param meta CloudTpoic的meta数据
     * @return CloudTpoic对象
     */
    public CloudTopic createTopic(TopicMeta meta) {
        CloudTopic topic = new CloudTopic(meta.getTopicName(), this.serviceClient, this.credentials, this.endpoint);
        topic.create(meta);
        return topic;
    }

    /**
     * @param prefix    topic name前缀
     * @param marker    topic的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return topic meta列表及marker数据
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<TopicMeta> listTopic(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException {
        return listTopic(prefix, marker, retNumber, true);
    }

    private PagingListResult<TopicMeta> listTopic(String prefix, String marker,
                                                  Integer retNumber, boolean withMeta) throws ClientException,
            ServiceException {
        ListTopicAction action = new ListTopicAction(this.serviceClient, this.credentials, this.endpoint);
        ListTopicRequest request = new ListTopicRequest();
        request.setRequestPath(MNSConstants.TPOIC_PREFIX.split("/")[0]);
        request.setMarker(marker);
        request.setPrefix(prefix);
        request.setMaxRet(retNumber);
        if (withMeta) {
            request.setWithMeta(withMeta);
        }
        return action.execute(request);
    }

    /**
     * @param prefix    topic name前缀
     * @param marker    topic的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return topic url列表及marker数据
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<String> listTopicURL(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException {
        PagingListResult<TopicMeta> results = listTopic(prefix, marker, retNumber, false);
        PagingListResult<String> ret = new PagingListResult<String>();

        if (results != null) {
            List<String> topics = new ArrayList<String>();
            for (TopicMeta meta : results.getResult()) {
                topics.add(meta.getTopicURL());
            }

            ret.setResult(topics);
            ret.setMarker(results.getMarker());
        }
        return ret;
    }

}
