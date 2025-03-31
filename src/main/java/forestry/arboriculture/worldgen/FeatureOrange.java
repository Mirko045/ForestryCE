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

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import java.util.HashSet;
import java.util.Set;

public class FeatureOrange extends FeatureTree {
	public FeatureOrange(ITreeGenData tree) {
		super(tree, 3, 3, 3);
	}

	@Override
	public Set<BlockPos> generateTrunk(LevelAccessor level, RandomSource rand, TreeBlockTypeLog wood, BlockPos startPos) {
		FeatureHelper.generateTreeTrunk(level, rand, wood, startPos, height, girth, 0, 0, null, 0);

		Set<BlockPos> branches = new HashSet<>();

		int branchSpawn = height - 1;
		int branchCount = 1;
		do {

			branches.addAll(FeatureHelper.generateBranches(level, rand, wood,
					startPos.offset(0, branchSpawn, 0),
					girth,
					0.2f, 0.25f,
					(girth/3)+branchCount,
					2, 0.75f));
			branchCount++;
			branchSpawn-=2;
		} while (branchSpawn > height/4);

		return branches;
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		/*float radius = (float)Math.ceil(girth/1.5f)+1;
		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, height, girth/2), radius, 1f, radius, 1.75f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		if (height >= 5) {
			FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, (2*height)/3, girth/2), radius+1, height/3f, radius+1, 1.75f, leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		}*/

		int spawnrange = height+1-rand.nextIntBetweenInclusive(1,2); //leave 1-2 blocks of clearance at the base of the tree
		int leafspawn = height+1;

		float topSpawnRange = (2f*spawnrange)/5;
		float bottomSpawnRange = (3f*spawnrange)/5;

		float radius = (float)Math.ceil(girth/1.5f);
		//Top Blob
		FeatureHelper.generateEllipsoid(level,
				startPos.offset(girth/2, leafspawn-((int)topSpawnRange/2), girth/2),
				radius, topSpawnRange/2, radius, 1.75f,
				leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);
		//Bottom Blob
		FeatureHelper.generateEllipsoid(level,
				startPos.offset(girth/2, leafspawn-((int)topSpawnRange + (int)(bottomSpawnRange/2))-1, girth/2),
				radius+1, bottomSpawnRange/2, radius+1, 1.75f,
				leaf, FeatureHelper.EnumReplaceMode.SOFT, contour);

		for (BlockPos branchEnd : contour.getBranchEnds()) {
			float r = 0.8f+(girth/2f);
			//FeatureHelper.generateEllipsoid(level, branchEnd.offset(0,1,0), r, 1f, r, 2f, leaf, FeatureHelper.EnumReplaceMode.AIR, contour);
			FeatureHelper.generateCylinderFromPos(level, leaf, branchEnd, r, 2, 2, FeatureHelper.EnumReplaceMode.AIR, contour);
		}

	}

	public void poos(){
		System.out.println("Hello, world!");
	}
}
