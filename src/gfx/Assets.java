package gfx;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage dirt, grass, rock, tree, spear;
    public static BufferedImage[] player_down, player_up, player_left, player_right, player_idle;

    private static final int width = 32, height = 32;

    public static void init(){

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Textures/sheet.png"));
        /*SpriteSheet playerSheetLeft = new SpriteSheet(ImageLoader.loadImage("/Textures/caveman.png"));
        SpriteSheet playerSheetRight = new SpriteSheet(ImageLoader.loadImage("/Textures/caveman fliped.png"));*/
        SpriteSheet caveman = new SpriteSheet(ImageLoader.loadImage("/Textures/charactersSheet.png"));
        SpriteSheet spears = new SpriteSheet(ImageLoader.loadImage("/Textures/sheet.png"));

        player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];
        player_idle = new BufferedImage[1];

        player_down = player_left;
        player_up = player_right;

        player_idle[0] = caveman.crop(width*5, 0, width*2, height*2);

        player_left[0] = caveman.crop(width*3, 0, width*2, height*2);
        player_left[1] = caveman.crop(width, 0, width*2, height*2);

        player_right[0] = caveman.crop((width*7)+12, 0, width*2, height*2);
        player_right[1] = caveman.crop((width*9)+12, 0, width*2, height*2);

        /*player_left[0] = playerSheetLeft.crop(width*3, 0, width, height);
        player_left[1] = playerSheetLeft.crop(width*2, 0, width, height);
        player_left[2] = playerSheetLeft.crop(width, 0, width, height);
        player_left[3] = playerSheetLeft.crop(0, 0, width, height);
        player_left[4] = playerSheetLeft.crop(width*3, height, width, height);
        player_left[5] = playerSheetLeft.crop(width*2, height, width, height);
        player_left[6] = playerSheetLeft.crop(width, height, width, height);
        player_left[7] = playerSheetLeft.crop(0, height, width, height);*/

        /*player_right[0] = playerSheetRight.crop(0, 0, width, height);
        player_right[1] = playerSheetRight.crop(width, 0, width, height);
        player_right[2] = playerSheetRight.crop(width*2, 0, width, height);
        player_right[3] = playerSheetRight.crop(width*3, 0, width, height);
        player_right[4] = playerSheetRight.crop(0, height, width, height);
        player_right[5] = playerSheetRight.crop(width, height, width, height);
        player_right[6] = playerSheetRight.crop(width*2, height, width, height);
        player_right[7] = playerSheetRight.crop(width*3, height, width, height);*/

        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width*2, 0, width, height);
        rock = sheet.crop(width*3, 0, width, height);
        tree = sheet.crop(width*2, height, width, height);
        spear = sheet.crop(width*3, height, width, height*2);
    }
}
