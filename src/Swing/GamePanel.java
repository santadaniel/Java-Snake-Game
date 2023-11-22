package Swing;

import Game.Food;
import Game.GameObject;
import Game.Player;
import Game.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The GamePanel class represents the panel where the Snake game is displayed.
 * It extends from JPanel and implements ActionListener.
 */
public class GamePanel extends JPanel implements ActionListener
{
    /**
     * The food object in the game.
     */
    private Food food;

    /**
     * The list of snakes in the game.
     */
    private ArrayList<Snake> snakes;

    /**
     * The current state of the game.
     */
    private State currentState;

    /**
     * The size of each cell of the game's grid.
     */
    private static int cellSize = 49;

    /**
     * The timer used to update the game state at regular intervals.
     */
    private Timer timer;

    /**
     * Flag indicating whether the game has ended.
     */
    private boolean isEnd = false;

    /**
     * The font used for displaying texts.
     */
    private Font font;

    /**
     * The SnakeFrame associated with the panel.
     */
    private SnakeFrame frame;

    /**
     * The delay between timer events, controlling the speed of the game.
     */
    private static int delay = 150;

    /**
     * The text field used for player name input.
     */
    private JTextField textField;

    /**
     * The set of key codes for each player.
     */
    private static ArrayList<ArrayList<Integer>> keys;

    /**
     * Constructs a GamePanel with the specified SnakeFrame.
     *
     * @param sf The SnakeFrame associated with the panel.
     */
    public GamePanel (SnakeFrame sf)
    {
        frame = sf;
        frame.setFocusable(true);
        snakes = new ArrayList<>();
        currentState = sf.getCurrentState();
        font = new Font("score", Font.BOLD, 20);
        keys = createKeys();
        setFocusable(true);
        setBackground(Color.WHITE);
        if (currentState.equals(State.OnePlayer))
        {
            setSnakes(Snake.createOneSnake());
        }
        if (currentState.equals(State.TwoPlayers))
        {
            setSnakes(Snake.createTwoSnakes());
        }
        food = Food.spawnFood(snakes);
        timer = new Timer(delay, this);
        timer.start();
        sf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e)
            {
                for (int i = 0; i < snakes.size(); i++)
                {
                    snakes.get(i).changeDir(e, keys.get(i));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * Overrides the actionPerformed method.
     * Handles timer events and updates the game's state.
     *
     * @param e The ActionEvent associated with the timer event.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        timer.setDelay(delay);
        for (Snake snake : snakes)
        {
            snake.move();
            if (food.isEaten(snake.getPart(0)))
            {
                // Speeds up the Game if the Food is eaten.
                timer.setDelay(delay / 3);
                for (int i = 0; i < food.getPower(); i++)
                {
                    snake.grow();
                }
                food = Food.spawnFood(snakes);
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

    /**
     * Checks for collisions in the game.
     */
    public void checkCollision()
    {
        checkSelf();
        checkWall();
        if (frame.getCurrentState().equals(State.TwoPlayers))
            checkOther();
    }

    /**
     * Checks for collision with the other snake.
     */
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

    /**
     * Checks for collision with the grid's walls.
     */
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

    /**
     * Checks for collision with itself.
     */
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

    /**
     * Checks if all snakes are alive.
     *
     * @return True if all snakes are alive, false otherwise.
     */
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

    /**
     * Checks if the player's score is on the leaderboard.
     *
     * @return True if the score is on the leaderboard, false otherwise.
     */
    public boolean isOnBoard()
    {
        ArrayList<Player> players = frame.lb.getPlayers();
        int i = 0;
        if (players.isEmpty())
        {
            return true;
        }
        for (Player player : players)
        {
            i++;
            if (snakes.get(0).getSize() - 3 > player.getScore())
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

    /**
     * Adds a button with the specified name, command, and position to the panel.
     *
     * @param name     The name of the button.
     * @param command  The ActionCommand associated with the button.
     * @param x        The x-coordinate of the button.
     * @param y        The y-coordinate of the button.
     */
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

    /**
     * Creates a JTextField for player name input.
     *
     * @param score The score associated with the player.
     * @return The created JTextField.
     */
    public JTextField createTextField(Integer score)
    {
        JTextField textField = new JTextField("Please enter your name");
        // Setting the JTextField's name to the score to store it.
        textField.setName(score.toString());
        textField.setFocusable(true);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.RED);
        textField.setBounds(150, 200, 300, 50);

        return textField;
    }

    /**
     * Overrides the paintComponent method.
     * Draws the game components on the panel.
     *
     * @param g The Graphics object.
     */
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
        if (frame.getCurrentState().equals(State.OnePlayer))
        {
            g.setFont(font);
            g.setColor(Color.ORANGE);
            drawStringToScreen(g, "Score: " + (snakes.get(0).getSize()-3), 0, -680);
        }
        if (isEnd)
            drawEnd(g);
    }

    /**
     * Draws a black screen and draws the ending based on the number of players.
     *
     * @param g The Graphics object.
     */
    public void drawEnd(Graphics g)
    {
        timer.stop();
        font = new Font("End", Font.BOLD, 36);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (frame.getCurrentState().equals(State.OnePlayer))
        {
            drawScenOne(g);
            addButton("Back to Menu", "backToMenu", 275, 415);
            if (isOnBoard())
            {
                textField = createTextField(snakes.get(0).getSize() - 3);
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

    /**
     * Draws the end game screen for a single-player scenario.
     *
     * @param g The Graphics object.
     */
    public void drawScenOne(Graphics g)
    {
        String gameOver = "GAME OVER";
        drawStringToScreen(g, gameOver,0, -40);
        String score = "Your score is: " + (snakes.get(0).getSize() - 3);
        drawStringToScreen(g, score, 0, 40);
    }

    /**
     * Draws the end game screen for a two-player scenario.
     *
     * @param g The Graphics object.
     */
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
        int y = (getHeight() - 36 + yRelPoz) / 2;
        g.setColor(Color.RED);
        g.drawString(s, x, y);
    }

    /**
     * Draws the game's grid on the panel.
     *
     * @param g The Graphics object.
     */
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

    /**
     * Draws a snakes on the panel.
     *
     * @param g      The Graphics object.
     * @param snake  The snake to be drawn.
     */
    private void drawSnake(Graphics g, Snake snake)
    {
        for (int i = 0; i < snake.getSize(); i++)
        {
            drawObject(g, snake.getPart(i));
        }
    }

    /**
     * Draws a GameObject on the panel.
     *
     * @param g  The Graphics object.
     * @param o  The GameObject to be drawn.
     */
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

    /**
     * Creates a set of key codes for each player.
     *
     * @return The ArrayList of key codes for each player.
     */
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

    /**
     * Sets the List
     * @param s The list of Snakes.
     */
    public void setSnakes(ArrayList<Snake> s)
    {
        snakes = s;
    }
}
