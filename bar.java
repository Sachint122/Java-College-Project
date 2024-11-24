import javax.swing.*;
import java.awt.*;
 class bar_line extends JPanel {

    public void paint(Graphics g){
        super.paint(g);
        for(int  i=0;i<=500;i++){
            g.drawString("➖", 100+i, 100);
            repaint();
        }
    }
}
public class bar {
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("snake game");
            frame.setBounds(0, 0, 1550, 800);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            bar_line pannel = new bar_line();
            pannel.setBackground(Color.black);
            pannel.setBounds(20, 20, 1550, 800);
            frame.add(pannel);
        });
    }
}
