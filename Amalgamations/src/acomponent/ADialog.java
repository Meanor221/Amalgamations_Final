package acomponent;

import java.awt.Color;
import java.util.Vector;

/**
 * An ADialog is a type of dialog that can perform many of the same animations
 * as an AComponent such as animating a change in location and size.
 * 
 * ADialogs also have some stylistic differences from regular dialogs (which
 * can be undone if desired). One change is the removal of a title bar, which
 * is used to simplify the design of the dialog. This can be undone in a 
 * subclass by calling <code>setUndecorated(false)</code>.
 * 
 * Aside from animations and stylistic differences, ADialogs contain some
 * methods to help add text and buttons to dialogs easily without worrying 
 * about the layout.
 * 
 * The ADialog class also contains some methods that can create some common
 * dialogs.
 * 
 * @author Caleb Rush
 */
public class ADialog extends javax.swing.JDialog {
    // The list of animation keys that are being run.
    private final Vector<String> animations;
    // The position the dialog expanded from.
    private int x;
    private int y;
    // The JPanel holding the buttons.
    private javax.swing.JPanel buttonPanel;
    
    /**
     * Creates a new ADialog instance with the given parent and modal value.
     * 
     * @param parent the parent of this dialog
     * @param modal true if the dialog is modal, false otherwise
     */
    public ADialog(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        // Make the dialog have no title bar.
        setUndecorated(true);
        // Set a border around the root pane.
        getRootPane().setBorder(javax.swing.BorderFactory.
            createCompoundBorder(
                javax.swing.BorderFactory.
                    createMatteBorder(1, 1, 2, 2, Color.LIGHT_GRAY),
                javax.swing.BorderFactory.
                    createEmptyBorder(10, 20, 10, 20)
            )
        );
        // Make the background of the dialog white.
        getRootPane().setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        // Set the layout as a BorderLayout.
        setLayout(new java.awt.BorderLayout(20, 20));
        animations = new Vector<>();
    }
    
    /**
     * Adds a button to the dialog.
     * 
     * @param text the text to be displayed on the button
     * @param actionListener the actionListener to be called when the button
     *                       is pressed
     */
    public void addButton(String text, 
            java.awt.event.ActionListener actionListener) {
        addButton(text, actionListener, Color.BLACK);
    }
    
    /**
     * Adds a button to the dialog.
     * 
     * @param text the text to be displayed on the button
     * @param actionListener the actionListener to be called when the button
     *                       is pressed
     * @param textColor the color of the text on the button (this will also be
     *                  the AButton's highlight color that will wash over it
     *                  when the user hovers over it)
     */
    public void addButton(String text, 
            java.awt.event.ActionListener actionListener, Color textColor) {
        addButton(text, actionListener, textColor, 
                getContentPane().getBackground());
    }
    
    /**
     * Adds a button to the dialog.
     * 
     * @param text the text to be displayed on the button
     * @param actionListener the actionListener to be called when the button
     *                       is pressed
     * @param textColor the color of the text on the button (this will also be
     *                  the AButton's highlight color that will wash over it
     *                  when the user hovers over it)
     * @param backgroundColor the background color of the button
     */
    public void addButton(String text, 
            java.awt.event.ActionListener actionListener, Color textColor,
            Color backgroundColor) {
        // Create the buttonPanel if it hasn't been initialized yet.
        if (buttonPanel == null) {
            buttonPanel = new javax.swing.JPanel(
                    new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            add(buttonPanel, java.awt.BorderLayout.SOUTH);
        }
        
        // Calculate the dimensions of the text.
        java.awt.Font font = new java.awt.Font("Berlin Sans FB Demi", 
                java.awt.Font.BOLD, 18);
        AButton button = new AButton(getFontMetrics(font).stringWidth(text) + 20, 
                getFontMetrics(font).getHeight() + 10);
        buttonPanel.add(button);
        
        // Set the text of the button.
        button.setText(text);
        // Add the action listener to the button.
        button.setActionListener(actionListener);
        // Remove the border from the button if its background matches the
        // dialog's background.
        if (getContentPane().getBackground().equals(backgroundColor))
            button.setBorder(null);
        // Set the color for the button.
        button.setForeground(textColor);
        button.setBackground(backgroundColor);
        button.setHighlightColor(textColor); 
        // Set the font of the button.
        button.setFont(font);
    }
    
    /**
     * Adds text of the specified color to the dialog.
     * 
     * Multiple calls of this function will not accumulate text, rather, each
     * call will replace the text added by the previous call.
     * 
     * @param text the text to add to the dialog
     * @param textColor the color to make the text
     */
    public void addText(String text, Color textColor) {
        // Create a string with formatted HTML for line breaks, since JLabels
        // are cool and totally support line breaks.
        String labelText = "<html>" + text.replaceAll("\n", "<br>") + "</html>";
        
        // Create the JLabel for the text.
        javax.swing.JLabel label = new javax.swing.JLabel(labelText);
        // Set the font for the text.
        label.setFont(new java.awt.Font("Berlin Sans FB Demi", 
                java.awt.Font.BOLD, 18));
        // Set the color of the text.
        label.setForeground(textColor);
        
        // Add the label to the dialog.
        add(label);
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
     * @return this instance of the ADialog to be used with chaining
     *         animation method calls together
     * @see ADialog#then
     */
    public ADialog await() {
        synchronized(animations) {
            // Ensure there is an animation to wait for.
            if (!animations.isEmpty())
                Animator.waitFor(animations.lastElement());
        }
        
        return this;
    }
    
    /**
     * Creates an AConfirmDialog that can be used to validate the user's choice.
     * 
     * To display the dialog and retrieve whether or not the user chose to
     * confirm, call the confirmed method on the returned dialog.
     * 
     * @param parent the parent of the dialog
     * @param message the message to display to the user
     * @return the created dialog
     */
    public static AConfirmDialog createConfirmDialog(javax.swing.JFrame parent,
            String message) {
        return new AConfirmDialog(parent, message, "Yes", "No");
    }
    
    /**
     * Creates an AConfirmDialog that can be used to validate the user's choice.
     * 
     * To display the dialog and retrieve whether or not the user chose to
     * confirm, call the confirmed method on the returned dialog.
     * 
     * @param parent the parent of the dialog
     * @param message the message to display to the user
     * @param confirmText the text for the confirm button
     * @param cancelText the text for the cancel button
     * @return the created dialog
     */
    public static AConfirmDialog createConfirmDialog(javax.swing.JFrame parent,
            String message, String confirmText, String cancelText) {
        return new AConfirmDialog(parent, message, confirmText, cancelText);
    }
    
    /**
     * Creates and displays a dialog containing the specified message and a
     * single button that closes the message (the "OK" button).
     * 
     * @param parent the parent of the dialog
     * @param message the message to be displayed in the dialog
     * @return the created dialog
     */
    public static AMessageDialog createMessageDialog(javax.swing.JFrame parent,
            String message) {
        return createMessageDialog(parent, message, "OK");
    }
    
    /**
     * Creates and displays a dialog containing the specified message and a
     * single button that closes the message (the "OK" button).
     * 
     * @param parent the parent of the dialog
     * @param message the message to be displayed in the dialog
     * @param buttonText the text of the "OK" button
     * @return the created dialog
     */
    public static AMessageDialog createMessageDialog(javax.swing.JFrame parent,
            String message, String buttonText) {
        return createMessageDialog(parent, message, buttonText, Color.GREEN);
    }
    
    /**
     * Creates and displays a dialog containing the specified message and a
     * single button that closes the message (the "OK" button).
     * 
     * @param parent the parent of the dialog
     * @param message the message to be displayed in the dialog
     * @param buttonText the text of the "OK" button
     * @param buttonColor the color of the text/highlight color of the "OK" 
     *                    button
     * @return the created dialog
     */
    public static AMessageDialog createMessageDialog(javax.swing.JFrame parent,
            String message, String buttonText, Color buttonColor) {
        return createMessageDialog(parent, message, Color.BLACK, buttonText, 
                buttonColor);
    }
    
    /**
     * Creates a dialog containing the specified message and a
     * single button that closes the message (the "OK" button).
     * 
     * @param parent the parent of the dialog
     * @param message the message to be displayed in the dialog
     * @param textColor the text color of the message being displayed
     * @param buttonText the text of the "OK" button
     * @param buttonColor the color of the text/highlight color of the "OK" 
     *                    button
     * @return the created dialog
     */
    public static AMessageDialog createMessageDialog(javax.swing.JFrame parent, 
            String message, Color textColor, String buttonText, 
            Color buttonColor) {
        return new AMessageDialog(parent, message, textColor, buttonText, 
                buttonColor);
    }
    
    /**
     * Animates the dialog expanding into the given bounds.
     * 
     * The dialog starts the animation at the center of the bounds with no
     * width or height and expands to fit the bounds.
     * 
     * @param x the X position the dialog animates towards
     * @param y the Y position the dialog animates towards
     * @param width the Width the dialog animates towards
     * @param height the Height the dialog animates towards
     * @return this instance of the ADialog to be used with chaining
     *         animation method calls together
     */
    public ADialog enter(int x, int y, int width, int height) {
        // Prepare the component to animate in.
        setBounds(x + width / 2, y + height / 2, 0, 0);
        // Animate in.
        transformCentered(width, height, 250);
        
        return this;
    }
    
    /**
     * Animates the dialog out.
     * 
     * The animation performed by this method is the reverse of the one
     * performed by enter. The animation shrinks towards the center until it
     * has no width or height.
     * 
     * @return this instance of the ADialog to be used with chaining
     *         animation method calls together
     */
    public ADialog exit() {
        // Animate out.
        transformCentered(0, 0, 250);
        
        return this;
    }
    
    /**
     * Hides the dialog, animating it out and then making it invisible. If the
     * dialog is modal, this will give control back to the caller.
     * 
     * The exit animation performed will depend on which showDialog method was
     * called to display the dialog. If the method with no arguments was called,
     * the dialog will slide off the bottom of the screen. If the method
     * specifying an X and Y position was called, the dialog will contract
     * back towards the specified point.
     */
    public void hideDialog() {
        // If x or y are nonzero, then the dialog expanded from a point.
        if (x != 0 || y != 0) {
            translate(x, y, 150);
            transform(0, 0, 150).then(() -> setVisible(false));
        }
        
        else {
            int distance = (int)java.awt.Toolkit.getDefaultToolkit()
                    .getScreenSize().getHeight() - getY();
            translate(getX(), getY() + distance, 250).then(
                    () -> setVisible(false)
            );
        }
    }
    
    /**
     * Adds black text to the dialog.
     * 
     * Multiple calls of this function will not accumulate text, rather, each
     * call will replace the text added by the previous call.
     * 
     * @param text the text to add to the dialog
     */
    public void addText(String text) {
        addText(text, Color.BLACK);
    }
    
    /**
     * Displays the dialog, setting it visible and animating it in. If the
     * dialog is modal, this will seize control from the caller.
     */
    public void showDialog() {
        // Calculate the distance from the current Y position to the bottom of
        // the screen.
        int distance = (int)java.awt.Toolkit.getDefaultToolkit()
                .getScreenSize().getHeight() - getY();
        setLocation(getX(), getY() + distance);
        translate(getX(), getY() - distance, 250);
        setVisible(true);
    }
    
    /**
     * Displays the dialog, setting it visible and animating it expanding from
     * the specified point.
     * 
     * This effect can be used to clearly show where the dialog originated from,
     * giving the user an idea of why the dialog appeared.
     * 
     * If the dialog is modal, this will seize control from the caller.
     * 
     * @param x the absolute x-position on the screen to expand the dialog from
     * @param y the absolute y-position on the screen to expand the dialog from
     */
    public void showDialog(int x, int y) {
        this.x = x;
        this.y = y;
        
        // Save the dialog's current bounds.
        int X = getX(), Y = getY(), W = getWidth(), H = getHeight();
        
        // Set the dialog's starting position at the specified point.
        setBounds(x, y, 0, 0);
        
        // Animate to the dialog's appropriate bounds.
        translate(X, Y, 150);
        transform(W, H, 150);
        
        // Set the dialog visible.
        setVisible(true);
    }
    
    /**
     * Stops all currently running animations.
     */
    public void stopAnimations() {
        synchronized(animations) {
            while (!animations.isEmpty()) {
                Animator.stopAnimation(animations.firstElement());
                animations.removeElementAt(0);
            }
        }
    }
    
    /**
     * Performs the requested action when the most recently started animation
     * finishes.
     * 
     * @param action a Runnable that will be run when the most recently started
     *               animation ends
     * @return this instance of the ADialog to be used with chaining
     *         animation method calls together
     */
    public ADialog then(Runnable action) {
        // Set the AnimationEndListener for the most previously added
        // animation.
        synchronized(animations) {
            Animator.setAnimationEndListener(animations.lastElement(), 
                    action::run);
        }
        
        return this;
    }
    
    /**
     * Animates a change in the dialog's size over time.
     * @param width the width to animate the dialog towards
     * @param height the height to animate the dialog towards
     * @param milliseconds the number of milliseconds the animation should last
     * @return this ADialog instance to be used to chain animation method calls
     */
    public ADialog transform(int width, int height, int milliseconds) {
        // Use the animator to animate the dialog's size.
        animations.add(
                Animator.animateValue(
                        getWidth(), width, milliseconds, 
                        newWidth -> {
                            setSize(newWidth, getHeight());
                            repaint();
                        }, 
                        null
                )
        );
        animations.add(
                Animator.animateValue(
                        getHeight(), height, milliseconds, 
                        newHeight -> {
                            setSize(getWidth(), newHeight);
                            repaint();
                        }, 
                        null
                )
        );
        
        return this;
    }
    
    /**
     * Animates the ADialog's size over the specified length of time without
     * changing the component's center position.
     * 
     * @param width the width to animate the ADialog towards
     * @param height the height to animate the ADialog towards
     * @param milliseconds the time in milliseconds the animation should last
     * @return this instance of the ADialog to be used with chaining
     *         animation method calls together
     */
    public ADialog transformCentered(int width, int height, int milliseconds) {
        translate(getX() - (width - getWidth()) / 2,
                getY() - (height - getHeight()) / 2, milliseconds);
        transform(width, height, milliseconds);
        
        return this;
    }
    
    /**
     * Animates a change in the dialog's position over the specified amount of
     * time.
     * 
     * @param x the x-position to animate the dialog towards.
     * @param y the y-position to animate the dialog towards
     * @param milliseconds the number of milliseconds the animation should last
     * @return this ADialog instance to be used to chain animation method calls
     */
    public ADialog translate(int x, int y, int milliseconds) {
        // Use the animator to animate the dialog's location.
        animations.add(
                Animator.animateValue(
                        getX(), x, milliseconds, 
                        newX -> {
                            setLocation(newX, getY());
                            repaint();
                        }, 
                        null
                )
        );
        animations.add(
                Animator.animateValue(
                        getY(), y, milliseconds, 
                        newY -> {
                            setLocation(getX(), newY);
                            repaint();
                        }, 
                        null
                )
        );
        
        return this;
    }
    
    /**
     * Animates a change in the dialog's position with the specified
     * acceleration.
     * 
     * @param x the x-position to animate the dialog towards.
     * @param y the y-position to animate the dialog towards
     * @param velocity the velocity of the dialog towards its new
     *                 position. This will be used to determine the length
     *                 of the animation.
     * @return this ADialog instance to be used to chain animation method calls
     */
    public ADialog translate(int x, int y, double velocity) {
        // Calculate the x and y velocities.
        double angle = x == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xVelocity = velocity * Math.cos(angle);
        final double yVelocity = velocity * Math.sin(angle);
        
        // Use the animator to animate the dialog's location.
        animations.add(
                Animator.animateValue(
                        getX(), x, xVelocity, 
                        newX -> {
                            setLocation(newX, getY());
                            repaint();
                        }, 
                        null
                )
        );
        animations.add(
                Animator.animateValue(
                        getY(), y, yVelocity, 
                        newY -> {
                            setLocation(getX(), newY);
                            repaint();
                        }, 
                        null
                )
        );
        
        return this;
    }
    
    /**
     * Animates a change in the dialog's position with the specified
     * acceleration.
     * 
     * @param x the x-position to animate the dialog towards.
     * @param y the y-position to animate the dialog towards
     * @param acceleration the acceleration of the dialog towards its new
     *                 position.
     * @param milliseconds the number of milliseconds the animation should
     *                     last.
     * @return this ADialog instance to be used to chain animation method calls
     */
    public ADialog translate(int x, int y, double acceleration, 
            int milliseconds) {
        // Calculate the x and y velocities.
        double angle = x == 0?
                Math.PI:
                Math.atan((double)y / (double)x);
        final double xAcceleration = acceleration * Math.cos(angle);
        final double yAcceleration = acceleration * Math.sin(angle);
        
        // Use the animator to animate the dialog's location.
        animations.add(
                Animator.animateValue(
                        getX(), x, xAcceleration, milliseconds,
                        newX -> {
                            setLocation(newX, getY());
                            repaint();
                        }, 
                        null
                )
        );
        animations.add(
                Animator.animateValue(
                        getY(), y, yAcceleration, milliseconds,
                        newY -> {
                            setLocation(getX(), newY);
                            repaint();
                        }, 
                        null
                )
        );
        
        return this;
    }
    
    /**
     * An AConfirmDialog displays a message and two buttons: a Confirm button
     * and a Cancel button. By calling the confirm method, you can display the
     * dialog and determine whether or not the user clicked the confirm button.
     */
    public static class AConfirmDialog extends ADialog {
        private boolean confirm;
        
        public AConfirmDialog(javax.swing.JFrame parent, String message,
                String confirmText, String cancelText) {
            super(parent, true);
            addText(message);
            addButton(confirmText, e -> {
                    confirm = true;
                    hideDialog();
                },
                new java.awt.Color(76, 175, 80)
            );
            addButton(cancelText, e -> hideDialog(), 
                    new java.awt.Color(244,67,54));
            pack();
            setLocationRelativeTo(null);
        }
        
        /**
         * Returns whether or not the user confirmed their choice.
         * 
         * @return whether or not the user confirmed their choice
         */
        public boolean confirmed() {
            showDialog();
            return confirm;
        }
        
        /**
         * Returns whether or not the user confirmed their choice.
         * 
         * @param x the x-position to expand the dialog from
         * @param y the y-position to expand the dialog from
         * @return whether or not the user confirmed their choice
         */
        public boolean confirmed(int x, int y) {
            showDialog(x, y);
            return confirm;
        }
    }
    
    /**
     * An AMessageDialog is a dialog that displays a simple message with a
     * single button that closes the dialog.
     */
    public static class AMessageDialog extends ADialog {
        public AMessageDialog(javax.swing.JFrame parent, String message, 
                Color textColor, String buttonText, Color buttonColor) {
            super(parent, true);
            addText(message, textColor);
            addButton(buttonText, e -> hideDialog(), buttonColor);
            pack();
            setLocationRelativeTo(parent);
            // Add a key listener to allow the enter button to close the
            // dialog.
            addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    // Check if the enter key was pressed.
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
                        // Close the dialog.
                        hideDialog();
                }
            });
        }
    }
    
    public static void main(String[] args) {
        if (createConfirmDialog(null, "Do you like cheese?").confirmed())
            createMessageDialog(null, "HA! That's <i>cheesy</i>!", "Ugh")
                    .showDialog();
        else
            createMessageDialog(null, "Well, <b>good</b>! 'Cause it's <i>nacho cheese</i> anyways!", "Ugh")
                    .showDialog();
    }
}
