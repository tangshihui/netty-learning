package com.example.netty.chat;

import com.example.netty.chat.bean.ServerConfig;
import com.example.netty.chat.handler.ClientHandler;
import com.example.netty.chat.protocol.ChatMessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class ChatClient {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)//设置客户端的通道实现类型
                    .handler(new ChannelInitializer<SocketChannel>() { //使用匿名内部类初始化通道
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,12,4,0 , 0));

                            ch.pipeline().addLast(new ChatMessageCodec());
                            ch.pipeline().addLast(new ClientHandler());//添加客户端通道的处理器
                        }
                    });
            ServerConfig config = new ServerConfig("127.0.0.1", 6666);

            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect(config.getHost(), config.getPort()).sync();
            System.out.println("connected to server ...");
            //对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
