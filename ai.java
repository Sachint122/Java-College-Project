import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

class Panel extends JPanel implements ActionListener, KeyListener {
    private static final String IMAGE_PATH = "D:\\Private\\devil code\\java code\\project\\";
    private static final String LIFE_IMAGE = "shortRed.jpg";
    private static final String DEVIL_IMAGE = "lowDevil.jpg";
    private static final String BACKGROUND_IMAGE = "bb.jpg";

    private static final int DELAY = 20;
    
    private int x, y, sx, sy, chance, i, j, k;
    private int score, delay, barPosX, barPosY;
    private boolean touch, gameover, kill, game, pause, backgroundColor;
    private boolean barEnd;

    private BufferedImage lifeImage, devilImage, backgroundImage;
    private Timer timer, timer1, timer2;
     private static class Point {
        int x;
        int y;
        int z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private Point[] xy = new Point[150];
     Panel() {
        loadImages();
        setUpKeyListeners();
        setUpGame();
    }

    private void loadImages() {
        try {
            lifeImage = ImageIO.read(new File(IMAGE_PATH + LIFE_IMAGE));
            devilImage = ImageIO.read(new File(IMAGE_PATH + DEVIL_IMAGE));
            backgroundImage = ImageIO.read(new File(IMAGE_PATH + BACKGROUND_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpKeyListeners() {
        setFocusable(true);
        timer = new Timer(DELAY, this);
        timer1 = new Timer(1000, this);
        timer2 = new Timer(-200, this);
        timer2.start();
    }

    private void setUpGame() {
        fill();
        game = true;
        backgroundColor = true;
        timer.start();
    }

   void fill() {
        int x1, y1 = 13, i = 0;
        Random random = new Random();
        while (y1 <= 300) {
            x1 = 0;
            while (x1 < 1160) {
                xy[i] = new Point(x1, y1, random.nextInt(4));
                x1 = x1 + 50;
                i++;
            }
            y1 = y1 + 50;
        }
    }
      private void drawMainFrame(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 10, 1520, 748);
        g.drawRect(1, 11, 1518, 746);
        g.drawRect(2, 12, 1516, 744);
        g.setColor(Color.blue);
        g.drawLine(1200, 10, 1200, 755);
        g.drawLine(1201, 10, 1201, 755);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundColor) {
            drawBackground(g);
        } else if (game) {
            drawMainFrame(g);
            drawLife(g);
            drawBoxes(g);
            drawBall(g);
            drawGameInfo(g);
            drawBar(g);
            drawBarEnd(g);
        } else {
            drawEndScreen(g);
        }
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0 + k, this);
    }
    
    void drawBoxes(Graphics g) {
        int x, y = 13, i = 0;
        while (y <= 300) {
            x = 0;
            while (x < 1160) {
                g.setColor(Color.red);
                if (xy[i].z == 1) {
                    g.setColor(Color.green);
                    g.drawOval(xy[i].x, xy[i].y, 44, 44);
                }
                if (xy[i].y > 0) {
                    g.drawRect(xy[i].x, xy[i].y, 46, 46);
                }
                x = x + 50;
                i++;
            }
            y = y + 50;
        }
    }


    private void drawBall(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, 10, 10);
    }

    private void drawGameInfo(Graphics g) {
        if (pause) {
            drawPauseScreen(g);
        }
    }

    private void drawBar(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fill3DRect(barPosX, barPosY, 100, 20, getFocusTraversalKeysEnabled());
    }

    private void drawBarEnd(Graphics g) {
        drawBackground(g);
    }

    private void drawEndScreen(Graphics g) {
        g.drawImage(devilImage, 330, -110, this);
        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 60));
        g.drawString("YOUR GAME IS OVER", 450, 686);
        g.drawString("GOOD BYE", 600, 746);
    }

    private void drawPauseScreen(Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 50));
        g.drawString("POUSE", 550, 500);
        g.drawString("PRESE LEFT/RIGHT KEY TO RESUME GAME", 100, 550);
        pause = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGameState();
        checkBallHit();
        repaint();
    }

    private void updateGameState() {
        if (score >= 10) {
            delay -= 5;
        }

        if (game) {
            moveBall();
        } else {
            i++;
        }

        if (j <= 1500) {
            j++;
        } else {
            backgroundColor = false;
            barEnd = true;
        }

        if (k >= -900 && barEnd) {
            k--;
        }

        if (k == -901) {
            barEnd = false;
            timer2.stop();
        }
    }

    private void moveBall() {
        if (pause) {
            timer.stop();
        }

        bounceBall();

        if (barPosX >= 10 && barPosX <= 1090 && k == -901) {
            barPosX += (delay == 20) ? 10 : 15;
            timer.start();
        }
    }

    private void bounceBall() {
        x += sx;
        y += sy;

        if (x > 1190 || x < 0) {
            sx = -sx;
        }
        if (y > 710 || kill || y < 10) {
            if (touch) {
                sy = -sy;
            } else {
                gameover = true;
            }
        }
    }

    private void checkBallHit() {
        if (y == barPosY - 10) {
            if (x >= barPosX && x <= (barPosX + 100)) {
                touch = true;
            } else {
                touch = false;
                chance--;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameover) {
            if (chance >= 1) {
                restartGame();
            } else {
                chance = 0;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Z && chance <= 2) {
            game = false;
            timer.stop();
            timer1.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            pause = true;
        }

        if ((e.getKeyCode() == KeyEvent.VK_LEFT && !gameover) && barPosX >= 10 && k == -901) {
            barPosX -= (delay == 20) ? 10 : 15;
            timer.start();
        }

        if ((e.getKeyCode() == KeyEvent.VK_RIGHT && !gameover) && barPosX <= 1090 && k == -901) {
            barPosX += (delay == 20) ? 10 : 15;
            timer.start();
        }
    }

    private void restartGame() {
        x = 500;
        y = 400;
        gameover = false;
        barPosX = 250;
        barPosY = 720;
        touch = false;
        kill = false;
        timer.stop();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

public class ai {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("snake game");
            frame.setBounds(0, 0, 1550,800);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            pannel panel = new pannel();
            panel.setBackground(Color.black);
            panel.setBounds(20, 20, 1550,800);
            frame.add(panel);
        });
    }
}