package com.aliyun.mns.common.parser;

import com.aliyun.mns.common.utils.DateUtil;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class JaxbDateSerializer extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date date) throws Exception {
        return DateUtil.formatRfc822Date(date);
    }

    @Override
    public Date unmarshal(String date) throws Exception {
        return DateUtil.parseRfc822Date(date);
    }
}