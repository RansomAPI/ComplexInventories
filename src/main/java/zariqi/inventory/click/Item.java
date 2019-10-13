package zariqi.inventory.click;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;


public class Item extends ItemStack
{
    private String itemName;
    private String[] itemLore;
    private int itemAmount;

    private Item(ItemStack itemStack, String itemName, String[] itemLore, int itemAmount)
    {
        super(itemStack);

        this.itemName = itemName;
        this.itemLore = itemLore;
        this.itemAmount = itemAmount;

        UpdateVisual();
    }

    public Item(Material material, byte data, String itemName, String[] itemLore, int itemAmount)
    {
        super(material, itemAmount, data, null);

        this.itemName = itemName;
        this.itemLore = itemLore;
        this.itemAmount = itemAmount;

        UpdateVisual();
    }

    public Item(Material material, String itemName, String[] itemLore, int itemAmount)
    {
        super(material, itemAmount);

        this.itemName = itemName;
        this.itemLore = itemLore;
        this.itemAmount = itemAmount;

        UpdateVisual();
    }

    public Item clone()
    {
        return new Item(super.clone(), itemName, itemLore, itemAmount);
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    private void UpdateVisual()
    {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(itemName);

        ArrayList<String> lore = new ArrayList<>();
        Collections.addAll(lore, itemLore);

        meta.setLore(lore);;
        setItemMeta(meta);
    }

    public void addGlow()
    {
        addUnsafeEnchantment(Enchantment.LURE, 1);

        //Removing enchantment name
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        setItemMeta(meta);
    }

    public void SetLore(String[] string)
    {
        itemLore = string;

        ArrayList<String> lore = new ArrayList<>();
        Collections.addAll(lore, itemLore);

        ItemMeta meta = getItemMeta();
        meta.setLore(lore);

        setItemMeta(meta);
    }

    Object getProperty(Property property)
    {
        switch (property)
        {
            case ITEM_NAME:
                return itemName;
            case ITEM_LORE:
                return itemLore;
            case ITEM_AMOUNT:
                return itemAmount;
        }

        return null;
    }

    public enum Property {ITEM_NAME,ITEM_LORE,ITEM_AMOUNT}
}
