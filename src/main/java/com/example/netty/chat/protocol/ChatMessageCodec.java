package com.example.netty.chat.protocol;

import com.example.netty.chat.bean.LoginMessage;
import com.example.netty.chat.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


/**
 * 协议的字节数必须是2的整数倍字节
 * 协议设计要素：
 * - 魔数
 * - 版本号
 * - 序列化算法
 * - 指令类型
 * - 请求序号
 * - 正文长度
 * <p>
 * - 正文
 */
public class ChatMessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(msg);
        byte[] bytes = baos.toByteArray();


        out.writeBytes(new byte[]{0, 1}); //2个字节的魔数
        out.writeInt(1);                  //4个字节的版本号
        out.writeByte(0);                 //1个字节的序列化算法    0:jdk,1:json
        out.writeByte(0);                 //1个字节的指令类型     0:login, 1:send, ...
        out.writeInt(msg.getSequenceId());//4个字节的请求序列化
        out.writeInt(bytes.length);       //4个字节正文长度

        out.writeBytes(bytes);//正文
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        byte[] magic = new byte[2];
        in.readBytes(magic);
        int version = in.readInt();
        int serializeType = in.readByte();
        int msgType = in.readByte();
        int sequenceId = in.readInt();
        int contentLength = in.readInt();

        byte[] content = new byte[contentLength];
        in.readBytes(content);
        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Message msg = (Message) ois.readObject();


        System.out.println(String.format("magic:{},version:{},serializeType:{},msgType:{},sequenceId:{}", magic, version, serializeType, msgType, sequenceId));
        System.out.println(msg);


        out.add(msg);
    }


    public static void main(String[] args) {
        EmbeddedChannel ch = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG), new ChatMessageCodec());

        ch.writeOneInbound(new LoginMessage("zhangsan", "123"));
    }
}
