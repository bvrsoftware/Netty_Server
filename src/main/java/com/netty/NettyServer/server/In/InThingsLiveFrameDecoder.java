package com.netty.NettyServer.server.In;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class InThingsLiveFrameDecoder extends ByteToMessageDecoder {
    private static final int minFrameLength = 13;
    private ByteBuf frame;
    private int totalPacketLen = 0;

    @Override
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        frame.release();
        frame = null;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        frame = ctx.alloc().buffer(25);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < minFrameLength) {
            System.out.println("Wrong packet");
            in.skipBytes(in.readableBytes());
            return;
        }
        while (in.readableBytes() >= totalPacketLen) {
            int ri = in.readerIndex();
            if (in.getByte(ri) == 0x0C) {
                int pactLen = in.getByte(ri + 9);
                totalPacketLen = pactLen + minFrameLength - 2;// 2 is starting , ending byte
                if (in.readableBytes() >= totalPacketLen) {
                    out.add(in.readBytes(totalPacketLen));
                    totalPacketLen = 0;
                }
            }else {
                return;
            }
        }

        /*while (in.readableBytes()>minFrameLength){
            int ri = in.readerIndex();
            if (in.getByte(ri) == 0x0C) {
                int pactLen = in.getByte(ri + 9);
                totalPacketLen = pactLen + minFrameLength-2;// 2 is starting , ending byte
                if (in.readableBytes() >= totalPacketLen) {
                    out.add(in.readBytes(totalPacketLen));
                }else {
                    frame.writeBytes(in.readBytes(in.readableBytes()));
                }
            }else {
                frame.writeBytes(in.readBytes(in.readableBytes()));
            }
        }
        if(in.readableBytes()>0&&in.readableBytes()<=minFrameLength){
            frame.writeBytes(in.readBytes(in.readableBytes()));
        }
       while (frame.readableBytes()>=totalPacketLen){
           out.add(frame.readBytes(totalPacketLen));
       }*/
    }
}
