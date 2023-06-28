package union.xenfork.n.cobblestone.compressed.mod.item;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class CobblestoneBlockItem extends BlockItem {
    public CobblestoneBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack defaultInstance = super.getDefaultInstance();
        CompoundTag tag = defaultInstance.getTag() != null ? defaultInstance.getTag() : new CompoundTag();
        if (!tag.contains("compressed")) {
            tag.putString("compressed", "0");
        }
        defaultInstance.setTag(tag);
        return defaultInstance;
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {

        if (itemStack.getTag() != null) {
            CompoundTag tag = itemStack.getTag();
            if (tag.contains("compressed")) {
                return Component.translatable("%sX%s".formatted(this.getDescriptionId(itemStack), tag.getString("compressed")));
            }
        }
        return Component.translatable(this.getDescriptionId(itemStack));
    }
}
