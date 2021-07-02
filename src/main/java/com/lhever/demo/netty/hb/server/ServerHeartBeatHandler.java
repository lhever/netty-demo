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
package com.lhever.demo.netty.hb.server;

import com.lhever.demo.netty.hb.register.AuthReq;
import com.lhever.demo.netty.hb.register.AuthResp;
import com.lhever.demo.netty.hb.register.CommonMsg;
import com.lhever.demo.netty.hb.register.PingPong;
import com.lhever.demo.netty.hb.utils.CommonUtils;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg == null ) {
            ctx.fireChannelRead(msg);
        } else if (msg instanceof PingPong) {
            System.out.println("Receive client heart beat message : ---> " + msg);
            System.out.println("echo client heart beat message : ---> " + msg);
            ctx.writeAndFlush(PingPong.INSTANCE);
        } else {
            ctx.fireChannelRead(msg);

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }
}
