package com.lhever.demo.netty.hb.server;

import com.lhever.demo.netty.hb.NettyConstant;
import com.lhever.demo.netty.hb.codec.NettyMessageDecoder;
import com.lhever.demo.netty.hb.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    public void bind() throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws IOException {
                        ch.pipeline().addLast(new NettyMessageDecoder());
                        ch.pipeline().addLast(new NettyMessageEncoder());

//                        ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));

                        ch.pipeline().addLast(new ObjectEchoServerHandler());

                    }
                });

        // 绑定端口，同步等待成功
        b.bind(NettyConstant.SERVER_IP, NettyConstant.SERVER_PORT).sync();

        LOG.info("Netty server start ok : " + (NettyConstant.SERVER_IP + " : " + NettyConstant.SERVER_PORT));
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }
}

