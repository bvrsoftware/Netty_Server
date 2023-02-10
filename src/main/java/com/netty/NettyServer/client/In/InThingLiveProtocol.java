package com.netty.NettyServer.client.In;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.DataInputStream;

public class InThingLiveProtocol extends ChannelInboundHandlerAdapter{
    private DataInputStream in=null;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        in = new DataInputStream(System.in);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Received - "+msg);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        while (true) {
            System.out.println("Enter Packet :");
            String str = in.readLine();
            ctx.writeAndFlush(str);
        }
    }
}
