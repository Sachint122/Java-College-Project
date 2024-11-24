import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class gamePannel extends JPanel implements ActionListener, KeyListener {
    private BufferedImage left, right, top, down, enemy, body;
    private boolean le = false, ri = true, to = false, d = false, gameover = false, move = true;
    private int snake_size = 2, score = 0, enemyx, enemyy, delay = 100;
    private int x[] = new int[750];
    private int y[] = new int[750];
    private int xpos[] = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475,500, 525, 550, 575, 600, 625, 200, 125, 700, 300, 750, 775, 800, 825, 850, 875, 900, 925, 950, 975, 1000,1025, 1050, 1075, 1100, 1125, 1150, 1175, 1200, 1225, 1250, 1275, 1300, 1325, 1350, 1375, 1400, 1425, 400, 1475 };
    private int ypos[] = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525,550, 575, 600, 625, 650, 200, 700, 500 };
    public static Random random = new Random();
    private Timer timer;
    gamePannel() {
        try {
            right = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\rightmouth.png"));
            top = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\upmouth.png"));
            down = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\downmouth.png"));
            left = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\leftmouth.png"));
            body = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\snakeimage.png"));
            enemy = ImageIO.read(new File("D:\\snake game\\src\\snakegame\\enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
        setEnemy();
    }

    private void setEnemy() {
        enemyx = xpos[random.nextInt(58)];
        enemyy = ypos[random.nextInt(26)];
        for (int i = snake_size; i >= 0; i--) {
            if (x[i] == enemyx && y[i] == enemyy) {
                setEnemy();
            }
        }
    }

    private void checkbody() {
        
        for (int i = snake_size - 1; i > 0; i--) {
            if (x[i] == x[0] && y[i] == y[0]) {
                timer.stop();
                gameover = true;
            }
        }
    }

    private void checkenemy() {
        if (x[0] == enemyx && y[0] == enemyy) {
            snake_size++;
            setEnemy();
            score++;
            if (score > 10) {
                delay = 80;
            } else if (score > 20) {
                delay = 60;
            }
        }
    }

    private void restart() {
        gameover = false;
        snake_size = 2;
        move = true;
        score = 0;
        le = false;
        ri = true;
        to = false;
        d = false;
        repaint();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.fillRect(25, 10, 1500, 55);
        g.setColor(Color.black);
        g.fillRect(25, 75, 1500, 710);
        g.setColor(Color.red);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 60));
        g.drawString("snake", 700, 55);
        if (gameover) {
            g.drawString("GAME OVER", 400, 400);
            g.drawString("Press Space_baar for restart", 350, 500);
        }
        g.setColor(Color.white);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 20));
        g.drawString("score=" + score, 1300, 25);
        g.drawString("length=" + snake_size, 1300, 50);
        if (move) {
            x[0] = 100;
            x[1] = 75;
            x[2] = 50;
            y[0] = 100;
            y[1] = 100;
            y[2] = 100;
        }
        if (le) {
            g.drawImage(left, x[0], y[0], this);
        } else if (ri) {
            g.drawImage(right, x[0], y[0], this);
        } else if (to) {
            g.drawImage(top, x[0], y[0], this);
        } else if (d) {
            g.drawImage(down, x[0], y[0], this);
        }
        for (int i = 1; i <= snake_size; i++) {
            g.drawImage(body, x[i], y[i], this);
        }
        g.drawImage(enemy, enemyx, enemyy, this);
        g.dispose();
    }// end of paint

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = snake_size; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (le) {
            x[0] = x[0] - 25;
        } else if (ri) {
            x[0] = x[0] + 25;
        } else if (to) {
            y[0] = y[0] - 25;
        } else if (d) {
            y[0] = y[0] + 25;
        }
        if (x[0] > 1500) {
            x[0] = 25;
        }
        if (x[0] < 25) {
            x[0] = 1500;
        }
        if (y[0] > 750) {
            y[0] = 75;
        }
        if (y[0] < 75) {
            y[0] = 750;
        }
        checkenemy();
        checkbody();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !ri) {

            le = true;
            ri = false;
            to = false;
            d = false;
            move = false;

        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !le) {

            le = false;
            ri = true;
            to = false;
            d = false;
            move = false;

        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !d) {

            le = false;
            ri = false;
            to = true;
            d = false;
            move = false;

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !to) {

            le = false;
            ri = false;
            to = false;
            d = true;
            move = false;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

public class snake extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("snake game");
            frame.setBounds(0, 0, 1550, 800);
            // frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            gamePannel pannel = new gamePannel();
            pannel.setBackground(Color.darkGray);
            pannel.setBounds(20, 20, 1550, 800);
            frame.add(pannel);
        });
    }
}
