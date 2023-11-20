package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class Snake
{
    private LinkedList<GameObject> snake;
    private boolean isAlive;
    private Direction direction;
    private int size;
    //Handling rapid key presses.
    private boolean canChangeDirection = true;

    public static ArrayList<Snake> createOneSnake()
    {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(9,8));
        points.add(new Point(8,8));
        points.add(new Point(7,8));
        ArrayList<Snake> ret = new ArrayList<>();
        ret.add(new Snake(points, Color.BLUE, Direction.RIGHT));

        return ret;
    }

    public static ArrayList<Snake> createTwoSnakes()
    {
        ArrayList<Point> firstPoints = new ArrayList<>();
        firstPoints.add(new Point(8,6));
        firstPoints.add(new Point(9,6));
        firstPoints.add(new Point(10,6));

        ArrayList<Point> secondPoints = new ArrayList<>();
        secondPoints.add(new Point(8,10));
        secondPoints.add(new Point(7,10));
        secondPoints.add(new Point(6,10));

        ArrayList<Snake> ret = new ArrayList<>();
        ret.add(new Snake(firstPoints, Color.BLUE, Direction.LEFT));
        ret.add(new Snake(secondPoints, Color.RED, Direction.RIGHT));

        return ret;
    }

    public void changeDir(KeyEvent e, ArrayList<Integer> keys)
    {
            int currentLeft = keys.get(0);
            int currentRight = keys.get(1);
            int currentUp = keys.get(2);
            int currentDown = keys.get(3);

            if (isAlive() && canChangeDirection)
            {
                if(e.getKeyCode() == currentLeft)
                    if (getDirection() != Direction.RIGHT)
                        setDirection(Direction.LEFT);
                if(e.getKeyCode() == currentRight)
                    if (getDirection() != Direction.LEFT)
                        setDirection(Direction.RIGHT);
                if(e.getKeyCode() == currentUp)
                    if (getDirection() != Direction.DOWN)
                        setDirection(Direction.UP);
                if(e.getKeyCode() == currentDown)
                    if (getDirection() != Direction.UP)
                        setDirection(Direction.DOWN);
            }
            canChangeDirection = false;
    }

    public void move()
    {
        moveBody();
        moveHead();
        canChangeDirection = true;
    }

    public void moveBody()
    {
        for (int i = size - 1; i > 0; i--)
        {
            Point prev = new Point(snake.get(i - 1).getPoint());
            snake.get(i).setPoint(prev);
        }
    }

    public void moveHead()
    {
        GameObject head = snake.get(0);
        int x = head.getPoint().x;
        int y = head.getPoint().y;
        Direction dir = getDirection();
        switch (dir)
        {
            case LEFT:
                head.setPoint(new Point(x-1, y));
                break;
            case RIGHT:
                head.setPoint(new Point(x+1, y));
                break;
            case UP:
                head.setPoint(new Point(x, y-1));
                break;
            case DOWN:
                head.setPoint(new Point(x, y+1));
                break;
        }
    }

    public void grow()
    {
        Color c = snake.get(size-1).getColor();
        Point p = snake.get(size-1).getPoint();
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        float hue = (hsb[0] + 0.02f) % 1.0f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        int diameter = snake.get(size-1).getDiameter();
        if (diameter > 21)
            diameter = diameter - 2;
        snake.add(new GameObject(p, new Color(rgb), diameter));
        size++;
    }

    public Snake(ArrayList<Point> points, Color c, Direction dir)
    {
        snake = new LinkedList<>();
        int diameter = 42;
        for (int i = 0; i < points.size(); i ++)
        {
            diameter = diameter - 2;
            GameObject part = new GameObject(points.get(i), c, diameter);

            float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
            float hue = (hsb[0] + 0.02f) % 1.0f;
            int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);

            c = new Color(rgb);
            snake.add(part);
        }
        isAlive = true;
        direction = dir;
        size = snake.size();
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction d)
    {
        direction = d;
    }
    public boolean isAlive()
    {
        return isAlive;
    }

    public int getSize()
    {
        return size;
    }

    public GameObject getPart(int i)
    {
        return snake.get(i);
    }

    public void dead()
    {
        isAlive = false;
    }
}
