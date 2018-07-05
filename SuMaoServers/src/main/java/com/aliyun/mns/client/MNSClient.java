package com.aliyun.mns.client;

import java.util.Vector;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.TopicMeta;

public interface MNSClient {
    /**
     * 关闭 client
     */
    public void close();

    /**
     * 打开 client
     */
    public void open();

    /**
     * 检查client是否为打开状态
     *
     * @return true if client is open, else false
     */
    public boolean isOpen();

    /**
     * 设置当前用户的账号级别属性
     *
     * @param accountAttributes object
     * @throws ServiceException
     * @throws ClientException
     */
    public void SetAccountAttributes(AccountAttributes accountAttributes) throws ServiceException, ClientException;

    /**
     * 获取当前用户的账号级别属性
     *
     * @return AccountAttributes object
     * @throws ServiceException
     * @throws ClientException
     */
    public AccountAttributes GetAccountAttributes() throws ServiceException, ClientException;

    /**
     * 根据队列的URL创建CloudQueue对象，后于后续对改对象的创建、查询等
     *
     * @param queueName
     * @return CloudQueue object
     */

    public CloudQueue getQueueRef(String queueName);

    /**
     * 创建队列
     *
     * @param queue 队列属性
     * @return CloudQueue object
     * @throws ClientException
     * @throws ServiceException
     */
    public CloudQueue createQueue(QueueMeta queue) throws ClientException,
            ServiceException;

    /**
     * Get reference to a transaction queue object by given queue name.
     * note: it will not do create queue operation and assumes the queue
     * has already be create properly.
     * 
     * @param queueName
     * @param checker TransactionChecker to check the transaction message status.
     * @return TransactionQueue object
     */
    public TransactionQueue getTransQueueRef(String queueName, TransactionChecker checker);
    
    /**
     * Creates transaction queue.
     * 
     * @param queueMeta queue properties.
     * @param checker TransactionChecker to check the transaction message status.
     * @param lifeTime message life time in seconds
     * @param delayTime message delay time in seconds
     * @throws ClientException
     * 
     * note:delayTime should be bigger than lifetime to make sure that that message
     *  will not be visible to consumer by default.
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta, TransactionChecker checker, long lifeTime, long delayTime) throws ClientException,
           ServiceException;
    
    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param queueMeta queue properties.
     * @param checker TransactionChecker to check the transaction message status.
     * @throws ClientException
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta, TransactionChecker checker) throws ClientException, ServiceException;

    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param topicMeta topic properties.
     * @param queueNameList the queue name list which are going to be endpoint of new created topic.
     * @param needCreateQueue flag to indicate that if we need to create the queue in the queueNameList.
     * @param queueMetaTemplate the queueMeta template used to create queues in queueNamelist if need.
     * @throws ClientException
     */
    public CloudPullTopic createPullTopic(TopicMeta topicMeta, Vector<String> queueNameList, boolean needCreateQueue, QueueMeta queueMetaTemplate) throws ClientException, ServiceException;
    
    
    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param topicMeta topic properties.
     * @param queueNameList the queue name list which are going to be endpoint of new created topic.
     * @throws ClientException
     */
    public CloudPullTopic createPullTopic(TopicMeta topicMeta, Vector<String> queueNameList) throws ClientException, ServiceException;

    /**
     * Creates transaction queue with default message life time and delayTime
     *
     * @param queueMeta queue properties.
     * @throws ClientException
     */
    public TransactionQueue createTransQueue(QueueMeta queueMeta) throws ClientException, ServiceException;

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
    PagingListResult<QueueMeta> listQueue(String prefix, String marker,
                                          Integer retNumber) throws ClientException, ServiceException;

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
    PagingListResult<String> listQueueURL(String prefix, String marker,
                                          Integer retNumber) throws ClientException, ServiceException;


    /**
     * 根据Tpoic的URL创建CloudTopic对象，后于后续对改对象的创建、查询等
     *
     * @param topicName topic name
     * @return CloudTpoic对象
     */
    public CloudTopic getTopicRef(String topicName);

    /**
     * 根据Topic的meta数据，创建CloudTopic对象，用于后续的消息发送等
     *
     * @param meta CloudTpoic的meta数据
     * @return CloudTpoic对象
     */
    public CloudTopic createTopic(TopicMeta meta);

    /**
     * @param prefix    topic name前缀
     * @param marker    topic的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return topic meta列表及marker数据
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<TopicMeta> listTopic(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException;

    /**
     * @param prefix    topic name前缀
     * @param marker    topic的起始位置，""表示从第一个开始，也可以是前一次列举返回的marker
     * @param retNumber 最多返回的个数
     * @return topic url列表及marker数据
     * @throws ClientException
     * @throws ServiceException
     */
    public PagingListResult<String> listTopicURL(String prefix, String marker,
                                                 Integer retNumber) throws ClientException, ServiceException;
}
