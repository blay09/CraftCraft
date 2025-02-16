package net.blay09.mods.craftingslots.network;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.craftingslots.CraftingSlots;
import net.blay09.mods.craftingslots.menu.InventoryCraftingMenu;
import net.blay09.mods.craftingslots.menu.PortableCraftingMenu;
import net.blay09.mods.craftingslots.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import java.util.function.Predicate;

public class PortableCraftingMessage implements CustomPacketPayload {

    public static final Type<PortableCraftingMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CraftingSlots.MOD_ID, "portable_crafting"));

    public static void encode(FriendlyByteBuf buf, PortableCraftingMessage message) {
    }

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

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
