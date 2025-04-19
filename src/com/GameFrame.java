package PhysicsProgram.src.com;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    
    GamePanel panel = new GamePanel();

    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Physics Thingymabob");
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
