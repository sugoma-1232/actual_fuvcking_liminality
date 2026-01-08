package com.theo222.liminality.entities;

import com.mojang.serialization.MapCodec;
import com.theo222.liminality.Config;
import com.theo222.liminality.Liminality;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Entity_1 extends Zombie {
    private boolean phase2_start = false;
    private int block_amount = 64 * 2;
    public Entity_1(EntityType<? extends Zombie> p_34271_, Level p_34272_)
    {
        super(p_34271_, p_34272_);
        super.setBaby(false);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WOODEN_SWORD, 1));
        //super.deathTime = 2;

    }

    @Override
    public void tick() {
        if (this.getHealth() < 60f && !phase2_start) {
            phase2_start = true;
            this.deathTime = 2;
            super.invulnerableTime = 20;

        }
        if (this.firstTick) {
            this.lookAt(EntityAnchorArgument.Anchor.FEET, this.position().add(0, -20, 0));
        }
        if (this.getTarget() != null) {
            if ((this.getTarget().position().y > this.position().y + 1) && (new Vec3(this.getX(), 0f, this.getZ()).distanceTo(new Vec3(this.getTarget().getX(), 0f, this.getTarget().getZ())) < 2) && (Math.abs(this.getDeltaMovement().x) < 0.01 && Math.abs(this.getDeltaMovement().z) < 0.01)) {
                if (this.onGround()) {
                    this.addDeltaMovement(new Vec3(0, 0.5f, 0));
                }
                else if ((Math.abs(this.getDeltaMovement().y) < 0.1f) && (this.level() instanceof ServerLevel serverLevel) && (block_amount != 0)) {
                    block_amount --;
                    //this.lookAt(EntityAnchorArgument.Anchor.EYES, this.position().add(0, -20, 0));
                    this.lerpHeadTo(0, 50);

                    BlockState a = serverLevel.getBlockState(this.blockPosition().below());
                    serverLevel.setBlock(this.blockPosition().below(),Blocks.COBBLESTONE.defaultBlockState(), Block.UPDATE_ALL);
                    if (serverLevel.getBlockState(this.blockPosition().below()).getBlock() != a.getBlock()) {
                        this.level().playSound(null, this.blockPosition().below().getX(), this.blockPosition().below().getY(), this.blockPosition().below().getZ(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    }
                    this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.COBBLESTONE, 64));
                }
            }
            else {
                super.lookAt(EntityAnchorArgument.Anchor.EYES, this.getTarget().position());
                this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.COBBLESTONE, 0));
            }
        }


        super.tick();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }


    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
    }

    @Override
    protected void registerGoals() {
        if (Config.SANDBOX.isTrue()) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, LivingEntity.class, false));
        }
        else {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, Player.class, false));
        }

        this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 3f, true));



        //this.goalSelector.addGoal(0, new AvoidEntityGoal(this, Player.class, 20f, 1f, 1f));
        //this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 30f));
        //super.registerGoals();
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return super.isWithinMeleeAttackRange(entity);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.STONE_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target.getClass() != this.getClass()) {
            return super.canAttack(target);
        }
        return false;
    }

}
