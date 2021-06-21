/*
 * Copyright 2013-2018 Lilinfeng.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lhever.demo.netty.hb.codec;

import com.lhever.demo.netty.hb.register.DtoRegister;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014年3月15日
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {


    public NettyMessageDecoder() throws IOException {
        super(20 * 1048576, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        int code = frame.readInt();
        System.out.println("code is " + code);

        Class<?> cls = DtoRegister.getCls(code);

        int i = frame.readableBytes();
        byte[] b = new byte[i];
        frame.readBytes(b);

//        ByteBufInputStream bis = new ByteBufInputStream(in, true);
        ByteArrayInputStream bis = new ByteArrayInputStream(b);

        Object o = null;
        try {
            o = JsonUtils.json2Obj(bis, cls);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bis.close();
        }

        return o;
    }
}
