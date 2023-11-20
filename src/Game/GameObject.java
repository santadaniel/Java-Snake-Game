package Game;

import java.awt.*;

public class GameObject
{
    private Point point;
    private Color color;
    int diameter;

    public GameObject(Point p, Color c, int d)
    {
        point = p;
        color = c;
        diameter = d;
    }

    public boolean isSamePoint(GameObject o)
    {
        return this.getPoint().equals(o.getPoint());
    }

    public Point getPoint()
    {
        return point;
    }

    public void setPoint(Point p)
    {
        point = p;
    }

    public Color getColor()
    {
        return color;
    }

    public int getDiameter()
    {
        return diameter;
    }
}
