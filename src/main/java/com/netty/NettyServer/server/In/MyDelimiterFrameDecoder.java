package com.netty.NettyServer.server.In;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class MyDelimiterFrameDecoder extends ByteToMessageDecoder {
    private static final int minFrameLength=3;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    while (in.readableBytes()>minFrameLength){
        int ri = in.readerIndex();
        if(in.getByte(ri)==0x0C){
            int pactLen = in.getByte(ri + 1);
            int totalLen=pactLen+minFrameLength;
        }
    }
    }
}
