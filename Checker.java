import java.awt.*;
import javax.swing.*;

/**
 * Name:        David Huang
 * Date:        7/2/2022
 * Description: The Checker.java abstract class provides methods for pieces to be placed on a 2d array
 */
public abstract class Checker {
    public abstract boolean isLocked(Checker[][] a);

    public abstract void move(Checker[][] a, int newX, int newY);

    public abstract boolean canMove(Checker[][] a, int newX, int newY);

    public abstract boolean canCapture(Checker[][] a);

    public abstract boolean canCaptureSquare(Checker[][] a, int newX, int newY);

    public abstract void capture(Checker[][] a, int newX, int newY);

    public abstract ImageIcon getIcon();

    public abstract Color getColor();
}
