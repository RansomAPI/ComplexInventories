package zariqi.inventory.click;

import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;

/**
 * {@link ItemHead} is an custom class extended to {@link Item}.
 * Only used to create a skull of player's head.
 */
public class ItemHead extends Item
{
    public ItemHead(String ownerName, String itemName, String[] itemLore, int itemAmount)
    {
        super(Material.SKULL_ITEM, (byte) 3, itemName, itemLore, itemAmount);

        UpdateVisual(ownerName);
    }

    private void UpdateVisual(String owner)
    {
        SkullMeta meta = (SkullMeta) getItemMeta();
        meta.setDisplayName( (String) getProperty(Property.ITEM_NAME));

        meta.setOwner(owner);

        ArrayList<String> lore = new ArrayList<>();
        Collections.addAll(lore, (String[]) getProperty(Property.ITEM_LORE));

        meta.setLore(lore);
        setItemMeta(meta);
    }
}
