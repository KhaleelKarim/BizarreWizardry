package net.raguraccoon.bizarre_wizardry.entity.mana_reaper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class ManaReaper extends Monster implements GeoEntity {

    //Store of animatable instance
    private final AnimatableInstanceCache reaperCache = GeckoLibUtil.createInstanceCache(this);

    //Idle Animation
    protected static final RawAnimation IDLE_ANIMATION =
            RawAnimation.begin().thenLoop("animation.model.mana_reaper.idle");
    //Moving Animation
    protected static final RawAnimation MOVE_ANIMATION =
            RawAnimation.begin().thenPlayAndHold("mana_reaper.walk");
    //Attacking Animation
    protected static final RawAnimation ATTACK_ANIMATION =
            RawAnimation.begin().thenPlay("mana_reaper.attack");


    //Constructor
    public ManaReaper(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Idle", this::IdleAnimController));
        controllerRegistrar.add(new AnimationController<>(this, "Move", this::MoveAnimController));
        controllerRegistrar.add(new AnimationController<>(this, "Attack", this::AttackAnimController));
    }

    //Idle Animation method
    protected <E extends ManaReaper> PlayState IdleAnimController(final AnimationState<E> event) {
        if (!event.isMoving())
            return event.setAndContinue(IDLE_ANIMATION);

        return PlayState.STOP;
    }

    //Moving Animation method
    protected <E extends ManaReaper> PlayState MoveAnimController(final AnimationState<E> event) {
        if (event.isMoving())
            return event.setAndContinue(MOVE_ANIMATION);

        return PlayState.STOP;
    }

    //Attack Animation method
    protected <E extends ManaReaper> PlayState AttackAnimController(final AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            this.swinging = false;
            return event.setAndContinue(ATTACK_ANIMATION);
        }

        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.reaperCache;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2003f)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Creeper.class, true));
    }


}
