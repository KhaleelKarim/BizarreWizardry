package net.raguraccoon.bizarre_wizardry.networking.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.raguraccoon.bizarre_wizardry.item.ModItems;
import net.raguraccoon.bizarre_wizardry.item.custom.WandItem;
import net.raguraccoon.bizarre_wizardry.util.Spells;

import java.util.function.Supplier;

public class SwitchSpellC2SPacket {

    public SwitchSpellC2SPacket() {

    }
    public SwitchSpellC2SPacket(FriendlyByteBuf buf) {

    }
    public void encode(FriendlyByteBuf buf) {

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Now on the server
            ServerPlayer player = context.getSender();

            if (player.getMainHandItem().is(ModItems.WAND.get())) {
                ItemStack wand = player.getMainHandItem();

                //If the wand doesn't have a tag, give it a tag and initialize it to 0
                if (!wand.hasTag()) {
                    CompoundTag tag = new CompoundTag();
                    tag.putInt("bizarre_wizardry.spell_number", 0);
                    wand.setTag(tag);
                }

                //Use helper method to switch spell one space forward
                CompoundTag tag = wand.getTag();
                assert tag != null;
                int newSpellNumber = switchSpell(tag.getInt("bizarre_wizardry.spell_number"));
                tag.putInt("bizarre_wizardry.spell_number", newSpellNumber);
            }

        });
        return true;
    }

    //Helper method to handle the logic of switching from spell to spell
    public static int switchSpell(int spellNumber) {
        if (spellNumber >= Spells.spells.length - 1) {
            spellNumber = 0;
        } else {
            ++spellNumber;
        }

        return spellNumber;
    }

}
