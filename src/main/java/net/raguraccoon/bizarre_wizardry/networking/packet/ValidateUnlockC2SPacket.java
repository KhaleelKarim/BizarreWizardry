package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.statistics.PlayerStatsGetter;

import java.util.function.Supplier;

public class ValidateUnlockC2SPacket {

    String spell;

    public ValidateUnlockC2SPacket(String spell) {
        this.spell = spell;
    }

    public ValidateUnlockC2SPacket(FriendlyByteBuf buf) {
        spell = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(spell);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the Server
            ServerPlayer player = context.getSender(); //Server player
            PlayerStatsGetter statsGetter = new PlayerStatsGetter(player); //Object to check player's stats
            boolean spellUnlockable = false; //Is the spell unlockable?


            //Check for appropriate requirements
            switch (spell) {

                case "Stomp":
                    spellUnlockable = statsGetter.unlockedStomp();
                    break;

                case "Magician's Red":
                    spellUnlockable = statsGetter.unlockedMagiciansRed();
                    break;

                case "Bloodletting":
                    spellUnlockable = statsGetter.unlockedBloodletting();
                    break;

                case "Crystalline Shield":
                    spellUnlockable = statsGetter.unlockedCrystallineShield();
                    break;

                default:
                    player.sendSystemMessage(Component.literal("Whoops!"));

            }

            //Update info on client side
            ModMessages.sendToPlayer(new FinalizeUnlockS2CPacket(spell, spellUnlockable), player);

        });
        return true;
    }

}
