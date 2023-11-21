package Swing;

import Game.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MyActionListener class implements the ActionListener interface
 * to handle various actions in the SnakeFrame Swing application.
 */
public class MyActionListener implements ActionListener
{
    /**
     * Reference to the SnakeFrame.
     */
    SnakeFrame frame;
    /**
     * Reference to the JTextField for user input.
     */
    JTextField textFieldf;

    /**
     * Constructs a MyActionListener with the specified SnakeFrame and JTextField.
     *
     * @param sf The SnakeFrame associated with this listener.
     * @param tf The JTextField associated with this listener for user input.
     */
    public MyActionListener(SnakeFrame sf, JTextField tf)
    {
        frame = sf;
        textFieldf = tf;
    }

    /**
     * Invoked when a button click occurs.
     *
     * @param ae The ActionEvent that occurred, in this case it is always a button getting clicked.
     */
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
