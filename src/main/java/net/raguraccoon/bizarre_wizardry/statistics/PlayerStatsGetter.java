package net.raguraccoon.bizarre_wizardry.statistics;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/*
Class that needs to be instantiated with a player and tells
you if that player has met the requirements to unlock any
spell
 */
public class PlayerStatsGetter {

    private final ServerPlayer player;

    public PlayerStatsGetter(ServerPlayer player) {
        this.player = player;
    }


    //Has player unlocked Stomp?
    public boolean unlockedStomp() {

        Stat<?> fallStat = Stats.CUSTOM.get(Stats.FALL_ONE_CM);
        int fallDistance = player.getStats().getValue(fallStat) / 100;

        return fallDistance > 100;

    }

    //Has player unlocked Magician's Red?
    public boolean unlockedMagiciansRed() {

        Stat<?> blazeKillsStats = Stats.ENTITY_KILLED.get(EntityType.BLAZE);
        int blazeKills = player.getStats().getValue(blazeKillsStats);

        return blazeKills >= 3;

    }

    //Has player unlocked Bloodletting?
    public boolean unlockedBloodletting() {

        Stat<?> spiderEyeEatenStat = Stats.ITEM_USED.get(Items.SPIDER_EYE);
        int spiderEyesEaten = player.getStats().getValue(spiderEyeEatenStat);

        return spiderEyesEaten >= 1;

    }


}
