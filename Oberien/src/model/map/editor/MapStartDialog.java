package model.map.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapStartDialog extends JDialog implements ActionListener {
	private String n;
	private int w;
	private int h;
	
	private JTextField name;
	private JTextField width;
	private JTextField height;
	
	public MapStartDialog(JFrame frame) {
		super(frame, true);
		add(new JLabel("Please insert name and size"), BorderLayout.NORTH);
		JPanel center = new JPanel(new GridLayout(3,2));
		name = new JTextField(8);
		name.addActionListener(this);
		width = new JTextField(2);
		width.addActionListener(this);
		height = new JTextField(2);
		height.addActionListener(this);
		center.add(new JLabel("Name:"));
		center.add(name);
		center.add(new JLabel("Width:"));
		center.add(width);
		center.add(new JLabel("Height:"));
		center.add(height);
		
		add(center, BorderLayout.CENTER);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		n = name.getText();
		try {
			w = Integer.parseInt(width.getText());
			h = Integer.parseInt(height.getText());
			if (w == 0 || h == 0) {
				JOptionPane.showMessageDialog(null, "Width and height cannot be 0.", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			dispose();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Please insert a valid width and height.", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String getN() {
		return n;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
}
