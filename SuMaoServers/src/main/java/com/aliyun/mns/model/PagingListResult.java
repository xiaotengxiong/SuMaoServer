package com.aliyun.mns.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagingListResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5424497025080704627L;
    private String marker;
    private List<T> result = new ArrayList<T>();

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}