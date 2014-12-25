package model.map;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class MapList {
	private static final MapList instance = new MapList();
	private HashMap<String, Map> maps = new HashMap<String, Map>();
	private Map currentMap;
	
	private MapList() {
		File[] files = new File("res/maps/").listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith(".map"))
					return true;
				else
					return false;
			}
		});
		for (int i = 0; i < files.length; i++) {
			Map map = MapIO.read(files[i]);
			if (map.getStartPosX1().length == 0) {
				JOptionPane.showMessageDialog(null, "[" + map.getName() + "] No start area for the teams defined. Please define them in the MapEditor.", "", JOptionPane.ERROR_MESSAGE);
			}
			maps.put(map.getName(), map);
		}
	}
	
	public static MapList getInstance() {
		return instance;
	}
	
	public String[] getMapNames() {
		Object[] o = maps.keySet().toArray();
		String[] s = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			s[i] = (String) o[i];
		}
		return s;
	}
	
	public Map getMap(String s) {
		currentMap = maps.get(s);
		return currentMap;
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
}
