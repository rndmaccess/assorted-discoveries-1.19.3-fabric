package rndm_access.assorteddiscoveries.client.block_entity;

import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import rndm_access.assorteddiscoveries.common.block_entity.ADDyedCampfireBlockEntity;

public class ADDyedCampfireBlockEntityRenderer implements BlockEntityRenderer<ADDyedCampfireBlockEntity> {
    public ADDyedCampfireBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(ADDyedCampfireBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction direction = entity.getCachedState().get(CampfireBlock.FACING);
        DefaultedList<ItemStack> defaultedList = entity.getItemsBeingCooked();
        int k = (int)entity.getPos().asLong();

        for(int l = 0; l < defaultedList.size(); ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack != ItemStack.EMPTY) {
                matrices.push();
                matrices.translate(0.5D, 0.44921875D, 0.5D);
                Direction direction2 = Direction.fromHorizontal((l + direction.getHorizontal()) % 4);
                float g = -direction2.asRotation();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                matrices.translate(-0.3125D, -0.3125D, 0.0D);
                matrices.scale(0.375F, 0.375F, 0.375F);
                MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, k + l);
                matrices.pop();
            }
        }
    }
}
