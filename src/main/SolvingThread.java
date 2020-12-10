package main;

public class SolvingThread extends Thread implements Runnable {

	private static Karte[][] k, solving;

	public void run() {
		k = AffenPuzzle.k;
		solving = AffenPuzzle.solving;

		while(true) {
			AffenPuzzle.reset();
			System.out.println(nextField(0, 0));

			try {
				Thread.sleep(100); // TODO
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	int counter = 0;

	boolean nextField(int x, int y) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < k.length; i++) {
			for (int j = 2; j >= 0; j--) {
				if (!k[i][j].isBlank()) {
					if (nextRotation(x, y, i, j, 0))
						return true;
					if (nextRotation(x, y, i, j, 1))
						return true;
					if (nextRotation(x, y, i, j, 2))
						return true;
					if (nextRotation(x, y, i, j, 3))
						return true;
				}
			}
		}
		return false;
	}

	boolean nextRotation(int x, int y, int i, int j, int rotation) {
		
		solving[x][y].rotate(rotation);
		// AffenPuzzle.swaps++;
		AffenPuzzle.swaps(i, j, x, y);
		if(!isCorrect(x,  y)) {
			solving[x][y].rotate(4 - rotation);
			AffenPuzzle.swaps(i, j, x, y);
			return false;
		}
		nextField(plusplus(x, y)[0], plusplus(x, y)[1]);
		if (AffenPuzzle.isSolved())
			return true;
		solving[x][y].rotate(4 - rotation);
		AffenPuzzle.swaps(i, j, x, y);
		return false;
	}
	
	private boolean isCorrect(int x, int y) {
		if(x == 1 && y == 0) {
			return (solving[x-1][y].getLeft() == solving[x][y].getRight());
		}else if ( x == 2 && y == 0) {
			return (solving[x-1][y].getLeft() == solving[x][y].getRight());
		}else if ( x == 0 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop());
		}else if ( x == 1 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop() && solving[x-1][y].getLeft() == solving[x][y].getRight());
		}else if ( x == 2 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop() && solving[x-1][y].getLeft() == solving[x][y].getRight());
		}else if ( x == 0 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop());
		}else if ( x == 1 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop() && solving[x-1][y].getLeft() == solving[x][y].getRight());
		}else if ( x == 2 && y == 1) {
			return (solving[x][y-1].getBottom() == solving[x][y].getTop() && solving[x-1][y].getLeft() == solving[x][y].getRight());
		}
		
		return true;
	}

	private int[] plusplus(int x, int y) {
		if (x == 2) {
			x = 0;
			y++;
		} else {
			if (x == 2) {
				x = 0;
			} else {
				x++;
			}
		}

		return new int[] { x, y };
	}

}
