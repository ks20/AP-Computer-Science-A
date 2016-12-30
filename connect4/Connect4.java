 

import android.app.Activity;
import android.graphics.Color;

/**
 * Created by Kushal on 10/24/15.
 */
public class Connect4 extends Activity {
    int rows = 6;
    int cols = 7;
    int size = 80;
    int [] [] board = new int [cols] [rows];
    boolean turn;
    
    public void paint(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(0,0,600,800);
        g.setColor(Color.white);
        for (int i = 0; i < cols; i++){
            for (int j = 0; j < rows; j++) {
                
                if(board[i][j] == 0)
                    g.setColor(Color.white);
                
                if(board[i][j] == 1)
                    g.setColor(Color.red);
                
                if(board[i][j] == 2)
                    g.setColor(Color.black);
                
                g.fillOval(j*size, i*size, size, size);
                
            }
        }
        if (win() == true){
            g.clearRect(0,0,600,800);
            if (turn){
                g.setColor(Color.black);
                g.drawString("Black wins", 100, 100);
            }
            else if (!turn){
                g.setColor(Color.red);
                g.drawString("Red wins", 100, 100);
            }
        }
        else if (win() == false){
            g.clearRect(0,0,600,800);
            g.drawString("Draw game", 100, 100);
        }
        
    }
    
    boolean dropPiece (int c){
        for (int r = board[0].length; r >= 0; r--){
            if (board[r][c] == 0){
                if (turn)
                    board[r][c] = 1;
                else
                    board[r][c] = 2;
                return true;
            }
        }
        return false;
    }
    
    public void mouseClicked(MouseEvent e) {
        if (dropPiece(e.getX()/size))
            turn = !turn;
        
        repaint();
    }
    
    boolean win (){
        //vertical
        for (int r = 0; r < board.length-3; r++){
            for (int c = 0; c < board[r].length; c++)
                if (board[r][c] == board[r+1][c] && board[r+1][c] == board[r+2][c] && board[r+2][c] == board[r+3][c])
                    if (board[r][c] != 0)
                        return true;
        }
        
        //horizontal
        for (int r = 0; r < board.length; r++){
            for (int c = 0; c < board[r].length-3; c++)
                if (board[r][c] == board[r][c+1] && board[r][c+1] == board[r][c+2] && board[r][c+2] == board[r][c+3])
                    if (board[r][c] != 0)
                        return true;
        }
        
        //diagonal upward left
        for (int r = 0; r < board.length-3; r++){
            for (int c = 0; c < board[r].length-3; c++)
                if (board[r][c] == board[r+1][c+1] && board[r+1][c+1] == board[r+2][c+2] && board[r+2][c+2] == board[r+3][c+3])
                    if (board[r][c] != 0)
                        return true;
        }
        
        for (int r = 0; r < board.length+3; r++){
            for (int c = 0; c < board[r].length+3; c++)
                if (board[r][c] == board[r+1][c-1] && board[r+1][c-1] == board[r+2][c+2] && board[r+2][c+2] == board[r+3][c+3])
                    if (board[r][c] != 0)
                        return true;
        }
        return false;
    }
    
    
    
}

