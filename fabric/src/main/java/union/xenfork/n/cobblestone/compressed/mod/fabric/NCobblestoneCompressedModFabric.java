package union.xenfork.n.cobblestone.compressed.mod.fabric;

import union.xenfork.n.cobblestone.compressed.mod.NCobblestoneCompressedMod;
import net.fabricmc.api.ModInitializer;

public class NCobblestoneCompressedModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NCobblestoneCompressedMod.init();
    }
}