package com.github.erozabesu.yplutillibrary.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;

import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.customsign.CustomSign;
import com.github.erozabesu.yplutillibrary.customsign.SignManager;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.util.PacketUtil;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class PlayerChannelHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String packetName = msg.getClass().getSimpleName();

        try {
            if(packetName.equalsIgnoreCase("PacketPlayInUpdateSign")){
                //NetworkManagerからプレイヤーを取得
                Object networkManager = ctx.pipeline().toMap().get("packet_handler");
                Player player = PacketUtil.getPlayerByNetworkManager(networkManager);

                // サインインスタンスが取得できた場合はメソッドを実行する
                CustomSign sign = SignManager.getUsingSign(player);
                if (sign != null) {
                    // プレイヤーからの入力文字列をサインにセット
                    PacketPlayInUpdateSign packet = (PacketPlayInUpdateSign) msg;
                    Object[] componentArray = packet.b();
                    int count = 0;
                    for (Object component : (Object[]) ReflectionUtil.getFieldValue(Fields.nmsPacketPlayInUpdateSign_getComponentArray, msg)) {
                        String text = (String) ReflectionUtil.invoke(Methods.nmsChatComponentText_getText, component);
                        sign.setLine(text, count);
                        count++;
                    }

                    sign.onClose();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        /*try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        super.write(ctx, msg, promise);
    }
}
