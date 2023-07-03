import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class JCompareAPI {
    public static Map<ItemStack, Integer> compareInventories(Inventory a, Inventory b) {
        Map<ItemStack, Integer> differences = new HashMap<>();

        for (ItemStack item : a.getContents()) {
            if (item != null) {
                int countA = countItemInInventory(a, item);
                int countB = countItemInInventory(b, item);

                if (countA != countB) {
                    differences.put(item, countB - countA);
                }
            }
        }

        for (ItemStack item : b.getContents()) {
            if (item != null && differences.keySet().stream().noneMatch(itemStack -> itemStack.isSimilar(item))) {
                int countA = countItemInInventory(a, item);
                int countB = countItemInInventory(b, item);

                if (countA != countB) {
                    differences.put(item, countB - countA);
                }
            }
        }

        return differences;
    }

    public static int countItemInInventory(Inventory inventory, ItemStack item) {
        int count = 0;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack currentItem = inventory.getItem(i);

            if (currentItem != null && currentItem.isSimilar(item)) {
                count += currentItem.getAmount();
            }
        }

        return count;
    }
}
