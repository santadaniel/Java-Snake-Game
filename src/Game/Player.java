package Game;

/**
 * Represents a player in a game with a name and a score.
 */
public class Player
{
    /**
     * The name of the player.
     */
    private String name;

    /**
     * The score achieved by the player.
     */
    private int score;

    /**
     * Constructs a player with the specified name and score.
     *
     * @param n The name of the player.
     * @param s The initial score of the player.
     */
    public Player(String n, int s)
    {
        name = n;
        score = s;
    }

    /**
     * Gets the current score of the player.
     *
     * @return The current score of the player.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }
}
