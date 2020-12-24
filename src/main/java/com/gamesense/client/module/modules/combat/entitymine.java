


package com.gamesense.client.module.modules.combat;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.client.GameSense;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;


public class EntityMine extends Module{
	public EntityMine(){
		super("EntityMine", Category.Combat);
	}

	  private boolean focus = false;

    @Override
    public void update() {
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .filter(entity -> mc.player == entity)
                .map(   entity -> (EntityLivingBase) entity)
                .filter(entity -> !(entity.isDead))
                .forEach(this::process);

        RayTraceResult normal_result = mc.objectMouseOver;

        if (normal_result != null) {
            focus = normal_result.typeOfHit == RayTraceResult.Type.ENTITY;
        }
    }

    private void process(EntityLivingBase event) {
        RayTraceResult bypass_entity_result = event.rayTrace(6, mc.getRenderPartialTicks());

        if (bypass_entity_result != null && focus) {
            if (bypass_entity_result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos block_pos = bypass_entity_result.getBlockPos();

                if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                    mc.playerController.onPlayerDamageBlock(block_pos, EnumFacing.UP);
                }
            }
        }
    }

}
