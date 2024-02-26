package net.raguraccoon.bizarre_wizardry.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.networking.packet.ChooseClassC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetClassSyncS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SwitchSpellC2SPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(BizarreWizardry.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SwitchSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SwitchSpellC2SPacket::new)
                .encoder(SwitchSpellC2SPacket::encode)
                .consumerMainThread(SwitchSpellC2SPacket::handle)
                .add();

        net.messageBuilder(ChooseClassC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChooseClassC2SPacket::new)
                .encoder(ChooseClassC2SPacket::encode)
                .consumerMainThread(ChooseClassC2SPacket::handle)
                .add();

        net.messageBuilder(SetClassSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SetClassSyncS2CPacket::new)
                .encoder(SetClassSyncS2CPacket::encode)
                .consumerMainThread(SetClassSyncS2CPacket::handle)
                .add();
    }



    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
