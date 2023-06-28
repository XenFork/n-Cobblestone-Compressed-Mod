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

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
    @Shadow @Final
    NonNullList<Ingredient> recipeItems;

    @Redirect(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack assemble(ItemStack stack) {
        int i = 0;
        NonNullList<Ingredient> list = NonNullList.withSize(9, Ingredient.EMPTY);
        for (Ingredient recipeItem : recipeItems) {
            if (!recipeItem.isEmpty()) i++;
        }
        System.out.println(i);

//        String integer = null;
//        for (Ingredient recipeItem : recipeItems) {
//            CompoundTag tag = recipeItem.getItems()[0].getTag();
//            if (tag != null) {
//                if (tag.contains("compressed")) {
//                    integer = tag.getString("compressed");
//                }
//            } else {
//                if (integer == null) {
//                    integer = "0";
//                } else {
//                    if (!integer.equals("0")) {
//                        return ItemStack.EMPTY;
//                    }
//                }
//            }
//        }
        return stack;
    }
}
