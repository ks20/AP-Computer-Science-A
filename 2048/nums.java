import java.net.URL;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class nums extends JApplet implements KeyListener {
    int[][] board = new int[4][4]; //make 2d array
    boolean lose = false;
    boolean win = false;

    public void init() {
        setFocusable(true); //don't forget this
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0; //initialize the 2d array
            }
        }
        addNum(2); //start off with two numbers somewhere on the board. Method is located after paint.
        addKeyListener(this);
        resize(400, 400);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, 400, 400);
        if (win)
            g.drawString("Cheater cheater pumpkin eater!", 90, 200);
        else if (lose){
            g.drawString("Sorry! You Lose! Better luck next time. =(", 65, 200);
        }
        else {
            g.setFont(new Font("Serif", Font.BOLD, 36));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 2048){
                        win = true;
                        repaint();
                    }
                    switch (board[i][j]) { //different colors from dark to light :D
                        case 2:
                            g.setColor(Color.black);
                            break;
                        case 4:
                            g.setColor(Color.darkGray);
                            break;
                        case 8:
                            g.setColor(Color.magenta);
                            break;
                        case 16:
                            g.setColor(Color.red);
                            break;
                        case 32:
                            g.setColor(Color.blue);
                            break;
                        case 64:
                            g.setColor(Color.green);
                            break;
                        case 128:
                            g.setColor(Color.orange);
                            break;
                        case 256:
                            g.setColor(Color.pink);
                            break;
                        case 512:
                            g.setColor(Color.cyan);
                            break;
                        case 1024:
                            g.setColor(Color.yellow);
                            break;
                        case 2048:
                            g.setColor(Color.white);
                            break;
                        default:
                            g.setColor(Color.black);
                            break;
                    }
                    g.fillRect(i * 100, j * 100, 100, 100);
                    switch (board[i][j]) { //to make it so that the font contrasts with the rectangle colors
                        case 128:
                            g.setColor(Color.black);
                            break;
                        case 256:
                            g.setColor(Color.black);
                            break;
                        case 512:
                            g.setColor(Color.black);
                            break;
                        case 1024:
                            g.setColor(Color.black);
                            break;
                        case 2048:
                            g.setColor(Color.black);
                            break;
                        default:
                            g.setColor(Color.white);
                            break;
                    }
                    g.drawRect(i * 100, j * 100, 100, 100); //make it so the boxes have outlines
                    if (board[i][j] != 0 && board[i][j] < 10) //these are here so you can center the numbers. you could do this waay more efficiently but im too lazy.
                        g.drawString("" + board[i][j], 43 + i * 100, 60 + j * 100); //if you look at java docs, youll find something called font metrics if you realllly don't wanna hardcode
                    else if (board[i][j] != 0 && board[i][j] < 100)
                        g.drawString("" + board[i][j], 33 + i * 100, 60 + j * 100);
                    else if (board[i][j] != 0 && board[i][j] < 1000)
                        g.drawString("" + board[i][j], 23 + i * 100, 60 + j * 100);
                    else if (board[i][j] != 0)
                        g.drawString("" + board[i][j], 15 + i * 100, 60 + j * 100);
                }
            }
        }
    }

    public void addNum(int n) { //starts to become a little complicated. I do int n so I can tell it how many random numbers I want it to "spawn"
        Random r = new Random(); //I do this so that I don't have to deal with doubles
        ArrayList<Point> spaces = new ArrayList<Point>(); //I use an Arraylist of "Points" called spaces. Points are predefined in Java just like in a coordinate plane. So x and y :D
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0)
                    spaces.add(new Point(i, j)); //I give the ArrayList all the spaces that are "open" or 0's in this context
            }
        }
        if (spaces.size() == 0) { //if there are 0 available spaces then you know the person loses
            lose = true;
            repaint();
        }
        for (int i = 0; i < n; i++) { //where the actual randomness happens
            Point f = spaces.get(r.nextInt(spaces.size())); //I take a random Point from my ArrayList of Points and put it into a placeholder Point.
            board[(int) f.getX()][(int) f.getY()] = r.nextInt(4) == 1 ? 4 : 2; //nextInt will find an int from 1-4. If its a 1 then place a 4. Else place a two. This effectively gives a 4 a 25% chance of appearing.
            //moar explanation: I use f.getX to get the X of the placeholder Point I made. Same with f.getY.
        }
    }

    public void slideLeft() {
        for (int j = 0; j < board[0].length; j++) { //checks y (up/down when u draw it out)
            for (int i = 0; i < board.length - 1; i++) { //checks x
                if (board[i + 1][j] != 0 && board[i][j] == 0) { //if the place you're at is a 0 and the place to the right is not, then move it to the left
                    board[i][j] = board[i + 1][j];
                    board[i + 1][j] = 0;
                    i = -1; //set it back to the beginning of the loop. -1 because i++ will put it at 0 anyways
                } else if (board[i + 1][j] == board[i][j]) { //HAVE to do else otherwise i=-1 will throw out of bounds. This is where the summing takes place.
                    board[i][j] *= 2;
                    board[i + 1][j] = 0;
                }
            }
        }
    }

    public void slideUp() { //slideLeft should give you enough context on how these work too. j is ALWAYS the y axis. i is ALWAYS the x axis.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 1; j++) {
                if (board[i][j + 1] != 0 && board[i][j] == 0) {
                    board[i][j] = board[i][j + 1];
                    board[i][j + 1] = 0;
                    j = -1;
                } else if (board[i][j + 1] == board[i][j]) {
                    board[i][j] *= 2;
                    board[i][j + 1] = 0;
                }
            }
        }
    }

    public void slideRight() {
        for (int j = 0; j < board[0].length; j++) {
            for (int i = board.length - 1; i > 0; i--) {
                if (board[i - 1][j] != 0 && board[i][j] == 0) {
                    board[i][j] = board[i - 1][j];
                    board[i - 1][j] = 0;
                    i = 4;
                } else if (board[i - 1][j] == board[i][j]) {
                    board[i][j] *= 2;
                    board[i - 1][j] = 0;
                }
            }
        }
    }

    public void slideDown() {
        for (int i = 0; i < board.length; i++) {
            for (int j = board[0].length - 1; j > 0; j--) {
                if (board[i][j - 1] != 0 && board[i][j] == 0) {
                    board[i][j] = board[i][j - 1];
                    board[i][j - 1] = 0;
                    j = 4;
                } else if (board[i][j - 1] == board[i][j]) {
                    board[i][j] *= 2;
                    board[i][j - 1] = 0;
                }
            }
        }
    }

    public void keyTyped(KeyEvent k) {
        if (k.getKeyChar() == 'a')
            slideLeft();
        if (k.getKeyChar() == 'w')
            slideUp();
        if (k.getKeyChar() == 'd')
            slideRight();
        if (k.getKeyChar() == 's')
            slideDown();
        if (!lose)
            addNum(1); //everytime you hit something, add a 2 or a 4 somewhere on the board
        repaint();
    }

    public void keyPressed(KeyEvent k) {
    }

    public void keyReleased(KeyEvent k) {
    }
}