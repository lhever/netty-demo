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
import com.lhever.demo.netty.hb.utils.CommonUtils;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ServerAuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        AuthReq authReq = CommonUtils.getIfMatch(msg, AuthReq.class);
        if (authReq == null) {
            System.out.println("服务端收到的不是认证请求，透传消息");
            ctx.fireChannelRead(msg);
        } else  {
            System.out.println("server received  auth req:  " + JsonUtils.obj2Json(authReq));
            AuthResp resp = new AuthResp();
            System.out.println("服务端发送认证相应");
            if ("lhever".equals(authReq.getUser()) && "123456".equals(authReq.getPwd())) {
                resp.setSuccess(true);
                resp.setClientId("lhever");
                ctx.write(CommonMsg.forInstance(resp));
            } else {
                resp.setSuccess(false);
                ctx.writeAndFlush(CommonMsg.forInstance(resp));
                System.out.println("认证不通过，关闭客户端");
                ctx.close();
            }
//            ctx.fireChannelUnregistered();
//            System.out.println("un register lhever");
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
