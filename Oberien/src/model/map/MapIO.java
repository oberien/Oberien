package model.map;

import java.io.*;

import javax.swing.JOptionPane;

public class MapIO {
	
	public static Map read(File f) {
		Map newMap = null;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			String name = f.getName();
			int version = dis.readInt();
			if (version == 2) {
				int width = dis.readInt();
				int height = dis.readInt();
				int teamNumber = dis.readInt();
				int[] startPosX1 = new int[teamNumber];
				int[] startPosY1 = new int[teamNumber];
				int[] startPosX2 = new int[teamNumber];
				int[] startPosY2 = new int[teamNumber];
				for (int i = 0; i < teamNumber; i++) {
					startPosX1[i] = dis.readInt();
					startPosY1[i] = dis.readInt();
					startPosX2[i] = dis.readInt();
					startPosY2[i] = dis.readInt();
				}
				newMap = new Map(name, width, height, startPosX1, startPosY1, startPosX2, startPosY2);
				int end = (width) * (height);
				for (int j = 0; j < end; j++) {
					newMap.add(dis.readByte());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Map \"" + name + "\" is an older version. Please convert it with the MapEditor." , "Old Map Version", JOptionPane.ERROR_MESSAGE);
			}
			dis.close();
		} catch (IOException e) {e.printStackTrace();}
		return newMap;
	}
	
	public static boolean write(Map map) {
		try {
			File file = new File("res/maps/" + map.getName());
			if (file.exists()) {
				int selection = JOptionPane.showConfirmDialog(null, "Do you want to overwrite your map?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (selection != 0) {
					return false;
				}
			} else {
				file.createNewFile();
			}
			
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
			dos.writeInt(2);
			
			int w = map.getWidth();
			int h = map.getHeight();
			dos.writeInt(w);
			dos.writeInt(h);
			
			int[] startPosX1 = map.getStartPosX1();
			int[] startPosY1 = map.getStartPosY1();
			int[] startPosX2 = map.getStartPosX2();
			int[] startPosY2 = map.getStartPosY2();
			int teamNumber;
			if (startPosX1 == null) {
				teamNumber = 0;
			} else {
				teamNumber = startPosX1.length;
			}
			dos.writeInt(teamNumber);
			for (int i = 0; i < teamNumber; i++) {
				dos.writeInt(startPosX1[i]);
				dos.writeInt(startPosY1[i]);
				dos.writeInt(startPosX2[i]);
				dos.writeInt(startPosY2[i]);
			}
			
			for (int j = 0; j < h; j++) {
				for (int i = 0; i < w; i++) {
					dos.writeByte(map.get(i, j));
				}
			}
			dos.close();
		} catch (IOException e) {e.printStackTrace(); return false;}
		return true;
	}
}
