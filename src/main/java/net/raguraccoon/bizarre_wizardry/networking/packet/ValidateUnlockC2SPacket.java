/*
This packet is sent to check to determine whether a
spell is unlockable or not. i.e. If the requirements
for unlocking the spell have been fulfilled.

The packet accepts an integer corresponding to the
BizarreSpell that is being checked.
 */

package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.FinalizeUnlockS2CPacket;
import net.raguraccoon.bizarre_wizardry.statistics.PlayerStatsGetter;

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
            PlayerStatsGetter statsGetter = new PlayerStatsGetter(player); //Object to check player's stats
            boolean spellUnlockable = false; //Is the spell unlockable?


            //Check for appropriate requirements
            switch (spellNumber) {

                case 1:
                    spellUnlockable = statsGetter.unlockedStomp();
                    break;

                case 2:
                    spellUnlockable = statsGetter.unlockedMagiciansRed();
                    break;

                case 3:
                    spellUnlockable = statsGetter.unlockedBloodletting();
                    break;

                case 4:
                    spellUnlockable = statsGetter.unlockedCrystallineShield();
                    break;

                default:
                    player.sendSystemMessage(Component.literal("Whoops!"));

            }

            //Update info on client side
            ModMessages.sendToPlayer(new FinalizeUnlockS2CPacket(spellNumber, spellUnlockable), player);

        });
        return true;
    }

}
