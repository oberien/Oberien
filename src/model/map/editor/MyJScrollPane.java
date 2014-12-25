package model.map.editor;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MyJScrollPane extends JScrollPane {
	public MyJScrollPane(JComponent c) {
		super(c);
		getVerticalScrollBar().setUnitIncrement(15);
		getHorizontalScrollBar().setUnitIncrement(15);
		MouseWheelListener[] l = getMouseWheelListeners();
		removeMouseWheelListener(l[0]);
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isShiftDown()) {
					JScrollBar h = getHorizontalScrollBar();
					if (e.getWheelRotation() == 1) {
						h.setValue(h.getValue() + h.getUnitIncrement()*e.getScrollAmount());
					} else {
						h.setValue(h.getValue() - h.getUnitIncrement()*e.getScrollAmount());
					}
					getVerticalScrollBar().setEnabled(true);
				} else {
					JScrollBar v = getVerticalScrollBar();
					if (e.getWheelRotation() == 1) {
						v.setValue(v.getValue() + v.getUnitIncrement()*e.getScrollAmount());
					} else {
						v.setValue(v.getValue() - v.getUnitIncrement()*e.getScrollAmount());
					}
				}
			}
		});
	}
}
