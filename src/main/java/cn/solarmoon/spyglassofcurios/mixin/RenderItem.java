package cn.solarmoon.spyglassofcurios.mixin;

import cn.solarmoon.spyglassofcurios.Client.Method.FindSpyglassInCurio;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.solarmoon.spyglassofcurios.Client.Constants.*;
import static cn.solarmoon.spyglassofcurios.Client.RegisterClient.useSpyglass;

@Mixin(ItemInHandLayer.class)
public abstract class RenderItem<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {

    public RenderItem(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Shadow protected abstract void renderArmWithItem(LivingEntity p_117185_, ItemStack p_117186_, ItemTransforms.TransformType p_270970_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_);

    //按住时替换副手渲染
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "HEAD"), cancellable = true)
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T t, float p1, float p2, float p3, float p4, float p5, float p6, CallbackInfo ci) {
        if (useSpyglass.isDown() && pressCheck && !t.isUsingItem() && check) {
            FindSpyglassInCurio curioFinder = new FindSpyglassInCurio();
            boolean hasSpyglass = curioFinder.hasSpyglass(mc.player);
            if(!t.getMainHandItem().isEmpty() && hasSpyglass) {
                this.renderArmWithItem(t, t.getMainHandItem(), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, i);
            }
            ci.cancel();
        }
    }

}
