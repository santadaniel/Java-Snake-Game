package Swing;

import Game.Food;
import Game.GameObject;
import Game.Snake;
import Game.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedMap;

public class GamePanel extends JPanel implements ActionListener
{
    private Food food;
    private ArrayList<Snake> snakes;
    private State currentState;
    private static int cellSize = 49;
    private Timer timer;
    private boolean isEnd = false;
    private Font font;
    private SnakeFrame frame;
    private static int delay = 150;
    private JTextField textField;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        timer.setDelay(delay);
        for (Snake snake : snakes)
        {
            snake.move();
            if (food.isEaten(snake.getPart(0)))
            {
                timer.setDelay(delay / 3);
                for (int i = 0; i < food.getPower(); i++)
                {
                    snake.grow();
                }
                food = Food.createFood(snakes);
            }
        }
        checkCollision();
        if (allAlive())
            repaint();
        else
        {
            isEnd = true;
            repaint();
        }
    }

    public void checkCollision()
    {
        checkSelf();
        checkWall();
        if (snakes.size() > 1)
            checkOther();
    }

    public void checkOther()
    {
        for (int i = 0; i < snakes.size(); i++)
        {
            GameObject currentSnakeHead = snakes.get(i).getPart(0);
            //j = index of the other Snake.
            int j = (i-1) * (-1);
            Snake otherSnake = snakes.get(j);
            for (int k = 0; k < otherSnake.getSize(); k++)
                if (currentSnakeHead.isSamePoint(otherSnake.getPart(k)))
                    snakes.get(i).dead();
        }
    }

    public void checkWall()
    {
        for (Snake snake : snakes)
        {
            Point head = snake.getPart(0).getPoint();
            if (head.x < 1 || head.x > 15 || head.y < 1 || head.y > 15)
            {
                snake.dead();
            }
        }
    }

    public void checkSelf()
    {
        for (Snake snake : snakes)
        {
            for (int i = 1; i < snake.getSize(); i++)
            {
                if (snake.getPart(0).isSamePoint(snake.getPart(i)))
                    snake.dead();
            }
        }
    }

    public boolean allAlive()
    {
        boolean ret = true;
        for (Snake snake : snakes)
        {
            if (!snake.isAlive())
                ret = false;
        }
        return ret;
    }

    public boolean isOnBoard()
    {
        SortedMap<Double, String> players = frame.lb.getPlayers();
        int i = 0;
        if (players.keySet().isEmpty())
        {
            return true;
        }
        for (Double score : players.keySet())
        {
            i++;
            if (snakes.get(0).getSize() - 3 > score)
            {
                return true;
            }
        }
        //Made it, but it is the last.
        if (i < 10)
            return true;
        //Did not make it on the Leaderboard.
        return false;
    }

    public void addButton(String name, String command, int x, int y)
    {
        ActionListener ml = new MyActionListener(frame, textField);
        JButton button = new JButton(name);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.RED);
        button.setFocusable(false);
        button.setActionCommand(command);
        button.addActionListener(ml);
        button.setBounds(x, y, 200, 50);

        add(button);
    }

    public JTextField createTextField(Double score)
    {
        JTextField textField = new JTextField("Please enter your name");
        textField.setName(score.toString());
        textField.setFocusable(true);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.RED);
        textField.setBounds(150, 200, 300, 50);

        return textField;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawMap(g);
        for (Snake snake : snakes)
        {
            drawSnake(g, snake);
        }
        drawObject(g, food);
        if (snakes.size() == 1)
        {
            g.setFont(font);
            g.setColor(Color.ORANGE);
            drawStringToScreen(g, "Score: " + (snakes.get(0).getSize()-3), 0, -680);
        }
        if (isEnd)
            drawEnd(g);
    }

    public void drawEnd(Graphics g)
    {
        timer.stop();
        font = new Font("End", Font.BOLD, 36);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (snakes.size() == 1)
        {
            drawScenOne(g);
            addButton("Back to Menu", "backToMenu", 275, 415);
            if (isOnBoard())
            {
                Random random = new Random();
                textField = createTextField(snakes.get(0).getSize() - 3 + random.nextDouble());
                add(textField);
                addButton("Submit", "Submit", 500, 200);
            }
        }
        else
        {
            drawScenTwo(g);
            addButton("Back to Menu", "backToMenu", 275, 375);
        }
    }

    public void drawScenOne(Graphics g)
    {
        String gameOver = "GAME OVER";
        drawStringToScreen(g, gameOver,0, -40);
        String score = "Your score is: " + (snakes.get(0).getSize() - 3);
        drawStringToScreen(g, score, 0, 40);
    }

    public void drawScenTwo(Graphics g)
    {
        Snake snake1 = snakes.get(0);
        Snake snake2 = snakes.get(1);
        if (!snake1.isAlive() && !snake2.isAlive())
            drawStringToScreen(g, "Draw", 0, 0);
        if (snake1.isAlive() && !snake2.isAlive())
            drawStringToScreen(g, "Blue wins", 0, 0);
        if (!snake1.isAlive() && snake2.isAlive())
            drawStringToScreen(g, "Red wins", 0, 0);
    }

    //Calculating the center of the screen and creating messages.
    public void drawStringToScreen(Graphics g, String s, int xRelPoz, int yRelPoz)
    {
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(s) + xRelPoz) / 2;
        int y = (getHeight() - 36 + yRelPoz) / 2;
        g.setColor(Color.RED);
        g.drawString(s, x, y);
    }

    private void drawMap(Graphics g)
    {
        g.setColor(Color.BLACK);
        for (int x = 0; x < 15; x++)
        {
            for (int y = 0; y < 15; y++)
            {
                g.fillRect(x + x * cellSize, y + y *cellSize, cellSize, cellSize);
            }
        }
    }

    private void drawSnake(Graphics g, Snake snake)
    {
        for (int i = 0; i < snake.getSize(); i++)
        {
            drawObject(g, snake.getPart(i));
        }
    }

    private void drawObject(Graphics g, GameObject o)
    {
        g.setColor(o.getColor());
        int x = o.getPoint().x;
        int y = o.getPoint().y;
        //cellSize+1 because of the Lines
        g.fillRect((x-1) * (cellSize+1) + ((cellSize-o.getDiameter())/2),
                (y-1) * (cellSize+1) + ((cellSize-o.getDiameter())/2),
                o.getDiameter(),
                o.getDiameter());
    }

    public GamePanel (SnakeFrame sf)
    {
        frame = sf;
        frame.setFocusable(true);
        snakes = new ArrayList<>();
        currentState = sf.getCurrentState();
        font = new Font("score", Font.BOLD, 20);
        setFocusable(true);
        setBackground(Color.WHITE);
        if (currentState.equals(State.OnePlayer))
        {
            snakes.addAll(Snake.createOneSnake());
        }
        if (currentState.equals(State.TwoPlayers))
        {
            snakes.addAll(Snake.createTwoSnakes());
        }
        food = Food.createFood(snakes);
        timer = new Timer(delay, this);
        timer.start();
        sf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e)
            {
                ArrayList<ArrayList<Integer>> keys = createKeys();
                for (int i = 0; i < snakes.size(); i++)
                {
                    snakes.get(i).changeDir(e, keys.get(i));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    public ArrayList<ArrayList<Integer>> createKeys()
    {
        ArrayList<ArrayList<Integer>> keys = new ArrayList<>();

        ArrayList<Integer> firstSet = new ArrayList<>();
        ArrayList<Integer> secondSet = new ArrayList<>();
        firstSet.add(KeyEvent.VK_A);
        firstSet.add(KeyEvent.VK_D);
        firstSet.add(KeyEvent.VK_W);
        firstSet.add(KeyEvent.VK_S);

        secondSet.add(KeyEvent.VK_LEFT);
        secondSet.add(KeyEvent.VK_RIGHT);
        secondSet.add(KeyEvent.VK_UP);
        secondSet.add(KeyEvent.VK_DOWN);

        keys.add(firstSet);
        keys.add(secondSet);

        return keys;
    }
}
