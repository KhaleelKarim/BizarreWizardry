package net.raguraccoon.bizarre_wizardry.statistics;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


/*
Class that needs to be instantiated with a player and tells
you if that player has met the requirements to unlock any
spell
 */
public class PlayerStatsGetter {


    //Fodder spell method
    public static boolean unlockedNoSpell(ServerPlayer player) {

        return true;

    }


    //Has player unlocked Stomp?
    public static boolean unlockedStomp(ServerPlayer player) {

        Stat<?> fallStat = Stats.CUSTOM.get(Stats.FALL_ONE_CM);
        int fallDistance = player.getStats().getValue(fallStat) / 100;

        return fallDistance > 100;

    }

    //Has player unlocked Magician's Red?
    public static boolean unlockedMagiciansRed(ServerPlayer player) {

        Stat<?> blazeKillsStats = Stats.ENTITY_KILLED.get(EntityType.BLAZE);
        int blazeKills = player.getStats().getValue(blazeKillsStats);

        return blazeKills >= 3;

    }

    //Has player unlocked Bloodletting?
    public static boolean unlockedBloodletting(ServerPlayer player) {

        Stat<?> spiderEyeEatenStat = Stats.ITEM_USED.get(Items.SPIDER_EYE);
        int spiderEyesEaten = player.getStats().getValue(spiderEyeEatenStat);

        return spiderEyesEaten >= 1;

    }

    //Has the player unlocked Crystalline Shield?
    public static boolean unlockedCrystallineShield(ServerPlayer player) {

        Inventory inventory = player.getInventory();

        //Iterate through all inventory slots
        for (int i = 0 ; i < inventory.getContainerSize() ; ++i) {

            //Get the current item and check if it is stone
            ItemStack currentItem = inventory.getItem(i);
            if (currentItem.is(Items.GLASS)) {

                //If it is stone, then get the count to make sure it is enough
                int count = currentItem.getCount();

                if (count >= 64) {
                    return true;
                }

            }

        }

        return false;

    }

    //Has the player unlocked impact?
    public static boolean unlockedImpact(ServerPlayer player) {

        Stat<?> creeperKillsStats = Stats.ENTITY_KILLED.get(EntityType.CREEPER);
        int creeperKills = player.getStats().getValue(creeperKillsStats);

        return creeperKills >= 3;

    }

    //Has the player unlocked fresh fish?
    public static boolean unlockedFreshFish(ServerPlayer player) {

        Inventory inventory = player.getInventory();

        //Iterate through all inventory slots
        for (int i = 0 ; i < inventory.getContainerSize() ; ++i) {

            //Get the current item and check if it is pufferfish
            ItemStack currentItem = inventory.getItem(i);
            if (currentItem.is(Items.PUFFERFISH)) {

                //If it is puffer, verify it is at least three and then consume it
                int count = currentItem.getCount();

                if (count >= 3) {
                    currentItem.shrink(3);
                    return true;
                }

            }

        }

        return false;

    }


}
