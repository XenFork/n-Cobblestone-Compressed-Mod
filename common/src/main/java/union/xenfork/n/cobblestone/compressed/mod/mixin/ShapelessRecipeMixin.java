package union.xenfork.n.cobblestone.compressed.mod.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import union.xenfork.n.cobblestone.compressed.mod.item.CobblestoneBlockItem;
import union.xenfork.n.cobblestone.compressed.mod.util.StringInteger;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin {
    @Shadow @Final
    NonNullList<Ingredient> ingredients;

    @Redirect(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack assemble(ItemStack result) {
        String integer = null;
        if (!(result.getItem() instanceof CobblestoneBlockItem)) {
            return result;
        }
        int i = 0;
        Ingredient ing = Ingredient.EMPTY;
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }
            ItemStack itemStack = ingredient.getItems()[0];
            if (!(itemStack.getItem() instanceof CobblestoneBlockItem)) {
                return result;
            } else {
                ing = ingredient;
            }
            i++;
        }
        if (i == 1) {
            if (ing.isEmpty()) {
                return result;
            } else {
                ItemStack item = ing.getItems()[0];
                CompoundTag tag = item.getTag();
                if (tag == null) {
                    return ItemStack.EMPTY;
                } else {
                    if (!tag.contains("compressed")) {
                        return ItemStack.EMPTY;
                    } else if (tag.getString("compressed").equals("0")) {
                        return ItemStack.EMPTY;
                    } else {
                        tag.putString("compressed", StringInteger.minusOne(tag.getString("compressed")));
                        result.setTag(tag);
                        return result;
                    }
                }
            }
        }
        return result;
    }
}
