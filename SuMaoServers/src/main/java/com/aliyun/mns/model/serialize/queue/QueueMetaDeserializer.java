package com.aliyun.mns.model.serialize.queue;


import com.aliyun.mns.model.QueueMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;


public class QueueMetaDeserializer extends AbstractQueueMetaDeserializer<QueueMeta> {

    @Override
    public QueueMeta deserialize(InputStream stream) throws Exception {

//		 byte[] bytes = new byte[1024];
//			 while(stream.read(bytes, 0, stream.available())>0){
//			 System.out.println(new String(bytes));
//		 }
        Document doc = getDocmentBuilder().parse(stream);

        Element root = doc.getDocumentElement();
        return parseQueueMeta(root);
    }

}
