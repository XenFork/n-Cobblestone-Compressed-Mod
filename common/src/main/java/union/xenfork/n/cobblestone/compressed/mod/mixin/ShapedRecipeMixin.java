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
        ItemStack iItemStack = ItemStack.EMPTY;
        for (Ingredient recipeItem : recipeItems) {
            if (recipeItem.isEmpty()) return result;
            ItemStack itemStack = recipeItem.getItems()[0];
            if (!(itemStack.getItem() instanceof CobblestoneBlockItem)) {
                return result;
            } else {
                if (iItemStack.isEmpty()) {
                    iItemStack = recipeItem.getItems()[0];
                } else {
                    CompoundTag tag = iItemStack.getTag();
                    CompoundTag tag1 = recipeItem.getItems()[0].getTag();
                    if (tag == null && tag1 != null) return result;
                    if (tag1 == null)
                        return result;
                    if (tag.contains("compressed") && (!tag.contains("compressed"))) return result;
                    if (!(tag.getString("compressed").equals(tag1.getString("compressed")))) {
                        return result;
                    }
                    if (tag1.contains("compressed")) {
                        return result;
                    }
                }
            }
        }

        CompoundTag tag = iItemStack.getTag();
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
