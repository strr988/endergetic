package com.teamabnormals.endergetic.common.block;

import com.teamabnormals.endergetic.core.registry.EEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class EnderTorchBlock extends TorchBlock {

	public EnderTorchBlock(Properties properties) {
		super(ParticleTypes.FLAME, properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource randomSource) {
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(EEParticleTypes.ENDER_FIRE_FLAME.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}	
