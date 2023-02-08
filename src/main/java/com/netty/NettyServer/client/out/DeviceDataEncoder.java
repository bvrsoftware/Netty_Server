package com.netty.NettyServer.client.out;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.xml.bind.DatatypeConverter;

public class DeviceDataEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        String str = (String) msg;
        int length = str.length();
        if (length % 2 != 0) {
            System.out.println("String Not Valid !!!");
        } else {
            out.writeBytes(DatatypeConverter.parseHexBinary(str));
        }
    }
}
