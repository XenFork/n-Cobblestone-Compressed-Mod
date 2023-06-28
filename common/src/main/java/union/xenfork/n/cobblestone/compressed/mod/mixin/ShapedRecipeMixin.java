package union.xenfork.n.cobblestone.compressed.mod.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import union.xenfork.n.cobblestone.compressed.mod.item.CobblestoneBlockItem;
import union.xenfork.n.cobblestone.compressed.mod.util.StringInteger;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
    @Shadow @Final
    NonNullList<Ingredient> recipeItems;

    @Redirect(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack assemble(ItemStack result) {
        if (!(result.getItem() instanceof CobblestoneBlockItem)) {
            return result;
        }
        Ingredient ing = Ingredient.EMPTY;
        for (Ingredient recipeItem : recipeItems) {
            if (recipeItem.isEmpty()) return result;
            ItemStack itemStack = recipeItem.getItems()[0];
            if (!(itemStack.getItem() instanceof CobblestoneBlockItem)) {
                return result;
            } else {
                if (ing.isEmpty()) ing = recipeItem;
                else {
                    CompoundTag tag = ing.getItems()[0].getTag();
                    CompoundTag tag1 = recipeItem.getItems()[0].getTag();
                    if (tag == null) {
                        if (tag1 != null) {
                            return result;
                        }
                    } else {
                        if (tag1 == null) {
                            return result;
                        } else {
                            if (tag.contains("compressed")) {
                                if (!tag1.contains("compressed")) {
                                    return result;
                                } else {
                                    if (!(tag.getString("compressed").equals(tag1.getString("compressed")))) {
                                        return result;
                                    }
                                }
                            } else {
                                if (tag1.contains("compressed")) {
                                    return result;
                                }
                            }
                        }
                    }
                }
            }
        }
        ItemStack itemStack = ing.getItems()[0];
        CompoundTag tag = itemStack.getTag();
        if (tag == null) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("compressed", "1");
            result.setTag(compoundTag);
        } else {
            CompoundTag compoundTag = tag.copy();
            compoundTag.putString("compressed", StringInteger.addOne(tag.getString("compressed")));
            result.setTag(compoundTag);
        }
        return result;
    }
}
