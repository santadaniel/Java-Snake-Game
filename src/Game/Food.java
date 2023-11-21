package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Food class represents a food item in the Snake game.
 * It extends from GameObject.
 * It includes an additional power attribute.
 */
public class Food extends GameObject
{
    /**
     * The power associated with the food.
     */
    private int power;

    /**
     * Constructs a new Food object with the specified position, color, diameter, and power.
     *
     * @param p    The position of the food item as a Point.
     * @param c    The color of the food item.
     * @param d    The diameter of the food item.
     * @param pow  The power associated with the food item.
     */
    public Food(Point p, Color c, int d, int pow)
    {
        super(p, c, d);
        power = pow;
    }

    /**
     * Spawns a new Food object at a random location, avoiding positions occupied by snakes.
     *
     * @param snakes The list of snakes currently in the game.
     * @return A new Food object.
     */
    public static Food spawnFood(ArrayList<Snake> snakes)
    {
        ArrayList<Point> occupiedPoints = new ArrayList<>();
        Random random = new Random();

        int x = random.nextInt(15) + 1;
        int y = random.nextInt(15) + 1;

        Point p = new Point(x, y);
        for (Snake snake : snakes)
        {
            for (int i = 0; i < snake.getSize(); i++)
                occupiedPoints.add(snake.getPart(i).getPoint());
        }
        while (occupiedPoints.contains(p))
        {
            p.x = random.nextInt(15) + 1;
            p.y = random.nextInt(15) + 1;
        }
        double chance = random.nextDouble();
        if (chance < 0.9)
            return new Food(p, Color.RED, 25, 1);
        else
            return new Food(p, Color.YELLOW, 25, 3);
    }

    /**
     * Checks if the food item has been eaten by a snake's head.
     *
     * @param o The Snake's head to check for collision with the food item.
     * @return True if the food item has been eaten, false otherwise.
     */
    public boolean isEaten(GameObject o)
    {
        return isSamePoint(o);
    }

    /**
     * Gets the power of the food item.
     *
     * @return The power of the food item.
     */
    public int getPower()
    {
        return power;
    }
}
