package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The Snake class represents a snake in the Snake game.
 * It manages the snake's body parts, movement, growth, and direction.
 */
public class Snake
{
    /**
     * The list of GameObjects representing the snake's body parts.
     */
    private LinkedList<GameObject> snake;

    /**
     * Flag indicating whether the snake is alive or not.
     */
    private boolean isAlive;

    /**
     * The current movement direction of the snake.
     */
    private Direction direction;

    /**
     * The size of the snake (number of body parts).
     */
    private int size;

    /**
     * Flag to handle rapid key presses.
     */
    private boolean canChangeDirection = true;

    /**
     * Constructs a new Snake with the specified body points, color, and initial direction.
     *
     * @param points The initial body points of the snake.
     * @param c      The color of the snake's head.
     * @param dir    The initial direction of the snake.
     */
    public Snake(ArrayList<Point> points, Color c, Direction dir)
    {
        snake = new LinkedList<>();
        int diameter = 42;
        for (int i = 0; i < points.size(); i ++)
        {
            if (diameter > 21)
            diameter = diameter - 2;
            GameObject part = new GameObject(points.get(i), c, diameter);
            // Changing the new part's color slightly.
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

    /**
     * Creates a new Snake with one body part at the specified position, color, and initial direction.
     *
     * @return A list containing the newly created Snake.
     */
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

    /**
     * Creates two Snakes with specific initial body points, colors, and initial directions.
     *
     * @return A list containing the two newly created Snakes.
     */
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

    /**
     * Changes the direction of the snake based on the provided KeyEvent and key configuration.
     * Changes the canChangeDirection to false.
     *
     * @param e    The KeyEvent representing the pressed key.
     * @param keys The list of key codes representing left, right, up, and down keys.
     */
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

    /**
     * Moves both the Snake's head and body.
     * Changes the canChangeDirection to true.
     */
    public void move()
    {
        moveBody();
        moveHead();
        canChangeDirection = true;
    }

    /**
     * Moves the body parts of the snake to follow the head.
     */
    public void moveBody()
    {
        for (int i = size - 1; i > 0; i--)
        {
            Point prev = new Point(snake.get(i - 1).getPoint());
            snake.get(i).setPoint(prev);
        }
    }

    /**
     * Moves the head of the snake based on its current direction.
     */
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

    /**
     * Grows the snake by adding a new body part.
     */
    public void grow()
    {
        Color c = snake.get(size-1).getColor();
        Point p = snake.get(size-1).getPoint();
        // Changing the new part's color slightly.
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        float hue = (hsb[0] + 0.02f) % 1.0f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);

        int diameter = snake.get(size-1).getDiameter();
        if (diameter > 21)
            diameter = diameter - 2;
        snake.add(new GameObject(p, new Color(rgb), diameter));
        size++;
    }

    /**
     * Gets the current direction of the snake.
     *
     * @return The current direction of the snake.
     */
    public Direction getDirection()
    {
        return direction;
    }

    /**
     * Sets the direction of the snake.
     *
     * @param d The new direction for the snake.
     */
    public void setDirection(Direction d)
    {
        direction = d;
    }

    /**
     * Checks if the snake is alive.
     *
     * @return True if the snake is alive, false otherwise.
     */
    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Marks the snake as dead.
     */
    public void dead()
    {
        isAlive = false;
    }

    /**
     * Gets the size of the snake.
     *
     * @return The size of the snake.
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Gets a specific body part of the snake.
     *
     * @param i The index of the desired body part.
     * @return The GameObject representing the specified body part.
     */
    public GameObject getPart(int i)
    {
        return snake.get(i);
    }

}
