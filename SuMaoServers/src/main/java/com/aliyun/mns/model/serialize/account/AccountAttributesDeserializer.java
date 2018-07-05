package com.aliyun.mns.model.serialize.account;

import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.LOGGING_BUCKET_TAG;

public class AccountAttributesDeserializer extends XMLDeserializer<AccountAttributes> {

    @Override
    public AccountAttributes deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);

        Element root = doc.getDocumentElement();

        AccountAttributes accountAttributes = new AccountAttributes();

        String loggingBucket = safeGetElementContent(root, LOGGING_BUCKET_TAG, null);
        accountAttributes.setLoggingBucket(loggingBucket);
        return accountAttributes;
    }
}
