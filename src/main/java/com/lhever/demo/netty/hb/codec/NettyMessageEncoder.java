package com.lhever.demo.netty.hb.codec;

import com.lhever.demo.netty.hb.consts.NettyConstants;
import com.lhever.demo.netty.hb.register.CommonMsg;
import com.lhever.demo.netty.hb.register.DtoRegister;
import com.lhever.demo.netty.hb.register.PingPong;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public final class NettyMessageEncoder extends MessageToByteEncoder<Object> {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf sendBuf) throws Exception {
        if (msg == null) throw new Exception("The encode message is null");

        if (msg instanceof PingPong) {
            encodePingPong(sendBuf);
        } else if (msg instanceof CommonMsg) {
            encodeCommonMsg(sendBuf, (CommonMsg) msg);
        } else {
            System.out.println("unknown msg: " + msg == null ? null : msg.getClass());
            //@TODO

        }

    }

    private void encodePingPong(ByteBuf sendBuf) {
        sendBuf.writeInt(4);
        sendBuf.writeInt(NettyConstants.PING_PONG);
    }


    private void encodeCommonMsg(ByteBuf sendBuf, CommonMsg<?> msg) {
        int startIdx = sendBuf.writerIndex();
        sendBuf.writeBytes(LENGTH_PLACEHOLDER);

        Object data = msg.getData();
        int code = NettyConstants.NULL;
        if (data != null) {
            Class<?> aClass = data.getClass();
            code = DtoRegister.getCode(aClass);
        }
        sendBuf.writeInt(code);
        sendBuf.writeBytes(JsonUtils.obj2Byte(msg));

        int endIdx = sendBuf.writerIndex();
        sendBuf.setInt(startIdx, endIdx - startIdx - 4);
    }


}
