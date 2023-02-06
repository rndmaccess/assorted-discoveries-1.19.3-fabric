package rndm_access.assorteddiscoveries.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ADFallingWeepingHeartNectarParticle extends SpriteBillboardParticle {
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    protected ADFallingWeepingHeartNectarParticle(ClientWorld clientWorld, SpriteProvider spriteProvider, double x, double y, double z, int maxAge) {
        super(clientWorld, x, y - 0.125D, z);

        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setColor(0.8F, 0.6F, 0.3F);
        this.setSprite(spriteProvider);
        this.gravityStrength = 0.005F;
        this.maxAge = maxAge;
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (!this.dead) {
            this.velocityY -= this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateVelocity();
            if (!this.dead) {
                this.velocityX *= 0.98D;
                this.velocityY *= 0.98D;
                this.velocityZ *= 0.98D;
                BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidState = this.world.getFluidState(blockPos);
                if (this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
                    this.markDead();
                }
            }
        }
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    protected void updateVelocity() {
        if (this.onGround) {
            this.markDead();
        }
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
                                       double e, double f, double g, double h, double i) {
            int maxAge = (int) (64.0F / MathHelper.nextBetween(clientWorld.getRandom(), 0.1F, 0.9F));
            return new ADFallingWeepingHeartNectarParticle(clientWorld, spriteProvider, d, e, f, maxAge);
        }
    }
}
