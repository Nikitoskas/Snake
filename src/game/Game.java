package game;

import elements.*;
import listeners.MyKeyListener;
import utils.Time;
import visual.Display;
import visual.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Set;


public class Game implements Runnable{
    private JFrame frame;
    private MyPanel panel;
    private Display display;
    private boolean running;
    private GameState state;

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 650;
    public static final String TITLE = "Snake";

    public static final float UPDATE_RATE = 60.0F;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    public static final int WIN_LENGTH = 5;
    public static final long WAITING_TIME = 3 * Time.SECOND;

    private Graphics2D graphics;

    private Long startWait;
    private double leftWaitSecond;

    private int round = 0;
    private int score = 0;
    private Food food;
    private MyKeyListener myKeyListener;
    private Player player;
    private Set<Wall> walls;

    private Thread gameThread;

    public Game() throws HeadlessException {
        frame = new JFrame(TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        panel = new MyPanel(this);

        frame.setContentPane(panel);
        frame.pack();
        display = panel.getDisplay();

        graphics = display.getMyGraphics();
        myKeyListener = new MyKeyListener(player);
        display.addKeyListener(myKeyListener);

        state = GameState.WELCOME;

    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void reset(int round){
        Element.deleteAllElements();
        walls = Wall.createWallsFromFile("walls" + round + ".txt");
        player = new Player(3);
        myKeyListener.setPlayer(player);
        food = Food.createInRandomLocation(Element.getAllElements());
        if (round == 0) {
            score = 0;
            this.round = 0;
        }
    }

    public synchronized void start(){
        if (running){
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update(){
        if(state == GameState.PLAY) {
            food.update();
            player.update();
            for (Wall w : walls) {
                w.update();
            }
            if (player.checkCollision(Element.getDangerousElements())){
                state = GameState.GAME_OVER;
            }
            checkEating();
            if (player.length() >= WIN_LENGTH){
                round++;
                reset(round);
                state = GameState.WAITING;
            }
        }
        if (state == GameState.WAITING){
            if (startWait == null){
                startWait = Time.getNanoTime();
            }
            leftWaitSecond = (double) (WAITING_TIME - Time.getNanoTime() + startWait) / Time.SECOND;
            if (leftWaitSecond <= 0){
                state = GameState.PLAY;
                startWait = null;
            }
        }
    }

    private void render(){
        display.clear();

        renderState();

        if (panel.displayGrid()) {
            display.drawNet();
        }
        display.swapBuffer();
    }

    private void renderState() {
        switch (state) {
            case PLAY:
                renderElements();
                break;
            case WELCOME:
                display.writeText("Welcome");
                break;
            case GAME_OVER:
                renderElements();
                display.writeText("Game over");
                break;
            case WAITING:
                renderElements();
                display.writeText("Round " + (round + 1) + "\n " + String.format("%.1f", leftWaitSecond));
                break;
        }
    }

    private void checkEating(){
        if (food.equalPoint(player.getHead())) {
            player.addFood(food);
            food.delete();
            food = Food.createInRandomLocation(Element.getAllElements());
            score += 10;
        }
    }

    private void renderElements(){
        for (Wall w : walls) {
            w.render(graphics);
        }
        food.render(graphics);
        player.render(graphics);
        panel.setScore(Integer.toString(score));
    }

    public void exit(){
        frame.dispose();
    }


    @Override
    public void run() {
        float delta = 0;
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        long lastTime = Time.getNanoTime();
        while (running){
            long currentTime = Time.getNanoTime();
            long elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1){
                update();
                upd++;
                delta--;
                if (render){
                    updl++;
                } else {
                    render = true;
                }
            }

            if (render){
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (count >= Time.SECOND){
                frame.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        }

    }

}
