package acomponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.util.Vector;

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
    // The list of animations currently being run.
    private final Vector<String> animations;
    // The image to be drawn.
    private Image image;
    // Whether or not to stretch the image when drawing it.
    private boolean stretchImage;
    // The color of the background (overriden from JPanel due to needing to
    // keep the component opaque.
    private Color background;
    // The color of the highlight.
    private Color highlightColor;
    // The radius of the highlight.
    private int highlightRadius;
    // The position of the center of the highlight.
    private int highlightX;
    private int highlightY;
    
    /**
     * Constructs a new AComponent object.
     */
    public AComponent() {
        animations = new Vector<>();
        highlightColor = Color.WHITE;
        // The component must be opaque in order to support the highlight.
        setOpaque(false);
    }
    
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
     * Stops the thread until the most recently started animation finishes.
     * 
     * This method should never be called on the event thread (a.k.a inside
     * a Listener) as doing so will pause the event thread and prevent any
     * further events from being processed. Not only will this freeze the UI, any
     * animation done on this component will require the event thread to
     * modify the UI to make the animation visible, meaning the animation will
     * freeze until it finishes.
     * 
     * If an action must be performed after an animation and you are inside the
     * event thread, use the <code>then</code> method instead.
     * 
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     * @see AComponent#then
     */
    public AComponent await() {
        synchronized(animations) {
            // Ensure there is an animation to wait for.
            if (!animations.isEmpty())
                Animator.waitFor(animations.lastElement());
        }
        
        return this;
    }
    
    /**
     * Creates the reverse animation of the animation produced by the highlight
     * method.
     * 
     * @param x the X position of the center point of the highlight
     * @param y the X position of the center point of the highlight
     * @param radius the final radius of the highlight
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent dehighlight(int x, int y, int radius) {
        // Determine the velocity based on the size of the component.
        double velocity = getWidth() > getHeight()?
                getWidth() / 100 + 10:
                getHeight() / 100 + 10;
        
        // Check if the highlight is already covering the entire component.
        if (highlightRadius == highlightRadius(highlightX, highlightY)) {
            highlightX = x;
            highlightY = y;
            highlightRadius = highlightRadius(x, y);
        }
        
        // Calculate the start value of the highligh radius.
        int startRadius = highlightRadius;
        
        animations.add(Animator.animateValue(startRadius, radius, -velocity, 
                value -> {
                    highlightRadius = value;
                    repaint();
                },
                // Erase the highlight completely at the end of the animation.
                () -> {
                    highlightRadius = 0;
                    repaint();
                }
        ));
        
        return this;
    }
    
    /**
     * Animates the component expanding into the given bounds.
     * 
     * The component starts the animation at the center of the bounds with no
     * width or height and expands to fit the bounds.
     * 
     * @param x the X position the component animates towards
     * @param y the Y position the component animates towards
     * @param width the Width the component animates towards
     * @param height the Height the component animates towards
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent enter(int x, int y, int width, int height) {
        // Prepare the component to animate in.
        setBounds(x + width / 2, y + height / 2, 0, 0);
        // Animate in.
        transformCentered(width, height, 250);
        
        return this;
    }
    
    /**
     * Animates the component out.
     * 
     * The animation performed by this method is the reverse of the one
     * performed by enter. The animation shrinks towards the center until it
     * has no width or height.
     * 
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent exit() {
        // Animate out.
        transformCentered(0, 0, 250);
        
        return this;
    }
    
    @Override
    public Color getBackground() {
        return background;
    }
    
    /**
     * Retrieves the color of the highlight.
     * 
     * @return the color of the highlight
     */
    public Color getHighlightColor() {
        return highlightColor;
    }
    
    /**
     * Retrieves the image to be displayed by this AComponent.
     * 
     * @return the image to be displayed by this AComponent
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Animates a circle that fills up the component with the highlight color
     * starting with the given radius and centered at the given point.
     * 
     * @param x the X position of the center point of the highlight
     * @param y the X position of the center point of the highlight
     * @param radius the initial radius of the highlight
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent highlight(int x, int y, int radius) {
        // Calculate the end value of the highlight radius.
        int endRadius = highlightRadius(x, y);
        // Determine the velocity based on the size of the component.
        double velocity = getWidth() > getHeight()?
                getWidth() / 100 + 10:
                getHeight() / 100 + 10;
        
        highlightX = x;
        highlightY = y;
        
        animations.add(Animator.animateValue(radius, endRadius, velocity, 
                value -> {
                    highlightRadius = value;
                    repaint();
                },
                // Set the highlight in the center at the end of the animation.
                () -> {
                    highlightX = getWidth() / 2;
                    highlightY = getHeight() / 2;
                    highlightRadius = highlightRadius(highlightX, highlightY);
                    repaint();
                }
//                null
        ));
        
        return this;
    }
    
    /**
     * Calculates the radius of the smallest circle required to fill the entire
     * component if the circle's center is at the specified position. 
     * 
     * This method is used to calculate the highlight's radius.
     * 
     * @param x the X position of the center of the circle
     * @param y the Y position of the center of the circle
     * @return the radius required for the circle to fill the entire component
     */
    public int highlightRadius(int x, int y) {
        return java.util.stream.Stream.of(
                    // Top left corner
                    Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)),
                    // Bottom right corner
                    Math.sqrt(Math.pow(x, 2) + Math.pow(getHeight() - y, 2)),
                    // Top right corner
                    Math.sqrt(Math.pow(getWidth() - x, 2) + Math.pow(y, 2)),
                    // Bottom right corner
                    Math.sqrt(Math.pow(getWidth() - x, 2) 
                            + Math.pow(getHeight() - y, 2))
                ).max(Double::compare).get().intValue() + 1;
    }
    
    /**
     * Returns whether or not any image being drawn by this component should be
     * stretched to fit the size of the component.
     * 
     * @return true if the image will be stretched, false if not
     */
    public boolean isStretchImage() {
        return stretchImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // Draw the background.
        if (background != null) {
            g.setColor(background);
            g.fillRect(0, 0, getWidth(), getHeight());    
        }
        
        // Draw the highlight.
        g.setColor(highlightColor);
        g.fillOval(
                highlightX - highlightRadius, 
                highlightY - highlightRadius, 
                2 * highlightRadius,
                2 * highlightRadius
        );
        
        // Draw the image.
        if (image != null)
            if (stretchImage)
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            else
                g.drawImage(image, 0, 0, this);
        
        // Draw everything else.
        super.paintComponent(g);
    }
    
    @Override
    public void setBackground(Color background) {
        this.background = background;
    }
    
    /**
     * Sets the color of the highlight.
     * 
     * @param highlightColor the new color of the highlight
     */
    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }
    
    /**
     * Sets the image to be displayed by the AComponent.
     * 
     * @param image the image to be displayed by the AComponent.
     */
    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setImage(String imageFilePath) throws IllegalArgumentException {
        try {
            setImage(javax.imageio.ImageIO.read(
                    new java.io.File(imageFilePath)));
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("Image file " + imageFilePath + 
                    " does not exist.");
        }
    }
    
    /**
     * Sets whether or not any image drawn by this component should be stretched
     * to fit the size of the component.
     * 
     * @param stretchImage true if the image should be stretched, false if not
     */
    public void setStretchImage(boolean stretchImage) {
        this.stretchImage = stretchImage;
    }
    
    /**
     * Stops all currently running animations.
     */
    public void stopAnimations() {
        synchronized(animations) {
            while (!animations.isEmpty()) {
                Animator.stopAnimation(animations.get(0));
                animations.remove(animations.get(0));
            }
        }
    }
    
    /**
     * Performs the requested action when the most recently started animation
     * finishes.
     * 
     * @param action a Runnable that will be run when the most recently started
     *               animation ends
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent then(Runnable action) {
        // Set the AnimationEndListener for the most previously added
        // animation.
        synchronized(animations) {
            Animator.setAnimationEndListener(animations.lastElement(), 
                    action::run);
        }
        
        return this;
    }
    
    /**
     * Animates the AComponent's size over the specified length of time.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent transform(int width, int height, int milliseconds) {
        animations.add(
            Animator.animateValue(getWidth(), width, milliseconds, value -> {
                setSize(value, getHeight());
        }, null));
        animations.add(
            Animator.animateValue(getHeight(), height, milliseconds, value -> {
                setSize(getWidth(), value);
        }, null));
        
        return this;
    }
    
    /**
     * Animates the AComponent's size at the specified rate.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param velocity the rate that the size should change
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent transform(int width, int height, double velocity) {
        // Calculate the width and height velocities.
        double angle = width == 0?
                Math.PI:
                Math.atan((double)height / (double)width);
        final double wVelocity = velocity * Math.cos(angle);
        final double hVelocity = velocity * Math.sin(angle);
        
        animations.add(
            Animator.animateValue(getWidth(), width, wVelocity, value -> {
                setSize(value, getHeight());
        }, null));
        animations.add(
            Animator.animateValue(getHeight(), height, hVelocity, value -> {
                setSize(getWidth(), value);
        }, null));
        
        return this;
    }
    
    /**
     * Animates the AComponent's size over the specified length of time without
     * changing the component's center position.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent transformCentered(int width, int height, int milliseconds) {
        translate(getX() - (width - getWidth()) / 2,
                getY() - (height - getHeight()) / 2, milliseconds);
        transform(width, height, milliseconds);
        
        return this;
    }
    
    /**
     * Animates the AComponent's size at the specified rate without
     * changing the component's center position.
     * 
     * @param width the width to animate the AComponent towards
     * @param height the height to animate the AComponent towards
     * @param velocity the rate at which the size should change
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent transformCentered(int width, int height, double velocity) {
        translate(getX() - (width - getWidth()) / 2,
                getY() - (height - getHeight()) / 2, -velocity / 2);
        transform(width, height, velocity);
        
        return this;
    }
    
    /**
     * Animates the AComponent's location over the specified length of time.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param milliseconds the time in milliseconds the animation should last
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent translate(int x, int y, int milliseconds) {
        animations.add(
            Animator.animateValue(getX(), x, milliseconds, value -> {
                setLocation(value, getY());
        }, null));
        animations.add(
            Animator.animateValue(getY(), y, milliseconds, value -> {
                setLocation(getX(), value);
        }, null));
        
        return this;
    }
    
    /**
     * Animates the AComponent's location with the specified velocity.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param velocity the amount the panel's position should increment each 
     *                 frame
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent translate(int x, int y, double velocity) {
        // Calculate the x and y velocities.
        double angle = y == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xVelocity = velocity * Math.cos(angle);
        final double yVelocity = velocity * Math.sin(angle);
        
        animations.add(
            Animator.animateValue(getX(), x, xVelocity, value -> {
                setLocation((int)value, getY());
            }, null));
        animations.add(
            Animator.animateValue(getY(), y, yVelocity, value -> {
                setLocation(getX(), (int)value);
            }, null));
        
        return this;
    }
    
    /**
     * Animates the AComponent's location over the specified length of time with
     * the specified acceleration.
     * 
     * @param x the X position to animate the AComponent towards
     * @param y the Y position to animate the AComponent towards
     * @param acceleration the acceleration the panel should have when moving
     * @param milliseconds the time in milliseconds the animation should last
     * @return this instance of the AComponent to be used with chaining
     *         animation method calls together
     */
    public AComponent translate(int x, int y, double acceleration, int milliseconds) {
        // Calculate the x and y accelerations.
        double angle = x == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xAcceleration = acceleration * Math.cos(angle);
        final double yAcceleration = acceleration * Math.sin(angle);
        
        animations.add(
            Animator.animateValue(getX(), x, xAcceleration, milliseconds, value -> {
                setLocation((int)value, getY());
            }, null));
        animations.add(
            Animator.animateValue(getY(), y, yAcceleration, milliseconds, value -> {
                setLocation(getX(), (int)value);
        }, null));
        
        return this;
    }
}
