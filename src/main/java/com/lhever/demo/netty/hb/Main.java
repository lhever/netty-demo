package com.lhever.demo.netty.hb;

import com.lhever.demo.netty.hb.client.NettyClient;
import com.lhever.demo.netty.hb.consts.NettyConstants;
import com.lhever.demo.netty.hb.server.NettyServer;

public class Main {

    public static void main(String[] args) throws Exception {

        new NettyServer(NettyConstants.SERVER_IP, NettyConstants.SERVER_PORT).bind();

        new NettyClient(NettyConstants.SERVER_IP, NettyConstants.SERVER_PORT,
                NettyConstants.CLIENT_IP, NettyConstants.CLIENT_PORT).connect();


    }
}
