package listeners;

import elements.Direction;
import elements.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MyKeyListener(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_S:
                player.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                player.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
                player.setDirection(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
