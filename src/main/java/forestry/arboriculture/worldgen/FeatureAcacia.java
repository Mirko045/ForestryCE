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
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;

public class FeatureAcacia extends FeatureTree {
	public FeatureAcacia(ITreeGenData tree) {
		super(tree, 5, 2);
	}

	@Override
	public Set<BlockPos> generateTrunk(LevelAccessor level, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, rand, wood, startPos, height - 3, girth, 0, 0, null, 0);

		Set<BlockPos> branches = new HashSet<>();

		for (Direction d: FeatureHelper.DirectionHelper.VALUES){
			FeatureHelper.generateTreeTrunk(level, rand, wood, startPos.offset(0, height-3, 0 ), 3, girth, 0, 0, d, 3);
		}

		int y = height-5;

		if (height > 7) {
			while (y >= 3) {

				branches.addAll(FeatureHelper.generateBranches(level, rand, wood, startPos.offset(0, y, 0), girth, 0.25f, 0.3f, 3, 1, 0.5f));

				y -= rand.nextIntBetweenInclusive(3, 5);
			}
		}

		return branches;
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		for (int y = 1; y <= 3; y++){

			//These numbers may seem as if they're arbitrary. That's because they are.
			float rad = (4f + (girth/1.5f)) * ( 1.2f-(1f/(y)) );
			float radMult = 1.125f + (rand.nextFloat()/2f);

			FeatureHelper.generateCylinderFromTreeStartPos(level, leaf, startPos.offset(0, height+2-y, 0), girth, rad, radMult, 1, FeatureHelper.EnumReplaceMode.SOFT, contour);
		}

		for (BlockPos blockPos: contour.getBranchEnds()) {
			FeatureHelper.generateCylinderFromPos(level, leaf, blockPos.offset(0,+1,0), 1.5f,  1, FeatureHelper.EnumReplaceMode.SOFT, contour );
			FeatureHelper.generateCylinderFromPos(level, leaf, blockPos, 2f, 1.5f, 1, FeatureHelper.EnumReplaceMode.SOFT, contour );
		}

	}
}
