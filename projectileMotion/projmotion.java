import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class projmotion extends JApplet implements Runnable, MouseListener {
    Thread t;
    int timeStep = 40;
    Projectile proj1;
    Projectile proj2;
    Projectile proj3;
    Projectile proj4;
    Projectile proj5;
    
    Projectile_NoAirDrag projI;
    Projectile_NoAirDrag projII;
    Projectile_NoAirDrag projI;
    Projectile_NoAirDrag projIV;
    Projectile_NoAirDrag projV;

    public void init() {
        resize (800,620);
        proj1 = new Projectile (40, Math.PI/12);
        proj2 = new Projectile (40, Math.PI/6);
        proj3 = new Projectile (40, Math.PI/4);
        proj4 = new Projectile (40, Math.PI/3);
        proj5 = new Projectile (40, Math.PI);
        
        projI = new Projectile_NoAirDrag (40, Math.PI/12);
        projII = new Projectile_NoAirDrag (40, Math.PI/6);
        projIII = new Projectile_NoAirDrag (40, Math.PI/4);
        projIV = new Projectile_NoAirDrag (40, Math.PI/3);
        projV = new Projectile_NoAirDrag (40, Math.PI);
        

        addMouseListener(this);
        t = new Thread(this);
        t.start();

    }

    public void paint (Graphics g) {
        g.setColor(Color.green);
        g.drawOval(2*(int)proj1.x, 2*(int)proj1.y, 20, 20);
        
        g.setColor(Color.red);
        g.drawOval(2*(int)proj2.x, 2*(int)proj2.y, 20, 20);
        
        g.setColor(Color.black);
        g.drawOval(2*(int)proj3.x, 2*(int)proj3.y, 20, 20);
        
        g.setColor(Color.blue);
        g.drawOval(2*(int)proj4.x, 2*(int)proj4.y, 20, 20);
    }
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void run() {
        try {
            while(true) {
                if (proj1.y <= 75){            
                    proj1.move();    
                    repaint();
                    t.sleep(timeStep);
               }
               if (proj2.y <= 75){   
                    proj2.move();  
                    repaint();
                    t.sleep(timeStep);
               }
               if (proj3.y <= 75){
                    proj3.move();
                    repaint();
                    t.sleep(timeStep);
               }
               if (proj4.y <= 75){
                    proj4.move();
                    repaint();
                    t.sleep(timeStep);
               }
            }
        } catch (InterruptedException e) {}
    }
}

class Projectile {
    double x;
    double y;
    double g;
    double vx;
    double vy;
    int timeStep;
    double a;
    double theta;
    double ax;
    double ay;
    double v;
    static final double cD = 0.4;
    static final double m = 0.057;
    static final double density = 1.2;
    static final double A = 0.0064;

    public Projectile (double v, double theta){
        this.theta = theta;
        this.v = v;
        vx = v * Math.cos(theta);
        vy = -v * Math.sin(theta);
        x = 0;
        y = 75;
        g = 9.8;
        timeStep = 40;
    }
    public void move(){
        v = Math.sqrt(vx*vx+vy*vy);
        theta = Math.atan(vy/vx);
        a = (0.5 * cD * A * v * v * density)/m;
        ax = a * Math.cos(theta);
        ay = a * Math.sin(theta);

        vx = vx - (ax * (timeStep/1000.));
        vy = vy + (g * (timeStep/1000.)) - (ay * (timeStep/1000.));

        x = x + (vx * (timeStep/1000.));
        y = y + (vy * (timeStep/1000.));

    }
}

class Projectile_NoAirDrag {
   double x;
   double y;
   double g;
   double vx;
   double vy;
   int timeStep;
   public Projectile_NoAirDrag (double v, double theta){
        vx = v * Math.cos(theta);
        vy = v * Math.sin(theta);
        x = 0;
        y = 75;
        g = 9.8;
        timeStep = 40;
   }     
   public void move(){
       vx = vx;
       vy = vy - (g * (timeStep/1000.));
       x = x + (vx * (timeStep/1000.));
       y = y - (vy * (timeStep/1000.));
   }
}



