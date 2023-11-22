package Test;

import Game.Direction;
import Game.Snake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeTest
{
    Snake snake;
    @Before
    public void setup()
    {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(9,8));
        points.add(new Point(8,8));
        points.add(new Point(7,8));
        snake = new Snake(points, Color.BLUE, Direction.RIGHT);
    }

    @Test
    public void growTest()
    {
        int before = snake.getSize();
        snake.grow();
        int after = snake.getSize();
        Assert.assertEquals(before + 1, after);
    }

    @Test
    public void moveTest()
    {
        ArrayList<Point> newPoints = new ArrayList<>();
        newPoints.add(new Point(10,8));
        newPoints.add(new Point(9,8));
        newPoints.add(new Point(8,8));
        Snake movedSnake = new Snake(newPoints, Color.BLUE, Direction.RIGHT);
        snake.move();
        for (int i = 0; i < snake.getSize(); i++)
        {
            Point currentPointS = snake.getPart(i).getPoint();
            Point currentPointMS = movedSnake.getPart(i).getPoint();
            Assert.assertEquals(currentPointS, currentPointMS);
        }
    }

    @Test
    public void changeDirTest()
    {
        Component sourceComponent = new Component() {};
        int keyCode = KeyEvent.VK_W;
        KeyEvent e = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, 'A');
        ArrayList<Integer> keySet = new ArrayList<>();
        keySet.add(KeyEvent.VK_A);
        keySet.add(KeyEvent.VK_D);
        keySet.add(KeyEvent.VK_W);
        keySet.add(KeyEvent.VK_S);
        snake.changeDir(e, keySet);
        Assert.assertEquals(snake.getDirection(), Direction.UP);
    }

    @Test
    public void isAliveTest()
    {
        Assert.assertTrue(snake.isAlive());
    }

    @Test
    public void deadTest()
    {
        snake.dead();
        Assert.assertFalse(snake.isAlive());
    }
}
