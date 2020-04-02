package elements;

import visual.Display;

import java.awt.*;
import java.util.*;

public class Player {

    private static final int startX = 300;
    private static final int startY = 300;

    private Body head;
    private ArrayList<Body> structure = new ArrayList<>();
    private Direction[] rotationQueue = new Direction[3];
    private LinkedList<Food> food = new LinkedList<>();

    public Player(int bodyLength) {
        head = new Body(startX, startY, Direction.RIGHT, true);
        structure.add(head);
        for (int i = 1; i < bodyLength; i++){
            structure.add(new Body(startX - i * Display.NET_CELL_WIDTH_PXL, startY, Direction.RIGHT, false));
        }
        rotationQueue[0] = Direction.RIGHT;
    }

    public void update(){
        if (head.inCell()){
            tryAddToEnd();
            rotateAll();
        }
        for (Body b : structure){
            b.update();
        }
        for (Food f : food) {
            f.update();
        }
    }

    public void render(Graphics2D g){
        for (Food f : food) {
            f.render(g);
        }
        for (int i = structure.size() - 1; i >= 0; i--){
            structure.get(i).render(g);
        }
    }

    public int length(){
        return structure.size() + food.size();
    }

    public void setDirection(Direction direction){
        for (int i = 0; i < rotationQueue.length; i++){
            if (rotationQueue[i] == null){
                rotationQueue[i] = direction;
                break;
            }
        }
    }

    public Body getEnd(){
        return structure.get(structure.size() - 1);
    }

    public Body getHead() {
        return head;
    }

    public void addFood(Food food){
        this.food.addFirst(food);
    }


    public boolean checkCollision(Set<Element> dangerousElements){
        for (Iterator<Element> i = dangerousElements.iterator(); i.hasNext();){
            if (head.equalPoint(i.next())){
                return true;
            }
        }
        return false;
    }

    private void rotateAll(){
        shiftQueue();
        for (int i = structure.size() - 1; i > 0; i--){
            Body second = structure.get(i);
            Body first = structure.get(i-1);
            second.setDirection(first.getDirection());
        }
        head.setDirection(rotationQueue[0]);
    }

    private void shiftQueue(){
        if (rotationQueue[1] == null){
            return;
        }
        for (int i = 0; i < rotationQueue.length - 1; i++){
            rotationQueue[i] = rotationQueue[i + 1];
        }
        rotationQueue[rotationQueue.length - 1] = null;
    }

    public void tryAddToEnd(){
        if (food.size() == 0){
            return;
        }
        Body tail = getEnd();
        Food lastFood = food.getLast();
        if (lastFood.nextPoint(tail, tail.getDirection())){
            structure.add(new Body((int)lastFood.getX(), (int)lastFood.getY(), tail.getDirection(), false));
            food.removeLast();
        }
    }

}
