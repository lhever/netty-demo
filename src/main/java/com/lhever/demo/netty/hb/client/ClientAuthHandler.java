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

import com.lhever.demo.netty.hb.register.AuthReq;
import com.lhever.demo.netty.hb.register.AuthResp;
import com.lhever.demo.netty.hb.register.CommonMsg;
import com.lhever.demo.netty.hb.register.User;
import com.lhever.demo.netty.hb.utils.CommonUtils;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static io.netty.channel.ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ClientAuthHandler extends ChannelInboundHandlerAdapter {

    /**
     * Creates a client-side handler.
     */
    public ClientAuthHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("ClientAuthHandler channel active !!!!");

        System.out.println("client send auth req");
        // Send the first message if this handler is a client-side handler.
        ChannelFuture future = ctx.writeAndFlush(buildAuthReq());
        future.addListener(FIRE_EXCEPTION_ON_FAILURE); // Let object serialisation exceptions propagate.
        ctx.fireChannelActive();
    }

    private CommonMsg<AuthReq> buildAuthReq() {
        AuthReq req = new AuthReq("lhever", "123456");
        CommonMsg<AuthReq> msg = CommonMsg.forInstance(req);
        return msg;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the server.
        AuthResp resp = CommonUtils.getIfMatch(msg, AuthResp.class);
        if (resp != null) {

            if (resp.getSuccess() != null && resp.getSuccess()) {

                System.out.println("client auth success");
//                ctx.fireChannelRead(msg);

                CommonMsg<User> userMsg = CommonMsg.forInstance(new User(18, "lihong-"));

                System.out.println("client send user msg");
                ctx.writeAndFlush(userMsg);

            } else {

                System.out.println("client auth error");
                ctx.close();
            }

        } else {
            System.out.println("客户端收到的不是认证响应，透传消息");
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
        ctx.fireExceptionCaught(cause);
    }
}
