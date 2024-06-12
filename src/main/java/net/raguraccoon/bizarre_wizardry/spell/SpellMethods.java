package net.raguraccoon.bizarre_wizardry.spell;

/*
Class with implementation for each spell

Every spell method must include a WandItem, Level,
Player, LivingEntity, and UseOnContext. That being
said, not every method will use all these parameters.
Each method should check if its relevant parameters are
null and return immediately if so. The WandItem will
never be null.
 */

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.raguraccoon.bizarre_wizardry.effect.ModEffects;
import net.raguraccoon.bizarre_wizardry.entity.ModEntities;
import net.raguraccoon.bizarre_wizardry.entity.magicians_red.MagiciansRed;
import net.raguraccoon.bizarre_wizardry.item.custom.WandItem;

import java.util.Random;


public class SpellMethods {

    public static void noSpell(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {
        //filler
    }

    public static void burn(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {

        if (level == null || player == null)
            return;

        Vec3 lookAngle = player.getLookAngle(); //Used to shoot projectile

        //Create instance of projectile
        MagiciansRed magiciansRed = new MagiciansRed(ModEntities.MAGICIANS_RED.get(),
                                        player,
                                        0, 0, 0, level);

        //Shoot projectile
        magiciansRed.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 1f, 0);
        level.addFreshEntity(magiciansRed);

        player.getCooldowns().addCooldown(wand, 10);

    }

    public static void stomp(WandItem wand, Level level, Player player, LivingEntity livingEntity, UseOnContext context) {

        if (level == null || player == null)
            return;


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

        player.getCooldowns().addCooldown(wand, 50);

    }

    public static void bloodletting(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {

        if (level == null || player == null)
            return;


        //Damage player for 3 hearts
        player.hurt(player.damageSources().generic(), 3);

        //Give player speed and strength

        MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1);
        MobEffectInstance strength = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 0);
        MobEffectInstance nausea = new MobEffectInstance(MobEffects.CONFUSION, 70, 0);

        player.addEffect(speed);
        player.addEffect(strength);
        player.addEffect(nausea);

        player.getCooldowns().addCooldown(wand, 50);

    }

    public static void crystallineShield(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {

        if (level == null || player == null)
            return;

        MobEffectInstance crystal_shield = new MobEffectInstance(ModEffects.CRYSTALLINE_SHIELD.get(),
                1000, 0);
        player.addEffect(crystal_shield);


        //Play nice sound effects
        level.playSound(null, player.blockPosition(), SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.PLAYERS);


        player.getCooldowns().addCooldown(wand, 15);

    }

    public static void impact(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {

        if (entity == null || player == null)
            return;


        Vec3 playerLookVector = player.getLookAngle();
        playerLookVector = playerLookVector.scale(3);
        entity.knockback(4, -1 * playerLookVector.x, -1 * playerLookVector.z);

        Level level1 = Minecraft.getInstance().level;
        level1.playSound(player, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS);


        player.getCooldowns().addCooldown(wand, 10);

    }

    public static void freshFish(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context) {

        if (level == null || player == null)
            return;



        player.getCooldowns().addCooldown(wand, 30);

    }

    private static boolean isOceanBiome(Biome biome) {

        return false;

    }

}
