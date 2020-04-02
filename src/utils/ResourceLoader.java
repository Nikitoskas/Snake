package utils;

import visual.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ResourceLoader {
    public static final String PATH = "files/";

    public static BufferedImage loadImage(String fileName){
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(PATH + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(fileInput);
        } catch (IOException e) {
            System.out.println("Can't read " + PATH + fileName);
            e.printStackTrace();
        }

        return image;
    }

    public static Set<Point> loadWallPoints(String fileName){
        Set<Point> points = new HashSet<>();

        BufferedReader br = getBufferedReaderFromFile(PATH + fileName);

        String line;
        for (int i = 0; i < Display.NET_HEIGHT; i++){
            line = "";
            try {
                line = br != null ? br.readLine() : "";
            } catch (IOException e) {
                e.printStackTrace();
                return points;
            }
            for (int j = 0; j < Display.NET_WIDTH; j++){
                char element = '0';
                try {
                    element = line.charAt(j);
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Can't read field structure");
                }
                if (element == '1'){
                    points.add(new Point(j * Display.NET_CELL_WIDTH_PXL, i * Display.NET_CELL_HEIGHT_PXL));
                }
            }
        }

        return points;
    }

    private static BufferedReader getBufferedReaderFromFile(String fileName){
        File f = new File(fileName);
        FileReader fr;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return new BufferedReader(fr);
    }
}
