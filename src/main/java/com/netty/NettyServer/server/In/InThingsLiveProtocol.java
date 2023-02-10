package com.netty.NettyServer.server.In;

import com.netty.NettyServer.helper.DateBuilder;
import com.netty.NettyServer.helper.Parser;
import com.netty.NettyServer.helper.PatternBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;
import java.util.regex.Pattern;

public class InThingsLiveProtocol extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("Hello Client !!!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        buf.skipBytes(1);
        System.out.println("IMEI - "+ByteBufUtil.hexDump(buf.readBytes(8)));
        buf.skipBytes(1);
        System.out.println("Device ID  - " + buf.readShort());
       short response_id = buf.readUnsignedByte();
        if (response_id == 0xB1) {
            System.out.println("Response Number - " + buf.readShort());
            System.out.println("Data - "+ByteBufUtil.hexDump(buf.readBytes(buf.writerIndex()-buf.readerIndex()-1)));
        } else if (response_id == 0xB2) {
            System.out.println("Response Number - " + buf.readShort());
            DateBuilder dateBuilder = new DateBuilder();
            byte day = buf.readByte();
            byte month = buf.readByte();
            byte year = buf.readByte();
            byte hours = buf.readByte();
            byte minutes = buf.readByte();
            byte seconds = buf.readByte();
            dateBuilder.setDate(year,month,day);
            dateBuilder.setTime(hours,minutes,seconds);
            System.out.println("Date Time - " +dateBuilder.getDate());
            double latitude =  buf.readInt() / 1000000.0;
            double longitude = buf.readInt() / 1000000.0;
            System.out.println("Location - " + Arrays.toString(new double[]{latitude, longitude}));
            System.out.println("Speed - " + buf.readUnsignedByte() + " Km/hr");
        }
        buf.skipBytes(buf.readableBytes());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ctx.writeAndFlush("Okay !!");
    }
}
