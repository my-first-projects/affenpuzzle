package main;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 04.12.2018
 * @author
 */

public class Karte {

	// Anfang Attribute
	private int[] kanten;
	private int size = 200;
	int size_2 = size / 2;
	int size_4 = size / 4;

	// Ende Attribute

	public Karte(int[] pKanten) {
		this.kanten = pKanten;
	}

	// Anfang Methoden
	public int[] getKanten() {
		return kanten;
	}
	
	public boolean isBlank() {
		return kanten[0] == 5;
	}

	public void draw(Graphics g, int x, int y, int index1, int index2) {
		
		// oben
		g.setColor(getColor(kanten[0]));
		g.fillRect(x + size_4, y, size_2, size_4);

		// rechts
		g.setColor(getColor(kanten[1]));
		g.fillRect(x + size_4 * 3, y + size_4, size_4, size_2);

		// unten
		g.setColor(getColor(kanten[2]));
		g.fillRect(x + size_4, y + size_4 * 3, size_2, size_4);

		// rechts
		g.setColor(getColor(kanten[3]));
		g.fillRect(x , y + size_4, size_4, size_2);
		
		// kasten
		g.setColor(Color.BLACK);
		g.drawRect(x, y, size, size);
		
		// index
		g.drawString("" + ++index1 + " | " + ++index2, x + size_2, y + size_2);
	}

	private Color getColor(int pc) {
		switch (pc) {
		case 1:
			return Color.BLUE;
		case 2:
			return Color.RED;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.YELLOW;
		}
		return new Color(230, 230, 230);
	}

	public Karte copy() {
		Karte k = new Karte(new int[] {0, 0, 0, 0});
		for(int i = 0; i < kanten.length; i++)
			k.kanten[i] = kanten[i];
		return k;
	}
	
	void rotate(int anzahl) {
		for(int i = 0; i < anzahl; i++) {
			int temp = kanten[0];
			kanten[0] = kanten[1];
			kanten[1] = kanten[2];
			kanten[2] = kanten[3];
			kanten[3] = temp;
		}
	}
	
	public int getTop() {
		return kanten[0];
	}
	public int getRight() {
		return kanten[1];
	}
	public int getBottom() {
		return kanten[2];
	}
	public int getLeft() {
		return kanten[3];
	}
	
	public int getSize() {
		return size;
	}

	// Ende Methoden
} // end of Karte
