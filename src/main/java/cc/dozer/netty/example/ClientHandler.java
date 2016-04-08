package cc.dozer.netty.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHandler extends SimpleChannelInboundHandler<String> {


	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("Client READER_IDLE");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("Client WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("Client ALL_IDLE");
                // ����������ע��Ҫʹ��writeAndFlush��ʹ��write���ڰ�̫С�����ܻ᲻ֱ�ӷ���
                ctx.channel().writeAndFlush("ping\n");
            }
        }
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		 ctx.channel().writeAndFlush("hell world \n");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("Server say : " + msg);
		 
        if ("ping".equals(msg)) {
        	//������������������
            ctx.channel().writeAndFlush("OK\n");
        } else if("OK".equals(msg)) {
        	//�������������غ���
        } else {
            //ҵ���߼�
    		System.out.println("receiver : " + msg);
        }

	}

}
