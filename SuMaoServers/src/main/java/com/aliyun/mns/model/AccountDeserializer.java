package com.aliyun.mns.model;

import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.ACCOUNT_ID_TAG;

public class AccountDeserializer extends XMLDeserializer<String> {

    @Override
    public String deserialize(InputStream stream) throws Exception {

        Element root = null;

//		 byte[] bytes = new byte[1024];
//		 while(stream.read(bytes, 0, stream.available())>0){
//		 System.out.println(new String(bytes));
//		 }
        Document doc = getDocmentBuilder().parse(stream);

        root = doc.getDocumentElement();
        String accountId = safeGetElementContent(root, ACCOUNT_ID_TAG, null);
        return accountId;
    }

}
