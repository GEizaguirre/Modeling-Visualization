/*
*
* Line drawer with Bresenham's algorithm.
*
* @author German Telmo Eizaguirre Suarez
* Modeling and Visualization
* Universitat Rovira i Virgili
 */

package cat.urv.deim.miv.laboratoris;

import cat.urv.deim.miv.Application;

public class Laboratori1 extends Application {

	public static final long serialVersionUID = 1L;
	
	public Laboratori1() {
		super("Laboratori 1");
	}

	public void paint() {

		int width = getPanelWidth();
		int height = getPanelHeight();

		int nlines = 10;
		int curWidth = 0;
		int curHeight = 0;

		for(int i = 0; i < nlines; i++) {

			setColor((float) i / nlines, 1.0f - (float) i / nlines, 0.0f);
			drawLine(0, curHeight, width - curWidth, 0);
			curWidth = width * i / nlines;
			curHeight = height * i / nlines;
		}
		// Tests.
		// We consider senses (top, bottom, left, right) are related to the positions of the screen.
		// Vertical line. Vertex from top to bottom.

		// Color: red.
		setColor(1f, 0f, 0f);
		defineDrawLine((int) (width * 0.95), (int) (height * 0.05), (int) (width * 0.95), (int) (height * 0.95));
		// Vertical line. Vertex from bottom to top.
		// Color: dark red.
		setColor(0.5f, 0f, 0f);
		defineDrawLine((int) (width * 0.98), (int) (height * 0.97), (int) (width * 0.98), (int) (height * 0.01));
		// Horizontal line. Vertex from left to right.
		// Color: green.
		setColor(0f, 1f, 0f);
		defineDrawLine((int) (width * 0.85), (int) (height * 0.85), (int) (width * 0.97), (int) (height * 0.85));
		// Horizontal line. Vertex from right to left.
		// Color: dark green.
		setColor(0f, 0.5f, 0f);
		defineDrawLine((int) (width * 0.99), (int) (height * 0.90), (int) (width * 0.88), (int) (height * 0.90));
		// Line with pendent = 1, vertex from left to right.
		// Color: orange.
		setColor(1f, 0.5f, 0f);
		defineDrawLine((int) (width * 0.7035), (int) (height * 0.918), (int) (width * 0.9065), (int) (height * 0.52));
		// Line with pendent = 1, vertex from right to left.
		// Color: dark brown.
		setColor(0.5f, 0.25f, 0f);
		defineDrawLine((int) (width * 0.805), (int) (height * 0.367), (int) (width * 0.625), (int) (height * 0.719));
		// Line with pendent < 1, vertex from left to right.
		// Color: cyan.
		setColor(0.5f, 0.5f, 1f);
		defineDrawLine((int) (width * 0.625), (int) (height * 0.719), (int) (width * 0.938), (int) (height * 0.367));
		// Line with pendent < 1, vertex from right to left.
		// Color: dark blue to cyan.
		setColor(0f, 0.25f, 0.5f);
		defineDrawLine((int) (width * 0.906), (int) (height * 0.52), (int) (width * 0.625), (int) (height * 0.918));
		// Line with pendent > 1, vertex from left to right.
		// Color: yellow.
		setColor(0.9f, 0.9f, 0f);
		defineDrawLine((int) (width * 0.625), (int) (height * 0.719), (int) (width * 0.844), (int) (height * 0));
		// Line with pendent > 1, vertex from right to left.
		// Color: dark yellow.
		setColor(0.7f, 0.7f, 0f);
		defineDrawLine((int) (width * 0.703), (int) (height * 1), (int) (width * 0.906), (int) (height * 0.52));
		// Unique point.
		// Color: Fucsia.
		setColor(0.941f, 0.071f, 0.745f);
		defineDrawLine((int) (width * 0.625), (int) (height * 0.306), (int) (width * 0.625), (int) (height * 0.306));
		// Line with pendent < 0, vertex from left to right.
		// Color: Purple.
		setColor(0.886f, 0.156f, 0.749f);
		defineDrawLine((int) (width * 0.47), (int) (height * 0.214), (int) (width * 0.656), (int) (height * 0.459));
		// Line with pendent < 0, vertex from right to left.
		// Color: Dark purple.
		setColor(0.305f, 0.152f, 0.278f);
		defineDrawLine((int) (width * 0.656), (int) (height * 0.581), (int) (width * 0.437), (int) (height * 0.275));

	}

	// Practica 1, implementa defineDrawLine
	// Hint: Pots utilitzar l'algorisme de Bresenham
	// Hint: Per dibuixar un punt a la pantalla utilitza el metode drawPoint(x, y);
	
	// Inici codi de l'alumne

	public void defineDrawLine(int x1, int y1, int x2, int y2) {

		// Useful values.
		int dy = Math.abs(y2 - y1);
		int dx = Math.abs(x2 - x1);
		int ddy = 2 * dy;
		int ddx = 2 * dx;
		int nx, ny;
		// Direction increments depend on the sense of the line.
		int incx = x1 < x2 ? 1 : -1; // increment direction
		int incy = y1 < y2 ? 1 : -1;

		// Decision element.
		int pk;

		// Control of horizontal lines.
		if ( dy == 0 ){
			while (true){
				drawPoint(x1, y1);
				if (x1==x2) break;
				x1+=incx;
			}
		}
		else {

			// Control of vertical lines.
			if ( dx == 0) {
				while (true){
					drawPoint(x1, y1);
					if (y1==y2) break;
					y1+=incy;
				}
			}
			// Rest of lines.
			else {
				// Pendent.
				float pend = ((float)dy / (float)dx);

				if ( pend == 1){
					while (true){
						drawPoint(x1, y1);
						if (x1==x2) break;
						y1+=incy;
						x1+=incx;
					}
				}
				else {
					// Procedure depends on the pendent value.
					if (pend > 1) {

						pk = ddx;

						drawPoint(x1, y1);

						while (y1 != y2) {

							y1 += incy;
							nx = pk > dy ? (x1 + incx) : x1;
							pk = pk + ddx - ddy * Math.abs(nx - x1);
							x1 = nx;
							drawPoint(x1, y1);
						}

					} else {

						pk = ddy;

						drawPoint(x1, y1);

						while (x1 != x2) {

							x1 += incx;
							ny = pk > dx ? (y1 + incy) : y1;
							pk = pk + ddy - ddx * Math.abs(ny - y1);
							y1 = ny;
							drawPoint(x1, y1);
						}
					}
				}
			}
		}

	}

	// Fi codi de l'alumne
	
	public static void main(String[] args) {
		Laboratori1 example = new Laboratori1();
		example.run();
	}

}
