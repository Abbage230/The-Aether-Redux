package net.zepalesque.redux.client;

import com.aetherteam.aether_genesis.client.GenesisAtlases;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.init.GItems;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.accessory.VampireAmuletItem;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;

import java.util.Optional;

@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxClient {


    public static void registerItemModelProperties() {
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return SubzeroCrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)SubzeroCrossbowItem.getChargeDuration(stack);
            }
        });
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("charged"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("firework"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) && SubzeroCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
        if (Redux.galosphereCompat()) {
            ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Galosphere.id("glow_flare"), (stack, world, entity, i) -> entity != null && SubzeroCrossbowItem.isCharged(stack) && SubzeroCrossbowItem.containsChargedProjectile(stack, GItems.GLOW_FLARE.get()) ? 1.0F : 0.0F);
            ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Galosphere.id("spectre_flare"), (stack, world, entity, i) -> entity != null && SubzeroCrossbowItem.isCharged(stack) && SubzeroCrossbowItem.containsChargedProjectile(stack, GItems.SPECTRE_FLARE.get()) ? 1.0F : 0.0F);
        }
        ItemProperties.register(ReduxItems.VAMPIRE_AMULET.get(), Redux.locate("active"), (stack, level, entity, seed) -> {
            if (entity == null) return 0.0F;
            Optional<Boolean> isActive = VampireAmulet.get(entity).map(VampireAmulet::canUseAbility);
            return VampireAmuletItem.validForActivation(stack) && isActive.isPresent() && isActive.get() ? 1.0F : 0.0F;
        });

        ItemProperties.register(ReduxItems.SPEAR_OF_THE_BLIGHT.get(), Redux.locate("throwing"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(ReduxBlocks.AURUM.get().asItem(), Redux.locate("enchanted"), (stack, world, living, seed) -> ReduxConfig.COMMON.enchanted_gilded_grass.get() ? 1.0F : 0.0F);
        ItemProperties.register(ReduxBlocks.GOLDEN_CLOVER.get().asItem(), Redux.locate("enchanted"), (stack, world, living, seed) -> ReduxConfig.COMMON.enchanted_gilded_grass.get() ? 1.0F : 0.0F);

    }

}
