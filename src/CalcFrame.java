import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;

class CalcFrame extends JFrame
{

	private double	preResult;
	private double	memory;
	private boolean	isReadyForCalc;
	private boolean	isMemoryFull;
	private boolean	isDotPressed;
	private String	currentOperation = "";

	private DecimalFormat numberFormat = new DecimalFormat("#.##########");
	private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
	private Font regular = new Font("Arial", Font.PLAIN, 22);
	private Font preBar = new Font("Impact", Font.PLAIN, 22);
	private Font mainBar = new Font("Impact", Font.PLAIN, 45);
	private JLabel preDisplay = new JLabel("\u0020");
	private JTextField display = new JTextField("");
	private JButton buttonZero = new JButton("0");
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
	private JButton buttonSquareRoot = new JButton("\u221A");
	private JButton buttonPow = new JButton("X" + "\u00B2");
	private JButton buttonBackspace = new JButton("<<");
	private JButton buttonMemoryAdd = new JButton("M+");
	private JButton buttonMemoryClear = new JButton("M-");
	private JButton buttonCancel = new JButton("C");
	private JButton buttonDivision = new JButton("/");
	private JButton buttonMultiplication = new JButton("\u00D7");
	private JButton buttonMinus = new JButton("-");
	private JButton buttonPlus = new JButton("+");
	private JButton buttonNegative = new JButton("\u00B1");
	private JButton buttonDot = new JButton(".");
	private JButton buttonResult = new JButton("=");


	CalcFrame()
	{
		super("Калькуляша");
		init();
	}

	private void init() {

		decimalFormatSymbols.setDecimalSeparator('.');
		numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);

		preDisplay.setFont(preBar);

		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setFont(mainBar);

		buttonZero.setFont(regular);
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

		JPanel pane = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		pane.setLayout(layout);

		GridBagConstraints placing = new GridBagConstraints();
		layout.setConstraints(display, placing);

		placing.insets = new Insets(5, 5, 5, 5);
		placing.fill = GridBagConstraints.BOTH;
		placing.weightx = 0.25;

		placing.gridx = 0;                                  //
		placing.gridy = 0;                                  //
		placing.gridheight = 1;                             //
		placing.gridwidth = GridBagConstraints.REMAINDER;   //Верхний бар с предв. расчетом
		preDisplay.setHorizontalAlignment(JLabel.RIGHT);     //
		pane.add(preDisplay, placing);                       //

		placing.gridx = 0;
		placing.gridy = 1;                                  //
		display.setEditable(false);                       //Основное поле
		display.setBorder(BorderFactory.createEtchedBorder());
		pane.add(display, placing);                       //

		placing.weighty = 0.01;
		placing.gridx = 0;                                  //Первый ряд кнопок
		placing.gridy = 2;                                  //
		placing.gridwidth = 1;                              //
		pane.add(buttonPercent, placing);                   //Проценты
		buttonPercent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simpleOperation("%");
			}
		});

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
		pane.add(buttonZero, placing);                      //Цифра 0
		buttonZero.addActionListener(new ActionListener() {
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


	private void digit(int digit) {
		switch (digit) {
			case 0:
				if (nullDisplay() || "0".equals(display.getText())) {
					display.setText("0");
				} else {
					display.setText(display.getText() + "0");
				}
				break;
			case 1:
				if ("0".equals(display.getText())) {
					display.setText("0.1");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "1");
				}
				break;
			case 2:
				if ("0".equals(display.getText())) {
					display.setText("0.2");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "2");
				}
				break;
			case 3:
				if ("0".equals(display.getText())) {
					display.setText("0.3");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "3");
				}
				break;
			case 4:
				if ("0".equals(display.getText())) {
					display.setText("0.4");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "4");
				}
				break;
			case 5:
				if ("0".equals(display.getText())) {
					display.setText("0.5");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "5");
				}
				break;
			case 6:
				if ("0".equals(display.getText())) {
					display.setText("0.6");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "6");
				}
				break;
			case 7:
				if ("0".equals(display.getText())) {
					display.setText("0.7");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "7");
				}
				break;
			case 8:
				if ("0".equals(display.getText())) {
					display.setText("0.8");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "8");
				}
				break;
			case 9:
				if ("0".equals(display.getText())) {
					display.setText("0.9");
					isDotPressed = true;
				} else {
					display.setText(display.getText() + "9");
				}
				break;
		}
	}

	private void clear() {
		display.setText("");
		preDisplay.setText("\u0020");
		isReadyForCalc = false;
		isDotPressed = false;
	}

	private void simpleOperation(String operation) {

		if (!nullDisplay()) {
			isDotPressed = false;
			currentOperation = operation;

			if (!isReadyForCalc) {
				preResult = Double.parseDouble(display.getText());
				isReadyForCalc = true;
			}

			if ("%".equals(operation)) {
				preDisplay.setText(operation + numberFormat.format(preResult));
			} else {
				preDisplay.setText(numberFormat.format(preResult) + " " + operation);
			}

			display.setText("");
		}

	}

	private void instantOperation(String operation) {
		if (!nullDisplay()) {

			double temp = Double.parseDouble(display.getText());

			switch (operation) {
				case "\u221A":
					if (temp > 0) {
						display.setText(numberFormat.format(Math.sqrt(temp)));
						break;
					} else {
						clear();
						warning("Нельзя извлечь квадратный корень из отрицательного числа!");
						break;
					}
				case "\u00B2":
					display.setText(numberFormat.format(Math.pow(temp, 2)));
					break;
			}

			if (isDotPressed()) isDotPressed = true;

		}
	}

	private boolean isDotPressed() {
		char[] displayArray = display.getText().toCharArray();

		for (char c : displayArray) {
			if (c == '.') {
				return true;
			}
		}

		return false;
	}

	private void result() {
		if (isReadyForCalc && !nullDisplay()) {
			double multipler = Double.parseDouble(display.getText());
			clear();
			switch (currentOperation) {
				case "%":
					display.setText(numberFormat.format(preResult / 100 * multipler));
					break;
				case "/":
					if (multipler != 0) {
						display.setText(numberFormat.format(preResult / multipler));
						break;
					} else {
						clear();
						warning("На ноль делить нельзя!");
						break;
					}
				case "\u00D7":
					display.setText(numberFormat.format(preResult * multipler));
					break;
				case "-":
					display.setText(numberFormat.format(preResult - multipler));
					break;
				case "+":
					display.setText(numberFormat.format(preResult + multipler));
					break;
			}

			if (isDotPressed()) isDotPressed = true;

		}
	}

	private void dot() {
		if (!isDotPressed && !nullDisplay() && !".".equals(display.getText().substring(display.getText().length() - 1))) {
			display.setText(display.getText() + ".");
			isDotPressed = true;
		}
	}

	private void negative() {
		if (!nullDisplay()) {
			double temp = Double.parseDouble(display.getText()) * -1;
			display.setText(numberFormat.format(temp));
		}
	}

	private void memoryAdd() {
		if (isMemoryFull) {
			if (!((memory == Math.floor(memory)) && !Double.isInfinite(memory))) {
				isDotPressed = true;
			}
			display.setText(String.valueOf(numberFormat.format(memory)));
		} else if (!nullDisplay()) {
			isMemoryFull = true;
			memory = Double.parseDouble(display.getText());
		}
	}

	private void backspace() {
		if (!nullDisplay()) {
			if (".".equals(display.getText().substring(display.getText().length() - 1))) {
				isDotPressed = false;
			}
			display.setText(display.getText().substring(0, display.getText().length() - 1));
		}
	}

	private void warning(String message) {

		JOptionPane.showMessageDialog(null, message, "Ошибка!", JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean nullDisplay() {
		return "".equals(display.getText());
	}
}
