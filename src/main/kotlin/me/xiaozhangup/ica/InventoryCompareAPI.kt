package me.xiaozhangup.ica

import compareInventories
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Plugin
import taboolib.common.platform.command.command
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info


object InventoryCompareAPI : Plugin() {

    val hm = HashMap<Player, Inventory>()

    override fun onEnable() {
        info("Successfully running InventoryCompareAPI!")
    }

    @Awake(LifeCycle.ENABLE)
    fun regCommand() {
        command("open") {
            execute<Player> { sender, _, _ ->
                val inv = Bukkit.createInventory(null, 36)
                inv.addItem(ItemStack(Material.BEETROOT, 20))

                sender.openInventory(inv)
                hm[sender] = inv.clone()
            }
        }
    }

    @SubscribeEvent
    fun e(event: InventoryCloseEvent) {
        hm[event.player as Player]?.let {
            compareInventories(it, event.inventory.clone()).forEach { (t, u) ->
                info("${t.type} > $u")
            }
        }

        hm.remove(event.player as Player)
    }

}

fun Inventory.clone(): Inventory {
    val size = size

    val clonedInventory = Bukkit.createInventory(null, size)

    for (i in 0 until size) {
        val item = getItem(i)
        clonedInventory.setItem(i, item?.clone())
    }

    return clonedInventory
}