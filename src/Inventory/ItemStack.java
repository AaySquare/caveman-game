package Inventory;

import Inventory.Items.Item;

public class ItemStack {

    private int amount;
    private Item item;

    public ItemStack(Item item, int amount){
        this.item = item;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }
}
