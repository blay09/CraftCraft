package net.blay09.mods.craftingslots.addon;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.blay09.mods.craftingslots.CraftingSlots;
import net.blay09.mods.craftingslots.container.InventoryCraftingMenu;
import net.blay09.mods.craftingslots.container.ModMenus;
import net.blay09.mods.craftingslots.container.PortableCraftingMenu;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIAddon implements IModPlugin {

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(PortableCraftingMenu.class, ModMenus.portableCrafting.get(), RecipeTypes.CRAFTING, 1, 9, 10, 36);
        registration.addRecipeTransferHandler(InventoryCraftingMenu.class, ModMenus.inventoryCrafting.get(), RecipeTypes.CRAFTING, 1, 9, 10, 27);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(CraftingSlots.MOD_ID, "jei");
    }
}
