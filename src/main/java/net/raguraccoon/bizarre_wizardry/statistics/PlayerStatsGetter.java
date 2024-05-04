package net.raguraccoon.bizarre_wizardry.statistics;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
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

    public int stomp() {
        Stat<?> fallStat = Stats.CUSTOM.get(Stats.FALL_ONE_CM);
        int fallDistance = player.getStats().getValue(fallStat) / 100;

        return fallDistance;
    }

    public boolean unlockedMagiciansRed() {

        Stat<?> killsStat = Stats.CUSTOM.get(Stats.MOB_KILLS);
        int kills = player.getStats().getValue(killsStat);

        return kills >= 3;

    }

    public int Red() {

        Stat<?> killsStat = Stats.CUSTOM.get(Stats.MOB_KILLS);
        int kills = player.getStats().getValue(killsStat);

        return kills;

    }

}
