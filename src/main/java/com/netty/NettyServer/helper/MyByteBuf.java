package com.netty.NettyServer.helper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.Delimiters;
import io.netty.util.internal.StringUtil;

import javax.xml.bind.DatatypeConverter;

public class MyByteBuf {
    public static void main(String[] args) {
        String str="0C12B2000101021711330001750BF6086304F064AA04F064AA";
       // String str="[0,104,864547034536305,P1.B3.H3.F1.D11<LF><CR>191,H,0,0,211125,163900,28.610662,77.112000,900,63,1.8,0,22,64,255,6888,3464,1332,0,235.2,0,0,0,404,10,20E,44B5,12,1<LF>*30234]3589485,]";

        ByteBuf buf = Unpooled.wrappedBuffer(DatatypeConverter.parseHexBinary(str));
        ByteBuf delimters = Unpooled.wrappedBuffer("]".getBytes());
        ByteBuf buf1 = buf.readRetainedSlice(10);
        buf.skipBytes(10);
        System.out.println(ByteBufUtil.hexDump(buf1));
    }
}
