package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The MenuPanel class represents the main menu panel in the Snake game.
 * It extends from JPanel.
 * It contains buttons for starting the game, viewing the leaderboard, and exiting the application.
 */
public class MenuPanel extends JPanel
{
    /**
     * Constructs a new MenuPanel with buttons for One Player, Two Players, Leader Board, and Exit.
     *
     * @param sf The SnakeFrame associated with this panel.
     */
    public MenuPanel(SnakeFrame sf)
    {
        ArrayList<JButton> buttons = new ArrayList<>();
        setBackground(Color.WHITE);
        setLayout(new GridLayout(4,1, 0, 10));
        JButton B1 = new JButton("One Player");
        JButton B2 = new JButton("Two Players");
        JButton B3 = new JButton("Leader Board");
        JButton B4 = new JButton("Exit");
        buttons.add(B1);
        buttons.add(B2);
        buttons.add(B3);
        buttons.add(B4);
        ActionListener ml = new MyActionListener(sf, null);

        B1.setActionCommand("onePlayer");
        B2.setActionCommand("twoPlayers");
        B3.setActionCommand("leaderBoard");
        B4.setActionCommand("exit");

        Font bFont = B1.getFont().deriveFont(Font.BOLD, 40);
        for (JButton button : buttons)
        {
            button.setFont(bFont);
            button.setFocusable(false);
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            add(button);
            button.addActionListener(ml);
        }
    }
}
