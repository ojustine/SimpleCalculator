import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class CalcFrame extends JFrame implements KeyListener, ActionListener
{

	private double	preResult;
	private double	memory;
	private boolean	isReadyForCalc;
	private boolean	isMemoryFull;
	private boolean	isDotPressed;
	private String	currentOperation = "";
	private final String[] allButtons = {"%", "\u02E3\u221A", "x\u02B8", "\u232B", "M+", "M-", "C", "\u00F7", "7", "8", "9", "\u00D7", "4", "5", "6", "-", "1", "2", "3", "+", "\u00B1", "0", ".", "="};

	private DecimalFormat			numberFormat = new DecimalFormat("#.##########");
	private DecimalFormatSymbols	decimalFormatSymbols = new DecimalFormatSymbols();
	private Font					regular = new Font(Font.MONOSPACED, Font.PLAIN, 22);
	private Font					mainBar = new Font(Font.MONOSPACED, Font.PLAIN, 45);
	private JLabel					preDisplay = new JLabel("\u0020");
	private JTextField				mainDisplay = new JTextField("");

	private JButton button0 = new JButton("0");
	private JButton buttonOne = new JButton("1");
	private JButton buttonTwo = new JButton("2");
	private JButton buttonThree = new JButton("3");
	private JButton buttonFour = new JButton("4");
	private JButton buttonFive = new JButton("5");
	private JButton buttonSix = new JButton("6");
	private JButton buttonSeven = new JButton("7");
	private JButton buttonEight = new JButton("8");
	private JButton buttonNine = new JButton("9");
	private JButton buttonPercent = new JButton("%");
	private JButton buttonSquareRoot = new JButton("\u02E3\u221A");
	private JButton buttonPow = new JButton("x\u02B8");
	private JButton buttonBackspace = new JButton("\u232B");
	private JButton buttonMemoryAdd = new JButton("M+");
	private JButton buttonMemoryClear = new JButton("M-");
	private JButton buttonCancel = new JButton("C");
	private JButton buttonDivision = new JButton("\u00F7");
	private JButton buttonMultiplication = new JButton("\u00D7");
	private JButton buttonMinus = new JButton("-");
	private JButton buttonPlus = new JButton("+");
	private JButton buttonNegative = new JButton("\u00B1");
	private JButton buttonDot = new JButton(".");
	private JButton buttonResult = new JButton("=");

	JPanel pane = new JPanel();

	CalcFrame()
	{
		super("Калькуляша");
		init();
	}

	private void init() {

		decimalFormatSymbols.setDecimalSeparator('.');
		numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		preDisplay.setFont(regular);

		mainDisplay.setHorizontalAlignment(JTextField.RIGHT);
		mainDisplay.setFont(mainBar);

		button0.setFont(regular);
		buttonOne.setFont(regular);
		buttonTwo.setFont(regular);
		buttonThree.setFont(regular);
		buttonFour.setFont(regular);
		buttonFive.setFont(regular);
		buttonSix.setFont(regular);
		buttonSeven.setFont(regular);
		buttonEight.setFont(regular);
		buttonNine.setFont(regular);
		buttonPercent.setFont(regular);
		buttonSquareRoot.setFont(regular);
		buttonPow.setFont(regular);
		buttonBackspace.setFont(regular);
		buttonMemoryAdd.setFont(regular);
		buttonMemoryClear.setFont(regular);
		buttonCancel.setFont(regular);
		buttonDivision.setFont(regular);
		buttonMultiplication.setFont(regular);
		buttonMinus.setFont(regular);
		buttonPlus.setFont(regular);
		buttonNegative.setFont(regular);
		buttonDot.setFont(regular);
		buttonResult.setFont(regular);

		GridBagLayout layout = new GridBagLayout();
		pane.setLayout(layout);

		GridBagConstraints placing = new GridBagConstraints();
		layout.setConstraints(mainDisplay, placing);

		placing.insets = new Insets(0, 0, 0, 0);
		placing.fill = GridBagConstraints.BOTH;

		placing.gridx = 0;                                  //
		placing.gridy = 0;                                  //
		placing.gridheight = 1;                             //
		placing.weightx = 0.25;
		placing.weighty = 0.01;
		placing.gridwidth = GridBagConstraints.REMAINDER;   //Верхний бар с предв. расчетом
		preDisplay.setHorizontalAlignment(JLabel.RIGHT);     //
		pane.add(preDisplay, placing);                       //

		placing.gridx = 0;
		placing.gridy = 1;                                  //
		mainDisplay.setEditable(false);                       //Основное поле
		mainDisplay.setBorder(BorderFactory.createEtchedBorder());
		pane.add(mainDisplay, placing);                       //

		placing.gridx = 0;                                  //Первый ряд кнопок
		placing.gridy = 2;                                  //
		placing.gridwidth = 1;                              //
		pane.add(buttonPercent, placing);                   //Проценты
		buttonPercent.addActionListener(this);

		placing.gridx = 1;                                  //
		pane.add(buttonSquareRoot, placing);                 //Кв корень
		buttonSquareRoot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instantOperation("\u221A");
			}
		});

		placing.gridx = 2;                                  //
		pane.add(buttonPow, placing);                       //Степень
		buttonPow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instantOperation("\u00B2");
			}
		});

		placing.gridx = 3;                                  //
		pane.add(buttonBackspace, placing);                 //Стиратель
		buttonBackspace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backspace();
			}
		});

		placing.gridx = 0;                                  //Второй ряд кнопок
		placing.gridy = 3;                                  //
		pane.add(buttonMemoryAdd, placing);                 //Добавление в память
		buttonMemoryAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoryAdd();
			}
		});
		//
		placing.gridx = 1;                                  //
		pane.add(buttonMemoryClear, placing);               //Очистка памяти
		buttonMemoryClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isMemoryFull = false;
			}
		});
		//
		placing.gridx = 2;                                  //
		pane.add(buttonCancel, placing);                    //Сброс
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		//
		placing.gridx = 3;                                  //
		pane.add(buttonDivision, placing);                  //Деление
		buttonDivision.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleOperation("/");
			}
		});

		placing.gridx = 0;                                  //Третий ряд кнопок
		placing.gridy = 4;                                  //
		pane.add(buttonSeven, placing);                     //цифра 7
		buttonSeven.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(7);
			}
		});
		//
		placing.gridx = 1;                                  //
		pane.add(buttonEight, placing);                     //Цифра 8
		buttonEight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(8);
			}
		});
		//
		placing.gridx = 2;                                  //
		pane.add(buttonNine, placing);                      //Цифра 9
		buttonNine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(9);
			}
		});
		//
		placing.gridx = 3;                                  //
		pane.add(buttonMultiplication, placing);            //Умножение
		buttonMultiplication.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleOperation("\u00D7");
			}
		});

		placing.gridx = 0;                                  //Четвертый ряд кнопок
		placing.gridy = 5;                                  //
		pane.add(buttonFour, placing);                      //цифра 4
		buttonFour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(4);
			}
		});
		//
		placing.gridx = 1;                                  //
		pane.add(buttonFive, placing);                      //Цифра 5
		buttonFive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(5);
			}
		});
		//
		placing.gridx = 2;                                  //
		pane.add(buttonSix, placing);                       //Цифра 6
		buttonSix.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(6);
			}
		});
		//
		placing.gridx = 3;                                  //
		pane.add(buttonMinus, placing);                     //Вычитание
		buttonMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleOperation("-");
			}
		});
		placing.gridx = 0;                                  //Пятый ряд кнопок
		placing.gridy = 6;                                  //
		pane.add(buttonOne, placing);                       //цифра 1
		buttonOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(1);
			}
		});
		//
		placing.gridx = 1;                                  //
		pane.add(buttonTwo, placing);                       //Цифра 2
		buttonTwo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(2);
			}
		});
		//
		placing.gridx = 2;                                  //
		pane.add(buttonThree, placing);                     //Цифра 3
		buttonThree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(3);
			}
		});
		//
		placing.gridx = 3;                                  //
		pane.add(buttonPlus, placing);                      //Сложение
		buttonPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleOperation("+");
			}
		});
		placing.gridx = 0;                                  //Шестой ряд кнопок
		placing.gridy = 7;                                  //
		pane.add(buttonNegative, placing);                  //Смена знака
		buttonNegative.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				negative();
			}
		});
		//
		placing.gridx = 1;                                  //
		pane.add(button0, placing);                      //Цифра 0
		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				digit(0);
			}
		});
		//
		placing.gridx = 2;                                  //
		pane.add(buttonDot, placing);                       //Точка
		buttonDot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dot();

			}
		});
		//
		placing.gridx = 3;                                  //
		pane.add(buttonResult, placing);                    //Результат
		buttonResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				result();
			}
		});

		setContentPane(pane);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		simpleOperation("%");
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

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
	}

	private void instantOperation(String operation)
	{
		if (!mainDisplay.getText().isEmpty())
		{
			double temp = Double.parseDouble(mainDisplay.getText());
			switch (operation)
			{
				case "\u221A":
					if (temp > 0)
						mainDisplay.setText(numberFormat.format(Math.sqrt(temp)));
					else
					{
						clear();
						warning("Нельзя извлечь квадратный корень из отрицательного числа!");
					}
					break;
				case "\u00B2":
					mainDisplay.setText(numberFormat.format(Math.pow(temp, 2)));
					break;
			}
			if (isDotPressed())
				isDotPressed = true;
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

	private void result()
	{
		if (isReadyForCalc && !mainDisplay.getText().isEmpty())
		{
			double multiplier = Double.parseDouble(mainDisplay.getText());
			clear();
			switch (currentOperation)
			{
				case "%":
					mainDisplay.setText(numberFormat.format(preResult / 100 * multiplier));
					break;
				case "/":
					if (multiplier != 0)
					{
						mainDisplay.setText(numberFormat.format(preResult / multiplier));
						break;
					}
					else
					{
						clear();
						warning("На ноль делить нельзя!");
						break;
					}
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
			double temp = Double.parseDouble(mainDisplay.getText()) * -1;
			mainDisplay.setText(numberFormat.format(temp));
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
			isMemoryFull = true;
			memory = Double.parseDouble(mainDisplay.getText());
		}
	}

	private void backspace()
	{
		if (!mainDisplay.getText().isEmpty())
		{
			if (".".equals(mainDisplay.getText().substring(mainDisplay.getText().length() - 1)))
				isDotPressed = false;
			mainDisplay.setText(mainDisplay.getText().substring(0, mainDisplay.getText().length() - 1));
		}
	}

	private void warning(String message)
	{
		JOptionPane.showMessageDialog(null, message, "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
	}
}
