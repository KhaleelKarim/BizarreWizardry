package net.raguraccoon.bizarre_wizardry.util;

//Class that has implementation for all spells

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.raguraccoon.bizarre_wizardry.entity.ModEntities;
import net.raguraccoon.bizarre_wizardry.entity.magicians_red.MagiciansRed;

import java.util.Random;


public class Spells {

    public static void burn(Level level, Player player) {

        Vec3 lookAngle = player.getLookAngle(); //Used to shoot projectile

        //Create instance of projectile
        MagiciansRed magiciansRed = new MagiciansRed(ModEntities.MAGICIANS_RED.get(),
                                        player.getX(), player.getEyeY(), player.getZ(),
                                        0, 0, 0, level);

        //Shoot projectile
        magiciansRed.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 1f, 0);
        level.addFreshEntity(magiciansRed);



    }

    public static void stomp(Level level, Player player) {

        AABB playerBoundingBox = player.getBoundingBox();

        //Bounding box inflated in the x and z directions that will include entities to be launched
        AABB launchBoundingBox = playerBoundingBox.inflate(5, 0, 5);
        Iterable<Entity> relevantEntities = level.getEntitiesOfClass(Entity.class, launchBoundingBox);

        //Generate a random trajectory
        Random randomNumberGenerator = new Random();


        //Iterate through all entities in the launch bounding box and launch 'em
        for (Entity entity : relevantEntities) {
            if (entity instanceof LivingEntity) {

                //Get random x and z directions
                double xOffset = randomNumberGenerator.nextDouble(.75);
                double zOffset = randomNumberGenerator.nextDouble(.75);

                //Randomly decide for direction to be positive or negative
                boolean positiveX = randomNumberGenerator.nextBoolean();
                boolean positiveZ = randomNumberGenerator.nextBoolean();

                if (!positiveX)
                    xOffset *= -1;

                if (!positiveZ)
                    zOffset *= -1;

                //Vector defining what trajectory entities will be launched
                Vec3 launchVector = new Vec3(xOffset, 1.4, zOffset);

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

    public static void bloodletting(Level level, Player player) {

        //Damage player for 3 hearts
        player.hurt(player.damageSources().generic(), 3);

        //Give player speed and strength

        MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1);
        MobEffectInstance strength = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 0);

        player.addEffect(speed);
        player.addEffect(strength);

    }
}
