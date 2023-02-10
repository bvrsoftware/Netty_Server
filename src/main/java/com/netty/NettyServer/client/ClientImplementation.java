package com.netty.NettyServer.client;

import com.netty.NettyServer.client.In.InThingLiveProtocol;
import com.netty.NettyServer.client.out.ThingLiveProtoColEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class ClientImplementation {
    public  void connectInitializer(String ip,int port){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //ICore Protocol Implementation
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new InICoreHandler());
                            //Device Data Format Protocol !!!
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new ThingLiveProtoColEncoder());
                            pipeline.addLast(new InThingLiveProtocol());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            if(channelFuture!=null){
                System.out.printf("Client is Connect with :"+channelFuture.channel());
            }
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            workerGroup.shutdownGracefully();
        }
    }
}
