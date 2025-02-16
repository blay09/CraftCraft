package net.blay09.mods.craftingcraft.network;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.craftingcraft.container.InventoryCraftingMenu;
import net.blay09.mods.craftingcraft.container.PortableCraftingMenu;
import net.blay09.mods.craftingcraft.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class PortableCraftingMessage {

    public static void encode(PortableCraftingMessage message, FriendlyByteBuf buf) {
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static PortableCraftingMessage decode(FriendlyByteBuf buf) {
        return new PortableCraftingMessage();
    }

    public static void handle(ServerPlayer player, PortableCraftingMessage message) {
        if (player == null) {
            return;
        }

        ItemStack itemStack = findPortableCrafting(player.getInventory());
        if (itemStack.getItem() == ModItems.inventoryCraftingTable) {
            Balm.getNetworking().openGui(player, InventoryCraftingMenu.provider);
        } else if (itemStack.getItem() == ModItems.portableCraftingTable) {
            Balm.getNetworking().openGui(player, PortableCraftingMenu.provider);
        }
    }

    private static ItemStack findPortableCrafting(Inventory inventory) {
        final Predicate<ItemStack> predicate = itemStack -> itemStack.getItem() == ModItems.inventoryCraftingTable || itemStack.getItem() == ModItems.portableCraftingTable;
        final var charm = Balm.getModSupport().trinkets().findEquipped(inventory.player, predicate);
        if (!charm.isEmpty()) {
            return charm;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            final var itemStack = inventory.getItem(i);
            if (predicate.test(itemStack)) {
                return itemStack;
            }
        }

        return ItemStack.EMPTY;
    }

}
