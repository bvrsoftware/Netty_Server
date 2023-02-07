package com.netty.NettyServer.server.In;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MyFirstDecoder extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
      if(msg.startsWith("[")&&msg.endsWith("]")){
          out.add(msg);
      }else {
          System.out.println("packet not complete");
      }
    }
}
