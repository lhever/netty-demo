/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.lhever.demo.netty.hb.client;

import com.lhever.demo.netty.hb.register.*;
import com.lhever.demo.netty.hb.utils.CommonUtils;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

import static io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture<?> scheduledFuture;

    /**
     * Creates a client-side handler.
     */
    public ClientHeartBeatHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("ClientHeartBeatHandler channel active !!!!");
        ctx.fireChannelActive();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端被断开");
        super.channelInactive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the server.
        AuthResp resp = CommonUtils.getIfMatch(msg, AuthResp.class);
        if (resp != null) {
            if (resp.getSuccess() != null && resp.getSuccess()) {
                scheduledFuture = ctx.executor().scheduleAtFixedRate(
                        new HeartBeatTask(ctx), 0, 30000, TimeUnit.MILLISECONDS);
            } else {
                System.out.println("客户端认证未通过，不定期心跳");
            }

        } else if(msg instanceof PingPong) {
            System.out.println("Client receive server heart beat message : ---> " + msg);

        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        ctx.fireChannelReadComplete();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();

        ScheduledFuture<?> scheduledFuture = this.scheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.scheduledFuture = null;
        }


        ctx.fireExceptionCaught(cause);
    }






    private static class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;
        public HeartBeatTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            PingPong heatBeat = buildHeatBeat();
            System.out.println("Client send heart beat messsage to server : ---> " + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private PingPong buildHeatBeat() {
            return PingPong.INSTANCE;
        }

    }
}
