package Swing;

import Game.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener
{
    SnakeFrame frame;
    JTextField textFieldf;

    public MyActionListener(SnakeFrame sf, JTextField tf)
    {
        frame = sf;
        textFieldf = tf;
    }

    public void actionPerformed(ActionEvent ae)
    {
        switch (ae.getActionCommand())
        {
            case "onePlayer":
                frame.setCurrentState(State.OnePlayer);
                break;
            case "twoPlayers":
                frame.setCurrentState(State.TwoPlayers);
                break;
            case "leaderBoard":
                frame.setCurrentState(State.LeaderBoard);
                break;
            case "backToMenu":
                frame.setCurrentState(State.Menu);
                break;
            case "Submit":
                String name = textFieldf.getText();
                if (!name.contains("###") && !name.equals("Please enter your name"))
                {
                    frame.lb.addNewPlayer(textFieldf.getText(), Double.parseDouble(textFieldf.getName()));
                    textFieldf.setFocusable(false);
                }
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }
}
