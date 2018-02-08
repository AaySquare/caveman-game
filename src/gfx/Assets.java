package gfx;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage dirt, grass, rock, tree, spear, arrow, wood, meat;
    public static BufferedImage[]
            //Player movement sprites
            player_down, player_up, player_left, player_right, player_idle_left, player_idle_right,
            //Player attack sprites
            player_attack_right, player_attack_left, bow_arrow_right, bow_arrow_left, player_throw_right, player_throw_left,
            //Fox movement sprites
            fox_down, fox_up, fox_left, fox_right;

    private static final int width = 32, height = 32;
    private static final int foxWidth = 48, foxHeight = 48;

    public static void init(){

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Textures/sheet.png"));
        /*SpriteSheet playerSheetLeft = new SpriteSheet(ImageLoader.loadImage("/Textures/caveman.png"));
        SpriteSheet playerSheetRight = new SpriteSheet(ImageLoader.loadImage("/Textures/caveman fliped.png"));*/
        SpriteSheet cavemanRight = new SpriteSheet(ImageLoader.loadImage("/Textures/spr_caveman v2.png"));
        SpriteSheet cavemanLeft = new SpriteSheet(ImageLoader.loadImage("/Textures/spr_caveman_flip v2.png"));
        SpriteSheet foxSheet = new SpriteSheet(ImageLoader.loadImage("/Textures/Foxes.png"));

        //For old Caveman sprite
        /*player_left = new BufferedImage[2];
        player_right = new BufferedImage[2];
        player_idle = new BufferedImage[1];

        player_down = player_left;
        player_up = player_right;

        player_idle[0] = caveman.crop(width*5, 0, width*2, height*2);

        player_left[0] = caveman.crop(width*3, 0, width*2, height*2);
        player_left[1] = caveman.crop(width, 0, width*2, height*2);

        player_right[0] = caveman.crop((width*7)+12, 0, width*2, height*2);
        player_right[1] = caveman.crop((width*9)+12, 0, width*2, height*2);*/

        //Sprite initialisation for animation
        player_right = new BufferedImage[7];
        player_left = new BufferedImage[7];
        player_down = new BufferedImage[5];
        player_up = new BufferedImage[6];
        player_idle_right = new BufferedImage[1];
        player_idle_left = new BufferedImage[1];
        player_attack_right = new BufferedImage[4];
        player_attack_left = new BufferedImage[4];
        bow_arrow_right = new BufferedImage[1];
        bow_arrow_left = new BufferedImage[1];
        player_throw_left = new BufferedImage[1];
        player_throw_right = new BufferedImage[1];

        fox_up = new BufferedImage[3];
        fox_down = new BufferedImage[3];
        fox_left = new BufferedImage[3];
        fox_right = new BufferedImage[3];

        //Weapons
        bow_arrow_right[0] = cavemanRight.crop(0, height*11, width, height);
        bow_arrow_left[0] = cavemanLeft.crop((width*7)-1, height*11, width, height);

        //Player
        player_idle_right[0] = cavemanRight.crop(0, height*2, width, height);
        player_idle_left[0] = cavemanLeft.crop((width*7)-1, height*2, width, height);

        player_down[0] = cavemanRight.crop(0, height*12, width, height);
        player_down[1] = cavemanRight.crop(width, height*12, width, height);
        player_down[2] = cavemanRight.crop(width*2, height*12, width, height);
        player_down[3] = cavemanRight.crop(width, height*13, width, height);
        player_down[4] = cavemanRight.crop(width*2, height*13, width, height);

        player_up[0] = cavemanRight.crop(0, height*4, width, height);
        player_up[1] = cavemanRight.crop(width, height*4, width, height);
        player_up[2] = cavemanRight.crop(width*2, height*4, width, height);
        player_up[3] = cavemanRight.crop(width*3, height*4, width, height);
        player_up[4] = cavemanRight.crop(width*4, height*4, width, height);
        player_up[5] = cavemanRight.crop(width*5, height*4, width, height);

        player_left[0] = cavemanLeft.crop((width*7)-1, height, width, height);
        player_left[1] = cavemanLeft.crop(width*6, height, width, height);
        player_left[2] = cavemanLeft.crop(width*5, height, width, height);
        player_left[3] = cavemanLeft.crop(width*4, height, width, height);
        player_left[4] = cavemanLeft.crop(width*3, height, width, height);
        player_left[5] = cavemanLeft.crop(width*2, height, width, height);
        player_left[6] = cavemanLeft.crop(width, height, width, height);

        player_right[0] = cavemanRight.crop(0, height, width, height);
        player_right[1] = cavemanRight.crop(width, height, width, height);
        player_right[2] = cavemanRight.crop(width*2, height, width, height);
        player_right[3] = cavemanRight.crop(width*3, height, width, height);
        player_right[4] = cavemanRight.crop(width*4, height, width, height);
        player_right[5] = cavemanRight.crop(width*5, height, width, height);
        player_right[6] = cavemanRight.crop(width*6, height, width, height);

        player_attack_left[0] = cavemanLeft.crop((width*7)-1, height*3, width, height);
        player_attack_left[1] = cavemanLeft.crop(width*6, height*3, width, height);
        player_attack_left[2] = cavemanLeft.crop(width*5, height*3, width, height);
        player_attack_left[3] = cavemanLeft.crop(width*4, height*3, width, height);

        player_attack_right[0] = cavemanRight.crop(0, height*3, width, height);
        player_attack_right[1] = cavemanRight.crop(width, height*3, width, height);
        player_attack_right[2] = cavemanRight.crop(width*2, height*3, width, height);
        player_attack_right[3] = cavemanRight.crop(width*3, height*3, width, height);

        player_throw_left[0] = cavemanLeft.crop(width*6, height*11, width, height);
        player_throw_right[0] = cavemanRight.crop(width, height*11, width, height);

        //Animals
        fox_up[0] = foxSheet.crop(0, foxHeight *3, foxWidth, foxHeight);
        fox_up[1] = foxSheet.crop(foxWidth, foxHeight *3, foxWidth, foxHeight);
        fox_up[2] = foxSheet.crop(foxWidth *2, foxHeight *3, foxWidth, foxHeight);

        fox_down[0] = foxSheet.crop(0, 0, foxWidth, foxHeight);
        fox_down[1] = foxSheet.crop(foxWidth, 0, foxWidth, foxHeight);
        fox_down[2] = foxSheet.crop(foxWidth *2, 0, foxWidth, foxHeight);

        fox_left[0] = foxSheet.crop(0, foxHeight, foxWidth, foxHeight);
        fox_left[1] = foxSheet.crop(foxWidth, foxHeight, foxWidth, foxHeight);
        fox_left[2] = foxSheet.crop(foxWidth *2, foxHeight, foxWidth, foxHeight);

        fox_right[0] = foxSheet.crop(0, foxHeight *2, foxWidth, foxHeight);
        fox_right[1] = foxSheet.crop(foxWidth, foxHeight *2, foxWidth, foxHeight);
        fox_right[2] = foxSheet.crop(foxWidth *2, foxHeight *2, foxWidth, foxHeight);


        //Non-Creatures
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width*2, 0, width, height);
        rock = sheet.crop(width*3, 0, width, height);
        tree = sheet.crop(width*5, 0, width*2, height*3);
        spear = sheet.crop(width*3, height, width, height); //Not finalised sprite
        arrow = sheet.crop(0, height*2, width*3, height*2); //Not finalised sprite
        wood = sheet.crop(width*3, height*2, width, height);
        meat = sheet.crop(width*4, 0, width, height);
    }
}
