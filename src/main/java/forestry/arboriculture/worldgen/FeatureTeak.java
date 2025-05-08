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

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;

public class FeatureTeak extends FeatureTree {

	public FeatureTeak(ITreeGenData tree) {
		super(tree, 8, 3);
	}

	@Override
	public Set<BlockPos> generateTrunk(LevelAccessor level, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, rand, wood, startPos, height, girth, 0, 0, null, 0);

		int branchWidth = (height / 3)-1;

        Set<BlockPos> branches = new HashSet<>(FeatureHelper.generateBranches(level, rand, wood, startPos.offset(0, height - 2, 0), girth, 0.35f, 0.33f, branchWidth, 1, 0.5f));

		if (height > 8) {
			branches.addAll(FeatureHelper.generateBranches(level, rand, wood, startPos.offset(0, height - 5, 0), girth, 0.2f, 0.2f, branchWidth, 1, 0.75f));
		}

		return branches;
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		float r = 2 + (girth/2f);

		FeatureHelper.generateEllipsoid(level,startPos.offset(girth/2, height,girth/2), r-1, 1.5f, r-1, 1.5f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		if (height > 4)
			FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, height-2,girth/2), r, 2.5f, r, 1.5f, leaf,  FeatureHelper.EnumReplaceMode.SOFT, contour);

		for(BlockPos branchEnd: contour.getBranchEnds()){

			float lRadius = (rand.nextFloat() * 0.5f) + 1.25f;
			FeatureHelper.generateEllipsoid(level, branchEnd.offset(0,1,0), 2, 1.5f, 2, lRadius-0.5f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);
			FeatureHelper.generateEllipsoid(level, branchEnd, 2, 1.5f, 2, lRadius, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		}

	}
}
