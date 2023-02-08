package com.netty.NettyServer.server;

import com.netty.NettyServer.server.In.MyDelimiterFrameDecoder;
import com.netty.NettyServer.server.In.DeviceDataInHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import javax.xml.bind.DatatypeConverter;

public class MyServer {
    public void serverStart(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,200)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //  //ICore Protocol Implementation
                            /*pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(new byte[] {']'})));
                            pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
                            pipeline.addLast(new InChannelHandler());*/
                            // DATA
                            pipeline.addFirst(new MyDelimiterFrameDecoder(Unpooled.wrappedBuffer(DatatypeConverter.parseHexBinary("0C")),1,3));
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new DeviceDataInHandler());
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            if(channelFuture!=null){
                System.out.printf("server start with :"+channelFuture.channel());
                channelFuture.channel().closeFuture().sync();
            }
        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.serverStart(8080);
    }
}
