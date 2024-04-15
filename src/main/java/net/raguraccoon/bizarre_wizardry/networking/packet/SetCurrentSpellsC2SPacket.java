package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.current_spells.CurrentSpellsProvider;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;

import java.util.function.Supplier;

public class SetCurrentSpellsC2SPacket {

    int[] spellInformation;
    int spellToAdd;
    int index;
    

    public SetCurrentSpellsC2SPacket(int[] spellInformation) {
        this.spellInformation = spellInformation;
        this.spellToAdd = spellInformation[0];
        this.index = spellInformation[1];
    }

    public SetCurrentSpellsC2SPacket(FriendlyByteBuf buf) {
        this.spellInformation = buf.readVarIntArray();
        this.spellToAdd = spellInformation[0];
        this.index = spellInformation[1];
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(spellInformation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Now on the server
            ServerPlayer player = context.getSender();
            //If the capability is present on the player, then modify it
            player.getCapability(CurrentSpellsProvider.CURRENT_SPELLS).ifPresent(currentSpells -> {
                currentSpells.setSpell(this.spellToAdd, this.index);
                ModMessages.sendToPlayer(new GetCurrentSpellsS2CPacket(currentSpells.getCurrentSpells()), player);
            });
        });

        return true;
    }


}