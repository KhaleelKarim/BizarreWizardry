package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.magical_class.MagicalClassProvider;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;

import java.util.function.Supplier;

public class ChooseClassC2SPacket {

    int magicalClass;

    public ChooseClassC2SPacket(int magicalClass) {
        this.magicalClass = magicalClass;
    }

    public ChooseClassC2SPacket(FriendlyByteBuf buf) {
        this.magicalClass = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(magicalClass);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the server
            ServerPlayer player = context.getSender();
            player.getCapability(MagicalClassProvider.MAGICAL_CLASS).ifPresent(magicalClass -> {
                magicalClass.setMagicalClass(this.magicalClass);
                ModMessages.sendToPlayer(new SetClassSyncS2CPacket(magicalClass.getMagicalClass()), player);
            });
        });
        return true;
    }
}
