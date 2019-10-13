package zariqi.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 *
 * @author  U8Y
 * @version 1.3
 * @since   7-10-2019
 * © Fatjon Zariqi
 *
 * @param <T> {@link T} Is the same class where {@link ComplexPage<T>} will be created.
 */
public abstract class ComplexBase<T> implements Listener
{
    private T pluginType;
    private HashMap<Player, ComplexPage> complexCache = new HashMap<>();

    public ComplexBase(JavaPlugin javaPlugin, T pluginType)
    {
        this.pluginType = pluginType;
        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

    /**
     *
     * @return If you ever need to access methods and/or variables from {@link T}, you can with getPlugin();
     */
    protected T getPlugin()
    {
        return pluginType;
    }

    @EventHandler
    public void Click(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (matchesPlayer(player) && matchesInventory(player, inventory))
        {
            complexCache.get(player).playerClick(event);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void Close(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (matchesPlayer(player) && notNull(player) && matchesInventory(player, inventory))
            executeSafeClose(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void Open(InventoryOpenEvent event)
    {
        if (!event.isCancelled())
            return;

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (matchesPlayer(player) && notNull(player) && matchesInventory(player, inventory))
            executeSafeClose(player);
    }

    @EventHandler
    public void Quit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        player.closeInventory();

        if (matchesPlayer(player))
            executeSafeClose(player);
    }

    /**
     *
     * @param player Player for whom the inventory get's opened.
     * @param page The {@link ComplexPage<T>} That you have declared for executing this method.
     *
     * This method is ALWAYS executed in a class that extends to {@link ComplexBase<T>}.
     */
    protected void openInventory(Player player, ComplexPage page)
    {
        complexCache.put(player, page);
        player.openInventory(page.getInventory());
    }

    /*
    These are private methods only used in this class, nothing to see here...
    */
    private void executeSafeClose(Player player)
    {
        complexCache.get(player).dispose();
        complexCache.remove(player);
    }

    private boolean notNull(Player player)
    {
        return complexCache.containsKey(player);
    }

    private boolean matchesPlayer(Player player)
    {
        return complexCache.containsKey(player);
    }

    private boolean matchesInventory(Player player, Inventory inventory)
    {
        return complexCache.get(player).getInventory().getName().equals(inventory.getName());
    }
}
