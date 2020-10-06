package UI;

import engine.core.SapperGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SapperUI  extends JFrame {
    
    private final int SIZE = 11;
    private SapperGame game;
    private JPanel gamePanel = new JPanel(new GridLayout(SIZE,SIZE,1,1));
    public SapperUI(){
        super("Sapper");
        game = new SapperGame(SIZE,20);
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        createMenu();
        Container container = getContentPane();
        gamePanel.setDoubleBuffered(true);
        container.add(gamePanel);
        initField();
    }

    private void initField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                var button = new CellButton(game.getCell(i,j),i,j);
                button.setFocusable(false);
                button.addActionListener(new ButtonClickListener());
                gamePanel.add(button);
            }
        }
        gamePanel.validate();
        gamePanel.repaint();
    }

    private void redrawField(){
        for(int i =0;i<SIZE*SIZE;i++)
            gamePanel.remove(0);
        initField();
    }


    private void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        var menu = new JMenu("File");
        for(var menuItemName : new String[]{"new","exit"}){
            var temp = new JMenuItem(menuItemName);
            temp.setActionCommand(menuItemName.toLowerCase());
            temp.addActionListener(new MenuClickListener());
            menu.add(temp);
        }
        menu.insertSeparator(1);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private class MenuClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            var command = e.getActionCommand();
            switch (command) {
                case "new" -> {
                    newGame();
                }
                case "exit" -> {

                }
            }
        }
    }

    private class ButtonClickListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            var button = (CellButton)e.getSource();
            int i = button.getI();
            int j = button.getJ();
            var turnRes =game.makeTurn(i,j);
            redrawField();
            if(turnRes){
                //Win check
                if(game.isWin()) {
                    gameWin();
                }
            }
            else{
                //Lose
                gameLose();
            }
            redrawField();
        }
    }
    private void gameWin(){
        JOptionPane.showMessageDialog(null, "You won!\nClicks: "+game.getClicks());
        newGame();
    }
    private  void gameLose(){
        revealField();
        JOptionPane.showMessageDialog(null, "You lose!");
    }
    private void newGame(){
        game.newGame();
        redrawField();
    }

    private void revealField(){
        game.revealField();
        redrawField();
    }

}
