package Test;

import Game.Direction;
import Game.Food;
import Game.Snake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static Game.Snake.createTwoSnakes;

public class FoodTest
{
    Food f1, f2;
    @Before
    public void setup()
    {
        f1 = new Food(new Point(2, 5), Color.RED, 30, 2);
        f2 = new Food(new Point(1, 12), Color.RED, 20, 2);
    }
    @Test
    public void getPowerTest()
    {
        Assert.assertEquals(f1.getPower(), f2.getPower());
    }

    @Test
    public void spawnFoodTest()
    {
        ArrayList<Snake> snakes = createTwoSnakes();
        f1 = Food.spawnFood(snakes);
        for (Snake snake : snakes)
            for (int i = 0; i < snake.getSize(); i++)
                Assert.assertNotEquals(f1.getPoint(), snake.getPart(i).getPoint());
    }

    @Test
    public void isEatenTest()
    {
        ArrayList<Point> headPoint = new ArrayList<>();
        headPoint.add(new Point(2, 5));
        Snake snake = new Snake(headPoint, Color.BLUE, Direction.RIGHT);
        Assert.assertTrue(f1.isEaten(snake.getPart(0)));
    }
}
