package union.xenfork.n.cobblestone.compressed.mod.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import union.xenfork.n.cobblestone.compressed.mod.item.CobblestoneBlockItem;
@Debug(export = true)
@Mixin(Items.class)
public class ItemsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerBlock(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/item/Item;", ordinal = 21))
    private static Item COBBLESTONE(Block block) {
        return Items.registerBlock(new CobblestoneBlockItem(block, new Item.Properties()));
    }
}
