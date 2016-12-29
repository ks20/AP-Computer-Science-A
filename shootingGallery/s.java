import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Image;
public class s extends JApplet implements Runnable, MouseListener, MouseMotionListener, KeyListener { 
    Thread t;   
    int timeStep = 100;
    ArrayList <Target> target2 = new ArrayList<Target>();
    int xhu;
    int yhu;
    int paolo;
    int polo;
    Image Battle_Droid;
    Image Droideka_SWE;
    Image review_sstrooper_large;
    boolean splash;
    int score;
    long timer = 1000;
    long begin = System.currentTimeMillis();
    boolean miss;
    public void init() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        t = new Thread(this);
        t.start();
        resize(2000, 1000);
        for (int i = 0; i < 20; i++){
            target2.add(new targetMovingSquare());
            target2.add(new targetMovingSideways());
            target2.add(new targetMovingUpDown());
        }
        Battle_Droid = getImage(getCodeBase(), "Battle_Droid.png");
        Droideka_SWE = getImage(getCodeBase(), "Droideka-SWE.png");
        review_sstrooper_large = getImage(getCodeBase(), "review_sstrooper_large.jpg");
        splash = false;
        score = 0;
        miss = true;
    }

    public void paint (Graphics g) {
        if (splash == false){
            g.drawString ("Press S and Click the Mouse to start the game! May the Force be with you!", 800, 500);
        }
        if (splash == true){
            g.clearRect(0,0,2000,2000);
            g.drawString("Time Remaining: " +(timer-((System.currentTimeMillis()-begin)/1000)), 80, 80) ;
            for (int i = 0; i < target2.size(); i++){
                if (target2.get(i) instanceof targetMovingSquare){
                    g.drawImage(Battle_Droid,target2.get(i).getX(), target2.get(i).getY(), target2.get(i).getW(), target2.get(i).getH(), this);
                }
                if (target2.get(i) instanceof targetMovingSideways){
                    g.drawImage(Droideka_SWE,target2.get(i).getX(), target2.get(i).getY(), target2.get(i).getW(), target2.get(i).getH(), this);
                }
                if (target2.get(i) instanceof targetMovingUpDown){
                    g.drawImage(review_sstrooper_large, target2.get(i).getX(), target2.get(i).getY(), target2.get(i).getW(), target2.get(i).getH(), this);
                }

            }
            // loop through the ArrayList, draw a circle at each element
            g.drawOval(xhu - 50, yhu - 50, 100, 100);
            g.drawLine(xhu, yhu - 50, xhu, yhu + 50);
            g.drawLine(xhu - 50, yhu, xhu + 50, yhu);
            g.drawLine(100, 700, 2000, 700);
            g.drawString("Score is:" + score, 100, 700);
            // g.drawString("Your Time Remaining is:" + time, 100, 715);
            if (miss == false){
               g.drawString("You have hit something! Congrats!", 0,700);
               miss = false;
            }
            if (miss == true){
               g.drawString("You have a terrible aim! A stormtrooper would be better than you!", 0, 715);
            }
        }  
    }

    public void mouseClicked(MouseEvent e) {
        paolo = e.getX();
        polo = e.getY();
        for (int i = 0; i < target2.size(); i++){
            if (paolo >= (target2.get(i).getX() - 30) && paolo <= (target2.get(i).getX() + 30)){
                if (polo >= (target2.get(i).getY() - 25) && polo <= (target2.get(i).getY() + 25)){
                    target2.remove(i);
                    score = score + 1;
                    miss = false;
                    repaint();
                }
            }   
            miss = true;
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        xhu = e.getX();
        yhu = e.getY();
        repaint();
    }

    public void mouseDragged(MouseEvent e){
    }
    public  void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
       if (e.getKeyChar() == 's' && splash == false){
           splash = true;
           repaint();
       }
       if (e.getKeyChar() == 'r'){
         for (int i = 0; i < target2.size(); i++){
             target2.remove(i);
         }
         for (int i = 0; i < 20; i++){
            target2.add(new targetMovingSquare());
            target2.add(new targetMovingSideways());
            target2.add(new targetMovingUpDown());
         }
         repaint();
       }
    }
    public void keyPressed(KeyEvent e){
    }
    public void run() {
        try {
            while(true) {
                for (int i = 0; i < target2.size(); i++){
                    target2.get(i).move();
                }
                repaint();
                t.sleep(timeStep);   
            }
        } catch (InterruptedException e) {} 
    }
}

abstract class Target {
    int x, y; 
    double w, h;
    int getX() { 
        return (int) x;
    }

    int getY() { 
        return (int) y;
    }

    int getW() { 
        return (int) w; 
    }

    int getH() { 
        return (int) h; 
    }

    void move() {}
}

class targetMovingUpDown extends Target {
    boolean direction;
    targetMovingUpDown(){
        //super();
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*500);
        w = 50;
        h = 50;
        direction = true;
    }

    void move() {
        //true moving down
        //false moving up
        if (direction == true){
            y = y - 1;
        }
        if (direction == false){
            y = y + 1;
        }
        if (y == 0){
            direction = false;
        }
        if (y == 500){
            direction = true;
        }
    }
}

class targetMovingSideways extends Target {
    boolean direction;
    targetMovingSideways(){
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*500);
        w = 50;
        h = 50;
        direction = true;
    }

    void move() {
        //true moving left
        //false moving right
        if (direction == true){
            x = x - 1;
        }
        if (direction == false){
            x = x + 1;
        }
        if (x == 0){
            direction = false;
        }
        if (x == 1000){
            direction = true;
        }
    }
}

class targetMovingSquare extends Target {
    boolean changeY;
    boolean changeX;
    boolean negativeChange;
    boolean positiveChange;
    double hold1;
    double hold2;
    targetMovingSquare(){
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*700);
        hold1 = x;
        hold2 = y;
        w = 50;
        h = 50;
        changeY = false;
        changeX = true;
        negativeChange = false;
        positiveChange = true;
    }

    void move() {
        if (x == hold1 + 50){
            changeX = false;
            changeY = true;
        }
        if (y == hold2 - 50){
            changeX = true;
            changeY = false;
            positiveChange = false;
            negativeChange = true;
        }
        if (x == hold1 - 50){
            changeX = false;
            changeY = true;
        }
        if (y == hold2 && negativeChange == true){
            changeX = true;
            changeY = false;
            positiveChange = true;
            negativeChange = false;
        }
        if (changeY == true && negativeChange == true){
            y = y + 1;
        }
        if (changeX == true && negativeChange == true){
            x = x - 1;
        }
        if (changeY == true && positiveChange == true){
            y = y - 1;
        }
        if (changeX == true && positiveChange == true){
            x = x + 1;
        }
    }
}