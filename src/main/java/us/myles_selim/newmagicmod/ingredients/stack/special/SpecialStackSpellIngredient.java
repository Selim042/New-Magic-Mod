package us.myles_selim.newmagicmod.ingredients.stack.special;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.myles_selim.newmagicmod.ModRegistry;
import us.myles_selim.newmagicmod.NewMagicMod;
import us.myles_selim.newmagicmod.ingredients.SpellIngredient;

public abstract class SpecialStackSpellIngredient extends SpellIngredient {

	public static final String INGREDIENT_KEY = NewMagicMod.MOD_ID + "_ingredient";

	public SpecialStackSpellIngredient(String name) {
		super(name);
	}

	public SpecialStackSpellIngredient(ResourceLocation name) {
		super(name);
	}

	public SpecialStackSpellIngredient(String modID, String name) {
		super(modID, name);
	}

	public final ItemStack getStack() {
		ItemStack ret = getIngredientStack();
		NBTTagCompound nbt = ret.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			ret.setTagCompound(nbt);
		}
		nbt.setString(INGREDIENT_KEY, this.getRegistryName().toString());
		return ret;
	}

	protected abstract ItemStack getIngredientStack();

	public boolean matchesStack(ItemStack stack) {
		if (ItemStack.areItemStacksEqual(getStack(), stack))
			return true;
		return false;
	}

	public String getNBTKey() {
		return NewMagicMod.MOD_ID + ":" + this.getRegistryName().getResourcePath();
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {}

	public static boolean isSpecialSpellIngredient(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(INGREDIENT_KEY);
	}

	public static SpecialStackSpellIngredient getIngredient(ItemStack stack) {
		if (!isSpecialSpellIngredient(stack))
			return null;
		String locationS = stack.getTagCompound().getString(INGREDIENT_KEY);
		return (SpecialStackSpellIngredient) ModRegistry.Registries.SPELL_INGREDIENTS
				.getValue(new ResourceLocation(locationS));
	}

}