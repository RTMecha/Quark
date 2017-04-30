package vazkii.quark.decoration.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import vazkii.arl.util.ModelHandler;
import vazkii.quark.decoration.entity.EntityFlatItemFrame;

public class RenderGlassItemFrame extends RenderFlatItemFrame {

	public static final IRenderFactory FACTORY = (RenderManager manager) -> new RenderGlassItemFrame(manager);
	
	public RenderGlassItemFrame(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	protected void renderModel(EntityFlatItemFrame entity, Minecraft mc) {
		BlockRendererDispatcher blockrendererdispatcher = mc.getBlockRendererDispatcher();
		ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();

		if(entity.getDisplayedItem().isEmpty()) {
			IBakedModel ibakedmodel = modelmanager.getModel(ModelHandler.resourceLocations.get("glass_item_frame_world"));
			blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	protected void transformItem() {
		GlStateManager.translate(0F, 0F, 0.05F);
		GlStateManager.scale(0.75F, 0.75F, 0.75F);
	}

}
