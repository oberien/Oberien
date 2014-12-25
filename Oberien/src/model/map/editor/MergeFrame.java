package model.map.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.map.Map;
import model.map.MapIO;

//TODO get team start positions out of all different maps

public class MergeFrame extends JFrame implements ActionListener {
	JButton[][] b;
	File[][] f;
	Map[][] maps;
	String name;
	
	public MergeFrame() {
		super();
		MergeStartDialog d = new MergeStartDialog(this);
		name = d.getN();
		int w = d.getW();
		int h = d.getH();
		setTitle("Merge - " + name);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem export = new JMenuItem("Export");
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = 0;
				for (int i = 0; i < b.length; i++) {
					for (int j = 0; j < b[0].length; j++) {
						if (f[i][j] == null) {
							JOptionPane.showMessageDialog(null, "Not enough maps selected", "", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (j == 0) {
							temp = maps[i][j].getWidth();
						} else {
							if (maps[i][j].getWidth() != temp) {
								JOptionPane.showMessageDialog(null, "Width of \"" + maps[i][j].getName() + "\" doesnt fit to column's width", "", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
				}
				for (int j = 0; j < b[0].length; j++) {
					for (int i = 0; i < b.length; i++) {
						if (i == 0) {
							temp = maps[i][j].getHeight();
						} else {
							if (maps[i][j].getHeight() != temp) {
								JOptionPane.showMessageDialog(null, "Height of \"" + maps[i][j].getName() + "\" doesnt fit to line's height", "", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
				}
				int width = 0;
				int height = 0;
				for (int i = 0; i < b.length; i++) {
					width += maps[i][0].getWidth();
				}
				for (int i = 0; i < b[0].length; i++) {
					height += maps[0][i].getHeight();
				}
				
				Map newMap = new Map(name, width, height, null, null, null, null);
				height = 1;
				for (int j = 0; j < b[0].length; j++) {
					for (int y = 0; y < height; y++) {
						for (int i = 0; i < b.length; i++) {
							height = maps[i][j].getHeight();
							for (int x = 0; x < maps[i][j].getWidth(); x++) {
								newMap.add(maps[i][j].get(x, y));
							}
						}
					}
				}
				boolean b = MapIO.write(newMap);
				if (b) {
					JOptionPane.showMessageDialog(null, "Map exported", "", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Map NOT exported", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		file.add(export);
		menuBar.add(file);
		setJMenuBar(menuBar);
		
		f = new File[w][h];
		b = new JButton[w][h];
		maps = new Map[w][h];
		JPanel center = new JPanel(new GridLayout(h, w));
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				b[i][j] = new JButton();
				b[i][j].setPreferredSize(new Dimension(100, 40));
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand(i + "," + j);
				center.add(b[i][j]);
			}
		}
		add(center, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] parts = e.getActionCommand().split(",");
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);
		FileDialog d = new FileDialog(new Frame(),"Text laden...",FileDialog.LOAD);
		d.setVisible(true);
		String file = d.getDirectory();
		file += d.getFile();
		if (!file.equals("nullnull")) {
			f[x][y] = new File(file);
			maps[x][y] = MapIO.read(f[x][y]);
			b[x][y].setText(maps[x][y].getName() + ": " + maps[x][y].getWidth() + "x" + maps[x][y].getHeight());
		}
	}
}
