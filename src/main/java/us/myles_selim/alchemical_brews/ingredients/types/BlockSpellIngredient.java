package us.myles_selim.alchemical_brews.ingredients.types;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import us.myles_selim.alchemical_brews.blocks.tiles.TileBrewingCauldron;
import us.myles_selim.alchemical_brews.ingredients.IngredientStack;
import us.myles_selim.alchemical_brews.ingredients.SpellIngredient;

// TODO: find a way to attach position of ing to this, mainly for BlockSpellIngredient#onCraft
public abstract class BlockSpellIngredient extends SpellIngredient {

	private static final Map<IBlockState, BlockSpellIngredient> STATE_MAP = new HashMap<>();

	private final IBlockState state;

	public BlockSpellIngredient(String name, IBlockState state) {
		super(name);
		STATE_MAP.put(state, this);
		this.state = state;
	}

	public BlockSpellIngredient(ResourceLocation name, IBlockState state) {
		super(name);
		STATE_MAP.put(state, this);
		this.state = state;
	}

	public BlockSpellIngredient(String modID, String name, IBlockState state) {
		super(modID, name);
		STATE_MAP.put(state, this);
		this.state = state;
	}

	public IBlockState getState() {
		return this.state;
	}

	@Override
	public boolean isStrict() {
		return false;
	}

	@Override
	public void onCraft(TileBrewingCauldron cauldron, IngredientStack stack) {
		BlockPos pos = getPos(stack);
		if (pos == null)
			return;
		if (cauldron.getWorld().getBlockState(pos).equals(this.getState()))
			cauldron.getWorld().destroyBlock(pos, false);
	}

	public BlockPos getPos(IngredientStack stack) {
		NBTTagCompound posNbt = stack.getTag().getCompoundTag("pos");
		if (posNbt == null)
			return null;
		return new BlockPos(posNbt.getInteger("x"), posNbt.getInteger("y"), posNbt.getInteger("z"));
	}

	public static boolean isBlockSpellIngredient(IBlockState state) {
		return STATE_MAP.containsKey(state);
	}

	public static BlockSpellIngredient getIngredient(IBlockState state) {
		if (!isBlockSpellIngredient(state))
			return null;
		return STATE_MAP.get(state);
	}

}