import java.awt.*;
import javax.swing.*;

/**
 * Name:        David Huang
 * Date:        7/2/2022
 * Description: The Regular.java class describes what a regular checker
 * piece can do on a 2d array of checkers
 */
public class Regular extends Checker{
    private int x;
    private int y;
    
    private ImageIcon icon;
    private Color     color;

    public Regular(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        if (color == Color.WHITE)
        {
            icon = new ImageIcon("White.png");
        }
        if (color == Color.BLACK)
        {
            icon = new ImageIcon("Black.png");
        }
    }

    /**
     * color accessor method
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Determines if the checker can move
     */
    public boolean isLocked(Checker[][] a)
    {
        if (canCapture(a))
        {
            return false;
        }
        // move forward (y decreases)
        if (color == Color.WHITE)
        {
            try {
                if (a[x-1][y-1] == null)
                {
                    return false;
                }
            } 
            catch (Exception e) {
                //TODO: handle exception
            }
            try {
                if (a[x-1][y+1] == null)
                {
                    return false;
                }
            } 
            catch (Exception e) {
                //TODO: handle exception
            }
        }
        //move down (y increases)
        if (color == Color.BLACK)
        {
            try {
                if (a[x+1][y+1] == null)
                {
                    return false;
                }
            } 
            catch (Exception e) {
                //TODO: handle exception
            }
            try {
                if (a[x+1][y-1] == null)
                {
                    return false;
                }
            } 
            catch (Exception e) {
                //TODO: handle exception
            }
        }
        return true;
    }

    /**
     * Moves the checker NOT CAPTURE
     */
    public void move(Checker[][] a, int newX, int newY)
    {
        // if can move, then move
        if (canMove(a, newX, newY))
        {
            a[newX][newY] = new Regular(color, newX, newY);
            a[x][y] = null;
        }
    }

    /**
     * Determines if the checker can move to a certain square
     */
    public boolean canMove(Checker[][] a, int newX, int newY)
    {
        // empty square prerequisite
        if (a[newX][newY] == null)
        {
            // if white checker...
            if (color == Color.WHITE)
            {
                // can only move forward
                if (newX - x == -1)
                {
                    // if 1 square diagonal to current position
                    if (Math.abs(newY - y) ==1)
                    {
                        return true;
                    }
                }
            }
            // if black checker
            if (color == Color.BLACK)
            {
                // can only move down the board
                if (newX - x == 1)
                {
                    // if 1 square diagonal to current position
                    if (Math.abs(newY - y) == 1)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the checker can capture any checker on the board
     */
    public boolean canCapture(Checker[][] a)
    {
        if (color == Color.WHITE)
        {
            try {
                // if there is a checker in front + left of it and there is a empty square to jump to..
                if (a[x-1][y+1] instanceof Checker && a[x-2][y+2] == null)
                {
                    // makes sure it cannot capture the same color
                    if (a[x-1][y+1].getColor() != color)
                    {
                        return true;
                    }
                } 
            }
            catch (Exception e) {
                // out of bounds exception
            }
            try {
                if (a[x-1][y-1] instanceof Checker && a[x-2][y-2] == null)
                {
                    if (a[x-1][y-1].getColor() != color)
                    {
                        return true;
                    }
                }
            } 
            catch (Exception e) {
                // out of bounds exception
            }
        }
        if (color == Color.BLACK)
        {
            try {
                if (a[x+1][y+1] instanceof Checker && a[x+2][y+2] == null)
                {
                    if (a[x+1][y+1].getColor() != color)
                    {
                        return true;
                    }
                }
            } 
            catch (Exception e) {
                // out of bounds exception
            }
            try {
                if (a[x+1][y-1] instanceof Checker && a[x+2][y-2] == null)
                {
                    if (a[x+1][y-1].getColor() != color)
                    {
                        return true;
                    }
                }
            } 
            catch (Exception e) {
                // out of bounds exception
            }
        }
        return false;
    }

    /**
     * Determines if the checker can capture to a certain square
     */
    public boolean canCaptureSquare(Checker[][] a, int newX, int newY)
    {
        // move to empty square and piece between it and empty square
        if (a[newX][newY] == null && a[(newX + x)/2][(newY + y)/2] != null)
        {
            if (color == Color.WHITE)
            {
                if (newX - x == -2 && Math.abs(newY - y) == 2)
                {
                    return true;
                }
            }
            if (color == Color.BLACK)
            {
                if (newX - x == 2 && Math.abs(newY - y) == 2)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes the checker capture a piece
     */
    public void capture(Checker[][] a, int newX, int newY)
    {
        if (canCaptureSquare(a, newX, newY))
        {
            if (color == Color.WHITE)
            {
                if (newX - x == -2 && Math.abs(newY - y) == 2)
                {
                    a[newX][newY] = new Regular(color, newX, newY);
                    a[x -1][(newY + y)/2] = null;
                    a[x][y] = null;
                }
            }
            if (color == Color.BLACK)
            {
                if (newX - x == 2 && Math.abs(newY - y) == 2)
                {
                    a[newX][newY] = new Regular(color, newX, newY);
                    a[x +1][(newY + y)/2] = null;
                    a[x][y] = null;
                }
            }
        }
    }

    /**
     * Icon accessor method 
     */
    public ImageIcon getIcon()
    {
        return icon;
    }
}