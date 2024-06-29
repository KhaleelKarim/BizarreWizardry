package net.raguraccoon.bizarre_wizardry.networking.packet.spell_capacity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.spell_capacity.SpellCapacityProvider;

import java.util.function.Supplier;

public class SetSpellCapacityC2SPacket {

    int spellCapacity;

    public SetSpellCapacityC2SPacket(int spellCapacity) {
        this.spellCapacity = spellCapacity;
    }

    public SetSpellCapacityC2SPacket(FriendlyByteBuf buf) {
        this.spellCapacity = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellCapacity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Now on the server
            ServerPlayer player = context.getSender();
            //If the capability is present on the player, then modify it
            player.getCapability(SpellCapacityProvider.SPELL_CAPACITY).ifPresent(spellCapacity -> {
                spellCapacity.setSpellCapacity(this.spellCapacity);
                ModMessages.sendToPlayer(new GetSpellCapacityS2CPacket(this.spellCapacity), player);
            });
        });

        return true;
    }


}
