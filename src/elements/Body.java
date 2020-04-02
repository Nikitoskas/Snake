package elements;

import visual.Display;

import java.awt.*;

public class Body extends Element {
    private static double speed = 4; // value 20%speed must be 0

    private boolean head;
    private Direction direction;

    public Body(int x, int y, Direction direction, boolean head) {
        super(x, y);
        this.direction = direction;
        this.head = head;
    }

    public boolean isHead(){
        return head;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if ((direction == Direction.UP || direction == Direction.DOWN)
                && (this.direction == Direction.UP || this.direction == Direction.DOWN)){
            return;
        }
        if ((direction == Direction.LEFT || direction == Direction.RIGHT)
                && (this.direction == Direction.LEFT || this.direction == Direction.RIGHT)){
            return;
        }
        this.direction = direction;
    }

    public boolean inCell(){
        return ((int)x % Display.NET_CELL_WIDTH_PXL == 0 && (int)y % Display.NET_CELL_HEIGHT_PXL == 0);
    }

    @Override
    public void update() {
        makeMovement();
        checkBorders();
    }

    @Override
    public void render(Graphics2D g) {
        Color color = (head) ? new Color(255, 69, 0) : new Color(255, 140, 0);
        g.setColor(color);
        g.fillOval((int)this.x, (int)this.y, Display.ELEMENTS_DIAMETER, Display.ELEMENTS_DIAMETER);

        if(x > Display.NET_WIDTH_PXL - Display.ELEMENTS_DIAMETER){
            g.fillOval((int)this.x - Display.NET_WIDTH_PXL, (int)this.y, Display.ELEMENTS_DIAMETER, Display.ELEMENTS_DIAMETER);
        }
        if(y > Display.NET_HEIGHT_PXL - Display.ELEMENTS_DIAMETER){
            g.fillOval((int)this.x, (int)this.y - Display.NET_HEIGHT_PXL, Display.ELEMENTS_DIAMETER, Display.ELEMENTS_DIAMETER);
        }
    }

    private void makeMovement(){
        switch (direction) {
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case UP:
                y -= speed;
                break;
        }
    }

}
