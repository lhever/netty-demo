package com.lhever.demo.netty.hb.codec;

import com.lhever.demo.netty.hb.register.DtoRegister;
import com.lhever.demo.netty.hb.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public final class NettyMessageEncoder extends MessageToByteEncoder<Object> {

	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];


	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf sendBuf) throws Exception {
		if (msg == null) throw new Exception("The encode message is null");

		int startIdx = sendBuf.writerIndex();

		sendBuf.writeBytes(LENGTH_PLACEHOLDER);

		Class<?> aClass = msg.getClass();
		int code = DtoRegister.getCode(aClass);
		sendBuf.writeInt(code);

		sendBuf.writeBytes(JsonUtils.obj2Byte(msg));


		int endIdx = sendBuf.writerIndex();

		sendBuf.setInt(startIdx, endIdx - startIdx - 4);
	}
}
