package net.raguraccoon.bizarre_wizardry.entity.magicians_red;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MagiciansRed extends AbstractHurtingProjectile implements GeoEntity {

    private int life = 0; //How long the projectile has been living


    //Store of animatable instance
    private final AnimatableInstanceCache phoenixCache = GeckoLibUtil.createInstanceCache(this);


    //Idle Animation
    protected static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("phoenix_idle");


    //3 Constructors!

    public MagiciansRed(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public MagiciansRed(EntityType<? extends AbstractHurtingProjectile> entityType, double spawnX, double spawnY, double spawnZ, double biasX, double biasY, double biasZ, Level level) {
        super(entityType, spawnX, spawnY, spawnZ, biasX, biasY, biasZ, level);
    }


    public MagiciansRed(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity livingEntity, double p_36828_, double p_36829_, double p_36830_, Level level) {
        super(entityType, livingEntity, p_36828_, p_36829_, p_36830_, level);
    }



    //All useful gecko lib methods

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Idle", this::IdleAnimController));
    }

    //Idle Animation method
    protected <E extends MagiciansRed> PlayState IdleAnimController(final AnimationState<E> event) {
        event.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    //Idk what this does but it's important!
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.phoenixCache;
    }


    //Don't want the fire covering the projectile
    @Override
    public boolean isOnFire() {
        return false;
    }


    //Called every tick
    @Override
    public void tick() {
        super.tick();

        ++this.life;
        if (this.life >= 20) {
            this.remove(RemovalReason.DISCARDED);
        }

    }

    //Blindly doing this
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    //When an entity is hit!
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide()) {

            super.onHitEntity(entityHitResult);
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof LivingEntity)
                entity.hurt(entity.damageSources().lava(), 5f);

        }


    }


}
