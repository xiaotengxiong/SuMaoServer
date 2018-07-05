package com.aliyun.mns.model;

public class ListObjectRequest extends AbstractRequest {
    private String prefix;
    private String marker;
    private Integer maxRet;
    private Boolean withMeta;

    public ListObjectRequest() {
        this.prefix = null;
        this.marker = null;
        this.maxRet = null;
        this.withMeta = null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Integer getMaxRet() {
        return maxRet;
    }

    public void setMaxRet(Integer maxRet) {
        this.maxRet = maxRet;
    }

    public Boolean getWithMeta() {
        return withMeta;
    }

    public void setWithMeta(Boolean withMeta) {
        this.withMeta = withMeta;
    }


}
