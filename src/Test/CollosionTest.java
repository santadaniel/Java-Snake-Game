package Test;

import Game.Direction;
import Game.Snake;
import Swing.GamePanel;
import Swing.SnakeFrame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class CollosionTest
{
    private Snake s1;
    private Snake s2;
    private Snake s3;
    private ArrayList<Snake> snakes = new ArrayList<>();
    private GamePanel gp;

    @Before
    public void setup()
    {
        gp = new GamePanel(new SnakeFrame());
        ArrayList<Point> points1 = new ArrayList<>();
        points1.add(new Point(15,8));
        points1.add(new Point(14,8));
        points1.add(new Point(13,8));
        s1 = new Snake(points1, Color.BLUE, Direction.RIGHT);

        ArrayList<Point> points2 = new ArrayList<>();
        points2.add(new Point(14,9));
        points2.add(new Point(14,10));
        points2.add(new Point(14,11));
        s2 = new Snake(points2, Color.RED, Direction.UP);

        ArrayList<Point> points3 = new ArrayList<>();
        points3.add(new Point(2,2));
        points3.add(new Point(1,2));
        points3.add(new Point(1,1));
        points3.add(new Point(2,1));
        points3.add(new Point(3,1));
        s3 = new Snake(points3, Color.RED, Direction.UP);
        snakes.add(s1);
        snakes.add(s2);
        snakes.add(s3);

        gp.setSnakes(snakes);
    }

    @Test
    public void checkWallTest()
    {
        s1.move();
        gp.checkWall();
        Assert.assertFalse(s1.isAlive());
    }

    @Test
    public void checkSelfTest()
    {
        s3.move();
        gp.checkSelf();
        Assert.assertFalse(s3.isAlive());
    }

    @Test
    public void checkOtherTest()
    {
        snakes.remove(s3);
        s2.move();
        System.out.println(s2.getPart(0).getPoint());
        gp.checkOther();
        Assert.assertFalse(s2.isAlive());
    }
}
