package PhysicsProgram.src.com;

import java.awt.*;
import java.awt.Rectangle;

public class Object extends Rectangle{
    
    int id;
    int Mass;
    int initVelocity;
    int Momentum;
    int velocityX;

    Object(int x, int y, int length, int width, int Mass, int Velocity, int id) {
        super(x, y, length, width);
        this.Mass = Mass;
        this.initVelocity = Velocity;
        this.id = id;
    }

    // Calculate Momentum
    public void calcMomentum() {
        Momentum = Mass * initVelocity;
    }

    public void reverseDirection() {
        x -= velocityX;
    }

    public void move() {
        x += velocityX;        
    }

    public void update() {
        if (x < 0 || x + width > 1000) {
            velocityX = -velocityX;
        }
        move();
        calcMomentum();
    }

    // Draw Objects at specified x coordinates in Object Constructor
    public void draw(Graphics g) {
        if (id == 1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        g.fillRect(x, y, width, height);
    }

}
