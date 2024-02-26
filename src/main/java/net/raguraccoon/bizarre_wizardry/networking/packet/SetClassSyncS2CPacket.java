package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class SetClassSyncS2CPacket {

    int magicalClass;

    public SetClassSyncS2CPacket(int magicalClass) {
        this.magicalClass = magicalClass;
    }

    public SetClassSyncS2CPacket(FriendlyByteBuf buf) {
        this.magicalClass = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(magicalClass);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the server
            ServerPlayer player = context.getSender();
            ClientSpellData.setMagicalClass(this.magicalClass);
        });
        return true;
    }
}
