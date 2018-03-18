package Inventory;

import Inventory.Items.Item;

import java.awt.*;

public class ItemSlot {

    public static final int SLOTSIZE = 70;
    private int x, y;

    private ItemStack itemStack;

    public ItemSlot(int x, int y, ItemStack itemStack){
        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
    }

    public void update(){

    }

    public void render(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(x+3, y+40, SLOTSIZE, SLOTSIZE);

        g.setColor(Color.BLACK);
        g.drawRect(x+3, y+40, SLOTSIZE, SLOTSIZE);

        if (itemStack != null){
            g.drawImage(itemStack.getItem().getTexture(), x+3, y+40, SLOTSIZE, SLOTSIZE, null);
            g.drawString(Integer.toString(itemStack.getAmount()), x + SLOTSIZE - 20, y + SLOTSIZE + 35);
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean addItem(Item item, int amount){
        if (itemStack != null){
            if (item.getId() == itemStack.getItem().getId()){
                this.itemStack.setAmount(this.itemStack.getAmount() + amount);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            this.itemStack = new ItemStack(item, amount);
            return true;
        }
    }

    public void setItem(ItemStack item){
        this.itemStack = item;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
