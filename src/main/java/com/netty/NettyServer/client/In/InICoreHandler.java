package com.netty.NettyServer.client.In;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InICoreHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String str = "[0,104,864547034536305,P1.B3.H3.F1.D11<LF><CR>191,H,0,0,211125,163900,28.610662,77.112000,900,63,1.8,0,22,64,255,6888,3464,1332,0,235.2,0,0,0,404,10,20E,44B5,12,1<LF><CR>1,H,0,0,211125,072512,28.610535,77.112122,0,0,0.0,0,0,64,255,6982,3864,1332,0,0.0,0,0,0,404,10,20E,32C,16,1<LF>";
       String str2="<CR>2,H,0,20,211125,072752,28.610535,77.112122,0,0,0.0,0,0,192,255,3823,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,1<LF><CR>3,H,0,20,211125,072757,28.610535,77.112122,0,0,0.0,0,00,192,255,2799,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,1<LF><CR>4,H,0,20,211125,072803,28.610535,77.112122,0,0,0.0,0,00,192,255,2799,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,0<LF><CR>191,H,0,0,211125,163900,28.610662,77.112000,900,63,1.8,0,22,64,255,6888,3464,1332,0,235.2,0,0,0,404,10,20E,44B5,12,1<LF>*30234]";
        ByteBuf buf = Unpooled.wrappedBuffer(str.getBytes());
        ByteBuf bu2 = Unpooled.wrappedBuffer(str2.getBytes());
        ctx.writeAndFlush(buf);;
        ctx.writeAndFlush(bu2);
    }
}
