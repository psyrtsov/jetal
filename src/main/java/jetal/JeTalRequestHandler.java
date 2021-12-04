package jetal;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.ide.HttpRequestHandler;
import vcidea.commands.VcCommand;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class JeTalRequestHandler extends HttpRequestHandler {
    @Override
    public boolean process(@NotNull QueryStringDecoder queryStringDecoder, @NotNull FullHttpRequest fullHttpRequest, @NotNull ChannelHandlerContext channelHandlerContext) throws IOException {
        DefaultHttpResponse response;
        try {
            System.out.println("Handling " + fullHttpRequest.uri() + fullHttpRequest.method().name());
            VcCommand vcCommand = VcCommand.fromRequestUri(fullHttpRequest.uri());
            if (vcCommand != null) {
                String result = vcCommand.run();
                sendData(result.getBytes(StandardCharsets.UTF_8), "response", fullHttpRequest, channelHandlerContext.channel(), fullHttpRequest.headers());
                return true;
            }
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        }
        channelHandlerContext.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        return true;
    }

    @Override
    public boolean isSupported(@NotNull FullHttpRequest request) {
        return VcCommand.isSupported(request.uri());
    }
}
