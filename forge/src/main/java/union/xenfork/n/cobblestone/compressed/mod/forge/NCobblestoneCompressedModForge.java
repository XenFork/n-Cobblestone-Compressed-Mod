package union.xenfork.n.cobblestone.compressed.mod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import union.xenfork.n.cobblestone.compressed.mod.NCobblestoneCompressedMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

@Mod(NCobblestoneCompressedMod.MOD_ID)
public class NCobblestoneCompressedModForge {
    public NCobblestoneCompressedModForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(NCobblestoneCompressedMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
            NCobblestoneCompressedMod.init();
    }

    @SubscribeEvent
    public void recipeData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<DataProvider>) NRecipeProvider::new
        );
    }

    private static class NRecipeProvider extends RecipeProvider {
        public NRecipeProvider(PackOutput arg) {
            super(arg);
        }

        @Override
        protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        }
    }
}