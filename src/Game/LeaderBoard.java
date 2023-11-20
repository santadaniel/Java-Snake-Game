package Game;

import java.io.*;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;


public class LeaderBoard
{
    private SortedMap<Double, String> players;

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

    public SortedMap<Double, String> getPlayers()
    {
        return players;
    }

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
