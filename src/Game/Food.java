package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Food extends GameObject
{
    private int power;

    public Food(Point p, Color c, int d, int pow)
    {
        super(p, c, d);
        power = pow;
    }

    public static Food createFood(ArrayList<Snake> snakes)
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

    public boolean isEaten(GameObject o)
    {
        return isSamePoint(o);
    }

    public void setPower(int p)
    {
        power = p;
    }

    public int getPower()
    {
        return power;
    }
}
