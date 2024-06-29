/*
This packet is sent to check to determine whether a
spell is unlockable or not. i.e. If the requirements
for unlocking the spell have been fulfilled.

The packet accepts an integer corresponding to the
BizarreSpell that is being checked.
 */

package net.raguraccoon.bizarre_wizardry.networking.packet.unlocks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;

import java.util.function.Supplier;

public class ValidateUnlockC2SPacket {

    int spellNumber;

    public ValidateUnlockC2SPacket(int spellNumber) {
        this.spellNumber = spellNumber;
    }

    public ValidateUnlockC2SPacket(FriendlyByteBuf buf) {
        spellNumber = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellNumber);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the Server
            ServerPlayer player = context.getSender(); //Server player
            boolean spellUnlockable = false; //Is the spell unlockable?
            BizarreSpell currentSpell = BizarreSpell.spellFromNumber(spellNumber);

            spellUnlockable = currentSpell.validator.validate(player);

            //Update info on client side
            ModMessages.sendToPlayer(new FinalizeUnlockS2CPacket(spellNumber, spellUnlockable), player);

        });
        return true;
    }

}
