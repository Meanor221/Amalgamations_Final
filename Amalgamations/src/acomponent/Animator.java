package acomponent;

import java.util.Vector;

/**
 * An Animator is used to change a value slowly over time at a consistent
 * interval.
 * 
 * An Animator is useful particularly to animate graphical elements easily and
 * in a synchronized manner. All animating is done through static methods, all
 * of which utilize the same Animator instance. Since all animated values use
 * the same Animator, only one thread will ever be needed to animate multiple
 * different values, regardless of their relation and when they are animated.
 * 
 * @author Caleb Rush
 */
public class Animator {
    // The number of frames to be advanced in a second.
    private static int fps = 60;
    // The universal Animator instance to be used.
    private static Animator animator;
    
    // Whether or not the Animator should continue running.
    private boolean running = false;
    // The list of keys to identify each value.
    private final Vector<String> keys;
    // The list of values to be animated.
    private final Vector<Double> values;
    // The list of ending values.
    private final Vector<Double> endValues;
    // The list of velocities.
    private final Vector<Double> velocities;
    // The list of accelerations.
    private final Vector<Double> accelerations;
    // The list of FrameListeners.
    private final Vector<FrameListener> frameListeners;
    // The list of AnimationEndListeners.
    private final Vector<AnimationEndListener> animationEndListeners;
    
    // Private constructor to prevent outside classes from running 
    // unsynchronized Animators.
    private Animator() {
        // Initialize vectors.
        keys = new Vector<>();
        values = new Vector<>();
        endValues = new Vector<>();
        velocities = new Vector<>();
        accelerations = new Vector<>();
        frameListeners = new Vector<>();
        animationEndListeners = new Vector<>();
    }
    
    // Begins the animator.
    private void animate() {
        running = true;
        
        // Start a new Thread to run the Animation
        new Thread(() -> {

            // Continue running until the Animator is stopped.
            while (running) {
                // Pause until the next frame.
                try {
                    Thread.sleep(1000/fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                synchronized (keys) {
                    // Iterate through each value being tracked by the Animator.
                    for (int i = 0; i < values.size(); i++) {
                        incrementVelocity(i);
                        incrementValue(i);
                        notifyFrameListener(i);
                        if (valueAtEnd(i)) {
                            // Notify the frame listener one last time.
                            frameListeners.get(i).frameIncremented(
                                    endValues.get(i).intValue());
                            // Alert the end listener.
                            if (animationEndListeners.get(i) != null)
                                animationEndListeners.get(i).animationEnded();
                            removeValue(i);
                            // Decrement the loop counter so that the next element
                            // does not get skipped.
                            i--;
                        }
                    }
                }
                
                // Check if there are any values left to animate.
                if (values.isEmpty()) 
                    running = false;
            }
        }).start();
    }
    
    /**
     * Animates the given value.
     * 
     * @param value the value the animation should start at
     * @param endValue the value the animation should end at
     * @param velocity the initial amount the value should increment each frame
     * @param frameListener the listener to update every time the frame is
     *                      incremented.
     * @param animationEndListener the listener to update once the animation
     *                             is ended
     * @return the identifier String that can be used to directly modify the
     *         animation later.
     */
    public static String animateValue(double value, double endValue, 
            double velocity, FrameListener frameListener, 
            AnimationEndListener animationEndListener) {
        // Calculate the acceleration.
        int frames = (int)((endValue - value) / velocity);
        double acceleration = frames == 0?
                0:
                2 * (((endValue - value) / Math.pow(frames, 2)) 
                        - (velocity / frames));
        return animateValue(value, endValue, velocity, acceleration, 
                frameListener, animationEndListener);
    }
    
    /**
     * Animates the given value in the universal Animator instance.
     * 
     * @param value the value the animation should start at
     * @param endValue the value the animation should end at
     * @param milliseconds the number of milliseconds the animation should last
     *                     for (the actual time may be slightly less than the
     *                     given time, as the precision of the animation is
     *                     limited by the length of a frame).
     * @param frameListener the listener to update every time the frame is
     *                      incremented.
     * @param animationEndListener the listener to update once the animation
     *                             is ended
     * @return the identifier String that can be used to directly modify the
     *         animation later.
     */
    public static String animateValue(double value, double endValue, 
            int milliseconds, FrameListener frameListener, 
            AnimationEndListener animationEndListener) {
        return animateValue(value, endValue, 0, milliseconds, frameListener,
                animationEndListener);
    }
    
    /**
     * Animates the given value in the universal Animator instance.
     * 
     * @param value the value the animation should start at
     * @param endValue the value the animation should end at
     * @param acceleration the amount the velocity should change each frame
     * @param milliseconds the number of milliseconds the animation should last
     *                     for (the actual time may be slightly less than the
     *                     given time, as the precision of the animation is
     *                     limited by the length of a frame).
     * @param frameListener the listener to update every time the frame is
     *                      incremented.
     * @param animationEndListener the listener to update once the animation
     *                             is ended
     * @return the identifier String that can be used to directly modify the
     *         animation later.
     */
    public static String animateValue(double value, double endValue, 
            double acceleration, int milliseconds, 
            FrameListener frameListener, 
            AnimationEndListener animationEndListener) {
        // Determine the number of frames that the animation will last.
        int frames = toFrames(milliseconds);
        // Calculate the velocity.
        double velocity = frames > 0?
                (endValue - value) / frames - (acceleration * frames):
                endValue - value;
        
        return animateValue(value, endValue, velocity, acceleration, 
                frameListener, animationEndListener);
    }
    
    /**
     * Animates the given value.
     * 
     * @param value the value the animation should start at
     * @param endValue the value the animation should end at
     * @param velocity the initial amount the value should increment each frame
     * @param acceleration the amount the velocity should change each frame
     * @param frameListener the listener to update every time the frame is
     *                      incremented.
     * @param animationEndListener the listener to update once the animation
     *                             is ended
     * @return the identifier String that can be used to directly modify the
     *         animation later.
     */
    public static String animateValue(double value, double endValue, 
            double velocity, double acceleration, FrameListener frameListener,
            AnimationEndListener animationEndListener) {
        // Check if the universal animator has been created yet.
        if (animator == null)
            animator = new Animator();
        
        // Add the value to the animator.
        animator.keys.add(String.format("%f%f%f%f%s%d", value, endValue, velocity, 
                acceleration, frameListener.toString(), animator.values.size()));
        animator.values.add(value);
        animator.endValues.add(endValue);
        animator.velocities.add(velocity);
        animator.accelerations.add(acceleration);
        animator.frameListeners.add(frameListener);
        animator.animationEndListeners.add(animationEndListener);
        
        // Start the animator if it is stopped.
        if (!animator.running)
            animator.animate();
        
        // Return an identifier String.
        return animator.keys.lastElement();
    }
    
    // Increments the value at the given index.
    private void incrementValue(int i) {
        values.set(i, values.get(i) + velocities.get(i));
    }
    
    // Increments the velocity at the given index.
    private void incrementVelocity(int i) {
        velocities.set(i, velocities.get(i) + accelerations.get(i));
    }
    
    // Notifies the frame listener at the given index of a change in value.
    private void notifyFrameListener(int i) {
        frameListeners.get(i).frameIncremented(values.get(i).intValue());
    }

    // Remove the value at the given index.
    private void removeValue(int i) {
        synchronized (keys) {   
            // Remove the value's properties frome each vector.
            keys.remove(i);
            values.remove(i);
            endValues.remove(i);
            velocities.remove(i);
            accelerations.remove(i);
            frameListeners.remove(i);
            animationEndListeners.remove(i);
        }
    }
    
    /**
     * Stops the animation with the specified key.
     * 
     * @param key the identifier String of the value animation to stop
     * @return true if the animation was successfully stopped, false otherwise
     */
    public static boolean stopAnimation(String key) {
        // Retrieve the index for the key.
        int i = animator.keys.indexOf(key);
        
        // Check if the key existed.
        if (i != -1) {
            // Remove the value.
            animator.removeValue(i);
            return true;
        }
        
        return false;
    }
    
    /**
     * Calculates the number of frames that can be completed in the specified
     * time.
     * 
     * @param milliseconds the amount of time in milliseconds
     * @return the number of frames that can be completed in the specified 
     *         time
     */
    public static int toFrames(int milliseconds) {
        return (int)(milliseconds * ((double)fps / 1000.0));
    }
    
    // Checks if the value at the given index has reached its end value.
    private boolean valueAtEnd(int i) {
        if (endValues.get(i) >= values.get(i) - velocities.get(i))
            return values.get(i) >= endValues.get(i);
        else
            return values.get(i) <= endValues.get(i);
//        return endValues.get(i).intValue() == values.get(i).intValue();
    }
    
    /**
     * A FrameListener should be used by anything utilizing an Animator to alert
     * itself any time the Animator increments a frame.
     */
    public static interface FrameListener {
        /**
         * Called when the Animator increments a frame. This should be used
         * to update the value being animated and calling any other necessary
         * methods (such as the repaint method if a GUI component is being
         * updated and needs to redraw).
         * 
         * @param value the incremented value.
         */
        void frameIncremented(int value);
    }
    
    /**
     * An AnimationEndListener should be used by anything utilizing an Animator
     * to alert itself when the animation has ended.
     */
    public static interface AnimationEndListener {
        /**
         * Called when the animation has ended. This should be used when an 
         * action needs to be performed only after an animation ends.
         * 
         * If the animation is ended prematurely 
         * (through the Animator::stopAnimation method), this method will not
         * be called.
         */
        void animationEnded();
    }
}
