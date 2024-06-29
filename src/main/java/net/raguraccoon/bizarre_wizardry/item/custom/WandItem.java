package net.raguraccoon.bizarre_wizardry.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.item.ModItems;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;



public class WandItem extends Item {
    public WandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

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
                int wandPosition = wand.getTag().getInt("bizarre_wizardry.spell_number");
                BizarreSpell currentBizarreSpell = ClientSpellData.SPELL_ARSENAL[wandPosition];



                //Cast appropriate spell
                currentBizarreSpell.caster.cast(this, level, player, null, null);

                player.getCooldowns().addCooldown(this, 10);

            }


        }

        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player,
                                                  LivingEntity livingEntity, InteractionHand interactionHand) {


        //Interaction result should only happen on the server side and the main hand
        if ((interactionHand.equals(InteractionHand.MAIN_HAND))) {
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
                BizarreSpell currentBizarreSpell = ClientSpellData.SPELL_ARSENAL[currentSpellNumber];



                //Cast appropriate spell
                currentBizarreSpell.caster.cast(this, null, player, livingEntity, null);
                player.getCooldowns().addCooldown(this, 10);


            }


        }


        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {


        return super.useOn(context);
    }
}
