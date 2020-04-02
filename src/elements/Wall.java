package elements;

import utils.ResourceLoader;
import visual.Display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Wall extends Element {
    private static BufferedImage img = ResourceLoader.loadImage("wall.jpg");

    public Wall(int x, int y) {
        super(x, y);
    }

    public Wall(Point point) {
        super(point);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, (int)x, (int)y, Display.NET_CELL_WIDTH_PXL, Display.NET_CELL_HEIGHT_PXL, null);
    }


    public static Set<Wall> createWallsFromFile(String fileName){
        Set<Wall> walls = new HashSet<>();
        for (Point p : ResourceLoader.loadWallPoints(fileName)) {
            walls.add(new Wall(p));
        }
        return walls;
    }
}
