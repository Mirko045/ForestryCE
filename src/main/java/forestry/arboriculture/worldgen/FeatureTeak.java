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
import java.util.List;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;

public class FeatureTeak extends FeatureTree {

	public FeatureTeak(ITreeGenData tree) {
		super(tree, 7, 4, 5);
	}

	@Override
	public void generateTrunk(LevelAccessor level, List<BlockPos> logOrigins, List<BlockPos> branchCoords, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, logOrigins, rand, wood, startPos, height, girth, 0, 0, null, 0);

		int branchWidth = (height / 3)-1;

		if (height > 4)
        	branchCoords.addAll(FeatureHelper.generateBranches(level, rand, wood, startPos.offset(0, height - 3, 0), girth, 0.2f, 0.33f, branchWidth, 1, 0.5f));

		if (height > 6)
			branchCoords.addAll(FeatureHelper.generateBranches(level, rand, wood, startPos.offset(0, height - 5, 0), girth, 0.2f, 0.2f, branchWidth, 1, 0.75f));

	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		float r = 3 + (girth/2f);
		float ri = r/2;

		FeatureHelper.generateEllipsoid(level,startPos.offset(girth/2, height+1,girth/2), ri, 1.5f, ri, 1.5f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);


		int leafSpawn = height;

		float radMult = 1.5f;

		while (leafSpawn >= (height/5)*3  ){

			radMult /= 2;
			if (radMult <= 0.05f) break;

			float ro = r * (1-radMult);

			FeatureHelper.generateEllipsoid(level,startPos.offset((girth/2), leafSpawn--,(girth/2)), ro, 1.5f, ro, 1.5f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		}


		for(BlockPos branchEnd: contour.getBranchEnds()){

			//float lRadius = (rand.nextFloat() * 0.5f) + 1.25f;
			FeatureHelper.generateEllipsoid(level, branchEnd.offset(0,1,0), 1, 1.5f, 1, 1.25f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);
			FeatureHelper.generateEllipsoid(level, branchEnd.offset(0,0,0), 2, 1.5f, 2, 1.75f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		}

	}
}
