package com.aliyun.mns.client;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ClientConfiguration;
import com.aliyun.mns.common.http.DefaultServiceClient;
import com.aliyun.mns.common.http.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class CloudAccount {
    private static Log log = LogFactory.getLog(CloudAccount.class);

    private String accessId;
    private String accessKey;
    private String securityToken;
    private String accountEndpoint;

    // 访问MNS服务的client
    private ServiceClient serviceClient = null;

    // 用户身份信息。
    private ServiceCredentials credentials = new ServiceCredentials();
    private ClientConfiguration config;

    private MNSClient mnsClient;

    public CloudAccount(String accessId, String accessKey,
                        String accountEndpoint) {
        this(accessId, accessKey, accountEndpoint, "", null);
    }

    public CloudAccount(String accessId, String accessKey,
                        String accountEndpoint, String securityToken) {
        this(accessId, accessKey, accountEndpoint, securityToken, null);
    }

    public CloudAccount(String accessId, String accessKey,
                        String accountEndpoint, ClientConfiguration config) {
        this(accessId, accessKey, accountEndpoint, "", config);
    }

    public CloudAccount(String accessId, String accessKey,
                        String accountEndpoint, String securityToken, ClientConfiguration config) {
        this.accessId = accessId;
        this.accessKey = accessKey;
        this.accountEndpoint = Utils.getHttpURI(accountEndpoint).toString();
        this.securityToken = securityToken;
        this.config = config;

        init();
    }

    /**
     * @return
     * @throws ServiceException
     * @throws ClientException
     */
    public MNSClient getMNSClient() throws ServiceException, ClientException {
        if (mnsClient == null) {
            synchronized (this) {
                if (mnsClient == null) {
                    String accountEndpoint = getAccountEndpoint();
                    try {
                        serviceClient = new DefaultServiceClient(config);
                        mnsClient = new DefaultMNSClient(credentials, serviceClient,
                                accountEndpoint);
                    }
                    catch (Exception e) {
                        if (serviceClient != null) {
                            serviceClient.close();
                            serviceClient = null;
                        }
                        throw new ClientException(e);
                    }
                }
            }
        }
        return mnsClient;
    }

    private void init() {
        this.credentials = new ServiceCredentials(accessId, accessKey, securityToken);
        if (config == null) {
            config = new ClientConfiguration();
        }

        if (log.isDebugEnabled()) {
            log.debug("initiated CloudAccount, accessId=" + accessId + ",accessKey="
                    + accessKey + ", endpoint=" + accountEndpoint + " securityToken=" + securityToken);
        }
    }

    /**
     * @return account Endpoint
     */
    public String getAccountEndpoint() {
        return accountEndpoint;
    }

    public void setAccountEndpoint(String endpoint) {
        this.accountEndpoint = Utils.getHttpURI(endpoint).toString();
    }
}
