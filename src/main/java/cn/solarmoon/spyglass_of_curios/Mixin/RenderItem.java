package cn.solarmoon.spyglass_of_curios.Mixin;

import cn.solarmoon.spyglass_of_curios.Common.Items.Spyglass.Method.FindSpyglassInCurio;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.solarmoon.spyglass_of_curios.Common.Items.RegisterItems.useSpyglass;
import static cn.solarmoon.spyglass_of_curios.Util.Constants.*;

@Mixin(ItemInHandLayer.class)
public abstract class RenderItem<T extends LivingEntity>{

    @Shadow protected abstract void renderArmWithItem(LivingEntity p_117185_, ItemStack p_117186_, ItemDisplayContext p_270970_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_);

    //按住时替换副手渲染
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "HEAD"), cancellable = true)
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T t, float p1, float p2, float p3, float p4, float p5, float p6, CallbackInfo ci) {
        FindSpyglassInCurio curioFinder = new FindSpyglassInCurio();
        if (useSpyglass.isDown() && curioFinder.hasSpyglass(mc.player) && (!usingInHand || usingInCurio)) {
            this.renderArmWithItem(t, t.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, i);
            ci.cancel();
        }
    }

}

