package com.netty.NettyServer.client;

public class Client2 {
    public static void main(String[] args) {
            ClientImplementation clientImplementation = new ClientImplementation();
            clientImplementation.connectInitializer("localhost",8080);
    }
}
