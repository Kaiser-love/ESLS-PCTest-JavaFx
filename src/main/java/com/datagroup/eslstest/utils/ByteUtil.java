package com.datagroup.eslstest.utils;


public class ByteUtil {
    private static byte[] ACK = new byte[5];
    private static byte[] NACK = new byte[5];
    private static byte[] OVER_TIME = new byte[5];
    public static byte[] splitByte(byte[] request, int begin, int len) {
        /*        byte数组截取当然要提到效率非常高的arraycopy，java中调用方式如下：
        System.arraycopy(src, srcPos, dest, destPos, length/)
        参数解析：
        src：byte源数组
        srcPos：截取源byte数组起始位置（0位置有效）
        dest,：byte目的数组（截取后存放的数组）
        destPos：截取后存放的数组起始位置（0位置有效）
        length：截取的数据长度*/
        byte[] target = new byte[len];
        System.arraycopy(request, begin, target, 0, len);
        return target;
    }

    public static int sumByte(byte[] request) {
        int sum = 0;
        for (byte b : request)
            sum += b;
        return sum;
    }
    public static String getMergeMessage(byte[] request){
        StringBuffer sb = new StringBuffer();
        for (byte item : request) {
            sb.append(SpringContextUtil.toHex(item));
        }
        return sb.toString();
    }
    public static String getVersionMessage(byte[] request){
        StringBuffer sb = new StringBuffer();
        // 字符ASSCI码 48-57(0-9) 65 - 90(A-Z)  97 - 122(a-z)
        sb.append((char)request[0]);
        sb.append((char)request[1]);
        sb.append(".");
        sb.append((char)request[3]);
        sb.append((char)request[4]);
        return sb.toString();
    }
    public static byte[] getVersionMessage(String version){
        byte[] result = new byte[6];
        // 字符ASSCI码 48-57(0-9) 65 - 90(A-Z)  97 - 122(a-z)
        result[0] = Byte.parseByte(String.valueOf(Integer.valueOf(version.charAt(0))));
        result[1] = Byte.parseByte(String.valueOf(Integer.valueOf(version.charAt(1))));
        result[2] = Byte.parseByte(String.valueOf(Integer.valueOf(version.charAt(2))));
        result[3] = Byte.parseByte(String.valueOf(Integer.valueOf(version.charAt(3))));
        result[4] = Byte.parseByte(String.valueOf(Integer.valueOf(version.charAt(4))));
        result[5] = Byte.parseByte(String.valueOf(Integer.valueOf('\0')));
        return result;
    }
    public static String getDigitalMessage(byte[] request){
        StringBuffer sb = new StringBuffer();
        for (byte item : request) {
            sb.append((char)item);
        }
        return sb.toString();
    }
    public static String getRealMessage(byte[] request) {
        // 将二进制转为16进制表示 然后在将16进制转成10进制
        StringBuffer sb = new StringBuffer();
        for (int i=request.length-1;i>=0;i--) {
            sb.append(SpringContextUtil.toHex(request[i]));
        }
        return String.valueOf(Integer.valueOf(sb.toString(),16));
    }
    public static String getWeightTipsMessage(byte[] request){
        return Integer.toBinaryString(request[0]);
    }
    public static String getIpMessage(byte[] request) {
        StringBuffer sb = new StringBuffer();
        int i=0;
        for (;i<request.length-1;i++) {
            if(request[i]<0)
                sb.append((request[i]+256)+".");
            else
                sb.append(request[i]+".");
        }
        if(request[i]<0)
            sb.append((request[i]+256));
        else
            sb.append(request[i]);
        return sb.toString();
    }

    public static byte[] getACK(byte _class,byte _id) {
        ByteUtil.ACK[0] = 0x01;
        ByteUtil.ACK[1] = 0x01;
        ByteUtil.ACK[2] = _class;
        ByteUtil.ACK[3] = _id;
        return ACK;
    }
    public static byte[] getNACK(byte _class,byte _id) {
        ByteUtil.NACK[0] = 0x01;
        ByteUtil.NACK[1] = 0x02;
        ByteUtil.NACK[2] = _class;
        ByteUtil.NACK[3] = _id;
        return NACK;
    }
    public static byte[] getOverTime(byte _class,byte _id) {
        ByteUtil.OVER_TIME[0] = 0x01;
        ByteUtil.OVER_TIME[1] = 0x03;
        ByteUtil.OVER_TIME[2] = _class;
        ByteUtil.OVER_TIME[3] = _id;
        return OVER_TIME;
    }
}
