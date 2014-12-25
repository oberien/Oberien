package model.map.editor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import logger.ErrorLogger;
import model.map.Map;
import model.map.MapIO;

public class MapConverter {
	public static void convert(File f) {
		Map newMap = null;
		String name = f.getName();
		int version;
		int width = 0;
		int height = 0;
		int[] startPosX1 = null;
		int[] startPosY1 = null;
		int[] startPosX2 = null;
		int[] startPosY2 = null;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			version = dis.readInt();
			if (version == 2) {
				JOptionPane.showMessageDialog(null, "Map is already the newest version.", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (version == 0) {
				dis.readUTF();
			}
			width = dis.readInt();
			height = dis.readInt();
			
			newMap = new Map(name, width, height, startPosX1, startPosY1, startPosX2, startPosY2);
			int end = (width) * (height);
			for (int j = 0; j < end; j++) {
				byte b = dis.readByte();
				if (version==0 || version==1) {
					if (b > 1) {
						b += 10;
					}
				}
				newMap.add(b);
			}
			dis.close();
			MapIO.write(newMap);
			JOptionPane.showMessageDialog(null, "Converting successful", "", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {ErrorLogger.logger.severe(e.getMessage());}
	}
}
