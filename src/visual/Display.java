package visual;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Display extends Canvas {
    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGraphics;
    private int clearColor;

    public static final int NET_HEIGHT = 30;
    public static final int NET_WIDTH = 30;
    public static final int NET_CELL_HEIGHT_PXL = 20;
    public static final int NET_CELL_WIDTH_PXL = 20;
    public static final int NET_WIDTH_PXL = NET_WIDTH * NET_CELL_WIDTH_PXL;
    public static final int NET_HEIGHT_PXL = NET_HEIGHT * NET_CELL_HEIGHT_PXL;

    public static final int TEXT_X = 50;
    public static final int TEXT_Y = 250;
    public static final int TEXT_SIZE = 60;

    public static final int ELEMENTS_DIAMETER = 20;

    public Display(int width, int height, int clearColor) {
        super();
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
        this.bufferGraphics = this.buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.clearColor = clearColor;
    }

    public void clear(){
        Arrays.fill(bufferData, clearColor);
    }

    public void drawNet(){
        bufferGraphics.setColor(Color.BLACK);
        for (int i = 0; i < NET_WIDTH + 1; i++){
            bufferGraphics.drawLine(i * NET_CELL_WIDTH_PXL, 0, i * NET_CELL_WIDTH_PXL, NET_WIDTH_PXL);
        }
        for (int i = 0; i < NET_HEIGHT + 1; i++) {
            bufferGraphics.drawLine(0, i * NET_CELL_HEIGHT_PXL, NET_HEIGHT_PXL, i * NET_CELL_HEIGHT_PXL);
        }
    }

    public void swapBuffer(){
        Graphics g = this.getGraphics();
        g.drawImage(buffer, 0, 0, null);
        g.dispose();
    }

    public Graphics2D getMyGraphics(){
        return (Graphics2D)bufferGraphics;
    }

    public void writeText(String text){
        bufferGraphics.setFont(new Font("Times New Roman", Font.BOLD, TEXT_SIZE));
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawString(text, TEXT_X, TEXT_Y);
    }
}
