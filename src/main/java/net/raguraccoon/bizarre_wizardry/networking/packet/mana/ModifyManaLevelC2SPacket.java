package net.raguraccoon.bizarre_wizardry.networking.packet.mana;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.capability.mana.ManaProvider;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;

import java.util.function.Supplier;

public class ModifyManaLevelC2SPacket {

    String operation; //How the mana level will be changed
    int value;        //The value that it is changed by


    public ModifyManaLevelC2SPacket(String operation, int value) {
        this.operation = operation;
        this.value = value;
    }

    public ModifyManaLevelC2SPacket(FriendlyByteBuf buf) {
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
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {

                switch (operation) {
                    case "set" -> mana.setManaLevel(value);
                    case "add" -> mana.addLevel(value);
                    case "subtract" -> mana.subtractLevel(value);
                }

                ModMessages.sendToPlayer(new UpdateManaInfoS2CPacket(new int[]{mana.getManaCapacity(),
                        mana.getManaLevel(),
                        mana.getManaRegenRate()}), player);

            });
        });

        return true;
    }

}
