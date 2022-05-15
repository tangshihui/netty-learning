package com.example.netty.chat.handler;

import com.example.netty.chat.bean.LoginMessage;
import com.example.netty.chat.bean.LoginMessageResp;
import com.example.netty.chat.service.Database;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginMessage msg) throws Exception {
        System.out.println("收到客户端[" + ctx.channel().remoteAddress() + "]的消息：" + msg.toString());
        boolean login = new Database().login(msg.getUsername(), msg.getPassword());

        LoginMessageResp resp = new LoginMessageResp(msg.getUsername(), msg.getPassword());
        resp.setSuccess(login);

        ctx.writeAndFlush(resp);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        System.out.println("server 接收异常：" + cause.getMessage() + "\n" + Arrays.stream(cause.getStackTrace()).map(x -> x.toString() + "\n").collect(Collectors.joining()));
        ctx.close();
    }
}
