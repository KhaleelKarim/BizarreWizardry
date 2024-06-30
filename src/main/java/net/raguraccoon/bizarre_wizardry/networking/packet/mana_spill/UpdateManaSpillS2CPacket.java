package net.raguraccoon.bizarre_wizardry.networking.packet.mana_spill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class UpdateManaSpillS2CPacket {

    int manaSpill; //Updated mana spill


    public UpdateManaSpillS2CPacket(int manaSpill) {
        this.manaSpill = manaSpill;
    }

    public UpdateManaSpillS2CPacket(FriendlyByteBuf buf) {
        manaSpill = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(manaSpill);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the client
            ClientSpellData.setManaSpill(manaSpill);
        });


        return true;
    }

}
