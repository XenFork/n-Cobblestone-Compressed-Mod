package union.xenfork.n.cobblestone.compressed.mod.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import union.xenfork.n.cobblestone.compressed.mod.util.StringInteger;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    private static int max = 3;
    private static void compressed(RecipeType<?> recipeType, ImmutableMap.Builder<ResourceLocation, Recipe<?>> resourceLocationRecipeBuilder) {
        if (recipeType.equals(RecipeType.CRAFTING)) {
            String integer = "0";
            String cobblestoneAddXn = "cobblestone_add_xn";
            while (integer.length() <= 10) {
                ItemStack itemStack = new ItemStack(Items.COBBLESTONE);
                CompoundTag tag = itemStack.getTag() != null ? itemStack.getTag().copy() : new CompoundTag();
                tag.putString("compressed", integer);
                itemStack.setTag(tag);
                ItemStack result = itemStack.copy();
                integer = StringInteger.addOne(integer);
                assert result.getTag() != null;
                result.getTag().putString("compressed", integer);

                NonNullList<Ingredient> recipeItems = NonNullList.withSize(9, Ingredient.of(itemStack));

                ResourceLocation resourceLocation = new ResourceLocation("cobblestone_add_x%s".formatted(integer));
                ShapedRecipe shapedRecipe = new ShapedRecipe(
                        resourceLocation,
                        cobblestoneAddXn,
                        CraftingBookCategory.BUILDING,
                        3,
                        3,
                        recipeItems,
                        result
                );
                resourceLocationRecipeBuilder.put(resourceLocation, shapedRecipe);
            }
        }
    }

    private static void unCompressed(RecipeType<?> recipeType, ImmutableMap.Builder<ResourceLocation, Recipe<?>> resourceLocationRecipeBuilder) {
        if (recipeType.equals(RecipeType.CRAFTING)) {
            String integer = "1";
            String cobblestoneMinusXn = "cobblestone_minus_xn";
            while (integer.length() <= 10) {
                ItemStack itemStack = new ItemStack(Items.COBBLESTONE);
                CompoundTag tag = itemStack.getTag() != null ? itemStack.getTag().copy() : new CompoundTag();
                tag.putString("compressed", integer);
                itemStack.setTag(tag);
                ItemStack result = itemStack.copy();
                assert result.getTag() != null;
                result.getTag().putString("compressed", StringInteger.minusOne(integer));
                ResourceLocation resourceLocation = new ResourceLocation("cobblestone_minus_x%s".formatted(integer));
                ShapelessRecipe shapelessRecipe = new ShapelessRecipe(
                        resourceLocation,
                        cobblestoneMinusXn,
                        CraftingBookCategory.BUILDING,
                        result,
                        NonNullList.withSize(1, Ingredient.of(itemStack))
                );
                resourceLocationRecipeBuilder.put(resourceLocation, shapelessRecipe);
                integer = StringInteger.addOne(integer);
            }
        }
    }

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;collect(Ljava/util/stream/Collector;)Ljava/lang/Object;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void apply(Map<ResourceLocation, JsonElement> map,
                       ResourceManager resourceManager,
                       ProfilerFiller profilerFiller,
                       CallbackInfo ci,
                       Map<RecipeType<?>, ImmutableMap.Builder<ResourceLocation, Recipe<?>>> map2) {
        map2.forEach(RecipeManagerMixin::compressed);
        map2.forEach(RecipeManagerMixin::unCompressed);
    }
}
