package com.datagroup.eslstest.serviceimpl;

import com.datagroup.eslstest.common.response.ResponseBean;
import com.datagroup.eslstest.entity.Router;
import com.datagroup.eslstest.utils.*;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * @author lenovo
 */
@Slf4j
@Component("AsyncServiceTask")
public class AsyncServiceTask {
    @Autowired
    private NettyUtil nettyUtil;
    @Async
    public ListenableFuture<Integer> sendMessageWithRepeat(Channel channel, byte[] message, Router router, long begin, int time) {
        log.info("-----向(路由器集合)发送命令线程-----");
        String result = nettyUtil.sendMessageWithRepeat(channel, message, time, 5100);
        int sucessNumber = 0;
        return new AsyncResult<>(sucessNumber);
    }
    public static void waitFreeRouter(Channel channel){
        if(channel==null) return;
        while(true) {
            //路由器不工作时退出循环
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!SocketChannelHelper.isWorking(channel.id().toString()))
                break;
        }
    }
}
