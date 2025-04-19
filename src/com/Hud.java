package PhysicsProgram.src.com;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Hud extends Rectangle{

    private double pixelsPerMeter = 50.0;
    private double fps = 60.0;
    private Object obj;

    Hud(Object obj) {
        this.obj = obj;
    }

    public double getSpeedMetersPerSecond(double pixelsPerMeter, double fps) {
        return Math.abs(obj.velocityX) / pixelsPerMeter * fps;
    } 

    public void draw (Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, 24));
        
        double speed = getSpeedMetersPerSecond(pixelsPerMeter, fps);

        String text =String.format("Object  %d Speed: %.2f m/s", obj.id, speed);

        int y = obj.id == 1 ? 40 : 70; // draw obj1 at y=40, obj2 at y=70

        g.drawString(text, 20, y);
    }
}
