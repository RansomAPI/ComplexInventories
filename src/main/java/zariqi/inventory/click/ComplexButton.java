package zariqi.inventory.click;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ComplexButton
{
    /**
     *
     * @param player The player that pressed the button in an inventory.
     * @param clickType The click type of which the player
     */
    void playerClick(Player player, ClickType clickType);
}
