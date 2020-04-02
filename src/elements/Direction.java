package elements;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction opposite(){
        Direction opposite = null;
        switch (this) {
            case UP:
                opposite = DOWN;
            break;
            case DOWN:
                opposite = UP;
            break;
            case LEFT:
                opposite = RIGHT;
            break;
            case RIGHT:
                opposite = LEFT;
            break;
        }
        return opposite;
    }
}
