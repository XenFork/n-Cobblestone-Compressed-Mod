package union.xenfork.n.cobblestone.compressed.mod.forge;

import dev.architectury.platform.forge.EventBuses;
import union.xenfork.n.cobblestone.compressed.mod.NCobblestoneCompressedMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NCobblestoneCompressedMod.MOD_ID)
public class NCobblestoneCompressedModForge {
    public NCobblestoneCompressedModForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(NCobblestoneCompressedMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
            NCobblestoneCompressedMod.init();
    }
}