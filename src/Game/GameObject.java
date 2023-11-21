package Game;

import java.awt.*;

/**
 * The GameObject class represents a generic object in the Snake game.
 * It encapsulates information about the object's position, color, and diameter.
 */
public class GameObject
{
    /**
     * The position of the object as a Point.
     */
    private Point point;
    /**
     * The color of the object.
     */
    private Color color;
    /**
     * The diameter of the object.
     */
    int diameter;

    /**
     * Constructs a new GameObject with the specified position, color, and diameter.
     *
     * @param p The position of the object as a Point.
     * @param c The color of the object.
     * @param d The diameter of the object.
     */
    public GameObject(Point p, Color c, int d)
    {
        point = p;
        color = c;
        diameter = d;
    }

    /**
     * Checks if the given GameObject has the same position as this object.
     *
     * @param o The GameObject to compare.
     * @return True if the objects have the same position, false otherwise.
     */
    public boolean isSamePoint(GameObject o)
    {
        return this.getPoint().equals(o.getPoint());
    }

    /**
     * Gets the position of the object.
     *
     * @return The position of the object as a Point.
     */
    public Point getPoint()
    {
        return point;
    }

    /**
     * Sets the position of the object.
     *
     * @param p The new position of the object as a Point.
     */
    public void setPoint(Point p)
    {
        point = p;
    }

    /**
     * Gets the color of the object.
     *
     * @return The color of the object.
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Gets the diameter of the object.
     *
     * @return The diameter of the object.
     */
    public int getDiameter()
    {
        return diameter;
    }
}
