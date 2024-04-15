package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class GetAvailableSpellsS2CPacket {

    int[] availableSpells;

    public GetAvailableSpellsS2CPacket(int[] availableSpells) {
        this.availableSpells = availableSpells;
    }

    //Decoding constructor
    public GetAvailableSpellsS2CPacket(FriendlyByteBuf buf) {
        availableSpells = buf.readVarIntArray();
    }

    //Encoding method
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(availableSpells);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the client
            ClientSpellData.setAvailableSpells(this.availableSpells);
        });


        return true;
    }

}
