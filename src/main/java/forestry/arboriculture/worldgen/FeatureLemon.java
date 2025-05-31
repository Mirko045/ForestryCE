/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.arboriculture.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeatureLemon extends FeatureTree {

	public FeatureLemon(ITreeGenData tree) {
		super(tree, 3, 2, 2);
	}

	//Generation code has been copy-pasted from Orange trees, which is fine because they're so closely related.
	@Override
	public void generateTrunk(LevelAccessor level, List<BlockPos> logOrigins, List<BlockPos> branchCoords, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, logOrigins, rand, wood, startPos, height, girth, 0, 0, null, 0);


		int branchSpawn = height - 1;
		int branchCount = 1;
		float heightIncreasePercent = height/3f;


		do {

			//try place more branches at the lower canopies
			float branchChance = 0.75f;
			if (branchSpawn >= 4 && branchSpawn <= 6) branchChance = 0.9f;

			branchCoords.addAll(FeatureHelper.generateBranches(level, rand, wood,
					startPos.offset(0, branchSpawn, 0),
					girth,
					0.4f, 0.15f,
					(girth/3)+(int)(branchCount * (1 + (heightIncreasePercent-1))),
					2, branchChance));
			branchCount++;
			branchSpawn-=2;

			if (branchSpawn < 4 && branchCount == 2 && height > 6) branchSpawn = 4;
		} while (branchSpawn >= 4);

	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		float heightIncreasePercent = height/3f;

		float radius = (float)Math.ceil(girth/1.5f)+1;
		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, height-(int)heightIncreasePercent, girth/2), radius, 1.5f+ Math.min(heightIncreasePercent-1, 1)  , radius, 1.75f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, height-(int)heightIncreasePercent, girth/2), radius, heightIncreasePercent, radius, 1.75f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		for (BlockPos branchEnd: contour.getBranchEnds()){
			FeatureHelper.generateCylinderFromPos(level, leaf, branchEnd, heightIncreasePercent+0.5f, 1f, 1+(int)heightIncreasePercent, FeatureHelper.EnumReplaceMode.SOFT, contour);
		}

	}
}
