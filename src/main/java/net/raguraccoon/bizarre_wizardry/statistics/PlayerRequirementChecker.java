package net.raguraccoon.bizarre_wizardry.statistics;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PlayerRequirementChecker {

    private final Player player;
    private final Inventory inventory;

    public PlayerRequirementChecker(Player player) {
        this.player = player != null ? player : Minecraft.getInstance().player;
        this.inventory = player.getInventory();
    }


    public boolean canUnlockStomp() {

        //Iterate through all inventory slots
        for (int i = 0 ; i < inventory.getContainerSize() ; ++i) {

            //Get the current item and check if it is stone
            ItemStack currentItem = inventory.getItem(i);
            if (currentItem.is(Items.STONE)) {

                //If it is stone, then get the count to make sure it is enough
                int count = currentItem.getCount();

                if (count >= 32) {
                    currentItem.shrink(32);

                    return true;
                }


            }

        }

        return false;

    }

}
