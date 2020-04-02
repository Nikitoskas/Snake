package elements;

import utils.ResourceLoader;
import visual.Display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Set;

public class Food extends Element {
    private static BufferedImage img = ResourceLoader.loadImage("food.png");

    private Food(int x, int y) {
        super(x, y);
    }

    public static Food createInRandomLocation(Set<Element> occupiers){
        int x = 0;
        int y = 0;
        Random rand = new Random();
        boolean access = true;
        while (access) {
            x = rand.nextInt(Display.NET_WIDTH) * Display.NET_CELL_WIDTH_PXL;
            y = rand.nextInt(Display.NET_HEIGHT) * Display.NET_CELL_HEIGHT_PXL;
            for (Element e : occupiers) {
                if (e != null && e.equalPoint(x, y)){
                    access = false;
                    break;
                }
            }
            if (access){
                break;
            } else {
                access = true;
            }
        }
        return new Food(x, y);
    }


    @Override
    public void update() {
        checkBorders();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, (int)x, (int)y, Display.NET_CELL_WIDTH_PXL, Display.NET_CELL_HEIGHT_PXL, null);
    }
}
