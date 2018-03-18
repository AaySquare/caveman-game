package Inventory;

import Inputs.Mouse;
import Inventory.Items.Item;
import TileGame.Handler;
import gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;

    private int x, y;
    private int width, height;

    private int numOfColms = 6;
    private int numOfRows = 4;

    private boolean hasBeenPressed;
    public static boolean isOpen = false;

    public ArrayList<ItemSlot> inventoryItems;
    private ItemStack currentSlot;

    public Inventory(Handler handler, int x, int y){
        this.x = x;
        this.y = y;
        this.handler = handler;
        inventoryItems = new ArrayList<>();

        for (int i = 0; i < numOfColms; i++){
            for (int j = 0; j < numOfRows; j++){
                if (j == (numOfRows - 1)){
                    y += 35;
                }
                inventoryItems.add(new ItemSlot(x + (i * (ItemSlot.SLOTSIZE + 10)), y + (j * (ItemSlot.SLOTSIZE)), null));
                if (j == (numOfRows - 1)){
                    y -= 35;
                }
            }
        }

        width = numOfColms * (ItemSlot.SLOTSIZE + 10);
        height = numOfRows * (ItemSlot.SLOTSIZE + 10) + 35;

        //inventoryItems.get(0).addItem(new Item(Assets.wood, "Wood", 0), 2);
    }

    public void update(){
        if (isOpen){
            Rectangle temp = new Rectangle(Mouse.getMouseX(), Mouse.getMouseY(), 1, 1);

            for (ItemSlot item : inventoryItems){
                item.update();

                Rectangle temp2 = new Rectangle(item.getX()+3, item.getY()+40, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);

                if (Mouse.isClicked){
                    if (temp2.contains(temp) && !hasBeenPressed) {
                        hasBeenPressed = true;

                        if (currentSlot == null) {
                            if (item.getItemStack() != null) {
                                currentSlot = item.getItemStack();

                                item.setItem(null);
                            }
                        }
                        else {
                            if (item.addItem(currentSlot.getItem(), currentSlot.getAmount())) {

                            } else {
                                item.setItem(currentSlot);
                            }
                            currentSlot = null;
                        }
                    }
                }

                if (hasBeenPressed && !Mouse.isClicked){
                    hasBeenPressed = false;
                }
            }
        }
    }

    public void render(Graphics2D g){
        if (isOpen) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x - 17, y - 17, width + 30, height + 30);

            g.setColor(Color.BLACK);
            g.drawRect(x - 17, y - 17, width + 30, height + 30);

            g.setFont(new Font("Arial", Font.BOLD, 12));
            for (ItemSlot item : inventoryItems) {
                item.render(g);
            }


            if (currentSlot != null) {
                g.drawImage(currentSlot.getItem().getTexture(), Mouse.getMouseX(), Mouse.getMouseY(), null);
                g.drawString(Integer.toString(currentSlot.getAmount()), Mouse.getMouseX() + 27, Mouse.getMouseY() + 33);
            }

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
            g.drawString("Inventory", 300, height-215);
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
