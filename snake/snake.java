import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class snake extends JApplet implements Runnable, KeyListener {
    Thread t;
    ArrayList <Part> arr;
    ArrayList <Apple> arr1;
    boolean leftDirection = false;
    boolean rightDirection = true;
    boolean upDirection = false;
    boolean downDirection = false;
    int timeStep = 100;
    int c;
    int timeInc;
    int lives = 3;
    boolean inPlay = true;

    public void init()
    {

        setFocusable(true);
        arr = new ArrayList <Part> ();
        arr1 = new ArrayList <Apple> ();
        arr.add(new Part(10, 10));
        arr.add(new Part(10, 25));
        arr.add(new Part(10, 40));
        arr.add(new Part (10, 55));
        addKeyListener(this);
        t = new Thread(this);
        t.start();
        timeInc = 1;
        resize(800,600);
        //arr1.add(new Apple(10,120));

    }

    public void paint(Graphics g)
    {
        g.clearRect(0,0,1000,1000);

        g.setColor(Color.black);
        g.fillRect(0,0,800,600);

        for (int t = 0; t < arr.size(); t++){
            g.setColor(Color.red);
            g.fillRect(arr.get(t).getX(), arr.get(t).getY(), 10, 10);
        }

        g.setColor(Color.green);
        for (int t = 0; t < arr1.size(); t++){
            g.fillOval(arr1.get(t).getX(), arr1.get(t).getY(), 10, 10);
        }

        g.setColor(Color.blue);
        g.drawString("You have " + lives + " lives remaining.", 620, 20);

        if (lives == 0){
            g.setColor(Color.black);
            g.clearRect(0, 0, 800, 600);
            inPlay = false;
            g.drawString("Unfortunately, you have lost.", 340, 275);
            g.drawString("Your final score is: ", 340, 295);
            g.drawString("Press 'r' if you would like to play again.", 340, 315);
        }
        //showStatus (""+arr1.size() + " "+arr1.get(0).getY()+" "+arr.get(0).getY()+" "+arr.get(0).getX()+" "+arr1.get(0).getX());

        //g.drawOval((int)Math.random()*100.Apple.getX(), (int)Math.random()*100.Apple.getY(), 10, 10));
    }

    public void run() {
        try {
            while(true) {
                if (inPlay == true){
                    if (timeInc >= 20){
                        if (arr.get(0).d==1 || arr.get(0).d ==3) {
                            arr.add(new Part((arr.get(arr.size()-1)).getX(), arr.get(arr.size()-1).getY() + 15));
                            System.out.println("y");
                        }
                        else if (arr.get(0).d==2 || arr.get(0).d==4) {
                            arr.add(new Part((arr.get(0)).getX()+10, arr.get(0).getY()));
                            System.out.println("x");
                        }
                        timeInc = 0;
                    }
                    timeInc = timeInc + 1;
                    for (int i = arr.size()-2; i >=0; i--) {

                        arr.get(i+1).x = arr.get(i).getX();
                        arr.get(i+1).y = arr.get(i).getY();
                        if (i == 0) {
                            switch(arr.get(0).getD()) {
                                case 1:
                                arr.get(i).setY(arr.get(i).getY()+15);
                                break;
                                case 2:
                                arr.get(i).setX(arr.get(i).getX()+10);
                                break;
                                case 3:
                                arr.get(i).setY(arr.get(i).getY()-15);
                                break;
                                case 4:
                                arr.get(i).setX(arr.get(i).getX()-10);
                                break;
                            }
                        }
                        if (arr.get(0).getX() >= 800)
                            arr.get(0).setX(0);
                        else if (arr.get(0).getX() <= 0)
                            arr.get(0).setX(800);
                        if (arr.get(0).getY() <= 0)
                            arr.get(0).setY(600);
                        else if (arr.get(0).getY() >= 600)
                            arr.get(0).setY(0);

                        arr.get(i).move();
                    }
                    for (int j = 0; j < arr1.size(); j++){
                        if (arr.size()>0 && arr.get(0).getY() >= arr1.get(j).getY() && arr.get(0).getY() <= arr1.get(j).getY()+15 && arr.get(0).getX()>=arr1.get(j).getX()&& arr.get(0).getX()<=arr1.get(j).getX()+10){
                            arr1.remove(0);
                            System.out.println("remove");
                            j--;
                            lives--;
                        }

                    }

                    if (Math.random() < 0.01)
                        arr1.add(new Apple(10 * (int)(30 * Math.random()), 10 * (int)(30 * Math.random())));
                }    

                t.sleep(timeStep);
                repaint();
            }
        } catch (InterruptedException e) {}
    }

    public boolean inPlays (){
        if (lives <= 3 && lives >= 0){
            inPlay = true;
        }
        else if (lives == 0) {
            inPlay = false;
        }
        return inPlay;
    }

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'a'){
            arr.get(0).d = 4;
            c = 4;
            //part.changeDir(4);
            //leftDirection = true;
            //upDirection = false;
            //downDirection = false;
        }
        if (e.getKeyChar() == 'd'){
            arr.get(0).d = 2;
            c = 2;
            //part.changeDir(2);
            //rightDirection = true;
            //upDirection = false;
            //downDirection = false;
        }
        if (e.getKeyChar() == 's'){
            arr.get(0).d = 1;
            c = 1;
            //part.changeDir(1);
            //upDirection = true;
            //rightDirection = false;
            //leftDirection = false;
        }
        if (e.getKeyChar() == 'w'){
            arr.get(0).d = 3;
            c = 3;
            //part.changeDir(3);
            //downDirection = true;
            //rightDirection = false;
            //leftDirection = false;
        }
        if (e.getKeyChar() == 'r'){
            inPlay = true;
            lives = 3;
            reset();
            repaint ();
        }

        repaint();
    }

    public void reset() {
        while(arr.size() > 0)
            arr.remove(0);
        arr.add(new Part(10, 10));
        arr.add(new Part(10, 25));
        arr.add(new Part(10, 40));
        arr.add(new Part (10, 55));
    }

    class Part {
        int x, y, d;
        Part (int x, int y){
            this.x = x;
            this.y = y;
        }

        int getX(){
            return x;
        }

        int getY(){
            return y;
        }

        int getD(){
            return d;
        }

        void setX(int _x) {
            x = _x;
        }

        void setY(int _y) {
            y = _y;
        }

        void move(){
            if (d == 1){
                y = y + 1;
            }
            if (d == 2){
                x = x + 1;
            }
            if (d == 3){
                y = y - 1;
            }
            if (d == 4){
                x = x - 1;
            }
        }
    }

    class Apple {
        int x, y;
        Apple (int x, int y){
            this.x = x;
            this.y = y;
        }

        void setX(int _x) {
            x = _x;
        }

        void setY(int _y) {
            y = _y;
        }

        int getX(){
            return x;
        }

        int getY(){
            return y;
        }

    }
}
