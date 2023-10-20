package org.teacon.xkdeco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.data.ModelData;
import org.teacon.xkdeco.block.SpecialWallBlock;
import org.teacon.xkdeco.blockentity.WallBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class WallRenderer implements BlockEntityRenderer<WallBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public WallRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    // When in doubt, refer to this Forge re-design document
    // https://forge-render-refactor.notion.site/forge-render-refactor/ecd69011d01042c48a0fca80696cb4da?v=47332327988948ebaf911b06b2aa1f5d&p=bad8c5ecb56745239b2a008365d803d0&pm=s

    @Override
    public void render(WallBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        var level = pBlockEntity.getLevel();
        var state = pBlockEntity.getBlockState();
        if (state.getBlock() instanceof SpecialWallBlock wall) {
            var wallState = pBlockEntity.withState(state, wall.getWallDelegate(), BlockStateProperties.UP);
            if (level == null) {
                this.blockRenderer.renderSingleBlock(wallState, pPoseStack,
                        pBufferSource, pPackedLight, pPackedOverlay, ModelData.EMPTY, null);
            }
        }
    }
}
