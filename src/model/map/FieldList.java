package model.map;

import model.Type;

public class FieldList {
	private static final FieldList instance = new FieldList();
	Field[] fields = new Field[256];
	
	private FieldList() {
		fields[0] = new Field("Plain Land", 0, 0, 2, 1, 0, 1, 0, 0);
		fields[1] = new Field("Road lr", 0, 0, 1, 1, 0, 1, 0, 1);
		fields[2] = new Field("Road ud", 0, 0, 1, 1, 0, 1, 0, 2);
		fields[3] = new Field("Curve ld", 0, 0, 1, 1, 0, 1, 0, 3);
		fields[4] = new Field("Curve ul", 0, 0, 1, 1, 0, 1, 0, 4);
		fields[5] = new Field("Curve ur", 0, 0, 1, 1, 0, 1, 0, 5);
		fields[6] = new Field("Curve rd", 0, 0, 1, 1, 0, 1, 0, 6);
		fields[7] = new Field("Junction tld", 0, 0, 1, 1, 0, 1, 0, 7);
		fields[8] = new Field("Junction ltr", 0, 0, 1, 1, 0, 1, 0, 8);
		fields[9] = new Field("Junction trd", 0, 0, 1, 1, 0, 1, 0, 9);
		fields[10] = new Field("Junction rdl", 0, 0, 1, 1, 0, 1, 0, 10);
		fields[11] = new Field("Cross", 0, 0, 1, 1, 0, 1, 0, 11);
		fields[12] = new Field("Sand", -5, -5, 3, 2, 0, 1, -1, 12);
		fields[13] = new Field("Mountain", -10, 25, 5, 5, 5, 3, 3, 13);
		fields[14] = new Field("Water", 0, 0, 1, 1, 0, 1, 0, 14);
		fields[15] = new Field("Deep Water", 0, -5, 2, 1, 0, 1, 0, 15);
		fields[16] = new Field("Forest", 20, -10, 3, 3, -2, 2, -2, 16);
		fields[17] = new Field("High Grass", 5, -5, 2, 2, 0, 2, -1, 17);
		fields[18] = new Field("Rift", 0, 0, 1, 1, 0, 1, 0, 18);
	}
	
	public static FieldList getInstance() {
		return instance;
	}
	
	public Field get(int i) {
		return fields[i];
	}

	public byte[] getCanPass(Type type) {
		if (type == Type.Infantry || type == Type.Robot || type == Type.Spider) {
			return new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,16,17};
		} else if (type == Type.Tank || type == Type.LightVehicle) {
			return new byte[]{0,1,12,17};
		} else if (type == Type.Ship) {
			return new byte[]{14,15};
		} else if (type == Type.Builder) {
			return new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
		} else {
			return new byte[]{};
		}
	}
}
