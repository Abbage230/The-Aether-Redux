package net.zepalesque.redux.mixin.common.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.orcinus.galosphere.criterion.GCriterion;
import net.orcinus.galosphere.entities.GlowFlare;
import net.orcinus.galosphere.entities.SpectreFlare;
import net.orcinus.galosphere.init.GCriteriaTriggers;
import net.orcinus.galosphere.init.GItems;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(SubzeroCrossbowItem.class)
public class SubzeroCrossbowItemMixin {
    @Unique
    private static final Predicate<ItemStack> PREDICATE = stack -> stack.is(GItems.GLOW_FLARE.get()) || stack.is(GItems.SPECTRE_FLARE.get());

    @Inject(at = @At(value = "INVOKE", target = "Lnet/zepalesque/redux/item/weapons/SubzeroCrossbowItem;setCharged(Lnet/minecraft/world/item/ItemStack;Z)V"), method = "releaseUsing")
    private void GE$releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        ItemStack projectileStack = ProjectileWeaponItem.getHeldProjectile(livingEntity, PREDICATE);
        if (!projectileStack.isEmpty() && (livingEntity instanceof Player player && !player.getAbilities().instabuild)) {
            projectileStack.shrink(1);
        }
    }

    @Inject(at = @At("HEAD"), method = "shootProjectile", cancellable = true)
    private static void GE$shootProjectile(Level world, LivingEntity entity, InteractionHand hand, ItemStack stack, ItemStack ammo, float p_40900_, boolean p_40901_, float p_40902_, float p_40903_, float p_40904_, CallbackInfo ci) {
        if (!world.isClientSide && PREDICATE.test(ammo)) {
            ci.cancel();
            boolean isSpectreFlare = ammo.is(GItems.SPECTRE_FLARE.get());
            Projectile projectile = isSpectreFlare ? new SpectreFlare(world, ammo, entity, entity.getX(), entity.getEyeY() - (double)0.15F, entity.getZ(), true) : new GlowFlare(world, ammo, entity, entity.getX(), entity.getEyeY() - (double)0.15F, entity.getZ(), true);

            if (entity instanceof ServerPlayer serverPlayer) {
                GCriterion criteria = isSpectreFlare ? GCriteriaTriggers.USE_SPECTRE_FLARE : GCriteriaTriggers.LIGHT_SPREAD;
                criteria.trigger(serverPlayer);
            }

            if (entity instanceof CrossbowAttackMob crossbowattackmob && crossbowattackmob.getTarget() != null) {
                crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), stack, projectile, p_40904_);
            } else {
                Vec3 vec3 = entity.getUpVector(1.0F);
                Quaternionf quaternionf = new Quaternionf().setAngleAxis(p_40904_ * ((float)Math.PI / 180), vec3.x, vec3.y, vec3.z);
                Vec3 vec32 = entity.getViewVector(1.0F);
                Vector3f vector3f = vec32.toVector3f().rotate(quaternionf);
                projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), p_40902_, p_40903_);
            }

            stack.hurtAndBreak(3, entity, (e) -> e.broadcastBreakEvent(hand));
            world.addFreshEntity(projectile);
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, p_40900_);
        }
    }

}