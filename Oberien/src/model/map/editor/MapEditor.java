package model.map.editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import model.TeamColors;
import model.map.*;

public class MapEditor extends JFrame implements MouseListener, MouseMotionListener, WindowListener {
	/**
	 * Buttons with fieldnames to choose the field
	 */
	private JButton[] fields;
	/**
	 * FielList containing all fields to get the image of the used field
	 */
	private FieldList fieldList = FieldList.getInstance();
	/**
	 * Panels with images of the fields
	 */
	private PicturePanel[][] fieldPanel; 
	
	/**
	 * width of the map
	 */
	private final int w;
	/**
	 * height of the map
	 */
	private final int h;
	/**
	 * name of current map
	 */
	private String name;
	/**
	 * number of the momentarily selected field for rightclick
	 */
	private byte rightField = 1;
	/**
	 * number of the momentarily selected field for leftclick
	 */
	private byte leftField = 0;
	
	ArrayList<Integer> startPosX1 = new ArrayList<Integer>();
	ArrayList<Integer> startPosY1 = new ArrayList<Integer>();
	ArrayList<Integer> startPosX2 = new ArrayList<Integer>();
	ArrayList<Integer> startPosY2 = new ArrayList<Integer>();
	/**
	 * defines whether the startingPosition must be chosen or not
	 * <ul>
	 * <li>0 when no team must be chosen</li>
	 * <li>1-n which team must be chosen</li>
	 * </ul>
	 */
	int choosePosOf = 0;
	int chooseStartX;
	int chooseStartY;
	int chooseActX;
	int chooseActY;
	
	private boolean changed;
	private int mouseButtonClicked = 0;
	
	public MapEditor() {
		super("Map Editor");
		changed = false;
		w = 0;
		h = 0;
		name = null;
		
		setJMenuBar(createJMenuBar());
		
		//west contains buttons to choose the field to use
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		fields = new JButton[256];
		for (int i = 0; i < 256; i++) {
			Field f = fieldList.get(i);
			if (f == null) {
				break;
			}
			fields[i] = new JButton(f.getName());
			fields[i].setIcon(new ImageIcon(f.getImage()));
			fields[i].addMouseListener(new ButtonMouseListener((byte)i));
			fields[i].setActionCommand(i + "");
			west.add(fields[i]);
		}
		fields[rightField].setForeground(Color.RED);
		fields[leftField].setForeground(Color.BLUE);
		add(new JScrollPane(west), BorderLayout.WEST);
		
		//size, location, closeoperation, display
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setVisible(true);
	}
	
	public MapEditor(int width, int height, String n) {
		super();
		changed = true;
		//set all needed basic values
		this.w = width;
		this.h = height;
		this.name = n;
		setTitle("Map Editor - " + name);
		
		setJMenuBar(createJMenuBar());
		
		//west contains buttons to choose the field to use
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		fields = new JButton[256];
		for (int i = 0; i < 256; i++) {
			Field f = fieldList.get(i);
			if (f == null) {
				break;
			}
			fields[i] = new JButton(f.getName());
			fields[i].setIcon(new ImageIcon(f.getImage()));
			fields[i].addMouseListener(new ButtonMouseListener((byte)i));
			fields[i].setActionCommand(i + "");
			west.add(fields[i]);
		}
		fields[rightField].setForeground(Color.RED);
		fields[leftField].setForeground(Color.BLUE);
		add(new JScrollPane(west), BorderLayout.WEST);
		
		//center contains the map with images
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(h, w));
		center.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (choosePosOf != 0) {
					for (int i = chooseStartX; i <= chooseActX; i++) {
						for (int j = chooseStartY; j <= chooseActY; j++) {
							fieldPanel[i][j].removeFG();
						}
					}
					chooseActX = chooseStartX + e.getX()/fieldPanel[0][0].getWidth();
					chooseActY = chooseStartY + e.getY()/fieldPanel[0][0].getHeight();
					for (int i = chooseStartX; i <= chooseActX; i++) {
						for (int j = chooseStartY; j <= chooseActY; j++) {
							fieldPanel[i][j].drawFG(TeamColors.get(choosePosOf-1));
						}
					}
					repaint();
				}
			}
		});
		center.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				PicturePanel p = (PicturePanel)e.getSource();
				chooseStartX = p.getXcoord();
				chooseStartY = p.getYcoord();
				chooseActX = chooseStartX;
				chooseActY = chooseStartY;
			}
			public void mouseReleased(MouseEvent e) {
				if (choosePosOf > startPosX1.size()) {
					startPosX1.add(chooseStartX);
					startPosY1.add(chooseStartY);
					startPosX2.add(chooseActX);
					startPosY2.add(chooseActY);
				} else {
					startPosX1.remove(choosePosOf-1);
					startPosY1.remove(choosePosOf-1);
					startPosX2.remove(choosePosOf-1);
					startPosY2.remove(choosePosOf-1);
					startPosX1.add(choosePosOf-1, chooseStartX);
					startPosY1.add(choosePosOf-1, chooseStartY);
					startPosX2.add(choosePosOf-1, chooseActX);
					startPosY2.add(choosePosOf-1, chooseActY);
				}
				choosePosOf = 0;
			}
		});
		fieldPanel = new PicturePanel[w][h];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				fieldPanel[j][i] = new PicturePanel(i, j);
				fieldPanel[j][i].setSize(new Dimension(50, 50));
				fieldPanel[j][i].setPreferredSize(new Dimension(50, 50));
				fieldPanel[j][i].setBackgroundImage(fieldList.get(0).getImage(), (byte)0);
				fieldPanel[j][i].addMouseListener(this);
				center.add(fieldPanel[j][i]);
			}
		}
		add(new MyJScrollPane(center), BorderLayout.CENTER);
		
		//size, location, closeoperation, display
		pack();
		if (getSize().width > Toolkit.getDefaultToolkit().getScreenSize().width ||
				getSize().height > Toolkit.getDefaultToolkit().getScreenSize().height) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
		}
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setVisible(true);
	}
	
	public MapEditor(Map m) {
		super();
		//set all needed basic values
		changed = false;
		this.w = m.getWidth();
		this.h = m.getHeight();
		this.name = m.getName();
		setTitle("Map Editor - " + name);
		if (w == 0 || h == 0) {
			System.exit(1);
		}
		
		setJMenuBar(createJMenuBar());
		
		//west contains buttons to choose the field to use
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		fields = new JButton[256];
		for (int i = 0; i < 256; i++) {
			Field f = fieldList.get(i);
			if (f == null) {
				break;
			}
			fields[i] = new JButton(f.getName());
			fields[i].setIcon(new ImageIcon(f.getImage()));
			fields[i].addMouseListener(new ButtonMouseListener((byte)i));
			west.add(fields[i]);
		}
		fields[rightField].setForeground(Color.RED);
		fields[leftField].setForeground(Color.BLUE);
		add(new JScrollPane(west), BorderLayout.WEST);
		
		//center contains the map with images
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(h, w));
		center.requestFocus();
		center.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (choosePosOf != 0) {
					for (int i = chooseStartX; i <= chooseActX; i++) {
						for (int j = chooseStartY; j <= chooseActY; j++) {
							fieldPanel[i][j].removeFG();
						}
					}
					chooseActX = chooseStartX + e.getX()/fieldPanel[0][0].getWidth();
					chooseActY = chooseStartY + e.getY()/fieldPanel[0][0].getHeight();
					for (int i = chooseStartX; i <= chooseActX; i++) {
						for (int j = chooseStartY; j <= chooseActY; j++) {
							fieldPanel[i][j].drawFG(TeamColors.get(choosePosOf-1));
						}
					}
					repaint();
				}
			}
		});
		center.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				PicturePanel p = (PicturePanel)e.getSource();
				chooseStartX = p.getXcoord();
				chooseStartY = p.getYcoord();
				chooseActX = chooseStartX;
				chooseActY = chooseStartY;
			}
			public void mouseReleased(MouseEvent e) {
				if (choosePosOf > startPosX1.size()) {
					startPosX1.add(chooseStartX);
					startPosY1.add(chooseStartY);
					startPosX2.add(chooseActX);
					startPosY2.add(chooseActY);
				} else {
					startPosX1.remove(choosePosOf-1);
					startPosY1.remove(choosePosOf-1);
					startPosX2.remove(choosePosOf-1);
					startPosY2.remove(choosePosOf-1);
					startPosX1.add(choosePosOf-1, chooseStartX);
					startPosY1.add(choosePosOf-1, chooseStartY);
					startPosX2.add(choosePosOf-1, chooseActX);
					startPosY2.add(choosePosOf-1, chooseActY);
				}
				choosePosOf = 0;
			}
		});
		fieldPanel = new PicturePanel[w][h];
		//i=actHeight
		for (int i = 0; i < h; i++) {
			//j=actWidth
			for (int j = 0; j < w; j++) {
				fieldPanel[j][i] = new PicturePanel(j, i);
				fieldPanel[j][i].setSize(new Dimension(30, 30));
				fieldPanel[j][i].setPreferredSize(new Dimension(30, 30));
				fieldPanel[j][i].setBackgroundImage(fieldList.get(m.get(j, i)).getImage(), m.get(j, i));
				fieldPanel[j][i].addMouseListener(this);
				fieldPanel[j][i].addMouseMotionListener(this);
				center.add(fieldPanel[j][i]);
			}
		}
		add(new MyJScrollPane(center), BorderLayout.CENTER);
		
		//Start positions of teams
		if (m.getStartPosX1() != null) {
			for (int i = 0; i < m.getStartPosX1().length; i++) {
				startPosX1.add(m.getStartPosX1()[i]);
				startPosY1.add(m.getStartPosY1()[i]);
				startPosX2.add(m.getStartPosX2()[i]);
				startPosY2.add(m.getStartPosY2()[i]);
				for (int x = startPosX1.get(i); x <= startPosX2.get(i); x++) {
					for (int y = startPosY1.get(i); y <= startPosY2.get(i); y++) {
						fieldPanel[x][y].drawFG(TeamColors.get(i));
					}
				}
			}
		}
		
		//size, location, closeoperation, display
		pack();
		if (getSize().width > Toolkit.getDefaultToolkit().getScreenSize().width ||
				getSize().height > Toolkit.getDefaultToolkit().getScreenSize().height) {
			setExtendedState(Frame.MAXIMIZED_BOTH);
		}
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setVisible(true);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newMap = new JMenuItem("New Map");
		newMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MapStartDialog dialog = new MapStartDialog(MapEditor.this);
				if (dialog.getW() == 0 || dialog.getH() == 0) {
					return;
				}
				if (dialog.getW() * dialog.getH() > 40000) {
					int sel = JOptionPane.showConfirmDialog(null,
							"<html>Your size is very big. It will need very much time to create it.<br/>" +
							"Better create smaller maps, which you can merge together.<br/>" +
							"Continue?", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (sel == 1) {
						return;
					}
				}
				new MapEditor(dialog.getW(), dialog.getH(), dialog.getN());
				MapEditor.this.dispose();
			}
		});
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog d = new FileDialog(new Frame(),"Text laden...",FileDialog.LOAD);
				d.setVisible(true);
				String file = d.getDirectory();
				file += d.getFile();
				Map map = MapIO.read(new File(file));
				new MapEditor(map);
				MapEditor.this.dispose();
			}
		});
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		file.add(newMap);
		file.add(open);
		file.add(save);
		menuBar.add(file);
		
		JMenu map = new JMenu("Map");
		JMenuItem rename = new JMenuItem("Rename");
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = JOptionPane.showInputDialog(null, "Please insert a new name:", "", JOptionPane.QUESTION_MESSAGE);
				if (temp != null) {
					name = temp;
					setTitle("Map Editor - " + name);
				}
			}
		});
		JMenuItem resize = new JMenuItem("Resize fields");
		resize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size = Integer.parseInt(JOptionPane.showInputDialog(null, "Please insert new size (only one int)"));
				for (int i = 0; i < fieldPanel.length; i++) {
					for (int j = 0; j < fieldPanel[0].length; j++) {
						fieldPanel[i][j].setPreferredSize(new Dimension(size, size));
						fieldPanel[i][j].updateUI();
					}
				}
			}
		});
		JMenuItem convert = new JMenuItem("Convert Maps to new version");
		convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog d = new FileDialog(new Frame(),"Text laden...",FileDialog.LOAD);
				d.setVisible(true);
				String file = d.getDirectory();
				file += d.getFile();
				MapConverter.convert(new File(file));
			}
		});
		JMenuItem merge = new JMenuItem("Merge small maps to a big one");
		merge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MergeFrame();
			}
		});
		
		map.add(rename);
		map.add(resize);
		map.add(convert);
		map.add(merge);
		menuBar.add(map);
		
		JMenu teams = new JMenu("Teams");
		JMenuItem chooseStartPos = new JMenuItem("Choose start area of team 1");
		chooseStartPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePosOf = startPosX1.size()+1;
				((JMenuItem)e.getSource()).setText("Choose start area of team " + (startPosX1.size()+2));
			}
		});
		JMenuItem chooseStartPosOf = new JMenuItem("Change start area of team input");
		chooseStartPosOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePosOf = Integer.parseInt(JOptionPane.showInputDialog(null, 
						"Which team would you like to select an area for?"));
				if (choosePosOf > startPosX1.size()) {
					choosePosOf = 0;
					JOptionPane.showMessageDialog(null, "The number input is too high.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					int x1 = startPosX1.get(choosePosOf-1);
					int x2 = startPosX2.get(choosePosOf-1);
					int y1 = startPosY1.get(choosePosOf-1);
					int y2 = startPosY2.get(choosePosOf-1);
					for (int i = x1; i <= x2; i++) {
						for (int j = y1; j <= y2; j++) {
							fieldPanel[i][j].removeFG();
						}
					}
				}
			}
		});
		
		teams.add(chooseStartPos);
		teams.add(chooseStartPosOf);
		menuBar.add(teams);
		
		return menuBar;
	}
	
	private boolean save() {
		if (name == null) {
			return false;
		}
		int[] startPosx1 = new int[startPosX1.size()];
		int[] startPosy1 = new int[startPosY1.size()];
		int[] startPosx2 = new int[startPosX2.size()];
		int[] startPosy2 = new int[startPosY2.size()];
		for (int i = 0; i < startPosx1.length; i++) {
			startPosx1[i] = (int) startPosX1.get(i);
			startPosy1[i] = (int) startPosY1.get(i);
			startPosx2[i] = (int) startPosX2.get(i);
			startPosy2[i] = (int) startPosY2.get(i);
		}
		Map map = new Map(name, w, h, startPosx1, startPosy1, startPosx2, startPosy2);
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				map.add(fieldPanel[i][j].getByte());
			}
		}
		boolean finished = MapIO.write(map);
		if (!finished) {
			JOptionPane.showMessageDialog(null, "Map not saved.", "", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Map saved.", "", JOptionPane.INFORMATION_MESSAGE);
		}
		changed = !finished;
		return finished;
	}
	
	public static void main(String[] args) {
		new MapEditor();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		PicturePanel p = (PicturePanel) e.getComponent();
		if (mouseButtonClicked == 1) {
			p.setBackgroundImage(fieldList.get(rightField).getImage(), rightField);
			repaint();
		} else if (mouseButtonClicked == 3) {
			p.setBackgroundImage(fieldList.get(leftField).getImage(), leftField);
			repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		changed = true;
		if (choosePosOf == 0) {
			PicturePanel p = (PicturePanel) e.getComponent();
			if (e.getButton() == 1) {
				mouseButtonClicked = 1;
				p.setBackgroundImage(fieldList.get(rightField).getImage(), rightField);
				repaint();
			} else if (e.getButton() == 3) {
				mouseButtonClicked = 3;
				p.setBackgroundImage(fieldList.get(leftField).getImage(), leftField);
				repaint();
			}
		} else {
			((JPanel)e.getSource()).getParent().getMouseListeners()[0].mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (choosePosOf == 0) {
			mouseButtonClicked = 0;
		} else {
			((JPanel)e.getSource()).getParent().getMouseListeners()[0].mouseReleased(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		((JPanel)e.getSource()).getParent().getMouseMotionListeners()[0].mouseDragged(e);
	}
	
	class ButtonMouseListener implements MouseListener {
		byte b;
		public ButtonMouseListener(byte b) {
			this.b = b;
		}
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 3) {
				if (fields[b].getForeground().equals(Color.RED)) {
					return;
				}
				fields[leftField].setForeground(Color.BLACK);
				leftField = b;
				fields[leftField].setForeground(Color.BLUE);
			} else if (e.getButton() == 1) {
				if (fields[b].getForeground().equals(Color.BLUE)) {
					return;
				}
				fields[rightField].setForeground(Color.BLACK);
				rightField = b;
				fields[rightField].setForeground(Color.RED);
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (changed) {
			int selected = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (selected == 0) {
				if (save()) {
					System.exit(0);
				}
			} else if (selected == 1) {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
}
