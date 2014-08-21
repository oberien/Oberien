package tests;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.map.MapIO;
import model.map.MapList;

public class TestMain {
	public static void main(String[] args) {
		String[] mapNames = MapList.getInstance().getMapNames();
		for (String s : mapNames) {
			MapIO.write(MapList.getInstance().getMap(s), true);
		}
		
//		for (int i = 0; i < 19; i++) {
//			StringBuilder field;
//			if (i == 0) {
//				field = new StringBuilder("000");
//			} else {
//				int digits = (int)Math.log10((double)i);
//				field = new StringBuilder();
//				for (int j = 2-digits; j > 0; j--) {
//					field.append("0");
//				}
//				field.append(i);
//			}
//			
//			try {
//				Image image = ImageIO.read(new File("res/imgs/tiles/" + field.toString() + ".png"));
//				BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
//				bi.getGraphics().drawImage(image, 0, 0, 32, 32, 1, 1, 32, 32, null);
//				ImageIO.write(bi, "png", new File("res/imgs/tiles/" + field.toString() + ".png"));
//			} catch (IOException e) {e.printStackTrace();}
//		}
	}
}
