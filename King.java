import java.awt.*;
import java.lang.Math;

import javax.swing.*;

public class King extends Checker{
    private int x;
    private int y;
    private ImageIcon kingIcon;
    private Color     color;

    public King(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        if (color == Color.BLACK)
        {
            kingIcon = new ImageIcon("BlackKing.png");
        }
        if (color == Color.WHITE)
        {
            kingIcon = new ImageIcon("WhiteKing.png");
        }
    }

    public Color getColor()
    {
        return color;
    }

    public boolean isLocked(Checker[][] a)
    {
        if (canCapture(a))
        {
            return false;
        }
        for (int i = -1; i <= 1; i+=2)
        {
            for (int z = -1; z <= 1; z+=2)
            {
                try {
                    if (a[x+i][y+z]  == null)
                    {
                        return false;
                    }
                } 
                catch (Exception e) {
                    //TODO: handle exception
                }
            }
        }
        return true;
    }

    public boolean canMove(Checker[][] a, int newX, int newY)
    {
        if (a[newX][newY] == null)
        {
            if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 1)
            {
                return true;
            }
        }
        return false;
    }

    public void move(Checker[][] a, int newX, int newY)
    {
        if (canMove(a, newX, newY))
        {
            a[x][y] = null;
            a[newX][newY] = new King(color, newX, newY);
        }
    }

    public boolean canCapture(Checker[][] a)
    {
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {  
                try {
                    if (a[x+i][y+j] instanceof Checker && a[x+ (2*i)][y+ (2*j)] == null)
                    {
                        if ( a[x+i][y+j].getColor() != color)
                        {
                            return true;
                        }
                    }
                }
                catch (Exception e) {}
            }
        }
        return false;
    }

    public boolean canCaptureSquare(Checker[][] a, int newX, int newY)
    {
        // move to empty square and piece between it and empty square
        if (a[newX][newY] == null && Math.abs(newX-x) == 2 && Math.abs(newY - y) == 2 && a[(newX + x)/2][(newY + y)/2] != null)
        {
            return true;
        }
        return false;
    }

    public void capture(Checker[][] a, int newX, int newY)
    {
        if (canCaptureSquare(a, newX, newY))
        {
            a[newX][newY] = new King(color, newX, newY);
            a[(newX + x)/2][(newY + y)/2] = null;
            a[x][y] = null;
        }
    }

    public ImageIcon getIcon()
    {
        return kingIcon;
    }
}