package net.jolene.thumbandthicket.effect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class PricklyEffect extends StatusEffect {
    public static final Identifier MODIFIER_ID_1 =
            Identifier.of("thumbandthicket", "prickly");
    public PricklyEffect(StatusEffectCategory category, int color) {
        super(category, color);

        this.addAttributeModifier(
                EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
                MODIFIER_ID_1,
                0.25,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}