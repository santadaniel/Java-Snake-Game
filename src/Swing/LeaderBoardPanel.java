package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LeaderBoardPanel extends JPanel
{
    ArrayList<String> names;
    SnakeFrame frame;

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Names", Font.BOLD, 36));
        for (int i = 0; i < names.size(); i++)
        {
            drawStringToScreen(g, i+1 + ". " + names.get(i), 0, (i + 1) * 55);
        }
    }

    public void drawStringToScreen(Graphics g, String s, int xRelPoz, int yRelPoz)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(s) + xRelPoz) / 2;
        int y = (yRelPoz);
        g.setColor(Color.RED);
        g.drawString(s, x, y);
    }

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

    public LeaderBoardPanel(SnakeFrame sf)
    {
        frame = sf;
        names = new ArrayList<>();
        int i = 0;
        for (Double score : frame.lb.getPlayers().keySet())
        {
            i++;
            String name = frame.lb.getPlayers().get(score);
            names.add(name + ", Score: " + (int) Math.floor(score));
            if (i == 10)
                break;
        }
        setLayout(new GridLayout(12, 3));
        backToMenuButton();
        repaint();
        revalidate();
    }
}
