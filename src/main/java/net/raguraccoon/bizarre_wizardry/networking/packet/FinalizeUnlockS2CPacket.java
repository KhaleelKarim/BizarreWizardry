/*
This packet is called by ValidateUnlockC2SPacket to
update information on the client side about whether
a certain spell is unlockable.

This packet accepts an integer corresponding to the
BizarreSpell that is being updated, and a boolean
indicating the unlockable status that will be placed
into the BizarreSpell.
 */

package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;

import java.util.function.Supplier;

public class FinalizeUnlockS2CPacket {

    int spellNumber; //Spell that will be set to true/false
    boolean unlockable; //Whether the spell is unlockable



    public FinalizeUnlockS2CPacket(int spellNumber, boolean unlockable) {
        this.spellNumber = spellNumber;
        this.unlockable = unlockable;
    }

    public FinalizeUnlockS2CPacket(FriendlyByteBuf buf) {
        this.spellNumber = buf.readInt();
        this.unlockable = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellNumber);
        buf.writeBoolean(unlockable);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Set the appropriate boolean to true
            BizarreSpell currentSpell = BizarreSpell.spellFromNumber(spellNumber);
            currentSpell.setUnlockable(this.unlockable);

        });

        return true;
    }

}
