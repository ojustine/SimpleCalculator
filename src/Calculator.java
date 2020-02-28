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
                window.setBounds(1100, 100, 400, 500);
                window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
                window.setResizable(false);
                window.setVisible(true);
            }
        });
    }
}