package com.aliyun.mns.client;

import org.apache.http.HttpResponse;

import java.util.concurrent.Future;

/**
 * asynchronous call result
 *
 * @param <T> type of Result model
 */
public interface AsyncResult<T> {
    /**
     * @return the result aysnc call return,
     * not null meaning async call successful,
     * wait result until call end.
     */
    public T getResult();

    /**
     * @param timewait wait for result in 'timewait' milliseconds.
     * @return as async call result.
     */
    public T getResult(long timewait);

    /**
     * @return async call is successful(true) or not (false)
     */
    public boolean isSuccess();

    /**
     * @return async call exception
     */
    public Exception getException();

    /**
     * @param timewait wait for result in 'timewait' milliseconds.
    */
    public void setTimewait(long timewait);

    /**
     * @param future thre http client Future response
     */
    void setFuture(Future<HttpResponse> future);
}
