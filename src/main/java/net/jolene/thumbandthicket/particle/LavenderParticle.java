package net.jolene.thumbandthicket.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class LavenderParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    protected LavenderParticle(ClientWorld world,
                               double x, double y, double z,
                               SpriteProvider spriteProvider) {
        super(world, x, y, z);

        this.spriteProvider = spriteProvider;

        this.velocityMultiplier = 0.98F;
        this.gravityStrength = 0.0F;

        this.velocityX = (random.nextDouble() - 0.5) * 0.003;
        this.velocityY = 0.008 + random.nextDouble() * 0.004;
        this.velocityZ = (random.nextDouble() - 0.5) * 0.003;

        this.maxAge = 60 + random.nextInt(20);

        this.scale = 1.5F;

        this.red = 1F;
        this.green = 1F;
        this.blue = 1F;

        this.alpha = 0F;

        this.angle = random.nextFloat() * ((float)Math.PI * 2F);
        this.prevAngle = this.angle;

        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.prevAngle = this.angle;

        super.tick();

        this.setSpriteForAge(spriteProvider);

        int fade = 10;

        if (age < fade) {
            alpha = age / (float) fade;
        } else if (age > maxAge - fade) {
            alpha = (maxAge - age) / (float) fade;
        } else {
            alpha = 1F;
        }

        angle += 0.01F;

        velocityY += 0.00015;

        velocityX *= 0.995;
        velocityZ *= 0.995;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getBrightness(float tint) {
        return 0xF000F0;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType parameters,
                                       ClientWorld world,
                                       double x,
                                       double y,
                                       double z,
                                       double velocityX,
                                       double velocityY,
                                       double velocityZ) {
            return new LavenderParticle(world, x, y, z, spriteProvider);
        }
    }
}
