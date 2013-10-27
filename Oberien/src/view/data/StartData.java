package view.data;

import java.awt.Font;

import org.newdawn.slick.Image;

import controller.StateMap;

import model.map.Map;
import view.components.UIElements;
import view.renderer.*;

public class StartData {
	private StateMap sm;
	private Map map;
	private Image[] tiles;
    private Image[][] units;
    private MapRenderer mr;
    private FoWRenderer fowr;
	private ActionGroundRenderer agr;
    private HUDRenderer hudr;
    private UnitRenderer ur;
    private DamageRenderer dr;
    private Font font;
	private UIElements ui;

	public HUDRenderer getHudr() {
		return hudr;
	}

	public void setHudr(HUDRenderer hudr) {
		this.hudr = hudr;
	}

	public UnitRenderer getUr() {
		return ur;
	}

	public void setUr(UnitRenderer ur) {
		this.ur = ur;
	}

	public Image[] getTiles() {
		return tiles;
	}

	public void setTiles(Image[] tiles) {
		this.tiles = tiles;
	}

	public Image[][] getUnits() {
		return units;
	}

	public void setUnits(Image[][] units) {
		this.units = units;
	}
	

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public MapRenderer getMr() {
		return mr;
	}
	public void setMr(MapRenderer mr) {
		this.mr = mr;
	}

	public DamageRenderer getDr() {
		return dr;
	}

	public void setDr(DamageRenderer dr) {
		this.dr = dr;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public StateMap getSm() {
		return sm;
	}

	public void setSm(StateMap sm) {
		this.sm = sm;
	}

	public FoWRenderer getFowr() {
		return fowr;
	}

	public void setFowr(FoWRenderer fowr) {
		this.fowr = fowr;
	}

	public ActionGroundRenderer getAgr() {
		return agr;
	}

	public void setAgr(ActionGroundRenderer agr) {
		this.agr = agr;
	}
	
	public UIElements getUI() {
		return ui;
	}
	
	public void setUI(UIElements ui) {
		this.ui = ui;
	}
}
