package Test;

import Game.GameObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class GameObjectTest
{
    GameObject o;

    @Before
    public void setup()
    {
        o = new GameObject(new Point(2, 5), Color.RED, 25);
    }

    @Test
    public void getDiameterTest()
    {
        Assert.assertEquals(o.getDiameter(), 25);
    }

    @Test
    public void isSamePointTest()
    {
        Assert.assertTrue(o.isSamePoint(new GameObject(new Point(2, 5), Color.BLUE, 500)));
    }
}
