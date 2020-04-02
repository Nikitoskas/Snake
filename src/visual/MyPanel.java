package visual;

import elements.GameState;
import game.Game;
import listeners.MyKeyListener;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    public static final int DISPLAY_WIDTH = Display.NET_WIDTH * Display.NET_CELL_WIDTH_PXL;
    public static final int DISPLAY_HEIGHT = Display.NET_HEIGHT * Display.NET_CELL_HEIGHT_PXL;

    private Display display;
    private JLabel labelScore = new JLabel("Score:");
    private JLabel scoreValue = new JLabel("0");
    private JButton buttonNewGame = new JButton("New game");
    private JButton buttonExit = new JButton("Exit");
    private JCheckBox netVisible = new JCheckBox("Display grid");

    public MyPanel(Game game) {
        super();
        this.setLayout(new BorderLayout());

        Panel rightPanel = new Panel(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(150, 650));

        Panel centralPanel = new Panel(new FlowLayout());
        centralPanel.setPreferredSize(new Dimension(650, 650));

        buttonNewGame.setPreferredSize(new Dimension(120, 50));
        buttonExit.setPreferredSize(new Dimension(120, 50));
        labelScore.setPreferredSize(new Dimension(120, 20));
        scoreValue.setPreferredSize(new Dimension(120, 20));
        labelScore.setFont(new Font("Colibry", Font.BOLD, 20));
        scoreValue.setFont(new Font("Colibry", Font.BOLD, 20));

        rightPanel.add(buttonNewGame);
        rightPanel.add(buttonExit);
        rightPanel.add(labelScore);
        rightPanel.add(scoreValue);
        rightPanel.add(netVisible);

        display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT, 0xFFFFEBCD); //87CEFA
        display.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        this.add(rightPanel, BorderLayout.EAST);
        this.add(centralPanel, BorderLayout.CENTER);

        centralPanel.add(display);

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.stop();
                game.exit();
            }
        });
        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.stop();
                game.reset(0);
                game.setState(GameState.WAITING);
                game.start();
                display.requestFocus();
            }
        });
        netVisible.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.requestFocus();
            }
        });
    }

    public boolean displayGrid(){
        return netVisible.isSelected();
    }

    public Display getDisplay() {
        return display;
    }

    public void setScore(String score){
        scoreValue.setText(score);
    }

}
