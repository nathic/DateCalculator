import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;

public class GUI extends JFrame {

	private JPanel contentPane;
	private DateCalculator dateCalculator;
	private JSpinner spinnerYear;
	private JComboBox<String> comboBoxDay;
	private JTextArea textAreaOutput;
	private Calendar calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		initialize();
	}

	private void initializeOutputPanel() {
		textAreaOutput = new JTextArea();
		textAreaOutput.setEditable(false);
		JScrollPane outputPanel = new JScrollPane(textAreaOutput);
		contentPane.add(outputPanel);

	}

	private void initializeControlPanel() {
		JPanel controlPanel = new JPanel();
		contentPane.add(controlPanel, BorderLayout.WEST);
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		JLabel lblDay = new JLabel("Tag");
		lblDay.setHorizontalAlignment(SwingConstants.LEFT);
		controlPanel.add(lblDay);

		String[] dayStrings = { "Montag", "Dienstag", "Mittwoch", "Donnerstag",
				"Freitag", "Samstag", "Sonntag" };
		comboBoxDay = new JComboBox<>(dayStrings);
		comboBoxDay.setAlignmentX(Component.LEFT_ALIGNMENT);
		controlPanel.add(comboBoxDay);

		JLabel lblYear = new JLabel("Jahr");
		controlPanel.add(lblYear);

		SpinnerNumberModel model = new SpinnerNumberModel(
				calendar.get(Calendar.YEAR),
				calendar.getActualMinimum(Calendar.YEAR),
				calendar.getActualMaximum(Calendar.YEAR), 1);
		spinnerYear = new JSpinner(model);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerYear,
				"#");
		spinnerYear.setEditor(editor);
		spinnerYear.setAlignmentX(Component.LEFT_ALIGNMENT);
		controlPanel.add(spinnerYear);
	}

	private void initialize() {

		calendar = Calendar.getInstance();
		initializeControlPanel();
		initializeOutputPanel();

		setListeners();

		dateCalculator = new DateCalculator();

		updateOutput();
	}

	private void setListeners() {
		comboBoxDay.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				updateOutput();
			}
		});

		spinnerYear.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateOutput();
			}
		});

		textAreaOutput.addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					final JPopupMenu menu = new JPopupMenu();
					JMenuItem item;
					item = new JMenuItem(new DefaultEditorKit.CopyAction());
					item.setText("Copy");
					item.setEnabled(textAreaOutput.getSelectionStart() != textAreaOutput
							.getSelectionEnd());
					menu.add(item);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

	}

	private int getWeekdayInNumberFromString(String dayInString) {
		int day;
		switch (dayInString) {
		case "Montag": {
			day = 2;
			break;
		}
		case "Dienstag": {
			day = 3;
			break;
		}
		case "Mittwoch": {
			day = 4;
			break;
		}
		case "Donnerstag": {
			day = 5;
			break;
		}
		case "Freitag": {
			day = 6;
			break;
		}
		case "Samstag": {
			day = 7;
			break;
		}
		case "Sonntag": {
			day = 1;
			break;
		}
		default: {
			day = 2;
			break;
		}
		}

		return day;
	}

	private void updateOutput() {

		String dayInString = (String) comboBoxDay.getSelectedItem();
		int day = getWeekdayInNumberFromString(dayInString);

		int year = (int) spinnerYear.getValue();

		ArrayList<Date> list = dateCalculator.getAll(day, year);

		StringBuilder sbuilder = new StringBuilder();
		for (Date date : list) {
			System.out.println(date);
			sbuilder.append(prettyPrint(date) + "\n");
		}
		textAreaOutput.setText(sbuilder.toString());

	}

	private String prettyPrint(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		StringBuilder sb = new StringBuilder();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			sb.append("0");
		}
		sb.append(day + ".");
		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month + ".");
		int year = cal.get(Calendar.YEAR);
		sb.append(year);
		return sb.toString();
	}

}
