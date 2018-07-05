package com.aliyun.mns.client;

/**
 * @param <T>
 */
public interface AsyncCallback<T> {
    /**
     * Async callback handler at successfully return.
     *
     * @param result
     */
    public void onSuccess(T result);

    /**
     * Async callback handler at failed return.
     *
     * @param ex
     */
    public void onFail(Exception ex);
}
