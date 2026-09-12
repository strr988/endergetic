package com.teamabnormals.endergetic.client;

import com.teamabnormals.endergetic.common.entity.PoiseClusterEntity;
import com.teamabnormals.endergetic.core.registry.EESoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class EEClientEntityEffects {
	private EEClientEntityEffects() {
	}

	public static void breakPoiseCluster(PoiseClusterEntity entity, BlockState state, VoxelShape shape) {
		ClientLevel level = (ClientLevel) entity.level();
		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
			double sizeX = Math.min(1.0D, x2 - x1);
			double sizeY = Math.min(1.0D, y2 - y1);
			double sizeZ = Math.min(1.0D, z2 - z1);
			int countX = Math.max(2, Mth.ceil(sizeX / 0.25D));
			int countY = Math.max(2, Mth.ceil(sizeY / 0.25D));
			int countZ = Math.max(2, Mth.ceil(sizeZ / 0.25D));

			for (int x = 0; x < countX; ++x) {
				for (int y = 0; y < countY; ++y) {
					for (int z = 0; z < countZ; ++z) {
						double fractionX = ((double) x + 0.5D) / countX;
						double fractionY = ((double) y + 0.5D) / countY;
						double fractionZ = ((double) z + 0.5D) / countZ;
						double particleX = fractionX * sizeX + x1;
						double particleY = fractionY * sizeY + y1;
						double particleZ = fractionZ * sizeZ + z1;
						Minecraft.getInstance().particleEngine.add(new TerrainParticle(level, entity.getX() + particleX - 0.5F, entity.getY() + particleY, entity.getZ() + particleZ - 0.5F, fractionX - 0.5D, fractionY - 0.5D, fractionZ - 0.5D, state, entity.blockPosition()).updateSprite(state, entity.blockPosition()));
					}
				}
			}
		});
	}

	public static void playPoiseClusterSound(PoiseClusterEntity entity) {
		Minecraft.getInstance().getSoundManager().play(new PoiseClusterSound(entity));
	}

	private static final class PoiseClusterSound extends AbstractTickableSoundInstance {
		private final PoiseClusterEntity cluster;
		private int ticksRemoved;

		private PoiseClusterSound(PoiseClusterEntity cluster) {
			super(EESoundEvents.POISE_CLUSTER_AMBIENT.get(), SoundSource.NEUTRAL, cluster.getRandom());
			this.cluster = cluster;
			this.looping = true;
			this.delay = 0;
			this.volume = 1.0F;
			this.x = (float) cluster.getX();
			this.y = (float) cluster.getY();
			this.z = (float) cluster.getZ();
			this.pitch = cluster.getRandom().nextFloat() * 0.3F + 0.8F;
		}

		@Override
		public boolean canStartSilent() {
			return true;
		}

		@Override
		public void tick() {
			if (this.cluster.isAlive()) {
				this.x = (float) this.cluster.getX();
				this.y = (float) this.cluster.getY();
				this.z = (float) this.cluster.getZ();
			} else if (++this.ticksRemoved > 10) {
				this.stop();
			}
			this.volume = Math.max(0.0F, this.volume - (this.ticksRemoved / 10.0F));
		}
	}
}
