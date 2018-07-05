package com.iscas.component.mqtt;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.google.gson.Gson;
import com.iscas.component.entity.ConnectionEntity;
import com.iscas.component.entity.MsgBodyEntity;
import com.iscas.component.entity.MsgEntity;
import com.iscas.component.entity.MsgSubEntity;
import com.iscas.component.services.host_computer.HostComputerService;
import com.iscas.component.services.host_computer.bean.HostComputer;
import com.iscas.component.services.hotel_extension.HotelExtensionService;
import com.iscas.component.services.hotel_extension.bean.HotelExtension;
import com.iscas.component.services.take_power.TakePowerService;
import com.iscas.component.services.take_power.bean.TakePower;
import com.iscas.component.util.Base64Util;
import com.iscas.component.util.ListSortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/11/30
 * Time: 11:38
 */
@Component
public class MqttServer {
    private static Logger log = LoggerFactory.getLogger(MqttServer.class);
    @Autowired
    private AliConfig aliConfig;
    @Autowired
    private Gson gson;
    @Autowired
    private HotelExtensionService hotelExtensionService;
    @Autowired
    private TakePowerService takePowerService;
    @Autowired
    private HostComputerService hostComputerService;

    /**
     * 实例化接收信息
     */
    public void init() {
        //创建线程池数量
        ExecutorService pool = Executors.newFixedThreadPool(aliConfig.getThreadNum());
        CloudAccount account = new CloudAccount(
                aliConfig.getAccessKeyId(),
                aliConfig.getAccessKeySecret(),
                aliConfig.getAccountEndpoint());
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        final CloudQueue queue = client.getQueueRef(aliConfig.getQueueRef());
        // 获取消息
        while (true) {
            final Message popMsg = queue.popMessage(10); //长轮询等待时间为10秒
            if (popMsg != null) {
                pool.submit(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("PopMessage Body: "
                                    + Base64Util.decode(popMsg.getMessageBodyAsRawString()));//获取原始消息
                            MsgBodyEntity msgBodyEntity = gson.fromJson(Base64Util.decode(popMsg.getMessageBodyAsRawString()), MsgBodyEntity.class);
                            // MsgBodyEntity msgBodyEntity = gson.fromJson("{\"payload\":\"eyJtc2ciOlt7ImFjdGlvblR5cGUiOiIxIiwiYWN0aW9uVGltZSI6IjE4MDEwMjE2NTYyNyIsImNhcmRJZCI6IjJiNTNlNzdkIiwibWFjSWQiOiJjODY3MjJhNGFlMzAiLCJtc2dJZCI6IjEwNiJ9XSwidHlwZSI6IjEiLCJpbWVpIjoiODYyOTkxNTI4MjYxODgzIiwiaG9zdE1hYyI6ImU4NzY0NWE0YWUzMCJ9\",\"messagetype\":\"upload\",\"topic\":\"/BjPoNlKQZ69/862991528261883/update\",\"messageid\":948115769901289472,\"timestamp\":1514883389}", MsgBodyEntity.class);
                            if (null != msgBodyEntity) {
                                queue.deleteMessage(popMsg.getReceiptHandle()); //从队列中删除消息
                                msgHandle(msgBodyEntity);
                            } else {
                                log.info("数据为空，请查看是否异常=============");
                            }
                            //手动回收
                        } catch (Exception e) {
                            log.info("数据处理异常====================：" + e.toString());
                            e.printStackTrace();
                        } finally {
                            System.gc();
                        }
                    }
                });

            } else {
                System.out.println("Continuing");
            }
        }
    }

    /**
     * 消息处理
     *
     * @param msgBodyEntity
     */
    public void msgHandle(MsgBodyEntity msgBodyEntity) {
        String messagetype = msgBodyEntity.getMessagetype();
        if (null != messagetype && !"".equals(messagetype)) {
            String payload = msgBodyEntity.getPayload();
            String tt = Base64Util.decode(payload);
            System.out.println("payload is :" + tt);
            //上线和掉线解析
            if (messagetype.equals("status")) {
                ConnectionEntity connectionEntity = gson.fromJson(Base64Util.decode(payload), ConnectionEntity.class);
                String status = connectionEntity.getStatus();
                HotelExtension he = new HotelExtension();
                he.setDeviceName(connectionEntity.getDeviceName());
                //总机上线
                if (status.equals("online")) {
                    //查看房间情况更新上线状况（默认全在线）
                    he.setStatus("1");
                }
                //总机掉线
                else if (status.equals("offline")) {
                    //查看房间情况更新下线情况
                    he.setStatus("0");
                    TakePower takePower = new TakePower();
                    takePower.setDeviceName(connectionEntity.getDeviceName());
                    int upIds = takePowerService.updateHostAll(takePower);
                    //补上分机离线时间

                }
                //主机上下线数据备份一下
                HostComputer hostComputer = new HostComputer();
                hostComputer.setActionTime(connectionEntity.getTime());
                hostComputer.setClientIp(connectionEntity.getClientIp());
                hostComputer.setDeviceName(connectionEntity.getDeviceName());
                hostComputer.setProductKey(connectionEntity.getProductKey());
                hostComputer.setStatus(connectionEntity.getStatus());
                hostComputerService.insert(hostComputer);
                int updates = hotelExtensionService.updateByDeviceName(he);

                if (updates > 0) {
                    log.info("更新酒店房间状态成功，更新条数为：" + updates + " ,DeviceName 为：" + connectionEntity.getDeviceName());
                } else {
                    log.info("更新失败DeviceName 为：" + connectionEntity.getDeviceName());
                }
            }
            //上传数据解析
            else if (messagetype.equals("upload")) {
                String t = Base64Util.decode(payload);
                MsgEntity msgEntity = gson.fromJson(Base64Util.decode(payload), MsgEntity.class);
                int type = msgEntity.getType();
                //1：插拔卡
                if (type == 1) {
                    List<MsgSubEntity> msgList = msgEntity.getMsg();
                    if (null != msgList && msgList.size() > 0) {
                        ListSortUtil<MsgSubEntity> listSortUtil = new ListSortUtil<MsgSubEntity>();
                        //排序
                        listSortUtil.sort(msgList, "msgId", "desc");
                        for (Object object : msgList) {
                            Map mse = (Map) object;
                            String actionTypeInt = mse.get("actionType").toString();
                            //插卡处理
                            if (actionTypeInt.equals("1.0")) {
                                TakePower tp = new TakePower();
                                //  tp.setDeviceName(msgEntity.getHostMac());
                                tp.setDeviceName(msgEntity.getImei());
                                tp.setStartTime(getFormatDate(String.valueOf(mse.get("actionTime"))));
                                tp.setMacId(String.valueOf(mse.get("macId")));
                                //   tp.setCardId(msgEntity.getImei());
                                tp.setCardId(String.valueOf(mse.get("cardId")));
                                tp.setHostMac(msgEntity.getHostMac());
                                int id = takePowerService.insert(tp);
                                log.info("插入数据成功，HostMac为：" + msgEntity.getHostMac() + " ,插入的Id 为：" + id);
                            }
                            //拔卡处理
                            else if (actionTypeInt.equals("0.0")) {
                                TakePower tp = new TakePower();
                                // tp.setDeviceName(msgEntity.getImei());
                                tp.setHostMac(msgEntity.getHostMac());
                                tp.setMacId(String.valueOf(mse.get("macId")));
                                tp.setEndTime(getFormatDate(String.valueOf(mse.get("actionTime"))));
                                int id = takePowerService.updateTopOne(tp);
                                if (id > 0) {
                                    log.info("更新成功" + " ,更新ID：" + id);
                                } else {
                                    log.info("更新失败");
                                }
                            }
                        }
                    } else {
                        log.info("查拔卡信息为空，确认信息是否有误 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                }
                // 2：上下线回报
                else if (type == 2) {
                    List<MsgSubEntity> msgList = msgEntity.getMsg();
                    if (null != msgList && msgList.size() > 0) {
                        HotelExtension hotelExtension = new HotelExtension();
                        for (Object object : msgList) {
                            Map mse = (Map) object;
                            hotelExtension.setStatus(String.valueOf(mse.get("actionType")));
                            // hotelExtension.setDeviceName(String.valueOf(mse.get("hostMac")));
                            hotelExtension.setDeviceName(msgEntity.getImei());
                            hotelExtension.setMacId(String.valueOf(mse.get("macId")));
                            hotelExtension.setActionTime(getFormatDate(String.valueOf(mse.get("actionTime"))));
                            //处理离线信息
                            int ids = hotelExtensionService.updateByDeviceName(hotelExtension);
                            //更新插拔卡信息表
                            TakePower takePower = new TakePower();
                            takePower.setHostMac(msgEntity.getHostMac());
                            takePower.setMacId(String.valueOf(mse.get("macId")));
                            int upIds = takePowerService.updateOneFast(takePower);
                         /*   if (ids > 0) {
                                log.info("更新酒店房间状态成功，更新条数为：" + ids + " ,hostMac 为：" + mse.get("hostMac"));
                            } else {
                                log.info("更新失败hostMac 为：" + mse.get("hostMac"));
                            }*/
                        }
                    } else {
                        log.info("上下线回报信息为空，请核实>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    }
                } //注册
                else if (type == 3) {
                    // ConnectionEntity connectionEntity = gson.fromJson(Base64Util.decode(payload), ConnectionEntity.class);
                    List<MsgSubEntity> msgList = msgEntity.getMsg();
                    if (null != msgList && msgList.size() > 0) {
                        ListSortUtil<MsgSubEntity> listSortUtil = new ListSortUtil<MsgSubEntity>();
                        //排序
                        listSortUtil.sort(msgList, "msgId", "desc");
                        for (Object object : msgList) {
                            Map mse = (Map) object;
                            HotelExtension hotelExtension = new HotelExtension();
                            hotelExtension.setMacId(String.valueOf(mse.get("macId")));
                            hotelExtension.setDeviceName(msgEntity.getImei());
                            //   hotelExtension.setActionTime(mse);
                            hotelExtension.setLatdm(msgEntity.getLatdm());
                            hotelExtension.setLngdm(msgEntity.getLngdm());
                            //   hotelExtension.setSubMacId(String.valueOf(mse.get("macId")));
                            hotelExtension.setCardId(String.valueOf(mse.get("cardId")));
                            hotelExtension.setHostMac(msgEntity.getHostMac());
                            hotelExtension.setActionTime(getFormatDate(String.valueOf(mse.get("actionTime"))));
                            hotelExtensionService.insert(hotelExtension);
                            log.info("注册成功,HostMac 为：" + msgEntity.getHostMac() + ", 分机macId：" + mse.get("macId"));
                        }
                    }
                }
            }
        } else {
            log.info("消息类型为空无法解析===============");
        }
    }

    /**
     * 时间格式转换
     *
     * @param date
     * @return
     */
    public String getFormatDate(String date) {
        StringBuffer stringBuffer = new StringBuffer(date);
        for (int index = 2; index < stringBuffer.length(); index += 3) {
            stringBuffer.insert(index, ',');
        }
        String[] array = stringBuffer.toString().split(",");
        return "20" + array[0] + "-" + array[1] + "-" + array[2] + " " + array[3] + ":" + array[4] + ":" + array[5];
    }
}
