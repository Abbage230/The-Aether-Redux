package net.zepalesque.redux.capability.player;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.ReduxPlayerSyncPacket;
import net.zepalesque.redux.util.player.AbilityUtil;
import org.apache.commons.lang3.tuple.Triple;
import teamrazor.deepaether.init.DAItems;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReduxPlayerCapability implements ReduxPlayer {
    private final Player player;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setMaxAirJumps", Triple.of(Type.INT, (object) -> this.setMaxAirJumps((int) object), this::getMaxAirJumps))
    );
    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    private final LoreBookModule lore;
    private final BlightshadeModule blightshade;
    private final AdrenalineModule adrenaline;



    int maxAirJumps = 0;


    int ticksInAir = 0;

    int airJumps = 0;
    int prevTickAirJumps = 0;

    int airJumpCooldown = 0;
    private boolean secondFireball = false;

    public ReduxPlayerCapability(Player pPlayer) {
        this.player = pPlayer;
        this.lore = new LoreBookModule();
        this.blightshade = new BlightshadeModule(pPlayer);
        this.adrenaline = new AdrenalineModule(pPlayer);
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public int getMaxAirJumps() {
        return maxAirJumps;
    }

    @Override
    public void setMaxAirJumps(int jumps) {
        this.maxAirJumps = jumps;
    }

    @Override
    public int getAirJumpsPerformed() {
        return airJumps;
    }

    public boolean increaseAirJumpCount()
    {
        if (this.airJumpCooldown == 0 && airJumps < maxAirJumps)
        {
            this.airJumps++;
            this.airJumpCooldown = 4;
            return true;
        }
        return false;
    }

    @Override
    public int getPrevTickAirJumps() {
        return this.prevTickAirJumps;
    }

    @Override
    public int getAirJumpCooldown() {
        return this.airJumpCooldown;
    }

    @Override
    public int ticksInAir() {
        return ticksInAir;
    }

    @Override
    public LoreBookModule getLoreModule() {
        return this.lore;
    }

    @Override
    public BlightshadeModule getBlightshadeModule() {
        return this.blightshade;
    }
    @Override
    public AdrenalineModule getAdrenalineModule() {
        return this.adrenaline;
    }


    @Override
    public void tick() {
        this.blightshade.tick();
        this.adrenaline.tick();


        this.prevTickAirJumps = airJumps;
        if (this.getPlayer().onGround()) {
            this.ticksInAir = 0;
            this.airJumps = 0;
            this.airJumpCooldown = 0;

            } else {
            this.ticksInAir++;

        }
        if (this.airJumpCooldown > 0) {
            this.airJumpCooldown--;
        }
/*        if (this.fireballCooldown > 0)
        {
            this.fireballCooldown--;
        }*/

    }

    @Override
    public boolean doubleJump() {
        if (this.increaseAirJumpCount())
        {
                AbilityUtil.doDoubleJumpMovement(this.getPlayer());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean fireballSetup() {
        if (player.getCooldowns().isOnCooldown(ReduxItems.SOLAR_EMBLEM.get())) {
            return false;
        } else {
            if (EquipmentUtil.hasCurio(this.getPlayer(), ReduxItems.SOLAR_EMBLEM.get())) {
                player.getCooldowns().addCooldown(ReduxItems.SOLAR_EMBLEM.get(), this.player.isCreative() ? 4 : 40);
            }
        return true;
        }
    }

    @Override
    public boolean canShootFireball() {
        return !this.player.level().isClientSide() && !this.player.getCooldowns().isOnCooldown(ReduxItems.SOLAR_EMBLEM.get()) && EquipmentUtil.hasCurio(this.player, ReduxItems.SOLAR_EMBLEM.get());
    }

    public boolean doubleFireball()
    {
        if (this.secondFireball) {
            this.secondFireball = false;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new ReduxPlayerSyncPacket(this.getPlayer().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }
    @Override
    public void deserializeSynchableNBT(CompoundTag tag) {
        deserializeNBT(tag);
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("max_jumps", this.maxAirJumps);
        tag.putInt("jumps", this.airJumps);
        tag.putInt("ticks_in_air", this.ticksInAir);
        tag.put("lore_module", this.lore.serializeNBT());
        tag.put("blightshade_module", this.blightshade.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.maxAirJumps = nbt.getInt("max_jumps");
        this.airJumps = nbt.getInt("jumps");
        this.ticksInAir = nbt.getInt("ticks_in_air");
        this.lore.deserializeNBT(nbt.get("lore_module"));
        this.blightshade.deserializeNBT(nbt.get("blightshade_module"));
    }


}