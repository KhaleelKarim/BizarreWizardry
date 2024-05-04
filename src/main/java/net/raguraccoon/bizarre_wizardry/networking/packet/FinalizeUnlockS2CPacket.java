package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

import java.util.function.Supplier;

public class FinalizeUnlockS2CPacket {

    String spell; //Spell that will be set to true/false
    boolean unlockable; //Whether the spell is unlockable



    public FinalizeUnlockS2CPacket(String spell, boolean unlockable) {
        this.spell = spell;
        this.unlockable = unlockable;
    }

    public FinalizeUnlockS2CPacket(FriendlyByteBuf buf) {
        this.spell = buf.readUtf();
        this.unlockable = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(spell);
        buf.writeBoolean(unlockable);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Set the appropriate boolean to true
            switch (spell) {

                case "Stomp":
                    ClientSpellData.setStomp(unlockable);
                    break;

                case "Magician's Red":
                    ClientSpellData.setMagiciansRed(unlockable);
                    break;

                case "Bloodletting":
                    ClientSpellData.setBloodletting(unlockable);
                    break;


            }

        });

        return true;
    }

}
