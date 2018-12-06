package chat_client;



import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import java.awt.CardLayout;
import javax.swing.JLabel;

public class Application extends JFrame {

	private JPanel contentPane;
	private JTextField ipField;
	private JTextField portField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
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
	public Application() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		portField = new JTextField();
		contentPane.add(portField, "name_12944676688147");
		portField.setColumns(10);
		
		ipField = new JTextField();
		contentPane.add(ipField, "name_12921824126734");
		ipField.setColumns(10);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		contentPane.add(lblIpAddress, "name_12870584092801");
		
		JLabel lblPort = new JLabel("Port");
		contentPane.add(lblPort, "name_12886880976381");
	}

}
