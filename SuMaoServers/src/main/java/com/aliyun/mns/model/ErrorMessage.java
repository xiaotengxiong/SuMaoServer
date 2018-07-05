package com.aliyun.mns.model;

import com.aliyun.mns.common.MNSConstants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
public class ErrorMessage {
    @XmlElement(name = "Code", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
    public String Code;

    @XmlElement(name = "Message", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
    public String Message;

    @XmlElement(name = "RequestId", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
    public String RequestId;

    @XmlElement(name = "Method", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
    public String Method;

    @XmlElement(name = "HostId", namespace = MNSConstants.DEFAULT_XML_NAMESPACE)
    public String HostId;
}
