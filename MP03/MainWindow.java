import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainWindow extends FrameWindow implements ActionListener {
    private static final String MAIN_TITLE = "Main Window";
    private static final String TEXTFIELD_WINDOW_TITLE = "TextField Window";
    private static final String LABEL_WINDOW_TITLE = "Label Window";
    private static final String TEXTFIELD_OBSERVER_REMOVE_BUTTON_TITLE = "Remove TextField Window Observer";
    private static final String TEXTFIELD_OBSERVER_ADD_BUTTON_TITLE = "Add TextField Window Observer";
    private static final String LABELFIELD_OBSERVER_REMOVE_BUTTON_TITLE = "Remove Label Window Observer";
    private static final String LABELFIELD_OBSERVER_ADD_BUTTON_TITLE = "Add TextField Window Observer";

    private static final String STOP_THREAD_BUTTON_TITLE = "Stop Generating Prime Number";
    private static final int X = 250;
    private static final int Y = 100;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    private static final int GAP = 50;
    private static final int NUM_BUTTONS = 3;

    private boolean isTextFieldRemoveTurn = true;
    private boolean isLabelFieldRemoveTurn = true;
    private JButton stopButton;
    private JButton removeTextFieldObserverButton;
    private JButton removeLabelFieldObserverButton;
    //private JButton addTextFieldObserverButton;
    //private JButton addLabelFieldObserverButton;

    private PrimeObservableThread primeThread;
    private TextFieldWindow textFieldWindow;
    private LabelWindow labelWindow;

    public MainWindow(String title) {
        super(title, X, Y, WIDTH, HEIGHT);
        textFieldWindow = new TextFieldWindow(TEXTFIELD_WINDOW_TITLE, X, Y + HEIGHT + GAP, WIDTH, HEIGHT);
        labelWindow = new LabelWindow(LABEL_WINDOW_TITLE, X, Y + (HEIGHT + GAP) * 2, WIDTH, HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                primeThread.stopRunning();
                textFieldWindow.closeWindow();
                labelWindow.closeWindow();
                System.exit(0);
            }
        });

        primeThread = new PrimeObservableThread(); // ?????? ??????
        primeThread.registerObserver(textFieldWindow); // ??????! -> ?????? ????????? ?????? .. ?????? ????????? ??? ?????? ?????? ????????? ????????? ????????? .. ?????? ?????? ..?
        primeThread.registerObserver(labelWindow);
        
        primeThread.run();  // ?????? ?????? ??????. ??? ????????? ????????? ????????? stopButton??? ????????? ????????? ?????? ?????????
    }

    public JPanel createPanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(width, height));
        removeTextFieldObserverButton = createButton(TEXTFIELD_OBSERVER_REMOVE_BUTTON_TITLE, this, width, height);
        panel.add(removeTextFieldObserverButton);
        removeLabelFieldObserverButton = createButton(LABELFIELD_OBSERVER_REMOVE_BUTTON_TITLE, this, width, height);
        panel.add(removeLabelFieldObserverButton);
        stopButton = createButton(STOP_THREAD_BUTTON_TITLE, this, width, height);
        panel.add(stopButton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == removeTextFieldObserverButton) {
            if (isTextFieldRemoveTurn) {
                primeThread.removeObserver(textFieldWindow);
                removeTextFieldObserverButton.setText(TEXTFIELD_OBSERVER_ADD_BUTTON_TITLE);
                isTextFieldRemoveTurn = false; //?????? setText??? ????????? ?????? ?????? ????????? ?????? ???????????? ????????? ???????????? ????????? ????????? ?????? ???????????? ??????????????? ..?
            }
            else {
                primeThread.registerObserver(textFieldWindow);
                removeTextFieldObserverButton.setText(TEXTFIELD_OBSERVER_REMOVE_BUTTON_TITLE);
                isTextFieldRemoveTurn = true;
            }

        }

        else if (e.getSource() == removeLabelFieldObserverButton) {
            if (isLabelFieldRemoveTurn) {
                primeThread.removeObserver(labelWindow);
                removeLabelFieldObserverButton.setText(LABELFIELD_OBSERVER_ADD_BUTTON_TITLE);
                isLabelFieldRemoveTurn = false;

            }
            else {
                primeThread.registerObserver(labelWindow);
                removeLabelFieldObserverButton.setText(LABELFIELD_OBSERVER_REMOVE_BUTTON_TITLE);
                isLabelFieldRemoveTurn = true;
            }

        }
        else if (e.getSource() == stopButton) {
            primeThread.stopRunning();
        }

    }

    private JButton createButton(String text, ActionListener listener, int width, int height) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        Dimension buttonDimension = new Dimension(width, height / NUM_BUTTONS);
        button.setMaximumSize(buttonDimension);
        button.setMinimumSize(buttonDimension);
        button.setPreferredSize(buttonDimension);
        return button;
    }

    public static void main(String[] args) {

        MainWindow mainWindow = new MainWindow(MainWindow.MAIN_TITLE);

    }
}
