package elements;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static visual.Display.*;

public abstract class Element {
    private static Set<Element> instances = new HashSet<>();
    protected double x;
    protected double y;

    abstract public void update();
    abstract public void render(Graphics2D g);

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
        addElement(this);
    }

    public Element(Point point){
        this.x = point.x;
        this.y = point.y;
        addElement(this);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void delete(){
        deleteElement(this);
    }

    protected void checkBorders(){
        x = (x >= NET_WIDTH_PXL) ? x - NET_HEIGHT_PXL : x;
        x = (x < 0) ? NET_WIDTH_PXL + x : x;
        y = (y >= NET_HEIGHT_PXL) ? y - NET_HEIGHT_PXL : y;
        y = (y < 0) ? NET_HEIGHT_PXL + y : y;
    }



    public boolean equalPoint(Element element){
        return (this.x == element.x && this.y == element.y);
    }

    public boolean equalPoint(double x, double y){
        return (this.x == x && this.y == y);
    }

    public boolean nextPoint(Element element, Direction direction){
        boolean next = false;
        double nextX;
        double nextY;
        switch (direction) {
            case UP:
                nextY = element.y + NET_CELL_HEIGHT_PXL;
                nextY = (nextY >= NET_HEIGHT_PXL) ? nextY - NET_HEIGHT_PXL : nextY;
                next = equalPoint(element.x, nextY);
                break;
            case DOWN:
                nextY = element.y - NET_CELL_HEIGHT_PXL;
                nextY = (nextY < 0) ? nextY + NET_HEIGHT_PXL : nextY;
                next = equalPoint(element.x, nextY);
                break;
            case LEFT:
                nextX = element.x + NET_CELL_HEIGHT_PXL;
                nextX = (nextX >= NET_WIDTH_PXL) ? nextX - NET_WIDTH_PXL : nextX;
                next = equalPoint(nextX, element.y);
                break;
            case RIGHT:
                nextX = element.x - NET_CELL_WIDTH_PXL;
                nextX = (nextX < 0) ? nextX + NET_WIDTH_PXL : nextX;
                next = equalPoint(nextX, element.y);
                break;
        }
        return next;
    }


    public static Set<Element> getAllElements() {
        return instances;
    }

    public static Set<Element> getDangerousElements() {
        Set<Element> elements  = new HashSet<>();
        for (Element e : instances) {
            if ((e instanceof Wall) || (e instanceof Body && !((Body) e).isHead())){
                elements.add(e);
            }
        }
        return elements;
    }

    public static void deleteElement(Element element) {
        instances.remove(element);
    }

    public static void addElement(Element element) {
        instances.add(element);
    }

    public static void deleteAllElements(){
        instances = new HashSet<>();
    }
}
