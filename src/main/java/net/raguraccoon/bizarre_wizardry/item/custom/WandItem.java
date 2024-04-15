package net.raguraccoon.bizarre_wizardry.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.item.ModItems;
import net.raguraccoon.bizarre_wizardry.util.Spells;


public class WandItem extends Item {
    public WandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        //Get basic information about the player

        //Interaction result should only happen on the server side and the main hand
        if ((!level.isClientSide()) && (hand.equals(InteractionHand.MAIN_HAND))) {
            ItemStack wand = player.getMainHandItem();

            //Check if the player's main hand is the wand
            if (wand.is(ModItems.WAND.get())) {

                //Give the wand a tag if it doesn't
                if (!wand.hasTag()) {
                    CompoundTag tag = new CompoundTag();
                    tag.putInt("bizarre_wizardry.spell_number", 0);
                    wand.setTag(tag);
                }

                //Get the wand's current spell number
                int currentSpellNumber = wand.getTag().getInt("bizarre_wizardry.spell_number");
                int spellIndex = ClientSpellData.currentSpells[currentSpellNumber];
                String currentSpell = ClientSpellData.spellsLibrary[spellIndex];

                //Switch statement to determine which spell should be cast
                switch (currentSpell) {
                    case "Magician's Red":
                        player.sendSystemMessage(Component.literal("Magician's Red!"));
                        break;
                    case "Rhino Stomp":
                        Spells.stomp(level, player);
                        player.getCooldowns().addCooldown(this, 50);
                        break;
                    case "Bloodletting":
                        player.sendSystemMessage(Component.literal("Bloodletting!"));
                        break;
                    case "Overgrowth":
                        player.sendSystemMessage(Component.literal("Overgrowth!"));
                        break;
                    default:
                        player.sendSystemMessage(Component.literal("Failure!"));
                }
            }

            player.sendSystemMessage(Component.literal("" + ClientSpellData.getMagicalClass()));

        }

        return super.use(level, player, hand);
    }
}
