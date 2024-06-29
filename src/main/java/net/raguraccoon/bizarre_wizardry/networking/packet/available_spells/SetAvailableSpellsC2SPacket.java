/*
This packet sets a BizarreSpell as available
to place into the player's arsenal.

The constructor accepts an integer corresponding
to the BizarreSpell that will become available
 */

package net.raguraccoon.bizarre_wizardry.networking.packet.available_spells;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.capability.available_spells.AvailableSpellsProvider;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;


import java.util.function.Supplier;

public class SetAvailableSpellsC2SPacket {

    int spellToUnlock;

    public SetAvailableSpellsC2SPacket(int spellToUnlock) {
        this.spellToUnlock = spellToUnlock;
    }

    public SetAvailableSpellsC2SPacket(FriendlyByteBuf buf) {
        this.spellToUnlock = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellToUnlock);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Now on the server
            ServerPlayer player = context.getSender();
            //If the capability is present on the player, then modify it
            player.getCapability(AvailableSpellsProvider.AVAILABLE_SPELLS).ifPresent(availableSpells -> {
                availableSpells.unlockSpell(this.spellToUnlock);
                ModMessages.sendToPlayer(new GetAvailableSpellsS2CPacket(availableSpells.getAvailableSpells()), player);
            });
        });

        return true;
    }


}
