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

import java.util.Set;

public class FeatureLemon extends FeatureTree {

	public FeatureLemon(ITreeGenData tree) {
		super(tree, 3, 3, 3);
	}

	@Override
	public Set<BlockPos> generateTrunk(LevelAccessor level, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, rand, wood, startPos, height, girth, 0, 0, null, 0);


		return FeatureHelper.generateBranches(level, rand, wood,
				startPos.offset(0, height/2, 0),
				girth,
				0, 0.25f,
				(girth/3)+1,
				2, 0.75f);
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {


		int leafSpawn = height-1;

		int radius = (int)Math.ceil(girth/2f)+1;
		do {
			FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, leafSpawn--, girth/2), radius, 1f, radius, 1.5f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);
		} while (leafSpawn > 2);


		for (BlockPos branchEnd : contour.getBranchEnds()) {
			FeatureHelper.generateCylinderFromPos(level, leaf, branchEnd, 1+(int)(girth/2f), 2+((girth-1)/2), FeatureHelper.EnumReplaceMode.AIR, contour);
		}

	}
}
