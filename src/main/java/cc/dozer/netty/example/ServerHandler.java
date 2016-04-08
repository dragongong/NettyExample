package cc.dozer.netty.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
        if ("OK".equals(msg)) {
            //�ͻ����������غ���
        } else if("ping".equals(msg)) {
        	//�ͻ�����������
        	ctx.channel().writeAndFlush("OK\n");
        } else {
        	//ҵ���߼�
        }
		
	}

	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("Server READER_IDLE");
                // ����ʱ��û���յ����󣬷����������ر�channel
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("Server WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("Server ALL_IDLE");
                // ����������ע��Ҫʹ��writeAndFlush��ʹ��write���ڰ�̫С�����ܻ᲻ֱ�ӷ���
                ctx.channel().writeAndFlush("ping\n");
            }
        }
        super.userEventTriggered(ctx, evt); 
    }
	

}
