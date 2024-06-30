package net.raguraccoon.bizarre_wizardry.networking.packet.mana_spill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.capability.mana_spill.ManaSpillProvider;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;

import java.util.function.Supplier;

public class ModifyManaSpillC2SPacket {

    String operation; //How the mana spill will be changed
    int value;        //The value that it is changed by


    public ModifyManaSpillC2SPacket(String operation, int value) {
        this.operation = operation;
        this.value = value;
    }

    public ModifyManaSpillC2SPacket(FriendlyByteBuf buf) {
        operation = buf.readUtf();
        value = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(operation);
        buf.writeInt(value);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            //Now on the server
            ServerPlayer player = context.getSender();
            //If the capability is present on the player, then modify it
            player.getCapability(ManaSpillProvider.MANA_SPILL).ifPresent(manaSpill -> {

                switch (operation) {
                    case "set" -> manaSpill.setManaSpill(value);
                    case "add" -> manaSpill.addManaSpill(value);
                }

                ModMessages.sendToPlayer(new UpdateManaSpillS2CPacket(this.value), player);

            });
        });

        return true;
    }

}
