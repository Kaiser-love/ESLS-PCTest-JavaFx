package com.datagroup.eslstest.utils;

import com.datagroup.eslstest.netty.client.NettyClient;
import com.datagroup.eslstest.netty.command.CommandConstant;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public class NettyUtil {
    // 单例模式
    @Autowired
    private ExecutorService executorService;
    // Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。
    public String sendMessageWithRepeat(Channel channel, byte[] message, int time, int waitingTime){
        SocketChannelHelper.addWorkingChannel(channel.id().toString());
        String result = sendMessage(channel, message,waitingTime);
        for(int i=0;i<time-1;i++){
            if(result==null|| result.equals("失败")) {
                result = sendMessage(channel, message,waitingTime);
            }
            if(result!=null && (result.equals("成功") || result.equals("通讯超时"))) {
                SocketChannelHelper.removeWorkingChannel(channel.id().toString());
                return result;
            }
        }
        SocketChannelHelper.removeWorkingChannel(channel.id().toString());
        // 对路由器和对标签广播 结束唤醒不发超时
        if(!SocketChannelHelper.isBroadcastCommand(message) && !"成功".equals(result)) {
            byte[] overTimeMessage = getOverTimeMessage(message);
            sendMessage(channel, overTimeMessage, 100);
        }
        return "通讯"+time+"次超时";
    }
    public String sendMessage(Channel channel, byte[] message, int waitingTime) {
        try {
            //   ExecutorService executorService = Executors.newSingleThreadExecutor();
            NettyClient nettyClient = new NettyClient(channel, message);
            Future future = executorService.submit(nettyClient);
            long begin = System.currentTimeMillis();
            Integer commandWaitingTime = waitingTime;
            while(!future.isDone()){
                long end = System.currentTimeMillis();
                if((end - begin)> commandWaitingTime) {
                    if(!SocketChannelHelper.isBroadcastCommand(message)) {
                        log.info("失败--线程移除前（命令没有响应）:" + SocketChannelHelper.getMapSize());
                        SocketChannelHelper.removeMapWithKey(channel, message);
                        log.info("失败--线程移除后（命令没有响应）:" + SocketChannelHelper.getMapSize());
                    }
                    future.cancel(true);
                    return null;
                }
            }
            return future.get().toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static byte[] getOverTimeMessage(byte[] message){
        byte[] overTimeMessage = new byte[13];
        for(int i=0;i<8;i++)
            overTimeMessage[i] = message[i];
        overTimeMessage[2] = 0;
        overTimeMessage[3] = 9;
        overTimeMessage[8] = 0x01;
        overTimeMessage[9] = 0x03;
        overTimeMessage[10] = 0x02;
        overTimeMessage[11] = message[8];
        overTimeMessage[12] = message[9];
        return overTimeMessage;
    }
}
