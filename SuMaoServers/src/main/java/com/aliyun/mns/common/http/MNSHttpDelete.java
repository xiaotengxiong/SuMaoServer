package com.aliyun.mns.common.http;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

@NotThreadSafe
public class MNSHttpDelete extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "DELETE";


    public MNSHttpDelete() {
        super();
    }

    public MNSHttpDelete(final URI uri) {
        super();
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public MNSHttpDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}