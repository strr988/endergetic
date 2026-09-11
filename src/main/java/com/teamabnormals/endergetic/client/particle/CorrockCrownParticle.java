package com.teamabnormals.endergetic.client.particle;

import com.teamabnormals.endergetic.client.particle.data.CorrockCrownParticleData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class CorrockCrownParticle extends TextureSheetParticle {
	private final SpriteSet animatedSprite;
	private final float rotSpeed;

	public CorrockCrownParticle(SpriteSet animatedSprite, ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, int lifetime, int lifetimeBoost, float gravity, float gravityBoost, Optional<Float> scale) {
		super(world, x, y, z);
		scale.ifPresent(value -> {
			float size = 0.2F * (this.quadSize = value * (0.75F + this.random.nextFloat() * 0.5F));
			this.setSize(size, size);
		});
		this.xd = motionX + motionX * ((float) Math.random() - 0.5F) * 0.2F;
		this.yd = motionY + Math.random() * 0.025D;
		this.zd = motionZ + motionZ * ((float) Math.random() - 0.5F) * 0.2F;
		this.animatedSprite = animatedSprite;
		this.gravity = gravity + (float) Math.random() * gravityBoost;
		this.roll = (float) Math.random() * ((float) Math.PI * 2.0F);
		this.lifetime = lifetime + (int) (Math.random() * lifetimeBoost);
		this.rotSpeed = ((float) Math.random() - 0.5F) * 0.075F;
		this.setSpriteFromAge(animatedSprite);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.stoppedByCollision && this.yd >= 0.25D) {
			this.stoppedByCollision = false;
			this.yd = -0.05D;
		}

		this.oRoll = this.roll;
		this.roll += (float) Math.PI * this.rotSpeed * 2.0F;

		if (this.onGround) {
			this.oRoll = this.roll = 0.0F;
		}

		float particleAngle = this.roll;
		this.xd += Math.cos(particleAngle) * 0.00175D;
		this.yd *= 1.025D;
		this.zd += Math.sin(particleAngle) * 0.00175D;

		if (this.isAlive()) {
			this.setSpriteFromAge(this.animatedSprite);
		}
	}

	@Override
	public int getLightColor(float partialTick) {
		float ageFactor = Mth.clamp(this.lifetime / (((this.age + (this.lifetime * 0.5F)) + partialTick)), 0.0F, 1.0F);
		int brightnessForRender = super.getLightColor(partialTick);
		int j = brightnessForRender & 255;
		int k = brightnessForRender >> 16 & 255;
		j += (int) (ageFactor * 240.0F);
		if (j > 240) {
			j = 240;
		}
		return j | k << 16;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Factory implements ParticleProvider<CorrockCrownParticleData> {
		private SpriteSet animatedSprite;

		public Factory(SpriteSet animatedSprite) {
			this.animatedSprite = animatedSprite;
		}

		@Override
		public Particle createParticle(CorrockCrownParticleData data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new CorrockCrownParticle(this.animatedSprite, world, x, y, z, xSpeed, ySpeed, zSpeed, data.getLifetime(), data.getLifetimeBoost(), data.getGravity(), data.getGravityBoost(), data.getScale());
		}
	}
}
