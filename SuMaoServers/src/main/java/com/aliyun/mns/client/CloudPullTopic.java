/*
 * Class for CloudTopicForPull which provides function to broadcast message to queue by given topic
 * and queue name list. The consumer only receive message from queue and do not need expose it's address.
 */

package com.aliyun.mns.client;

import java.util.List;
import java.util.Vector;

import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.SubscriptionMeta.NotifyContentFormat;
import com.aliyun.mns.model.TopicMessage;

public class CloudPullTopic {
    private CloudTopic rawTopic;
    private Vector<String> queueNameList;
    private Vector<CloudQueue> queueList;
    private String QUEUE_SUB_NAME_PREFIX;

    /*
     * use queues in the queueNameList to subscribe the topic.
     */
    private void subscribe(Vector<String> queueNameList)
    {
        for (int i = 0; i < queueNameList.size(); i++)
        {
            String queueName = queueNameList.get(i);
            String queueEndpoint = this.rawTopic.generateQueueEndpoint(queueName);
            String subName = QUEUE_SUB_NAME_PREFIX + queueName;
            SubscriptionMeta subMeta = new SubscriptionMeta();
            subMeta.setSubscriptionName(subName);
            subMeta.setNotifyContentFormat(NotifyContentFormat.SIMPLIFIED);
            subMeta.setNotifyStrategy(SubscriptionMeta.NotifyStrategy.EXPONENTIAL_DECAY_RETRY);
            subMeta.setEndpoint(queueEndpoint);
            this.rawTopic.subscribe(subMeta);
        }
    }

    /*
     * Constructor.
     */
    public CloudPullTopic(CloudTopic rawTopic, Vector<String> queueNameList, Vector<CloudQueue> queueList)
    {
        this.rawTopic = rawTopic;
        this.queueNameList = queueNameList;
        this.queueList = queueList;
        this.QUEUE_SUB_NAME_PREFIX = "sub-for-queue-";

        this.subscribe(this.queueNameList);
    }

    /*
     * Publish message to topic.
     */
    public TopicMessage publishMessage(TopicMessage msg) {
        return this.rawTopic.publishMessage(msg);
    }

    /*
     * get the raw topic.
     */
    public CloudTopic getRawTopic()
    {
        return this.rawTopic;
    }

    /*
     * delete the raw topic and related queues;
     */
    public void delete()
    {
        this.delete(true);
    }
    
    /*
     * delete the raw topic and delete related queues if need
     */
    public void delete(boolean needDeleteQueues)
    {
        this.rawTopic.delete();

        if(needDeleteQueues){
            for (int i = 0; i < this.queueList.size(); i++)
            {
                CloudQueue queue = queueList.get(i);
                queue.delete();
            }
        }
    }

    /*
     * get the queue name list.
     */
    public List<String> getQueueNameList()
    {
        return this.queueNameList;
    }

    /*
     * get the default subscription name for queue.
     */
    public String getQueueSubNamePrefix()
    {
        return this.QUEUE_SUB_NAME_PREFIX;
    }

    /*
     * set the default prefix of subscription name.
     */
    public void setQueueSubNamePrefix(String queueSubNamePrefix)
    {
        this.QUEUE_SUB_NAME_PREFIX = queueSubNamePrefix;
    }
}
