package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The LeaderBoardPanel class represents the panel displaying the leaderboard in the game.
 * It extends from JPanel.
 * It includes a list of player names and scores, and a button to go back to the main menu.
 */
public class LeaderBoardPanel extends JPanel
{
    /**
     * The list of player names and scores to display on the leaderboard.
     */
    private ArrayList<String> players;
    /**
     * Reference to the SnakeFrame associated with this panel.
     */
    private SnakeFrame frame;

    /**
     * Constructs a new LeaderBoardPanel.
     *
     * @param sf The SnakeFrame associated with this panel.
     */
    public LeaderBoardPanel(SnakeFrame sf)
    {
        frame = sf;
        players = new ArrayList<>();
        int i = 0;
        for (Double score : frame.lb.getPlayers().keySet())
        {
            i++;
            String name = frame.lb.getPlayers().get(score);
            players.add(name + ", Score: " + (int) Math.floor(score));
            if (i == 10)
                break;
        }
        setLayout(new GridLayout(12, 3));
        backToMenuButton();
        repaint();
        revalidate();
    }

    /**
     * Overrides the paintComponent method.
     * Draws the players in order, on the screen.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Names", Font.BOLD, 36));
        for (int i = 0; i < players.size(); i++)
        {
            drawStringToScreen(g, i+1 + ". " + players.get(i), 0, (i + 1) * 55);
        }
    }

    /**
     * Draws the specified string on the screen using the provided Graphics object.
     *
     * @param g         The Graphics object used for drawing.
     * @param s         The string to be drawn.
     * @param xRelPoz   x-position relative to the middle of the screen.
     * @param yRelPoz   y-position relative to the middle of the screen.
     */
    public void drawStringToScreen(Graphics g, String s, int xRelPoz, int yRelPoz)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(s) + xRelPoz) / 2;
        int y = (yRelPoz);
        g.setColor(Color.RED);
        g.drawString(s, x, y);
    }

    /**
     * Adds a "Back to Menu" button to the panel with the associated MyActionListener.
     */
    public void backToMenuButton()
    {
        ActionListener ml = new MyActionListener(frame, null);
        JButton button = new JButton("Back to Menu");
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.RED);
        button.setFocusable(false);
        button.setActionCommand("backToMenu");
        button.addActionListener(ml);
        button.setBounds(275, 600, 200, 50);
        for (int i = 0; i < 31; i++) {
            add(new JLabel());
        }
        add(button);
    }
}
