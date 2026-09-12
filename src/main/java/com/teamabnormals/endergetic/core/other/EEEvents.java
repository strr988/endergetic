package com.teamabnormals.endergetic.core.other;

import com.google.common.collect.Maps;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.endergetic.common.advancement.EECriteriaTriggers;
import com.teamabnormals.endergetic.common.block.*;
import com.teamabnormals.endergetic.common.entity.bolloom.BolloomBalloon;
import com.teamabnormals.endergetic.common.entity.purpoid.Purpoid;
import com.teamabnormals.endergetic.common.network.entity.S2CUpdateBalloonsMessage;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.interfaces.BalloonHolder;
import com.teamabnormals.endergetic.core.registry.EEBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = EndergeticExpansion.MOD_ID)
public final class EEEvents {
	private static final AttributeModifier SLOW_BALLOON = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "slow_balloon"), -0.07, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier SUPER_SLOW_BALLOON = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "super_slow_balloon"), -0.075, AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier PURPOID_SLOWFALL = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "purpoid_slowfall"), -0.07, AttributeModifier.Operation.ADD_VALUE);

	public static final Map<Supplier<Block>, Supplier<Block>> PETRIFICATION_MAP = Util.make(Maps.newHashMap(), (petrifications) -> {
		petrifications.put(EEBlocks.END_CORROCK, EEBlocks.PETRIFIED_END_CORROCK);
		petrifications.put(EEBlocks.NETHER_CORROCK, EEBlocks.PETRIFIED_NETHER_CORROCK);
		petrifications.put(EEBlocks.OVERWORLD_CORROCK, EEBlocks.PETRIFIED_OVERWORLD_CORROCK);
		petrifications.put(EEBlocks.END_CORROCK_BLOCK, EEBlocks.PETRIFIED_END_CORROCK_BLOCK);
		petrifications.put(EEBlocks.NETHER_CORROCK_BLOCK, EEBlocks.PETRIFIED_NETHER_CORROCK_BLOCK);
		petrifications.put(EEBlocks.OVERWORLD_CORROCK_BLOCK, EEBlocks.PETRIFIED_OVERWORLD_CORROCK_BLOCK);
		petrifications.put(EEBlocks.SPECKLED_OVERWORLD_CORROCK, EEBlocks.PETRIFIED_SPECKLED_OVERWORLD_CORROCK);
		petrifications.put(EEBlocks.SPECKLED_NETHER_CORROCK, EEBlocks.PETRIFIED_SPECKLED_NETHER_CORROCK);
		petrifications.put(EEBlocks.SPECKLED_END_CORROCK, EEBlocks.PETRIFIED_SPECKLED_END_CORROCK);
		petrifications.put(EEBlocks.END_CORROCK_CROWN::get, EEBlocks.PETRIFIED_END_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.NETHER_CORROCK_CROWN::get, EEBlocks.PETRIFIED_NETHER_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.OVERWORLD_CORROCK_CROWN::get, EEBlocks.PETRIFIED_OVERWORLD_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.END_WALL_CORROCK_CROWN::get, EEBlocks.PETRIFIED_END_WALL_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.NETHER_WALL_CORROCK_CROWN::get, EEBlocks.PETRIFIED_NETHER_WALL_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.OVERWORLD_WALL_CORROCK_CROWN::get, EEBlocks.PETRIFIED_OVERWORLD_WALL_CORROCK_CROWN::get);
		petrifications.put(EEBlocks.INFESTED_CORROCK, EEBlocks.PETRIFIED_INFESTED_CORROCK);
	});

	@SubscribeEvent
	public static void onThrowableImpact(final ProjectileImpactEvent event) {
		Projectile projectileEntity = event.getProjectile();
		if (projectileEntity instanceof ThrownPotion potionEntity) {
			ItemStack itemstack = potionEntity.getItem();
			PotionContents potion = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

			if (potion.is(Potions.WATER) && !potion.hasEffects() && event.getRayTraceResult() instanceof BlockHitResult blockraytraceresult) {
				Level world = potionEntity.level();
				Direction direction = blockraytraceresult.getDirection();
				BlockPos blockpos = blockraytraceresult.getBlockPos().relative(Direction.DOWN).relative(direction);

				tryToConvertCorrockBlock(world, blockpos);
				tryToConvertCorrockBlock(world, blockpos.relative(direction.getOpposite()));
				for (Direction faces : Direction.values()) {
					tryToConvertCorrockBlock(world, blockpos.relative(faces));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingTick(EntityTickEvent.Post event) {
		if (!(event.getEntity() instanceof LivingEntity entity)) return;
		if (!entity.level().isClientSide) {
			int balloonCount = ((BalloonHolder) entity).getBalloons().size();
			AttributeInstance gravity = entity.getAttribute(Attributes.GRAVITY);

			if (gravity != null) {
				boolean hasABalloon = balloonCount > 0;
				if (hasABalloon) entity.fallDistance = 0.0F;
				boolean isFalling = entity.getDeltaMovement().y <= 0.0D;

				if (isFalling && balloonCount < 3 && hasABalloon) {
					if (!gravity.hasModifier(SLOW_BALLOON.id())) gravity.addTransientModifier(SLOW_BALLOON);
				} else if (gravity.hasModifier(SLOW_BALLOON.id())) {
					gravity.removeModifier(SLOW_BALLOON.id());
				}

				if (isFalling && balloonCount == 3) {
					if (!gravity.hasModifier(SUPER_SLOW_BALLOON.id())) gravity.addTransientModifier(SUPER_SLOW_BALLOON);
				} else if (gravity.hasModifier(SUPER_SLOW_BALLOON.id())) {
					gravity.removeModifier(SUPER_SLOW_BALLOON.id());
				}

				if (isFalling && entity.hasPassenger(e -> e instanceof Purpoid)) {
					entity.fallDistance = 0.0F;
					if (!gravity.hasModifier(PURPOID_SLOWFALL.id())) gravity.addTransientModifier(PURPOID_SLOWFALL);
				} else if (gravity.hasModifier(PURPOID_SLOWFALL.id())) {
					gravity.removeModifier(PURPOID_SLOWFALL.id());
				}

				if (balloonCount > 3) {
					entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 2, balloonCount - 4, false, false, false));
					if (entity instanceof ServerPlayer serverPlayer) {
						EECriteriaTriggers.UP_UP_AND_AWAY.get().trigger(serverPlayer);
					}
				}
			}

			if (entity instanceof IDataManager dataManager) {
				int cooldown = dataManager.getValue(EEDataProcessors.CATCHING_COOLDOWN);
				if (cooldown > 0) {
					dataManager.setValue(EEDataProcessors.CATCHING_COOLDOWN, cooldown - 1);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityTracked(PlayerEvent.StartTracking event) {
		ServerPlayer player = (ServerPlayer) event.getEntity();
		Entity trackingEntity = event.getTarget();
		if (trackingEntity instanceof BolloomBalloon balloon) {
			Entity attachedEntity = balloon.getAttachedEntity();
			if (attachedEntity != null) {
				PacketDistributor.sendToPlayer(player, new S2CUpdateBalloonsMessage(attachedEntity));
			}
		} else {
			PacketDistributor.sendToPlayer(player, new S2CUpdateBalloonsMessage(trackingEntity));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		BalloonHolder holder = (BalloonHolder) event.getEntity();
		if (!holder.getBalloons().isEmpty()) {
			holder.detachBalloons();
		}
	}

	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		BlockState state = level.getBlockState(pos);
		Player player = event.getEntity();
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		InteractionHand hand = event.getHand();

		if (event.getFace() != Direction.DOWN && item instanceof ShovelItem && !player.isSpectator()) {
			BlockState newState = state.is(EEBlocks.POISMOSS.get()) ? EEBlocks.POISMOSS_PATH.get().defaultBlockState() : state.is(EEBlocks.EUMUS_POISMOSS.get()) ? EEBlocks.EUMUS_POISMOSS_PATH.get().defaultBlockState() : null;
			if (newState != null && level.isEmptyBlock(pos.above())) {
				level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.setBlock(pos, newState, 11);
				event.getItemStack().hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
				event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity.level().isClientSide && entity instanceof Purpoid purpoid) {
			purpoid.updatePull(entity.position());
		}
	}

	private static void tryToConvertCorrockBlock(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if ((block instanceof CorrockPlantBlock && !((CorrockPlantBlock) block).petrified) || block instanceof CorrockBlock || block instanceof SpeckledCorrockBlock || block instanceof InfestedCorrockBlock || (block instanceof CorrockCrownBlock && !((CorrockCrownBlock) block).petrified)) {
			BlockState convertedState = convertCorrockBlock(state);
			if (convertedState != null) {
				world.setBlockAndUpdate(pos, convertedState);
			}
		}
	}

	public static BlockState convertCorrockBlock(BlockState state) {
		Block block = state.getBlock();
		for (Map.Entry<Supplier<Block>, Supplier<Block>> entries : PETRIFICATION_MAP.entrySet()) {
			Block petrifiedBlock = entries.getValue().get();
			if (entries.getKey().get() == block) {
				if (block instanceof CorrockPlantBlock) {
					return petrifiedBlock.defaultBlockState().setValue(CorrockPlantBlock.WATERLOGGED, state.getValue(CorrockPlantBlock.WATERLOGGED));
				} else if (block instanceof CorrockBlock || block instanceof SpeckledCorrockBlock || block instanceof InfestedCorrockBlock) {
					return petrifiedBlock.defaultBlockState();
				} else if (block instanceof CorrockCrownStandingBlock) {
					return petrifiedBlock.defaultBlockState()
							.setValue(CorrockCrownStandingBlock.ROTATION, state.getValue(CorrockCrownStandingBlock.ROTATION))
							.setValue(CorrockCrownStandingBlock.UPSIDE_DOWN, state.getValue(CorrockCrownStandingBlock.UPSIDE_DOWN))
							.setValue(CorrockCrownStandingBlock.WATERLOGGED, state.getValue(CorrockCrownStandingBlock.WATERLOGGED));
				}
				return petrifiedBlock.defaultBlockState().setValue(CorrockCrownWallBlock.WATERLOGGED, state.getValue(CorrockCrownWallBlock.WATERLOGGED)).setValue(CorrockCrownWallBlock.FACING, state.getValue(CorrockCrownWallBlock.FACING));
			}
		}
		return null;
	}
}
