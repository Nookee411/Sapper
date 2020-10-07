package UI;

import engine.core.SapperGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SapperUI  extends JFrame {
    //TODO fix first click
    //TODO fix mine placement
    private final int SIZE = 11;
    private final SapperGame game;
    private final JPanel gamePanel = new JPanel(new GridLayout(SIZE,SIZE,0,0));
    public SapperUI(){
        super("Sapper");
        super.addKeyListener(new KeyListener());
        game = new SapperGame(SIZE,15);
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        createMenu();
        Container container = getContentPane();
        gamePanel.setDoubleBuffered(true);
        container.add(gamePanel);
        //container.addContainerListener(new KeyUpListener());
        initField();
    }

    private void initField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                var button = new CellButton(game.getCell(i,j),i,j);
                button.setFocusable(false);
                button.addMouseListener(new ButtonClickListener());
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

    private class ButtonClickListener implements  MouseListener{


        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            var mouseButton = mouseEvent.getButton();
            var button = (CellButton) mouseEvent.getSource();
            if(mouseButton==1) {
                int i = button.getI();
                int j = button.getJ();
                var turnRes = game.makeTurn(i, j);
                redrawField();
                if (turnRes) {
                    //Win check
                    if (game.isWin()) {
                        gameWin();
                    }
                } else {
                    //Lose
                    gameLose();
                }
            }
            else if(mouseButton==3){
                //JOptionPane.showMessageDialog(null,"WAKE UP");
                int i = button.getI();
                int j = button.getJ();
                game.markMine(i,j);
                redrawField();
            }
            redrawField();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    /*private class KeyUpListener extends  {
        @Override
        public void keyReleased(KeyEvent e) {
            JOptionPane.showMessageDialog(null,"Fuck");
            super.keyReleased(e);
        }
    }*/

    private class KeyListener implements java.awt.event.KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            JOptionPane.showMessageDialog(null,"Fuck");
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
