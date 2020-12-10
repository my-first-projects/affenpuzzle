package main;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;

@SuppressWarnings("serial")
public class AffenPuzzle extends JPanel implements Runnable {

	static JFrame myFrame;
	static int n = 3;
	static Karte[][] k = new Karte[n][n];
	static Karte[][] solving = new Karte[n][n];
	static boolean solved = false;
	static long swaps = 0;
	static long max = 1;
	static long start = 0;
	static String status = "";

	public AffenPuzzle(int w, int h) {
		initGame();

		this.setPreferredSize(new Dimension(w, h));
		setBackground(Color.WHITE);
		myFrame = new JFrame("Affenpuzzle");
		myFrame.setLocation(300, 100);
		myFrame.setResizable(false);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.add(this);
		myFrame.pack();
		myFrame.setVisible(true);

		Thread th = new Thread(this);
		th.start();
		
		// test
		/*
		for(int i = 0; i < 100; i++) {
			swap((int)(Math.random()*3), (int)(Math.random()*3), (int)(Math.random()*3), (int)(Math.random()*3));
			k[(int)(Math.random()*3)][(int)(Math.random()*3)].rotate(1);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		SolvingThread sth = new SolvingThread();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sth.start();
	}

	static public void initGame() {
		/*
		k[0][0] = new Karte(new int[] { 1, 2, 3, 4 });
		k[0][1] = new Karte(new int[] { 3, 1, 4, 1 });
		k[0][2] = new Karte(new int[] { 4, 2, 3, 2 });
		k[1][0] = new Karte(new int[] { 2, 4, 3, 3 });
		k[1][1] = new Karte(new int[] { 3, 1, 4, 2 });
		k[1][2] = new Karte(new int[] { 4, 2, 1, 3 });
		k[2][0] = new Karte(new int[] { 3, 3, 1, 2 });
		k[2][1] = new Karte(new int[] { 1, 2, 4, 4 });
		k[2][2] = new Karte(new int[] { 4, 3, 4, 1 });
		
		max = (long) (Math.pow(4, 9)*362880);
		max /= 4;
		System.out.println(max);

		for (int i = 0; i < solving.length; i++)
			for (int j = 0; j < solving[i].length; j++)
				solving[i][j] = new Karte(new int[] { 5, 5, 5, 5 });*/
		reset();
	}
	
	private static long fak(int n) {
		long ret = 1;
		for(int i = 1; i < n; i++)
			ret *= i;
		return ret;
	}
	
	static void reset() {
		k[0][0] = new Karte(new int[] { 1, 2, 3, 4 });
		k[0][1] = new Karte(new int[] { 3, 1, 4, 1 });
		k[0][2] = new Karte(new int[] { 4, 2, 3, 2 });
		k[1][0] = new Karte(new int[] { 2, 4, 3, 3 });
		k[1][1] = new Karte(new int[] { 3, 1, 4, 2 });
		k[1][2] = new Karte(new int[] { 4, 2, 1, 3 });
		k[2][0] = new Karte(new int[] { 3, 3, 1, 2 });
		k[2][1] = new Karte(new int[] { 1, 2, 4, 4 });
		k[2][2] = new Karte(new int[] { 4, 3, 4, 1 });
		for (int i = 0; i < solving.length; i++)
			for (int j = 0; j < solving[i].length; j++)
				solving[i][j] = new Karte(new int[] { 5, 5, 5, 5 });
		for(int i = 0; i < 10; i++) {
			swap((int)(Math.random()*3), (int)(Math.random()*3), (int)(Math.random()*3), (int)(Math.random()*3));
			k[(int)(Math.random()*3)][(int)(Math.random()*3)].rotate(1);
			try {
				Thread.sleep(10); // TODO
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static public void swap(int x1, int y1, int x2, int y2) {
		Karte temp = k[x1][y1].copy();
		k[x1][y1] = k[x2][y2].copy();
		k[x2][y2] = temp.copy();
	}

	static public void swaps(int x1, int y1, int x2, int y2) {
		//swaps++;
		Karte temp = k[x1][y1].copy();
		k[x1][y1] = solving[x2][y2].copy();
		solving[x2][y2] = temp.copy();
	}

	static public boolean isSolved() {
		
		boolean full = true;
		for (int x = 0; x < solving.length; x++) 
			for (int y = 0; y < solving[x].length; y++) 
				if(solving[x][y].isBlank()) {
					full = false;
					break;
				}
		if(full) 
			swaps++;
		else 
			return false;
		
		for (int x = 0; x < solving.length; x++) {
			for (int y = 0; y < solving[x].length; y++) {
				if (x > 0 && x < solving[x].length - 0) {
					if (solving[x][y].getRight() != solving[x - 1][y].getLeft()) {
						return false;
					}
				}
				if (y < solving.length - 1) {
					if (solving[x][y].getBottom() != solving[x][y + 1].getTop()) {
						return false;
					}
				}
			}
		}
		System.out.println("sleep");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void run() {
		while (myFrame.isVisible()) {
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Stapel", 250, 640);
		g.drawString("Ziel", 950, 640);
		/*float fortschritt = (float)((double)(double)swaps/((double)max))*100;
		g.drawString("Fortschritt: ", 15, 675);
		g.drawString(fortschritt + "%", 175, 675);
		long time = System.currentTimeMillis() - start;
		long verbleibend = (long) ((100/fortschritt) * time);
		verbleibend -= time * fortschritt;
		g.drawString("Übrig: ", 15, 710);
		g.drawString(verbleibend/1000/60 + " Minuten | " + verbleibend/1000/60/60 + " Stunden", 175, 710);
		
		Date d = new java.util.Date(System.currentTimeMillis() + verbleibend);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy | HH:mm:ss");
		
		g.drawString("Zeitpunkt: ", 15, 745);
		//g.drawString(d.getTime() + "." + d.getMonth() + "." + d.getYear() + " | " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds(), 150, 745);
		g.drawString(sdf.format(d), 175, 745);*/
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		
		// links
		for (int i = 0; i < k.length; i++)
			for (int j = 2; j >= 0; j--)
				k[j][i].draw(g, (2 - j) * k[j][i].getSize(), i * k[j][i].getSize(), j, i);

		// rechts
		for (int i = 0; i < k.length; i++)
			for (int j = 2; j >= 0; j--)
				solving[j][i].draw(g, (2 - j) * solving[j][i].getSize() + 650, i * solving[j][i].getSize(), j, i);
	}

	public static void main(String[] args) {
		new AffenPuzzle(1250, 800);
	}
}