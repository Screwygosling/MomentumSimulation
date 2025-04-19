package PhysicsProgram.src.com;

import java.awt.EventQueue;

public class Physics {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}
