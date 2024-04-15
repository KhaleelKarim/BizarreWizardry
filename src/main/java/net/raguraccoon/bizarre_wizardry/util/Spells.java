package net.raguraccoon.bizarre_wizardry.util;

//Class that has implementation for all spells

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Spells {

    public static String[] spellsLibrary = {"Magician's Red", "Rhino Stomp", "Bloodletting", "Overgrowth"};
    public static String[] availableSpells = new String[spellsLibrary.length];
    public static String[] currentSpells = new String[spellsLibrary.length];

    public static void burn(UseOnContext context) {

    }

    public static void stomp(Level level, Player player) {

        AABB playerBoundingBox = player.getBoundingBox();

        //Bounding box inflated in the x and z directions that will include entities to be launched
        AABB launchBoundingBox = playerBoundingBox.inflate(5, 0, 5);
        Iterable<Entity> relevantEntities = level.getEntitiesOfClass(Entity.class, launchBoundingBox);

        //Vector defining what trajectory entities will be launched
        Vec3 launchVector = new Vec3(0, 1.4, 0);

        //Iterate through all entities in the launch bounding box and launch 'em
        for (Entity entity : relevantEntities) {
            if (entity instanceof LivingEntity) {
                entity.setDeltaMovement(launchVector);
            }
        }

        //Make a bounding box that will contain all the areas to spawn particles
        //Contracted by 1 so that it only hits the ground
        AABB particlesBoundingBox = launchBoundingBox.contract(0, 1, 0);
        ParticleOptions particleOptions = ParticleTypes.EXPLOSION;
        level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.5f,
                1.0f);
        for (double x = particlesBoundingBox.minX ; x < particlesBoundingBox.maxX ; ++x) {
            for (double y = particlesBoundingBox.minY ; y < particlesBoundingBox.maxY ; ++y) {
                for (double z = particlesBoundingBox.minZ ; z < particlesBoundingBox.maxZ ; ++z) {
                    if (!level.isClientSide()) {
                        ServerLevel serverLevel = (ServerLevel) level;
                        serverLevel.sendParticles(particleOptions, x, y, z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public static void bloodletting(UseOnContext context) {

    }
}
