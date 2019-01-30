package us.myles_selim.newmagicmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import us.myles_selim.newmagicmod.ingredients.SpellIngredient;
import us.myles_selim.newmagicmod.ingredients.stack.special.SpecialStackSpellIngredient;

public class StackIngCreativeTab extends CreativeTabs {

	public StackIngCreativeTab() {
		super(NewMagicMod.MOD_ID + "_ingredients");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.CAULDRON);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		for (SpellIngredient ing : ModRegistry.Registries.SPELL_INGREDIENTS.getValuesCollection()) {
			if (ing instanceof SpecialStackSpellIngredient)
				items.add(((SpecialStackSpellIngredient) ing).getStack());
		}
	}

}