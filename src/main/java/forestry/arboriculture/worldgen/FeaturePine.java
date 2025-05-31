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

public class FeaturePine extends FeatureTree {

	public FeaturePine(ITreeGenData tree) {
		super(tree, 11, 4);
	}

	@Override
	protected void generateLeaves(LevelAccessor level, RandomSource rand, TreeBlockTypeLeaf leaf, TreeContour contour, BlockPos startPos) {



		int vRadius = (int)(height*0.4f);

		//Make the initial leaf body - this is mainly to make the point
		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, height-2, girth/2), 1+(girth/2f), vRadius, 1+(girth/2f), leaf, FeatureHelper.EnumReplaceMode.SOFT, contour );

		//Make the 'layers'
		int leafSpawn = height+1 ;
		for (int y = 0; y < (vRadius*2)-6; y+=2){
			FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, leafSpawn--, girth/2), 1+(girth/2f), 1, 1+(girth/2f), leaf, FeatureHelper.EnumReplaceMode.SOFT, contour );
			FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, leafSpawn--, girth/2), 2+(girth/2f), 1, 2+(girth/2f), leaf, FeatureHelper.EnumReplaceMode.SOFT, contour );
		}

		//Add the last little ring at the base of the canopy
		leafSpawn--;
		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, leafSpawn--, girth/2), 0.5f+(girth/2f), 1, 0.5f+(girth/2f), leaf, FeatureHelper.EnumReplaceMode.SOFT, contour );
		FeatureHelper.generateEllipsoid(level, startPos.offset(girth/2, leafSpawn--, girth/2), 1+(girth/2f), 1, 1+(girth/2f), leaf, FeatureHelper.EnumReplaceMode.SOFT, contour );

	}
}
