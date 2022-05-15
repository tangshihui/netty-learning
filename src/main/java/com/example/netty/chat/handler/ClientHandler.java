package com.example.netty.chat.handler;

import com.example.netty.chat.bean.LoginMessage;
import com.example.netty.chat.bean.LoginMessageResp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.nio.charset.StandardCharsets;


public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginMessage msg = new LoginMessage("zhangsan","123");

//        ByteBuf buf = ctx.alloc().buffer();
//        buf.writeBytes(msg.toString().getBytes(StandardCharsets.UTF_8));

                ctx.writeAndFlush(msg);//Impartant: writeAndFlush的参数必须是ByteBuf
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收服务端发送过来的消息
        LoginMessageResp resp = (LoginMessageResp) msg;
        //System.out.println("收到服务端[" + ctx.channel().remoteAddress() + "]的消息：" + resp);
        if (resp.isSuccess()) {
            System.out.println("login success");
        } else {
            System.out.println("login failed.");
        }
    }
}
