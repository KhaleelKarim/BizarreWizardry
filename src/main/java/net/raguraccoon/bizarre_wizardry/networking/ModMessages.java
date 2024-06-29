package net.raguraccoon.bizarre_wizardry.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.networking.packet.*;
import net.raguraccoon.bizarre_wizardry.networking.packet.available_spells.GetAvailableSpellsS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.available_spells.SetAvailableSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.current_spells.GetCurrentSpellsS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.current_spells.SetCurrentSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.magical_class.ChooseClassC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.magical_class.SetClassSyncS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.ModifyManaCapacityC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.ModifyManaLevelC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.ModifyManaRateC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.UpdateManaInfoS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.spell_capacity.GetSpellCapacityS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.spell_capacity.SetSpellCapacityC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.unlocks.FinalizeUnlockS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.unlocks.ValidateUnlockC2SPacket;

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

        //Simple spell switching packet
        net.messageBuilder(SwitchSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SwitchSpellC2SPacket::new)
                .encoder(SwitchSpellC2SPacket::encode)
                .consumerMainThread(SwitchSpellC2SPacket::handle)
                .add();


        //Packets for selecting mage class
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


        //Packets for spell capacity
        net.messageBuilder(SetSpellCapacityC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSpellCapacityC2SPacket::new)
                .encoder(SetSpellCapacityC2SPacket::encode)
                .consumerMainThread(SetSpellCapacityC2SPacket::handle)
                .add();

        net.messageBuilder(GetSpellCapacityS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GetSpellCapacityS2CPacket::new)
                .encoder(GetSpellCapacityS2CPacket::encode)
                .consumerMainThread(GetSpellCapacityS2CPacket::handle)
                .add();


        //Packets for available spells
        net.messageBuilder(SetAvailableSpellsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetAvailableSpellsC2SPacket::new)
                .encoder(SetAvailableSpellsC2SPacket::encode)
                .consumerMainThread(SetAvailableSpellsC2SPacket::handle)
                .add();

        net.messageBuilder(GetAvailableSpellsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GetAvailableSpellsS2CPacket::new)
                .encoder(GetAvailableSpellsS2CPacket::encode)
                .consumerMainThread(GetAvailableSpellsS2CPacket::handle)
                .add();



        //Packets for current spells
        net.messageBuilder(SetCurrentSpellsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetCurrentSpellsC2SPacket::new)
                .encoder(SetCurrentSpellsC2SPacket::encode)
                .consumerMainThread(SetCurrentSpellsC2SPacket::handle)
                .add();

        net.messageBuilder(GetCurrentSpellsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GetCurrentSpellsS2CPacket::new)
                .encoder(GetCurrentSpellsS2CPacket::encode)
                .consumerMainThread(GetCurrentSpellsS2CPacket::handle)
                .add();



        //Packet for checking player stats
        net.messageBuilder(ValidateUnlockC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ValidateUnlockC2SPacket::new)
                .encoder(ValidateUnlockC2SPacket::encode)
                .consumerMainThread(ValidateUnlockC2SPacket::handle)
                .add();

        net.messageBuilder(FinalizeUnlockS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FinalizeUnlockS2CPacket::new)
                .encoder(FinalizeUnlockS2CPacket::encode)
                .consumerMainThread(FinalizeUnlockS2CPacket::handle)
                .add();



        //Packets dealing with various aspects of mana
        net.messageBuilder(ModifyManaCapacityC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ModifyManaCapacityC2SPacket::new)
                .encoder(ModifyManaCapacityC2SPacket::encode)
                .consumerMainThread(ModifyManaCapacityC2SPacket::handle)
                .add();

        net.messageBuilder(ModifyManaLevelC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ModifyManaLevelC2SPacket::new)
                .encoder(ModifyManaLevelC2SPacket::encode)
                .consumerMainThread(ModifyManaLevelC2SPacket::handle)
                .add();

        net.messageBuilder(ModifyManaRateC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ModifyManaRateC2SPacket::new)
                .encoder(ModifyManaRateC2SPacket::encode)
                .consumerMainThread(ModifyManaRateC2SPacket::handle)
                .add();

        net.messageBuilder(UpdateManaInfoS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateManaInfoS2CPacket::new)
                .encoder(UpdateManaInfoS2CPacket::encode)
                .consumerMainThread(UpdateManaInfoS2CPacket::handle)
                .add();


    }



    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
