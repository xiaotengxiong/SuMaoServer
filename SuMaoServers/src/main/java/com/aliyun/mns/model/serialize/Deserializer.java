package com.aliyun.mns.model.serialize;

import java.io.InputStream;

public interface Deserializer<T> {
    T deserialize(InputStream stream) throws Exception;
}
