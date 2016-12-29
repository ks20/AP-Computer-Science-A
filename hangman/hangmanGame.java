import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 
import java.util.*;
public class hangmanGame extends JApplet implements KeyListener { 
    static String theWord = "xenophobe";
    static String guessWord;
    static int misses;
    static String wrongString;
    public void init() { 
        setFocusable(true); 
        addKeyListener(this); 
        guessWord = "";
        for (int x = 0; x <= theWord.length()-1; x++){
            guessWord = guessWord + "-";
            // initializes guessWord with the same number of dashes as theWord has chars
        }
        wrongString = ""; 
        misses = 0; repaint();
    }

    public void paint(Graphics g) { 
        g.clearRect(0,0,500,500); 
        g.drawString(guessWord, 300, 400); 
        g.drawLine(200,300,450,300); 
        g.drawLine(250,300,250,75); 
        g.drawLine(250,75,350,75); 
        g.drawLine(350,75,350,110); 
        g.drawString(""+misses, 100, 100); 
        g.drawString(wrongString, 100, 150);
        if (misses >= 1) {
            g.drawOval(325,110,50,50);
        }
        if (misses >= 2) { 
            g.drawLine(350,160,350,260);
        }
        if (misses >= 3) { 
            g.drawLine(350,200,390,180);
        }
        if (misses >= 4) { 
            g.drawLine(350,200,310,180);
        }
        if (misses >= 5) {
            g.drawLine(350,260,390,280);
        }
        if (misses >= theWord.length()*(2.0/3.0)) { 
            g.drawLine(350,260,310,280);
            g.drawString("Sorry, you lose", 100, 200); }
        else if (guessWord.equals(theWord)) { 
            g.drawString("Congratulations, you win", 100, 200);
        }
    }
    // displays guessWord
    // displays the "hangman" based on the value of misses }
    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {} 

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()=='1') { 
            init();
            return; 
        }
        if (!wrongString.contains(""+e.getKeyChar())){ 
            hangmanGuess(e.getKeyChar()); 
            repaint();
        } 
    }

    public static void hangmanGuess(char c) { 
        boolean is = false;
        for (int j = 0; j <= theWord.length()-1; j++){ 
            if (theWord.charAt(j)==c) {
                guessWord = guessWord.substring(0, j) + c + guessWord.substring(j+1); 
                is = true;
            }
        }
        if (is == false) {
            wrongString = wrongString + c; 
            misses = misses + 1;
        }
        // discussed in class
        // code is similar to that of replaceWith() in ps11
        // NOTE: theWord and guessWord are global, and need not be passed as parameters
    } 
}