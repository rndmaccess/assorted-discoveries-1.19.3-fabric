package rndm_access.assorteddiscoveries.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

public class ADWeepingHeartAirNectarParticle extends SpriteBillboardParticle {
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    protected ADWeepingHeartAirNectarParticle(ClientWorld clientWorld, SpriteProvider spriteProvider, double x, double y, double z,
                                              double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
        this.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
        this.collidesWithWorld = false;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.01F;
        this.setColor(0.8F, 0.6F, 0.3F);
    }

    @Override
    public void tick() {
        this.updateAge();

        if(this.isAlive()) {
            super.tick();
        }
    }

    private void updateAge() {
        if(this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld,
                                       double d, double e, double f, double g, double h, double i) {
            return new ADWeepingHeartAirNectarParticle(clientWorld, Factory.this.spriteProvider, d, e, f,
                    0.0D, -0.8D, 0.0D) {
                public Optional<ParticleGroup> getGroup() {
                    return Optional.of(ParticleGroup.SPORE_BLOSSOM_AIR);
                }
            };
        }
    }
}
