import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun compareInventories(a: Inventory, b: Inventory): Map<ItemStack, Int> {
    val differences = mutableMapOf<ItemStack, Int>()

    for (item in a.contents) {
        if (item != null) {
            val countA = countItemInInventory(a, item)
            val countB = countItemInInventory(b, item)

            if (countA != countB) {
                differences[item] = countB - countA
            }
        }
    }

    for (item in b.contents) {
        if (item != null && !differences.keys.any { it.isSimilar(item) }) {
            val countA = countItemInInventory(a, item)
            val countB = countItemInInventory(b, item)

            if (countA != countB) {
                differences[item] = countB - countA
            }
        }
    }

    return differences
}

fun countItemInInventory(inventory: Inventory, item: ItemStack): Int {
    var count = 0

    for (i in 0 until inventory.size) {
        val currentItem = inventory.getItem(i)

        if (currentItem != null && currentItem.isSimilar(item)) {
            count += currentItem.amount
        }
    }

    return count
}