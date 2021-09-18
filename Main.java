package com.company.alekseyvalouev;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Reference ref = new Reference();
        ReadFile fileReader = new ReadFile();
	    Board gameBoard = new Board();
        String boardInput = "";
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter " + ref.getBoardSize() + "x" + ref.getBoardSize() + " input file name: ");
        String filename = myScanner.nextLine();

        boardInput = fileReader.read(filename);

       if (gameBoard.setBoardState(boardInput) == false) {
           System.out.println("Something went wrong, please try again.");
           System.exit(1);
       }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(gameBoard);
            }
        });


    }

    private static void createAndShowGUI(Board board) {
        JFrame f = new JFrame("Conway's Game of Life");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel(board));
        f.pack();
        f.setVisible(true);
    }
}

class MyPanel extends JPanel implements ActionListener {
    private Reference ref = new Reference();
    private boolean setUp = true;
    private int currentX = 0;
    private int currentY = 0;
    private int squareX = 0;
    private int squareY = 0;
    private int squareW = ref.getWindowSize()/ref.getBoardSize();
    private int squareH = ref.getWindowSize()/ref.getBoardSize();
    private Board board;

    Timer timer = new Timer(1, this);
    Timer updateTimer = new Timer(ref.getUpdateSpeed(), this);

    private static final String ENTER = "Enter";
    private Action enter = new AbstractAction(ENTER) {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (setUp == true) {
                setUp = false;
            } else {
                setUp = true;
            }
        }
    };

    public MyPanel(Board newBoard) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.board = newBoard;
        timer.start();
        updateTimer.start();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (setUp == true) {
                    changeSquare(e.getX(), e.getY());
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ENTER);
        this.getActionMap().put(ENTER, enter);
        this.setBackground(Color.BLACK);

    }

    public void changeCoords() {
        Point location = SwingUtilities.getWindowAncestor(this).getLocation();
        currentX = (int)location.getX();
        currentY = (int)location.getY();
    }

    public Dimension getPreferredSize() {
        return new Dimension(ref.getWindowSize(),ref.getWindowSize());
    }

    public void changeSquare(int x, int y) {
        int xInd = ((x + currentX) - (x + currentX) % (ref.getWindowSize()/ref.getBoardSize()))/(ref.getWindowSize()/ref.getBoardSize());
        int yInd = ((y + currentY) - (y + currentY) % (ref.getWindowSize()/ref.getBoardSize()))/(ref.getWindowSize()/ref.getBoardSize());
        this.board.changeCell(xInd, yInd);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < ref.getBoardSize(); i++) {
            for (int j = 0; j < ref.getBoardSize(); j++) {
                if (this.board.getCell(i, j).checkState())
                    g.setColor(Color.WHITE);
                else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(squareX + squareW * i - currentX, squareY + squareH * j - currentY, squareW, squareH);
            }
        }

    }

    public void actionPerformed(ActionEvent ev){
        if(ev.getSource()==timer) {
            changeCoords();
            repaint();
        }
        if (ev.getSource() == updateTimer && setUp == false) {
            board.updateBoard();
        }

    }
}

class ReadFile {
    public static String read(String filename) {
        try {
            File myObj = new File("/Users/alekseyvalouev/IdeaProjects/ConwayGame/src/com/company/alekseyvalouev/"+filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            myReader.close();
            return data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "2";
        }
    }
}
