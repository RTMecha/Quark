package vazkii.quark.content.world.feature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import vazkii.quark.content.world.module.GlimmeringWealdModule;

public class GlowExtrasFeature extends Feature<NoneFeatureConfiguration> {

	public GlowExtrasFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	public static PlacedFeature placed(ConfiguredFeature<NoneFeatureConfiguration, ?> f) {
		return f.placed(CountPlacement.of(200), 
				InSquarePlacement.spread(), 
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, 
				RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
		WorldGenLevel worldgenlevel = config.level();
		BlockPos blockpos = config.origin();
		Random rng = config.random();

		MutableBlockPos setPos = new MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
		for(int k = -6; k < 7; k++) {
			setPos.set(blockpos.getX(), blockpos.getY() + k, blockpos.getZ());

			if(worldgenlevel.isStateAtPosition(setPos, BlockState::isAir)) {
				double res = rng.nextDouble();
				if(res > 0.7) { // try to place shrub
					if(worldgenlevel.isStateAtPosition(setPos.below(), s -> s.getBlock() == Blocks.DEEPSLATE))
						worldgenlevel.setBlock(setPos, GlimmeringWealdModule.glow_lichen_growth.defaultBlockState(), 2);
				} 

				else if(res > 0.55) { // try to place lichen
					for(Direction dir : Direction.values()) {
						if(worldgenlevel.isStateAtPosition(setPos.relative(dir), s -> s.getBlock() == Blocks.DEEPSLATE)) {
							BlockState place = Blocks.GLOW_LICHEN.defaultBlockState();
							for(Direction dir2 : Direction.values()) {
								BooleanProperty prop = GlowLichenBlock.getFaceProperty(dir2);
								place = place.setValue(prop, dir == dir2);
							}

							worldgenlevel.setBlock(setPos, place, 2);
							break;
						}
					}
				}
			}
		}

		return true;
	}

}
