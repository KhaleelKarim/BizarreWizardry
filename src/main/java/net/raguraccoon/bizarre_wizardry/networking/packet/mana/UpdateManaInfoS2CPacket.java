package net.raguraccoon.bizarre_wizardry.networking.packet.mana;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class UpdateManaInfoS2CPacket {

    int[] manaInfo; //Array of mana capacity, level, and regen rate in that order


    public UpdateManaInfoS2CPacket(int[] manaInfo) {
        this.manaInfo = manaInfo;
    }

    public UpdateManaInfoS2CPacket(FriendlyByteBuf buf) {
        manaInfo = buf.readVarIntArray();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(manaInfo);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the client
            ClientSpellData.setManaCap(manaInfo[0]);
            ClientSpellData.setManaLevel(manaInfo[1]);
            ClientSpellData.setManaRate(manaInfo[2]);
        });


        return true;
    }

}
