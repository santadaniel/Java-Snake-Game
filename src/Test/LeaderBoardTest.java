package Test;

import Game.LeaderBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LeaderBoardTest
{
    private LeaderBoard leaderBoard;

    @Before
    public void setup() throws IOException
    {
        leaderBoard = new LeaderBoard();
    }

    @Test
    public void addNewPlayerTest()
    {
        String newPlayer = "Test";
        double score = 50;
        leaderBoard.addNewPlayer(newPlayer, score);
        Assert.assertTrue(leaderBoard.getPlayers().containsKey(score));
    }
}
