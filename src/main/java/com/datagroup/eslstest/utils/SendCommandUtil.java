package com.datagroup.eslstest.utils;

import com.datagroup.eslstest.common.response.ResponseBean;
import com.datagroup.eslstest.entity.Router;
import com.datagroup.eslstest.entity.SystemVersionArgs;
import com.datagroup.eslstest.netty.command.CommandConstant;
import com.datagroup.eslstest.service.RouterService;
import com.datagroup.eslstest.serviceimpl.AsyncServiceTask;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SendCommandUtil {
    public static ResponseBean sendCommandWithRouters(List<Router> routers, String contentType, Integer messageType){
        int sum= routers.size();
        ArrayList<ListenableFuture<Integer>> listenableFutures = new ArrayList<>();
        byte[] content = CommandConstant.COMMAND_BYTE.get(contentType);
        byte[] message = CommandConstant.getBytesByType(null, content, messageType);
        try{
            for (Router router : routers) {
                // 路由器未连接或禁用
                if( router.getState()!=null && router.getState()==0) continue;
                Channel channel = SocketChannelHelper.getChannelByRouter(router);
                if(channel == null) continue;
                // 广播命令只发一次 广播命令没有响应
                ListenableFuture<Integer> result = ((AsyncServiceTask) SpringContextUtil.getBean("AsyncServiceTask")).sendMessageWithRepeat(channel, message,router,System.currentTimeMillis(),1);
                listenableFutures.add(result);
            }
        }
        catch (Exception e){
            log.info("SendCommandUtil - sendCommandWithRouters : "+e);
        }
        return new ResponseBean(sum, sum);
    }
    public static int waitAllThread(ArrayList<ListenableFuture<Integer>> listenableFutures){
        ArrayList<Integer> listenableFuturesResults = new ArrayList<>();
        //等待所有线程执行完在返回
        int sumBreak = 0,sumThreads = listenableFutures.size(),successNumber = 0;
        try {
            while (true) {
                //遍历所有线程 获得结果
                for (int i=0;i<listenableFutures.size();i++) {
                    ListenableFuture<Integer> item = listenableFutures.get(i);
                    if (item.isDone()) {
                        sumBreak++;
                        listenableFutures.remove(i);
                        log.info(item.toString() + "最终响应结果:" + item.get());
                        if (item.get()>0) {
                            listenableFuturesResults.add(item.get());
                            successNumber+=item.get();
                        }
                    }
                }
                if (sumBreak == sumThreads)
                    break;
            }
            return successNumber;
        }
        catch (Exception e){}
        return 0;
    }

    // 路由器测试
    // AP写入
    public static ResponseBean sendAPWrite(List<Router> routers,String barCode,String channelId,String hardVersion){
        int sum= routers.size(), successNumber;
        ArrayList<ListenableFuture<Integer>> listenableFutures = new ArrayList<>();
        try{
            for (Router router : routers) {
                Channel channel = SocketChannelHelper.getChannelByRouter(router);
                if(channel == null) continue;
                if(router.getIsWorking()==0 || (router.getState()!=null && router.getState()==0)) continue;
                // 更新路由器 发送设置命令
                byte[] message = new byte[22];
                message[0]=0x09;
                message[1]=0x02;
                message[2]=0x13;
                // 条码
                for(int i = 0 ;i<barCode.length();i++)
                    message[3+i] = (byte) barCode.charAt(i);
                // 通道号
                message[15] = Byte.parseByte(channelId);
                // 硬件版本号
                byte[] versionMessage = ByteUtil.getVersionMessage(hardVersion);
                for(int i=0;i<versionMessage.length;i++)
                    message[16+i] = versionMessage[i];
                RouterService routerService = (RouterService)SpringContextUtil.getBean("RouterService");
                router.setBarCode(barCode);
                router.setChannelId(channelId);
                router.setHardVersion(hardVersion);
                routerService.saveOne(router);
                SpringContextUtil.printBytes("AP写入信息：",message);
                byte[] realMessage = CommandConstant.getBytesByType(null, message, CommandConstant.COMMANDTYPE_ROUTER);
                ListenableFuture<Integer> result = ((AsyncServiceTask) SpringContextUtil.getBean("AsyncServiceTask")).sendMessageWithRepeat(channel, realMessage,router,System.currentTimeMillis(), Integer.valueOf(SystemVersionArgs.commandRepeatTime));
                listenableFutures.add(result);
            }
        }
        catch (Exception e){
            log.error("sendAPWrite:"+e);
        }
        //等待所有线程执行完在返回
        successNumber = waitAllThread(listenableFutures);
        return new ResponseBean(sum, successNumber);
    }
    // AP发送无线帧
    public static ResponseBean sendAPByChannelId(List<Router> routers,String channelId){
        int sum= routers.size(), successNumber;
        ArrayList<ListenableFuture<Integer>> listenableFutures = new ArrayList<>();
        try{
            for (Router router : routers) {
                Channel channel = SocketChannelHelper.getChannelByRouter(router);
                if(channel == null) continue;
                if(router.getIsWorking()==0 || (router.getState()!=null && router.getState()==0)) continue;
                // 更新路由器 发送设置命令
                byte[] message = new byte[4];
                message[0]=0x09;
                message[1]=0x6;
                message[2]=0x01;
                message[3]=Byte.parseByte(channelId);
                SpringContextUtil.printBytes("AP发送无线帧：",message);
                byte[] realMessage = CommandConstant.getBytesByType(null, message, CommandConstant.COMMANDTYPE_ROUTER);
                ListenableFuture<Integer> result = ((AsyncServiceTask) SpringContextUtil.getBean("AsyncServiceTask")).sendMessageWithRepeat(channel, realMessage,router,System.currentTimeMillis(), Integer.valueOf(SystemVersionArgs.commandRepeatTime));
                listenableFutures.add(result);
            }
        }
        catch (Exception e){
            log.error("sendAPByChannelId:"+e);
        }
        //等待所有线程执行完在返回
        successNumber = waitAllThread(listenableFutures);
        return new ResponseBean(sum, successNumber);
    }
    // AP接收无线帧
    public static ResponseBean sendAPReceiveByChannelId(List<Router> routers,String channelId){
        int sum= routers.size(), successNumber;
        ArrayList<ListenableFuture<Integer>> listenableFutures = new ArrayList<>();
        try{
            for (Router router : routers) {
                Channel channel = SocketChannelHelper.getChannelByRouter(router);
                if(channel == null) continue;
                if(router.getIsWorking()==0 || (router.getState()!=null && router.getState()==0)) continue;
                // 更新路由器 发送设置命令
                byte[] message = new byte[4];
                message[0]=0x09;
                message[1]=0x7;
                message[2]=0x01;
                message[3]=Byte.parseByte(channelId);
                SpringContextUtil.printBytes("AP发送接收无线帧：",message);
                byte[] realMessage = CommandConstant.getBytesByType(null, message, CommandConstant.COMMANDTYPE_ROUTER);
                ListenableFuture<Integer> result = ((AsyncServiceTask) SpringContextUtil.getBean("AsyncServiceTask")).sendMessageWithRepeat(channel, realMessage,router,System.currentTimeMillis(), Integer.valueOf(SystemVersionArgs.commandRepeatTime));
                listenableFutures.add(result);
            }
        }
        catch (Exception e){
            log.error("sendAPReceiveByChannelId:"+e);
        }
        //等待所有线程执行完在返回
        successNumber = waitAllThread(listenableFutures);
        return new ResponseBean(sum, successNumber);
    }
    // 设置当前目标服务器IP
    public static ResponseBean setLocalhostIp(List<Router> routers,String outNetIp){
        int sum= routers.size();
        ArrayList<ListenableFuture<Integer>> listenableFutures = new ArrayList<>();
        try{
            for (Router router : routers) {
                router.setOutNetIp(outNetIp);
                RouterService routerService = (RouterService)SpringContextUtil.getBean("RouterService");
                routerService.saveOne(router);
                Channel channel = SocketChannelHelper.getChannelByRouter(router);
                if(channel == null) continue;
                if(router.getIsWorking()==0 || (router.getState()!=null && router.getState()==0)) continue;
                byte[] message = new byte[7];
                message[0]=0x0A;
                message[1]=0x01;
                message[2]=4;
                String[] split = outNetIp.split("\\.");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);
                int c = Integer.parseInt(split[2]);
                int d  = Integer.parseInt(split[3]);
                message[3]= (byte)a;
                message[4]= (byte)b;
                message[5]= (byte)c;
                message[6]= (byte)d;
                SpringContextUtil.printBytes("设置当前目标服务器IP：",message);
                byte[] realMessage = CommandConstant.getBytesByType(null, message, CommandConstant.COMMANDTYPE_ROUTER);
                ListenableFuture<Integer> result = ((AsyncServiceTask) SpringContextUtil.getBean("AsyncServiceTask")).sendMessageWithRepeat(channel, realMessage,router,System.currentTimeMillis(),1);
                listenableFutures.add(result);
            }
        }
        catch (Exception e){
            log.error("sendAPReceiveByChannelIdStop:"+e);
        }
        //等待所有线程执行完在返回
//        successNumber = waitAllThread(listenableFutures);
        return new ResponseBean(sum, sum);
    }
}
