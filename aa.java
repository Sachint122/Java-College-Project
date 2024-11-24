import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

class pannel extends JPanel implements ActionListener, KeyListener {
    private int x = 500, y = 400, sx = 5, sy = 5, chance = 3, i = 0, j = 0, k = 0;
    private int score = 0, delay = 40, bar_posx = 250, bar_posy = 720;
    private BufferedImage life, devil, back;
    private Timer timer, timer1, timer2, endTimer;
    private Color myColor = new Color(148, 0, 211);
    private Color myColor1 = new Color(26, 188, 156);
    private boolean touch = false, gameover = false, kill = false, game = true, pouse = false, backc = true, barEnd, win = false;

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

    pannel() {
        try {
            life = ImageIO.read(new File("D:\\Private\\devil code\\java code\\project\\shortRed.jpg"));
            devil = ImageIO.read(new File("D:\\Private\\devil code\\java code\\project\\lowDevil.jpg"));
            back = ImageIO.read(new File("D:\\Private\\devil code\\java code\\project\\bb.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer1 = new Timer(1000, this);
        timer2 = new Timer(-200, this);
        endTimer = new Timer(1000, this);
        timer2.start();
        fill();
    }

    void main_frame(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 10, 1520, 748);
        g.drawRect(1, 11, 1518, 746);
        g.drawRect(2, 12, 1516, 744);
        g.setColor(Color.blue);
        g.drawLine(1200, 10, 1200, 755);
        g.drawLine(1201, 10, 1201, 755);
    }

    void drawBox(Graphics g) {
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

    void bounceBall(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, 10, 10);
        if (x > 1190 || x < 0)
            sx = -sx;
        if (y > 710 || kill || y < 10) {
            if (touch)
                sy = -sy;
            else
                gameover = true;
        }
        x = x + sx;
        y = y + sy;
    }

    void checkTouchBaar() {
        if (y == bar_posy - 10) {
            if (x >= (bar_posx + 1) && x <= (bar_posx + 101))
                touch = true;
            else {
                touch = false;
                chance--;
            }
        }
    }

    void kill_box() {
        int x1, y1 = 13, i = 0;
        kill = false;
        while (y1 <= 300) {
            x1 = 0;
            while (x1 < 1160) {
                if ((x >= xy[i].x && x <= xy[i].x + 47) && (y >= xy[i].y && y <= xy[i].y + 47)) {
                    if (xy[i].z == 1) {
                        xy[i].z = 0;
                    } else {
                        xy[i].x = 0;
                        xy[i].y = 0;
                    }
                    kill = true;
                    score++;
                    break;
                }
                x1 = x1 + 50;
                i++;
            }
            if (kill)
                break;
            y1 = y1 + 50;
        }
    }

    void countinueGame() {
        x = 500;
        y = 400;
        gameover = false;
        bar_posx = 250;
        bar_posy = 720;
        touch = false;
        kill = false;
        timer.stop();
        repaint();
    }

    void life(Graphics g) {
        for (int i = 0; i < chance; i++) {
            g.drawImage(life, 1207 + (i * 70), 13, this);
        }
    }

    void gameInstruction(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 40));
        g.drawString("Score=" + score + "", 1210, 105);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 30));
        g.setColor(myColor1);
        g.drawString("🕉️Instruction🕉️", 1240, 160);
        g.drawString("Hit the box conta", 1200, 190);
        g.drawString("-ining the circle", 1200, 220);
        g.drawString("     twice       ", 1200, 250);
        g.drawString("PRESS ENTER FOR  ", 1200, 280);
        g.drawString(" pouse GAME       ", 1200, 310);
    }

    void createrInfo(Graphics g) {
        g.setColor(myColor);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 30));
        g.drawString("CREATED BY DEVIL", 1200, 340);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 60));
        g.drawString("😈😈😈", 1275, 400);

    }

    void drawEndScreen(Graphics g) {
        g.drawImage(devil, 330, -110, this);
        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 60));
        g.drawString("YOUR GAME IS OVER", 450, 686);
        g.drawString("GOOD BYE", 600, 746);

    }

    void desion(Graphics g) {
        if (gameover && chance > 0) {
            g.setColor(Color.red);
            g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 50));
            g.drawString("YOU L00SE A LIFE", 350, 400);
            g.drawString("Press SPACE BAAR FOR COUNTINUE GAME", 100, 460);
            g.drawString("PRESS Z FOR EXIT THE GAME", 230, 520);
        } else if (chance == 0) {
            g.setColor(Color.red);
            g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 50));
            g.drawString("YOUR LIFE IS END", 400, 450);
            g.drawString("Press Z For EXIT THE GAME", 250, 500);
        }
    }

    void baarLine(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fill3DRect(bar_posx, bar_posy, 100, 20, getFocusTraversalKeysEnabled());
    }

    void darawPouseScreen(Graphics g) {
        if (pouse) {
            timer.stop();
            g.setColor(Color.yellow);
            g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 50));
            g.drawString("POUSE", 550, 500);
            g.drawString("PRESE LEFT/RIGHT KEY TO RESUME GAME", 50, 550);
            pouse = false;
        }
    }

    void progressBaar(Graphics g) {
        g.drawImage(back, 0, 0, this);
        g.setColor(Color.green);
        g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 40));
        g.drawString("Plese be patient your game will be loaded", bar_posx, bar_posy);
        for (int k = 0; k <= j; k++) {
            g.setColor(Color.green);
            g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 40));
            g.drawString("➖", -40 + k, 770);
            g.drawRoundRect(0, 748, 1528, 13, 5, 5);
        }
        g.setColor(Color.red);
        g.drawString((j / 15) + ".", j, 760);
    }

    void introGame(Graphics g) {
        g.drawImage(back, 0, 0 + k, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backc) {
            progressBaar(g);
        } else if (game) {
            main_frame(g);
            life(g);
            drawBox(g);
            g.setColor(Color.red);
            g.fillOval(x, y, 10, 10);
            if (k == -901&& !win) {
                bounceBall(g);
            }
            gameInstruction(g);
            createrInfo(g);
            darawPouseScreen(g);
            baarLine(g);
            desion(g);
            introGame(g);
             if (win) {
                g.drawString("you are win", 400, 400);
            }
        } else {
            drawEndScreen(g);
        }
        if (i == 5){
          SwingUtilities.getWindowAncestor(this).dispose(); }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameover) {
            if (chance >= 1)
                countinueGame();
            else
                chance = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_Z && chance <= 2) {
            game = false;
            timer.stop();
            timer1.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            pouse = true;
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT && !gameover) && bar_posx >= 10 && k == -901 && !win) {
            if (delay == 40)
                bar_posx = bar_posx - 10;
            else
                bar_posx = bar_posx - 15;
            timer1.stop();
            timer.start();
        }
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT && !gameover) && bar_posx <= 1090 && k == -901 && !win) {
            if (delay == 40)
                bar_posx = bar_posx + 10;
            else
                bar_posx = bar_posx + 15;
            timer1.stop();
            timer.start();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if (score == 4 && !win) {
        win = true;
        timer.stop();
        endTimer.start(); // Start the timer when score reaches 4
    }
        if (score >= 10)
            delay = delay - 5;
        if (!game|| win)
            i = i + 1;
        if (j <= 1500) {
            j++;
        } else {
            backc = false;
            barEnd = true;
        }
        if (k >= -900 && barEnd) {
            k--;
        }
        if (k == -901) {
            barEnd = false;
            timer2.stop();
        }
        kill_box();
        checkTouchBaar();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

public class aa {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("snake game");
            frame.setBounds(0, 0, 1550, 800);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            pannel pannel = new pannel();
            pannel.setBackground(Color.black);
            pannel.setBounds(20, 20, 1550, 800);
            frame.add(pannel);
        });
    }
}