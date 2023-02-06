package rndm_access.assorteddiscoveries.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ADSporeParticle extends SpriteBillboardParticle {
    protected ADSporeParticle(ClientWorld clientWorld, double x, double y, double z, double xd, double yd, double zd) {
        super(clientWorld, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.velocityX = xd * 0.2F + (Math.random() * 2.0D - 1.0D) * 0.02F;
        this.velocityY = yd * 0.2F + (Math.random() * 2.0D - 1.0D) * 0.02F;
        this.velocityZ = zd * 0.2F + (Math.random() * 2.0D - 1.0D) * 0.02F;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
        } else {
            this.velocityY += 0.002D;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.85F;
            this.velocityY *= 0.85F;
            this.velocityZ *= 0.85F;
        }
    }

    public record Factory(SpriteProvider sprite) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld,
                                       double x, double y, double z, double xd, double yd, double zd) {
            ADSporeParticle particle = new ADSporeParticle(clientWorld, x, y, z, xd, yd, zd);
            particle.setSprite(this.sprite);
            return particle;
        }
    }
}
