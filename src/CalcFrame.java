import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class CalcFrame extends JFrame
{

	private double			preResult;
	private double			memory;
	private boolean			isReadyForCalc;
	private boolean			isMemoryFull;
	private boolean			isDotPressed;
	private String			currentOperation = "";
	private final String[]	allButtons = {"%", "\u221A", "x\u00B2", "\u232B", "MSR", "MC", "C", "\u00F7",
										"7", "8", "9", "\u00D7", "4", "5", "6", "-",
										"1", "2", "3", "+", "\u00B1", "0", ".", "="};

	private DecimalFormat			numberFormat = new DecimalFormat("#.##########");
	private DecimalFormatSymbols	decimalFormatSymbols = new DecimalFormatSymbols();
	private Font					regular = new Font("Helvetica", Font.PLAIN, 22);
	private Font					mainBar = new Font("Helvetica", Font.PLAIN, 45);
	private JLabel					preDisplay = new JLabel("\u0020");
	private JTextField				mainDisplay = new JTextField("");
	private JPanel					pane = new JPanel();

	CalcFrame()
	{
		super("Калькуляша");
		init();
	}

	private void init()
	{
		GridBagLayout		layout = new GridBagLayout();
		GridBagConstraints	placing = new GridBagConstraints();

		decimalFormatSymbols.setDecimalSeparator('.');
		numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		preDisplay.setFont(regular);
		mainDisplay.setHorizontalAlignment(JTextField.RIGHT);
		mainDisplay.setFont(mainBar);
		pane.setLayout(layout);
		layout.setConstraints(mainDisplay, placing);

		placing.insets = new Insets(3, 3, 3, 3);
		placing.gridx = 0;
		placing.gridy = 0;
		placing.gridheight = 1;
		placing.weightx = 0.25;
		placing.weighty = 0.01;
		placing.fill = GridBagConstraints.BOTH;
		placing.gridwidth = GridBagConstraints.REMAINDER;
		preDisplay.setHorizontalAlignment(JLabel.RIGHT);
		pane.add(preDisplay, placing);

		placing.gridy = 1;
		mainDisplay.setEditable(false);
		mainDisplay.setBorder(BorderFactory.createEtchedBorder());
		pane.add(mainDisplay, placing);

		placing.gridwidth = GridBagConstraints.BOTH;
		for (int y = 0; y < 6; y++)
		{
			placing.gridy = y + 2;
			for (int x = 0; x < 4; x++)
			{
				placing.gridx = x;
				pane.add(makeButton(y * 4 + x), placing);
			}
		}
		setContentPane(pane);
	}

	private ActionListener buttonActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String source = ((JButton) e.getSource()).getText();
			switch (source)
			{
				case "MC" : isMemoryFull = false;
					break;
				case "MSR" : memoryAdd();
					break;
				case "\u232B" : backspace();
					break;
				case "C" : clear();
					break;
				case "." : dot();
					break;
				case "\u00B1" : negative();
					break;
				case "=" : result();
					break;
				case "\u221A" :
				case "x\u00B2" : instantOperation(source);
					break;
				case "%" :
				case "\u00F7" :
				case "\u00D7" :
				case "-" :
				case "+" : simpleOperation(source);
					break;
				case "0" :
				case "1" :
				case "2" :
				case "3" :
				case "4" :
				case "5" :
				case "6" :
				case "7" :
				case "8" :
				case "9" : digit(Integer.parseInt(source));
					break;
				default: warning("Как это вышло?");
			}
		}
	};

	private KeyListener keyListener = new KeyAdapter()
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
			int key = e.getKeyCode();
			if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9)
				digit(key - '0');
		}
	};

	private JButton makeButton(int code)
	{
		JButton button = new JButton(allButtons[code]);
		button.setFont(regular);
		button.addActionListener(buttonActionListener);
		return button;
	}

	private void digit(int digit)
	{
		if (digit == 0)
		{
			if (mainDisplay.getText().isEmpty() || "0".equals(mainDisplay.getText()))
				mainDisplay.setText("0");
			else
				mainDisplay.setText(mainDisplay.getText() + "0");
		}
		else
		{
			if ("0".equals(mainDisplay.getText()))
			{
				mainDisplay.setText(Double.toString((double)digit / 10));
				isDotPressed = true;
			}
			else
				mainDisplay.setText(mainDisplay.getText() + digit);
		}
	}

	private void clear()
	{
		mainDisplay.setText("");
		preDisplay.setText("\u0020");
		isReadyForCalc = false;
		isDotPressed = false;
	}

	private void simpleOperation(String operation)
	{
		if (!mainDisplay.getText().isEmpty())
		{
			try
			{
				isDotPressed = false;
				currentOperation = operation;
				if (!isReadyForCalc)
				{
					preResult = Double.parseDouble(mainDisplay.getText());
					isReadyForCalc = true;
				}
				if ("%".equals(operation))
					preDisplay.setText(operation + numberFormat.format(preResult));
				else
					preDisplay.setText(numberFormat.format(preResult) + " " + operation);
				mainDisplay.setText("");
			}
			catch (NumberFormatException nfe)
			{
				warning("Некорректное число!");
			}
		}
	}

	private void instantOperation(String operation) throws NumberFormatException
	{
		if (!mainDisplay.getText().isEmpty())
		{
			try
			{
				double temp = Double.parseDouble(mainDisplay.getText());
				switch (operation)
				{
					case "\u221A":
						mainDisplay.setText(numberFormat.format(Math.sqrt(temp)));
						break;
					case "x\u00B2":
						mainDisplay.setText(numberFormat.format(Math.pow(temp, 2)));
						break;
				}
				if (isDotPressed())
					isDotPressed = true;
			}
			catch (NumberFormatException nfe)
			{
				warning("Некорректное число!");
			}
		}
	}

	private boolean isDotPressed()
	{
		char[] displayArray = mainDisplay.getText().toCharArray();
		for (char c : displayArray)
		{
			if (c == '.')
				return true;
		}
		return false;
	}

	private void result() throws ArithmeticException
	{
		if (isReadyForCalc && !mainDisplay.getText().isEmpty())
		{
			try
			{
				double multiplier = Double.parseDouble(mainDisplay.getText());
				clear();
				switch (currentOperation) {
					case "%":
						mainDisplay.setText(numberFormat.format(preResult / 100 * multiplier));
						break;
					case "\u00F7":
						mainDisplay.setText(numberFormat.format(preResult / multiplier));
						break;
					case "\u00D7":
						mainDisplay.setText(numberFormat.format(preResult * multiplier));
						break;
					case "-":
						mainDisplay.setText(numberFormat.format(preResult - multiplier));
						break;
					case "+":
						mainDisplay.setText(numberFormat.format(preResult + multiplier));
						break;
				}
				if (isDotPressed()) isDotPressed = true;
			}
			catch (ArithmeticException ae)
			{
				warning("Недопустимая операция!");
			}

		}
	}

	private void dot()
	{
		if (!isDotPressed
		&& !mainDisplay.getText().isEmpty()
		&& !".".equals(mainDisplay.getText().substring(mainDisplay.getText().length() - 1)))
		{
			mainDisplay.setText(mainDisplay.getText() + ".");
			isDotPressed = true;
		}
	}

	private void negative()
	{
		if (!mainDisplay.getText().isEmpty())
		{
			try
			{
				double temp = -Double.parseDouble(mainDisplay.getText());
				mainDisplay.setText(numberFormat.format(temp));
				if (mainDisplay.getText().indexOf('.') == -1)
					isDotPressed = false;
			}
			catch (NumberFormatException nfe)
			{
				warning("Некорректное число!");
			}
		}
	}

	private void memoryAdd()
	{
		if (isMemoryFull)
		{
			if (!((memory == Math.floor(memory)) && !Double.isInfinite(memory)))
				isDotPressed = true;
			mainDisplay.setText(String.valueOf(numberFormat.format(memory)));
		}
		else if (!mainDisplay.getText().isEmpty())
		{
			try
			{
				memory = Double.parseDouble(mainDisplay.getText());
				isMemoryFull = true;
			}
			catch (NumberFormatException nfe)
			{
				warning("Некорректное число!");
			}
		}
	}
	private void backspace()
	{
		if (!mainDisplay.getText().isEmpty())
		{
			String temp = mainDisplay.getText();
			if (temp.charAt(temp.length() - 1) == '.')
				isDotPressed = false;
			mainDisplay.setText(temp.substring(0, temp.length() - 1));
		}
	}

	private void warning(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
	}
}
