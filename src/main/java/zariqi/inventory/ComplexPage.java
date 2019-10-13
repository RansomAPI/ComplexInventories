package zariqi.inventory;

import com.sun.istack.internal.NotNull;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import zariqi.animator.Interval;
import zariqi.animator.InventoryUpdateEvent;
import zariqi.inventory.click.ComplexButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author  U8Y
 * @version 1.3
 * @since   7-10-2019
 * © Fatjon Zariqi
 *
 * @param <T> {@link T} Is the same class where {@link ComplexBase<T>} will be created.
 */
public abstract class ComplexPage<T> implements InventoryHolder, Listener, Runnable
{
    private final Inventory inventory;
    private T pluginType;
    private JavaPlugin plugin;
    private Player player;
    private HashMap<ComplexPage<T>, Interval> intervalMap;
    private HashMap<Integer, ComplexButton> complexButton;

    /**
     *
     * Constructor when {@link ComplexPage} gets extended from {@link T}
     */
    public ComplexPage(T pluginType, JavaPlugin plugin, Player player, String inventoryName, int slots)
    {
        inventory = Bukkit.createInventory(this, slots, inventoryName);
        intervalMap = new HashMap<>();

        this.pluginType = pluginType;

        this.plugin = plugin;
        this.player = player;

        this.complexButton = new HashMap<>();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
    }

    /**
     *
     * Abstract method in {@link ComplexBase<T>} to initialize and {@link ComplexPage<T>} to decelerate.
     */
    public abstract void loadPage();

    /**
     *
     * @return Inventory instance for {@link ComplexPage<T>}
     */
    @NotNull
    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }

    /**
     *
     * @param slot The slot of the added item
     * @param item The {@link zariqi.inventory.click.Item} of the item
     */
    protected void addItem(int slot, ItemStack item)
    {
        inventory.setItem(slot, item);
    }

    /**
     *
     * @param slot The slot of the added button
     * @param item The {@link zariqi.inventory.click.Item} of the button
     * @param button The execution of when the button gets clicked
     */
    protected void addClick(int slot, ItemStack item, ComplexButton button)
    {
        addItem(slot, item);
        complexButton.put(slot, button);
    }

    /**
     *
     * @param event Whenever a player clicks on a button object {@link ComplexButton} this executes
     */
    @NotNull
    void playerClick(InventoryClickEvent event)
    {
        if(complexButton.containsKey(event.getRawSlot()))
        {
            complexButton.get(event.getRawSlot()).playerClick(player, event.getClick());
        }
    }

    /**
     *
     * Deleting instance of {@link ComplexPage} in parameter {@link T}
     */
    void dispose()
    {
        player = null;
        pluginType = null;
    }

    /**
     *
     * @return pluginType of {@link T}, you might need this in your page
     */
    public T getPlugin()
    {
        return pluginType;
    }

    /**
     *
     * @return Player object of player who opened the inventory, might need this in your page
     */
    protected Player getPlayer()
    {
        return player;
    }

    /**
     *
     * @param interval Interval of update-speed of inventory {@link ComplexPage<T>}
     * @param page The page {@link ComplexPage<T>} which will update every {@link Interval}
     */
    protected void setInterval(Interval interval, ComplexPage<T> page)
    {
        plugin.getServer().getPluginManager().registerEvents(page, plugin);
        intervalMap.put(page, interval);
    }

    protected Interval getInterval()
    {
        return intervalMap.get(this);
    }

    /**
     * Runnable that executes every 1/1000 of a second to catch the events
     */
    @Override
    public void run()
    {
        for (Map.Entry<ComplexPage<T>, Interval> interval : intervalMap.entrySet())
        {
            if (interval.getValue().elapsed())
            {
                plugin.getServer().getPluginManager().callEvent(new InventoryUpdateEvent(interval.getValue(), interval.getKey()));
            }
        }
    }
}
