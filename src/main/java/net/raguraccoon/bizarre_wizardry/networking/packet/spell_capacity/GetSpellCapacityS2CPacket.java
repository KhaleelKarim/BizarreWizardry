package net.raguraccoon.bizarre_wizardry.networking.packet.spell_capacity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class GetSpellCapacityS2CPacket {

    int spellCapacity;

    public GetSpellCapacityS2CPacket(int spellCapacity) {
        this.spellCapacity = spellCapacity;
    }

    //Decoding constructor
    public GetSpellCapacityS2CPacket(FriendlyByteBuf buf) {
        spellCapacity = buf.readInt();
    }

    //Encoding method
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellCapacity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the client
            ClientSpellData.setSpellCapacity(this.spellCapacity);
        });


        return true;
    }

}
