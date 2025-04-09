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

package forestry.core.items;

import java.util.Locale;

import forestry.api.core.IItemSubtype;

import net.minecraft.world.item.Item;

public class ItemFruit extends ItemForestryFood {

	public enum EnumFruit implements IItemSubtype {
		CHERRY(1, 0.4f, 16),
		WALNUT(1, 0.6f, 16),
		CHESTNUT(1, 0.8f, 16),
		LEMON,
		PLUM,
		DATES(1, 0.2f, 16),
		PAPAYA(4, 3.2f, 32),

		PEAR,
		ORANGE,
		COCONUT(2, 4.2f, 64), //Not sure what the best stat is here. Low hunger, high saturation and use time seems fair???
		OLIVE(1, 0.2f, 16),
		FEIJOA(2, 1.2f, 16);

		private final String name;

		private final int heal; //the number of half-shanks to heal
		private final float saturation;
		private final int useTime;

		EnumFruit() {
			this.name = name().toLowerCase(Locale.ENGLISH);
			//The default stats is the same as the vanilla apple.
			this.heal = 4;
			this.saturation = 2.4f;
			this.useTime = 32;
		}

		//Constructor for overriding default values
		EnumFruit(int h, float s, int u){
			this.name = name().toLowerCase(Locale.ENGLISH);
			this.heal = h;
			this.saturation = s;
			this.useTime = u;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
	}

	private final EnumFruit type;
	public ItemFruit(EnumFruit type) {
		super(type.heal, type.saturation, type.useTime);
		this.type = type;
	}

	public EnumFruit getType() {
		return type;
	}


	@Override
	public boolean canBeDepleted() {
		return false;
	}
}
