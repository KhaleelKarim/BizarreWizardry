/*
This packet is sent by SetCurrentSpellsC2SPacket
to update client information about the spells
in the player's arsenal.

It accepts an array of integers representing the
player's arsenal.
 */

package net.raguraccoon.bizarre_wizardry.networking.packet.current_spells;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class GetCurrentSpellsS2CPacket {

    int[] currentSpells;

    public GetCurrentSpellsS2CPacket(int[] currentSpells) {
        this.currentSpells = currentSpells;
    }

    //Decoding constructor
    public GetCurrentSpellsS2CPacket(FriendlyByteBuf buf) {
        currentSpells = buf.readVarIntArray();
    }

    //Encoding method
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(currentSpells);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the client
            ClientSpellData.setCurrentSpells(this.currentSpells);
        });


        return true;
    }

}
