package Game;

import java.io.*;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The LeaderBoard class manages the leaderboard for the Snake game, including reading, writing, and updating player scores.
 */
public class LeaderBoard
{
    /**
     * A sorted map containing player scores and names, ordered by descending scores.
     * Scores stored as doubles so that 2 same scores can get on the leader board.
     */
    private SortedMap<Double, String> players;

    /**
     * Constructs a LeaderBoard by reading player scores and names from a file.
     * The file format is expected to be in the form of "name1###score1###name2###score2...".
     *
     * @throws IOException If an error occurs while reading from the file.
     */
    public LeaderBoard() throws IOException
    {
        players = new TreeMap<>(Collections.reverseOrder());
        FileReader fr = new FileReader("res/LeaderBoard.txt");
        BufferedReader br = new BufferedReader(fr);
        while (true)
        {
            String line = br.readLine();
            if (line == null) break;
            String[] input = line.split("###");
            //every two strings contains a player
            for (int i = 0; i < input.length; i = i + 2)
            {
                players.put(Double.parseDouble(input[i+1]), input[i]);
            }
        }
        br.close();
    }

    /**
     * Writes the current leaderboard to the file.
     *
     * @throws IOException If an error occurs while writing to the file.
     */
    public void changeLeaderBoard() throws IOException
    {
        FileWriter fw = new FileWriter("res/LeaderBoard.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        if (players != null)
        {
            int i = 0;
            for (Double score : players.keySet())
            {
                if (i == 10)
                {
                    break;
                }
                bw.write(players.get(score));
                bw.write("###");
                bw.write(score.toString());
                bw.write("###");
                i++;
            }
        }
        bw.close();
    }

    /**
     * Gets the sorted map of player scores and names.
     *
     * @return The sorted map of player scores and names.
     */
    public SortedMap<Double, String> getPlayers()
    {
        return players;
    }

    /**
     * Adds a new player with the given name and score to the leaderboard.
     *
     * @param name  The name of the player.
     * @param score The score achieved by the player.
     */
    public void addNewPlayer(String name, double score)
    {
        players.put(score, name);
        try {
            changeLeaderBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
