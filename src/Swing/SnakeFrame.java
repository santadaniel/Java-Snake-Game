package Swing;

import Game.LeaderBoard;
import Game.State;

import javax.swing.*;
import java.io.IOException;

public class SnakeFrame extends JFrame
{
    private State currentState;
    public LeaderBoard lb;

    private void updateContentPane()
    {
        switch (currentState)
        {
            case Menu:
                setContentPane(new MenuPanel(this));
                break;
            case OnePlayer, TwoPlayers:
                setContentPane(new GamePanel(this));
                break;
            case LeaderBoard:
                setContentPane(new LeaderBoardPanel(this));
                break;
        }
        revalidate();
        repaint();
    }

    public void setCurrentState(State s)
    {
        currentState = s;
        updateContentPane();
    }

    public State getCurrentState()
    {
        return currentState;
    }

    public SnakeFrame()
    {
        setTitle("Snake");
        setVisible(true);
        setLocation(560, 140);
        //Adding to the height due to the Frame title bar
        setSize(765, 765 + 23);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCurrentState(State.Menu);
        try {
            lb = new LeaderBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
