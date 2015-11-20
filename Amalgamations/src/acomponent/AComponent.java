package acomponent;

import javax.swing.JPanel;

/**
 * An AComponent is a type of JPanel that utilizes the Animator class to animate
 * changes in its properties. 
 * 
 * An AComponent can animate changes in its location and size with built in
 * methods.
 * 
 * @author Caleb Rush
 */
public class AComponent extends JPanel {
    /**
     * Constructs a new AComponent object.
     */
    public AComponent() {}
    
    /**
     * Constructs a new AComponent object with the specified default size.
     * 
     * @param width the default width of the AComponent
     * @param height the default height of the AComponent
     */
    public AComponent(int width, int height) {
        this();
        setSize(width, height);
    }
    
    /**
     * Animates the AComponent's size over the specified length of time.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     */
    public void transform(int width, int height, int milliseconds) {
        Animator.animateValue(getWidth(), width, milliseconds, value -> {
            setSize(value, getHeight());
            return true;
        });
        Animator.animateValue(getHeight(), height, milliseconds, value -> {
            setSize(getWidth(), value);
            return true;
        });
    }
    
    /**
     * Animates the AComponent's size at the specified rate.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param velocity the rate that the size should change
     */
    public void transform(int width, int height, double velocity) {
        // Calculate the width and height velocities.
        double angle = width == 0?
                Math.PI:
                Math.atan((double)height / (double)width);
        final double wVelocity = velocity * Math.cos(angle);
        final double hVelocity = velocity * Math.sin(angle);
        
        Animator.animateValue(getWidth(), width, wVelocity, value -> {
            setSize(value, getHeight());
            return true;
        });
        Animator.animateValue(getHeight(), height, hVelocity, value -> {
            setSize(getWidth(), value);
            return true;
        });
    }
    
    /**
     * Animates the AComponent's size over the specified length of time without
     * changing the component's center position.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     */
    public void transformCentered(int width, int height, int milliseconds) {
        translate(getX() - (width - getWidth()) / 2,
                getY() - (height - getHeight()) / 2, milliseconds);
        transform(width, height, milliseconds);
    }
    
    /**
     * Animates the AComponent's size at the specified rate without
     * changing the component's center position.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param velocity the rate at which the size should change
     */
    public void transformCentered(int width, int height, double velocity) {
        translate(getX() - (width - getWidth()) / 2,
                getY() - (height - getHeight()) / 2, -velocity / 2);
        transform(width, height, velocity);
    }
    
    /**
     * Animates the AComponent's location over the specified length of time.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     */
    public void translate(int x, int y, int milliseconds) {
        Animator.animateValue(getX(), x, milliseconds, value -> {
            setLocation(value, getY());
            return true;
        });
        Animator.animateValue(getY(), y, milliseconds, value -> {
            setLocation(getX(), value);
            return true;
        });
    }
    
    /**
     * Animates the AComponent's location with the specified velocity.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param velocity the amount the panel's position should increment each 
     *                 frame
     */
    public void translate(int x, int y, double velocity) {
        // Calculate the x and y velocities.
        double angle = y == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xVelocity = velocity * Math.cos(angle);
        final double yVelocity = velocity * Math.sin(angle);
        
        Animator.animateValue(getX(), x, xVelocity, value -> {
            setLocation((int)value, getY());
            return true;
        });
        Animator.animateValue(getY(), y, yVelocity, value -> {
            setLocation(getX(), (int)value);
            return true;
        });
    }
    
    /**
     * Animates the AComponent's location over the specified length of time with
     * the specified acceleration.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param acceleration the acceleration the panel should have when moving
     * @param milliseconds the time in milliseconds the animation should last
     */
    public void translate(int x, int y, double acceleration, int milliseconds) {
        // Calculate the x and y velocities.
        double angle = x == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xAcceleration = acceleration * Math.cos(angle);
        final double yAcceleration = acceleration * Math.sin(angle);
        
        Animator.animateValue(getX(), x, xAcceleration, milliseconds, value -> {
            setLocation((int)value, getY());
            return true;
        });
        Animator.animateValue(getY(), y, yAcceleration, milliseconds, value -> {
            setLocation(getX(), (int)value);
            return true;
        });
    }
    
    public static void main(String[] args) {
        javax.swing.JFrame window = new javax.swing.JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        AComponent test2 = new AComponent(50, 50);
        test2.setBackground(java.awt.Color.BLUE);
        //window.add(test2);
        test2.setLocation(500, 500);
        
        AComponent test = new AComponent(50, 50);
        test.setBackground(java.awt.Color.RED);
        window.add(test);
        test.setLocation(500, 500);
        
        test.transformCentered(200, 200, 10.0);
    }
}
