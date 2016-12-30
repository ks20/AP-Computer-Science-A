import java.awt.*; 
import javax.swing.*; 
import java.util.*; 
import java.awt.event.*;
public class brickbreaker extends JApplet implements Runnable, KeyListener {
    Thread t;
    int timeStep = 100;
    int h = 0;
    ArrayList <Brick> arr;
    int w = getWidth();
    int brickHit = 0;
    int brickRemain = 180;
    int lives = 3;
    boolean pause = false;
    boolean inPlay = true;
    // declare the ArrayList of Brick objects
    public void init() {
        arr = new ArrayList <Brick> (); 
        addKeyListener(this);
        resize (700, 800);
        setFocusable(true);
        t = new Thread(this);
        t.start();
        for (int d = 0; d < 23; d = d + 1) {
            for (int f = 0; f < 9; f = f + 1) {
                arr.add(new Brick(7, 25, d*26, f*30));
            }
        }
        // using a nested for-loop, populate the ArrayList
        // give the Ball initial values
        Ball.v = 20;
        Ball.x = 200;
        Ball.y = 320;
        Ball.a = 4.57; 
        Paddle.x = 140; 
        Paddle.y = 550;
    }

    public void paint(Graphics g){
        g.setColor(Color.black); 
        g.fillRect(0,0,620,600); 
        g.setColor(Color.green);
        for (int t = 0; t < arr.size(); t++) {
            g.fillRect(arr.get(t).getX(), arr.get(t).getY(), arr.get(t).getW(), arr.get(t).getL()); 
        }
        g.setColor(Color.red);

        //extra feature: reduces the size of the paddle and the ball as the game progresses 
        if (brickRemain <= 90) {
            g.setColor(Color.pink); 
            g.fillRect(Paddle.x, Paddle.y, 40, 10); 
            g.fillOval((int)Ball.x-5, (int)Ball.y-5, 8, 8);
        }
        else if (brickRemain <= 45){
            g.setColor(Color.white); 
            g.fillRect(Paddle.x, Paddle.y, 30, 10); 
            g.fillOval((int)Ball.x-5, (int)Ball.y-5, 7, 7);
        }
        else if (brickRemain <= 15){
            g.setColor(Color.orange); 
            g.fillRect(Paddle.x, Paddle.y, 15, 10); 
            g.fillOval((int)Ball.x-5, (int)Ball.y-5, 6, 6);
        } else{
            g.fillRect(Paddle.x, Paddle.y, 60, 10);
            g.fillOval((int)Ball.x-5, (int)Ball.y-5, 10, 10); 
        }
        g.setColor(Color.yellow);
        g.drawString("Your score is: " + brickHit, 440, 375); 
        g.drawString("Total Bricks Remaining: " + brickRemain, 440, 400); 
        g.drawString("You have " + lives + " lives remaining.", 440, 425);
        if (lives == 0){
            g.setColor(Color.black);
            g.clearRect(0, 0, 700, 700);
            inPlay = false;
            g.drawString("Unfortunately, you have lost.", 340, 275); 
            g.drawString("Your final score is: " + brickHit, 340, 285); 
            g.drawString("Press 'r' if you would like to play again.", 340, 295);
        }
    }
    // use a for-each loop to draw the Bricks // draw the Ball

    public void run() { 
        try {
            while(true) {
                if (inPlay == true){
                    //move the Ball
                    if (Ball.x <= 0 || Ball.x >= 605)
                        Ball.a = Math.PI - Ball.a; 
                    if (Ball.y <= 0)
                        Ball.a *= -1;
                    for (int i = 0; i < arr.size(); i++){
                        if (Ball.x >= arr.get(i).getX() && Ball.x <= arr.get(i).getX() + arr.get(i).getL()){
                            if (Ball.y >= arr.get(i).getY() && Ball.y <= arr.get(i).getY() + arr.get(i).getW()){
                                arr.remove(i);
                                brickHit = brickHit + 1; brickRemain = brickRemain - 1; Ball.a = Ball.a * -1;
                            } 
                        }
                    }
                    if (Ball.x >= Paddle.x && Ball.x <= Paddle.x + 40){
                        if (Ball.y >= Paddle.y && Ball.y <= Paddle.y + 10)
                            Ball.a = -Ball.a - ((Paddle.x + 20 - Ball.x - 5)/(20)*(Math.PI/4));
                    }
                    if (Ball.y >= 589){ 
                        lives--;
                        pause = true; 
                        Ball.x = 200; 
                        Ball.y = 320; 
                        Ball.a = 4.57; 
                        Paddle.x = 140; 
                        Paddle.y = 550;
                    }
                    Ball.move();
                }
                repaint(); 
                t.sleep(timeStep);
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

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            Paddle.move(1);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            Paddle.move(2);
        if (e.getKeyChar() == 'h')
            Ball.a = Ball.a * -1;
        if (e.getKeyChar() == 'p'){
            pause = false; }
        if (e.getKeyChar() == 'r'){ 
            inPlay = true;
            lives = 3;
            reset();
            repaint ();
        } 
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public void reset() { 
        brickHit = 0; 
        brickRemain = 180; 
        while(arr.size()>0) {
            arr.remove(0);
        }
        for (int d = 0; d < 23; d = d + 1) {
            for (int f = 0; f < 9; f = f + 1) 
                arr.add(new Brick(7, 25, d*26, f*30));
        }

        Ball.x = 200;
        Ball.y = 320;
        Ball.a = 4.57; 
        Paddle.x = 140; 
        Paddle.y = 550;
    } 
}

class Brick {
    int l, w, x, y;
    Brick (int l, int w, int x, int y){
        this.l = l; 
        this.w = w; 
        this.x = x; 
        this.y = y;
    }

    int getX(){ 
        return x;
    }

    int getY(){ 
        return y;
    }

    int getW(){ 
        return w;
    }

    int getL(){ 
        return l;
    } 
}

class Ball {
    static double x, y, v, a; 
    static void move(){
        x = x + v*Math.cos(a);
        y = y + v*Math.sin(a); 
    }
}

class Paddle {
    static int x, y;
    static void move(int c){
        if (x >= -5 && x <= 550) {
            if (c == 1 && x-10 >= -5)
                x = x - 10;
            if (c == 2 && x+10 <= 550)
                x = x + 10; 
        }
    } 
}