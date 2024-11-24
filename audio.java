 import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;

public class audio extends JPanel {
    private boolean condition;

    public audio() {
        condition = false; // Initial condition
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
        if (condition) {
            playSound("D:\\Private\\devil code\\java code\\project\\devil.wav"); // Replace with your audio file path
        }
        repaint(); // Repaint the panel
    }

    private void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (condition) {
            // Draw something when condition is true
            g.setColor(Color.RED);
            g.fillRect(50, 50, 100, 100);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Audio Paint Panel");
        audio paintPanel = new audio();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.add(paintPanel);
        frame.setVisible(true);
        paintPanel.setCondition(true); // Condition becomes true and plays audio
    }
}
 
