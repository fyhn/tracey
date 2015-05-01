import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author afy
 *
 */
public class Tracey {

	// This is added to avoid warning
	private static final long serialVersionUID = 1L;

	private static void createAndShowGUI() {
    	//Create and set up the window.
        JFrame frame = new TraceyFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
	}
}
