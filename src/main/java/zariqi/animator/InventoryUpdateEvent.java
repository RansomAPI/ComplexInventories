package zariqi.animator;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import zariqi.inventory.ComplexPage;

public class InventoryUpdateEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private Interval interval;
    private ComplexPage<?> page;

    public InventoryUpdateEvent(Interval interval, ComplexPage<?> page)
    {
        this.interval = interval;
        this.page = page;
    }

    public Interval getInterval()
    {
        return interval;
    }

    public ComplexPage<?> getPage()
    {
        return page;
    }

    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
