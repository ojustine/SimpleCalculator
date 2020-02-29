import javax.swing.*;

public class Calculator {
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame window = new CalcFrame();
                window.setBounds(1100, 100, 600, 400);
                window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
                window.setResizable(false);
                window.setFocusable(true);
                window.setVisible(true);
                window.requestFocus();
            }
        });
    }
}