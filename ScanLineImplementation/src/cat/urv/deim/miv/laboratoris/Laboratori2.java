package cat.urv.deim.miv.laboratoris;

import cat.urv.deim.miv.Application;

import java.util.Arrays;
import java.util.Collections;

public class Laboratori2 extends Application {

	public static final long serialVersionUID = 1L;
	
	public Laboratori2() {
		super("Laboratori 2");
	}

	public void paint() {
		int width = getPanelWidth();
		int height = getPanelHeight();

		setColor(1.0f, 0.0f, 0.0f);
		fillPolygon(
				(int) (0.2f * width), (int) (0.6f * height),
				(int) (0.3f * width), (int) (0.1f * height),
				(int) (0.8f * width), (int) (0.4f * height),
				(int) (0.7f * width), (int) (0.7f * height),
				(int) (0.4f * width), (int) (0.9f * height));

		//setColor(0.0f, 1.0f, 0.0f);
		setColor(0.0f, 0.0f, 0.0f);
		drawPolygon(
				(int) (0.2f * width), (int) (0.6f * height),
				(int) (0.3f * width), (int) (0.1f * height),
				(int) (0.8f * width), (int) (0.4f * height),
				(int) (0.7f * width), (int) (0.7f * height),
				(int) (0.4f * width), (int) (0.9f * height));

		testGermanBasic();
		testGermanAdvanced();
	}

	public void testGermanBasic() {

		// 640 * 327
		int width = getPanelWidth();
		int height = getPanelHeight();

		// Square (horizontal lines).
		// Color: blues.
		setColor(0.674f, 0.905f, 0.862f);
		fillPolygon(
				(int) (0.95f * width), (int) (0.05f * height),
				(int) (0.95f * width), (int) (0.15f * height),
				(int) (0.85f * width), (int) (0.15f * height),
				(int) (0.85f * width), (int) (0.05f * height));

		setColor(0.066f,0.125f, 0.635f);
		drawPolygon(
				(int) (0.95f * width), (int) (0.05f * height),
				(int) (0.95f * width), (int) (0.15f * height),
				(int) (0.85f * width), (int) (0.15f * height),
				(int) (0.85f * width), (int) (0.05f * height));

		// Triangle.
		// Color: Greens
		setColor(0.482f, 0.886f, 0.094f);
		fillPolygon(
				(int) (0.15f * width), (int) (0.05f * height),
				(int) (0.20f * width), (int) (0.35f * height),
				(int) (0.05f * width), (int) (0.40f * height));

		setColor(0.196f, 0.317f, 0.082f);
		drawPolygon(
				(int) (0.15f * width), (int) (0.05f * height),
				(int) (0.20f * width), (int) (0.35f * height),
				(int) (0.05f * width), (int) (0.40f * height));


		// Convex polygon without holes, 7 sides.
		// Color: Yellows.
		setColor(0.95f, 0.95f, 0.08f);
		fillPolygon(
				(int) (0.12f * width), (int) (0.73f * height),
				(int) (0.20f * width), (int) (0.76f * height),
				(int) (0.20f * width), (int) (0.86f * height),
				(int) (0.18f * width), (int) (0.91f * height),
				(int) (0.14f * width), (int) (0.95f * height),
				(int) (0.08f * width), (int) (0.9f * height),
				(int) (0.02f * width), (int) (0.8f * height),
				(int) (0.05f * width), (int) (0.78f * height));

		setColor(0.6f, 0.6f, 0.08f);
		drawPolygon(
				(int) (0.12f * width), (int) (0.73f * height),
				(int) (0.20f * width), (int) (0.76f * height),
				(int) (0.20f * width), (int) (0.86f * height),
				(int) (0.18f * width), (int) (0.91f * height),
				(int) (0.14f * width), (int) (0.95f * height),
				(int) (0.08f * width), (int) (0.9f * height),
				(int) (0.02f * width), (int) (0.8f * height),
				(int) (0.05f * width), (int) (0.78f * height));


	}

	public void testGermanAdvanced() {

		// 640 * 327
		int width = getPanelWidth();
		int height = getPanelHeight();

		// Concave polygon (sides).
		// Color: orange.
		setColor(1f, 0.2f, 0);
		fillPolygon(
				(int) (0.12f * width), (int) (0.73f * height),
				(int) (0.28f * width), (int) (0.76f * height),
				(int) (0.15f * width), (int) (0.86f * height),
				(int) (0.18f * width), (int) (0.91f * height),
				(int) (0.10f * width), (int) (0.88f * height),
				(int) (0.11f * width), (int) (0.8f * height),
				(int) (0.05f * width), (int) (0.78f * height));

		setColor(0.4f, 0, 0);
		drawPolygon(
				(int) (0.12f * width), (int) (0.73f * height),
				(int) (0.28f * width), (int) (0.76f * height),
				(int) (0.15f * width), (int) (0.86f * height),
				(int) (0.18f * width), (int) (0.91f * height),
				(int) (0.10f * width), (int) (0.88f * height),
				(int) (0.11f * width), (int) (0.8f * height),
				(int) (0.05f * width), (int) (0.78f * height));

		// Concave polygon (top and bottom)
		// Color: Blues.
		setColor(0f, 0.1f, 0.7f);
		fillPolygon(
				(int) (0.18f * width), (int) (0.02f * height),
				(int) (0.21f * width), (int) (0.1f * height),
				(int) (0.25f * width), (int) (0.01f * height),
				(int) (0.3f * width), (int) (0.2f * height),
				(int) (0.27f * width), (int) (0.4f * height),
				(int) (0.24f * width), (int) (0.22f * height),
				(int) (0.19f * width), (int) (0.35f * height),
				(int) (0.17f * width), (int) (0.3f * height));

		setColor(0f, 0, 0.2f);
		drawPolygon(
				(int) (0.18f * width), (int) (0.02f * height),
				(int) (0.21f * width), (int) (0.1f * height),
				(int) (0.25f * width), (int) (0.01f * height),
				(int) (0.3f * width), (int) (0.2f * height),
				(int) (0.27f * width), (int) (0.4f * height),
				(int) (0.24f * width), (int) (0.22f * height),
				(int) (0.19f * width), (int) (0.35f * height),
				(int) (0.17f * width), (int) (0.3f * height));


		// Polygon (triangle) with one hole.
		// Color: indigo.
		setColor(0.5f, 0, 1);
		fillPolygon(
				(int) (0.5f * width), (int) (0.3f * height),
				(int) (0.75f * width), (int) (0.6f * height),
				(int) (0.25f * width), (int) (0.6f * height),
				(int) (0.5f * width), (int) (0.3f * height),
				(int) (0.5f * width), (int) (0.4f * height),
				(int) (0.65f * width), (int) (0.5f * height),
				(int) (0.35f * width), (int) (0.5f * height),
				(int) (0.5f * width), (int) (0.4f * height));

		setColor(0.2f, 0, 0.6f);
		drawPolygon(
				(int) (0.5f * width), (int) (0.3f * height),
				(int) (0.75f * width), (int) (0.6f * height),
				(int) (0.25f * width), (int) (0.6f * height),
				(int) (0.5f * width), (int) (0.3f * height),
				(int) (0.5f * width), (int) (0.4f * height),
				(int) (0.65f * width), (int) (0.5f * height),
				(int) (0.35f * width), (int) (0.5f * height),
				(int) (0.5f * width), (int) (0.4f * height));

		// Concave polygon with various holes.
		// Color: Brown.
		setColor(0.6f, 0.2f, 0);
		drawPolygon(
				(int) (0.65f * width), (int) (0.75f * height),
				(int) (0.75f * width), (int) (0.90f * height),
				(int) (0.70f * width), (int) (0.91f * height),
				(int) (0.80f * width), (int) (0.95f * height),
				(int) (0.90f * width), (int) (0.87f * height),
				(int) (0.94f * width), (int) (0.78f * height),

				(int) (0.65f * width), (int) (0.75f * height),

				(int) (0.7f * width), (int) (0.80f * height),
				(int) (0.78f * width), (int) (0.86f * height),
				(int) (0.75f * width), (int) (0.88f * height),
				(int) (0.72f * width), (int) (0.82f * height),
				(int) (0.7f * width), (int) (0.80f * height),

				(int) (0.65f * width), (int) (0.75f * height),

				(int) (0.84f * width), (int) (0.80f * height),
				(int) (0.88f * width), (int) (0.78f * height),
				(int) (0.90f * width), (int) (0.81f * height),
				(int) (0.85f * width), (int) (0.84f * height),
				(int) (0.84f * width), (int) (0.80f * height),

				(int) (0.65f * width), (int) (0.75f * height));

		setColor(0.8f, 0.45f, 0);
		fillPolygon(
				(int) (0.65f * width), (int) (0.75f * height),
				(int) (0.75f * width), (int) (0.90f * height),
				(int) (0.70f * width), (int) (0.91f * height),
				(int) (0.80f * width), (int) (0.95f * height),
				(int) (0.90f * width), (int) (0.87f * height),
				(int) (0.94f * width), (int) (0.78f * height),

				(int) (0.65f * width), (int) (0.75f * height),

				(int) (0.7f * width), (int) (0.80f * height),
				(int) (0.78f * width), (int) (0.86f * height),
				(int) (0.75f * width), (int) (0.88f * height),
				(int) (0.72f * width), (int) (0.82f * height),
				(int) (0.7f * width), (int) (0.80f * height),

				(int) (0.65f * width), (int) (0.75f * height),

				(int) (0.84f * width), (int) (0.80f * height),
				(int) (0.88f * width), (int) (0.78f * height),
				(int) (0.90f * width), (int) (0.81f * height),
				(int) (0.85f * width), (int) (0.84f * height),
				(int) (0.84f * width), (int) (0.80f * height),

				(int) (0.65f * width), (int) (0.75f * height));

	}


	// Practica 2, implementa defineDrawPolygon i defineFillPolygon
	// Hint: Pots utilitzar l'algorisme Scan line fill polygon
	
	// Inici codi de l'alumne

	/*
	Test if a coupled coordinate-saving structure (an array for x and
	an array for y) contains a certain coordinate.
	 */
	public Boolean containCoord (int[] coordx, int[] coordy, int x1, int y1, int nelem){
		for (int i = 0; i < nelem; i++){
			if (coordx[i]==x1 && coordy[i]==y1) return true;
		}
		return false;
	}

	/*
	For drawing a holed polygon:
	Being the points of an external ring: (E1, E2, ... ,En)
	and the points of the internal ring: (I1, I2, ..., In)
	then, the polygon must be specified as
	(E1, E2, ..., En, E1, I1, I2, ..., In, I1).
	Equally, for multiple holes, repeat the process returning
	to a external point for each hole.
	Uses drawLine to draw the edges.
	 */
	public void defineDrawPolygon(Integer... p) {

		// Coordinate insertion control.
		if ( p.length < 6 )
			System.out.println("ERROR: defineDrawPolygon must at least receive 3 coordinate pairs.");
		if ( p.length % 2 != 0 )
			System.out.println("ERROR: defineDrawPolygon argument number must be pair.");

		int ncoord = 0;
		int [] pointx = new int[p.length/2];
		int [] pointy = new int[p.length/2];

		int x1, y1, x2, y2;

		// Group the lines into pairs of coordinates.
		for ( int i = 0; i < ( p.length - 2 ); i+=2 ){
			x1 = p[i];
			y1 = p[i+1];
			x2 = p[i+2];
			y2 = p[i+3];

			// Compulsory control for holes inside polygons.
			// As a symbolic "hallway into the hole" is created,
			// it also has to be ignored.
			if (!containCoord(pointx, pointy, x1, y1, ncoord))
				drawLine( x1, y1, x2, y2 );

			pointx[ncoord] = x1;
			pointy[ncoord] = y1;
			ncoord++;
		}

		// Draw last line to close the polygon.
		x1 = p[p.length-2];
		y1 = p[p.length-1];
		x2 = p[0];
		y2 = p[1];

		if (!containCoord(pointx, pointy, x1, y1, ncoord))
			drawLine( x1, y1, x2, y2 );

	}

	/*
	Sort an array with the Bubble sort algorithm and
	cut the surplus elements.
	 */
	public static int[] BubbleSort(int[] input, int nelem) {

		int[] intx = Arrays.copyOfRange(input, 0, nelem);
		int temp;
		boolean is_sorted;

		for (int i = 0; i < nelem; i++) {

			is_sorted = true;

			for (int j = 1; j < (nelem - i); j++) {

				if (intx[j - 1] > intx[j]) {
					temp = intx[j - 1];
					intx[j - 1] = intx[j];
					intx[j] = temp;
					is_sorted = false;
				}

			}
			if (is_sorted) break;

		}
		return intx;

	}

	/*
	Test if an integer array contains an element.
	 */
	public int arrayContains (int [] array, int nelem, int search){
		for (int i = 0; i < nelem; i++){
			if ( array[i] == search ) return i;
		}
		return -1;
	}

	/*
	Fill a polygon with colour.
	Uses drawPoint to draw pixels inside the polygon.
	 */
	public void defineFillPolygon (Integer... p) {
		// TODO: has de ficar aqui el codi!

		if (p.length < 6)
			System.out.println("ERROR: defineFillPolygon must at least receive 3 coordinate pairs.");
		if (p.length % 2 != 0)
			System.out.println("ERROR: defineFillPolygon argument number must be pair.");

		// Define the maximum and minimum boundaries.
		int[] coordy = new int[p.length / 2];
		int maxy = Integer.MIN_VALUE;
		int miny = Integer.MAX_VALUE;
		for (int i = 1; i < (p.length); i += 2) {
			if (p[i] > maxy) maxy = p[i];
			if (p[i] < miny) miny = p[i];
		}
		// For each scan line.
		// Structures for accumulating intersection points.
		int[] intx = new int[p.length / 2];
		// Save y-s sense line for each vertex.
		// pend = 1, ys go up from vertex.
		// pend = -1, ys go down from vertex.
		// pend = 0, intersection is not vertex.
		int[] pend = new int[p.length / 2];
		int [] cutintx;
		int intercounter, aux;
		int x1, x2, y1, y2;
		float dx, dy, xcalc;
		int newinterx;
		for (int i = maxy; i >= miny; i--) {

			intercounter = 0;
			// for each coordinate in pairs.
			for (int points = 0; points < (p.length - 2); points += 2) {
				x1 = p[points];
				y1 = p[points + 1];
				x2 = p[points + 2];
				y2 = p[points + 3];

				dx = x2 - x1;
				dy = y2 - y1;

				// Do not draw horizontal lines.
				if ( dy == 0 ) continue;
				if ((y1 <= i && y2 > i) || (y2 <= i && y1 > i) || y1 == i ||  y2 == i) {
					xcalc = x1 + dx / dy * (i - y1);
					newinterx = (int) Math.round(xcalc);

					// Add mid-line intersections automatically.
					if (newinterx != x1 && newinterx != x2){
						pend[intercounter] = 0;
						intx[intercounter] = newinterx;
						intercounter++;
					}
					else {
						int ycal, yres;
						// With the ys direction value we will manage concave vertex,
						// inner holes etc.
						ycal = (newinterx == x1) ? (y2 - y1) : ( (y1 -y2));
						if (x1 == x2) yres = 2;
						else if (ycal > 0) yres = 1;
						else if (ycal < 0) yres = -1;
						else yres = 0;
						// Check if the intersection has been added before.
						aux = arrayContains(intx, intercounter, newinterx);

						// If the intersection has been added before, check if ys direction was different.
						// Side concave vertex (different ys directions in both lines) will count as
						// a unique intersection.
						// Top/Bottom concave vertex (different ys directions in both lines) will count
						// as two intersections.
						if (aux != -1) {
							if (pend[aux] == yres && yres != 0) {
								pend[intercounter] = yres;
								intx[intercounter] = newinterx;
								intercounter++;
							}
						} else {
							pend[intercounter] = yres;
							intx[intercounter] = newinterx;
							intercounter++;
						}
					}
				}
			}

			// Last scan line.
			x1 = p[p.length - 2];
			y1 = p[p.length - 1];
			x2 = p[0];
			y2 = p[1];
			dx = x2 - x1;
			dy = y2 - y1;

			if ((y1 <= i && y2 > i) || (y2 <= i && y1 > i) ||  y1 == i ||  y2 == i) {
				if ( dy == 0 ) continue;
				xcalc = x1 + dx / dy * (i - y1);
				newinterx = (int) Math.round(xcalc);

				if (newinterx != x1 && newinterx != x2){
					pend[intercounter] = 0;
					intx[intercounter] = newinterx;
					intercounter++;
				}
				else {
					int ycal, yres;
					ycal = (newinterx == x1) ? (y2 - y1) : ((y1 - y2));
					if (x1 == x2) yres = 2;
					else if (ycal > 0) yres = 1;
					else if (ycal < 0) yres = -1;
					else yres = 0;
					aux = arrayContains(intx, intercounter, newinterx);

					if (aux != -1) {

						if (pend[aux] == yres && yres != 0) {
							pend[intercounter] = yres;
							intx[intercounter] = newinterx;
							intercounter++;
						}
					} else {
						pend[intercounter] = yres;
						intx[intercounter] = newinterx;
						intercounter++;
					}
				}
			}

			//Sort intersection points.
			cutintx = BubbleSort(intx, intercounter);

			// Print regions between intersection points (in couples).
			for (int dcounter = 0; dcounter < intercounter-1; dcounter += 2) {
				for (int xpaint = cutintx[dcounter]; xpaint < cutintx[dcounter+1]; xpaint++) {
					drawPoint(xpaint, i);
				}
			}
		}
	}

	// Fi codi de l'alumne
	
	public static void main(String[] args) {
		Laboratori2 example = new Laboratori2();
		example.run();
	}

}
