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

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import forestry.api.arboriculture.ITreeGenData;
import forestry.core.worldgen.FeatureHelper;

public class FeatureBalsa extends FeatureTree {

	public FeatureBalsa(ITreeGenData tree) {
		super(tree, 6, 6);
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {

		int leafRadius = (girth/2) + 1;

		float heightMult = (height/6f); //Taller trees have a longer canopy

		int leafSpawn = height + 1;

		FeatureHelper.generateCylinderFromPos(level, leaf, startPos.offset(girth/2, leafSpawn--, girth/2), girth/2f, 1, FeatureHelper.EnumReplaceMode.SOFT, contour);

		int canopyLength = (int)Math.min(Math.max((4*heightMult), 4), 8);

		while (canopyLength > 0) {

			float failChance = 0.45f;
			FeatureHelper.generateCylinderFromPosWithChance(level, leaf, startPos.offset(girth/2, leafSpawn--, girth/2), leafRadius, 2f, 1, FeatureHelper.EnumReplaceMode.SOFT, contour, rand, failChance);

			canopyLength--;
		}

	}
}
