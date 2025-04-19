package PhysicsProgram.src.com;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {
    
    JTextField obj1TF = new JTextField("Input Mass of Object 1 (kg)");
    JTextField obj2TF = new JTextField("Input Mass of Object 2 (kg)");
    JTextField obj1TFVelo = new JTextField("Input Initial Velocity of Object 1");
    JTextField obj2TFVelo = new JTextField("Input Initial Velocity of Object 2");

    JButton doneButton = new JButton("Start Simulation");

    JPanel input = new JPanel();

    private static final int WIN_WIDTH = 1000;
    private static final int WIN_HEIGHT = WIN_WIDTH;
    private static final Dimension SCREEN_SIZE = new Dimension(WIN_WIDTH, WIN_HEIGHT);
    int scale = 5;
    int obj1_Mass, obj2_Mass, obj1InitVelo, obj2InitVelo;
    Object obj1, obj2;
    
    Hud obj1Hud, obj2Hud;
    Thread gameThread;
    Graphics graphics;
    Image image; 

    GamePanel() {
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        inputPanel();
        
    }

    public void inputPanel() {
        
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
        input.add(obj1TF);
        input.add(obj1TFVelo);
        input.add(obj2TF);
        input.add(obj2TFVelo);
        input.add(doneButton);
        this.add(input);

        doneButton.addActionListener(e -> {
            try {
            obj1_Mass = Integer.parseInt(obj1TF.getText());
            obj2_Mass = Integer.parseInt(obj2TF.getText());
            obj1InitVelo = Integer.parseInt(obj1TFVelo.getText());
            obj2InitVelo = Integer.parseInt(obj2TFVelo.getText());
            this.remove(input);
            setupSimulationPanel();
            this.revalidate();
            this.repaint();

            gameThread = new Thread(this);
            gameThread.start();

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setupSimulationPanel() {
        this.setBackground(Color.WHITE);
        createObject();
    }


    public void createObject() {
        System.out.println("Creating objects...");
        int obj1_width = obj1_Mass * scale; 
        int obj1_length = obj1_Mass * scale;
        int obj2_width = obj2_Mass * scale; 
        int obj2_length = obj2_Mass * scale;
        obj1 = new Object(0, (WIN_HEIGHT / 2), obj1_length, obj1_width, obj1_Mass, obj1InitVelo, 1);
        obj2 = new Object((WIN_WIDTH - obj1_width), (WIN_HEIGHT / 2), obj2_length, obj2_width, obj2_Mass, obj2InitVelo, 2);
        System.out.println("Objects created: " + obj1 + ", " + obj2);
        obj1.velocityX = obj1.initVelocity;
        obj2.velocityX = -obj2.initVelocity;

        obj1Hud = new Hud(obj1);
        obj2Hud = new Hud(obj2);

    }

    public void update() {
        if (obj1 != null && obj2 != null) {
            obj1.update();
            obj2.update();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while (delta >= 1) {
                update();
                checkCollision();
                SwingUtilities.invokeLater(() -> repaint());
                delta--;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkCollision() {
        if (obj1 == null || obj2 == null) return;

        if (obj1.intersects(obj2)) {
            handleElasticCollision(obj1, obj2);
        }
        while (obj1.intersects(obj2)) {
            obj1.x += Integer.signum(obj1.velocityX);
            obj2.x += Integer.signum(obj2.velocityX);
        }
    }

    // Calculate the conservation of Momentum
    public void handleElasticCollision(Object obj1, Object obj2) {
        int m1 = obj1.Mass;
        int m2 = obj2.Mass;
        int u1 = obj1.velocityX;
        int u2 = obj2.velocityX;

        int v1 = (int)(((m1 - m2) / (double)(m1 + m2)) * u1 + ((2.0 * m2) / (m1 + m2)) * u2);
        int v2 = (int)(((2.0 * m1) / (m1 + m2)) * u1 + ((m2 - m1) / (double)(m1 + m2)) * u2);

        obj1.velocityX = v1;
        obj2.velocityX = v2;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        obj1.draw(g);
        obj2.draw(g);
        obj1Hud.draw(g);
        obj2Hud.draw(g);
    }

}
