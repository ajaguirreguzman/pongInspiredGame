/*************************************************************************
 *  Andres J. Aguirre G.
 *  February 25, 2013
 *
 *  Compilation:  javac main.java
 *  Execution:    java main
 *
 *  I can't remember how is the title of the game that I want to copy but it's like
 *  a tenis game.
 *
 *  Programming details
 *  -------
 *    For the draw and graphics, the Standard drawing library is used (see StdDraw.java). 
 *
 *  Key codes
 *  -------
 *    1 -> 49
 *    2 -> 50
 *       .
 *       .
 *       .
 *    8 -> 56
 *    9 -> 57
 *
 *************************************************************************/

import java.util.*;

public class main {
	
	public static void main(String argv[]) {
		
		// Dimensions of the window
		int xmin=0, xmax=1000, ymin=0, ymax=600;
		// Scale of the window
		StdDraw.setCanvasSize(xmax, ymax);
		StdDraw.setXscale(xmin, xmax);
		StdDraw.setYscale(ymin, ymax);
		
		// Ball
		double rB=5;                                             // Radius
		double xB=1, yB=1, xoB=(xmax+xmin)/2, yoB=(ymax+ymin)/2; // Initial position
		double vB=100, aB=0.2;                                   // Speed, Angle
		double vxB=vB*Math.cos(aB), vyB=vB*Math.sin(aB);		 // Components of the velocity
		
		// Bars
		double x1=30, y1=(ymax+ymin)/2;
		double xo1=x1-10, yo1=y1-100, xf1=x1+10, yf1=y1+100;
		double y2=y1;
		double xo2=xmax-xf1, yo2=yo1, xf2=xmax-xo1, yf2=yf1;
		
		// Points
		int n1=0, n2=0;
		
		// Time
		double t=0, dt=0.01; // Counter "time", Time step
		
		// Define random numbers generator
		Random generator = new Random();
		
		while (n1<5 && n2<5) {
			
			// Clean the window at each iteration
			StdDraw.clear(StdDraw.BLUE);
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledRectangle(0.5*(xmin+xmax), 0.5*(ymin+ymax), 0.5*(xmax-xmin), 0.5*(ymax-ymin));
			
			// Time and position
			t+=dt;
			xB = xoB + vxB*t;
			yB = yoB + vyB*t;
			
			// The y-cordinate of the bars is entered via keyboard
			if (StdDraw.hasNextKeyTyped()==true) {
				if (StdDraw.isKeyPressed(49)==true) y1=y1-1; // Number 1 is pressed
				if (StdDraw.isKeyPressed(50)==true) y1=y1+1; // Number 2 is pressed
				if (StdDraw.isKeyPressed(56)==true) y2=y2-1; // Number 8 is pressed
				if (StdDraw.isKeyPressed(57)==true) y2=y2+1; // Number 9 is pressed
			}
			
			// Invert direction in the top and bottom wall
			if (yB<ymin || yB>ymax) {
				t=0;
				xoB=xB;
				yoB=yB;
				vyB=-vyB;
			}
			
			// ball hist the paddles
			if (xB>xo1 && xB<xf1 && yB>yo1 && yB<yf1) {
				t=0;
				xoB=xB;
				yoB=yB;
				vxB=-vxB+10;    // increase x-component of velocity
				vyB=vyB*1.05;  // increase y-component of velocity
			}
			if (xB>xo2 && xB<xf2 && yB>yo2 && yB<yf2) {
				t=0;
				xoB=xB;
				yoB=yB;
				vxB=-vxB-10;    // increase x-component of velocity
				vyB=vyB*1.05;  // increase y-component of velocity
			}
			
			// Count a points ...
			if (xB>xmax) n1+=1;
			if (xB<xmin) n2+=1;
			
			// ... and reset the ball
			if (xB>xmax || xB<xmin) {
				t=0;
				xoB=(xmax+xmin)/2;
				yoB=(ymax+ymin)/2;

				// Random direction for the motion of the ball
				aB = 2*Math.PI*generator.nextDouble();
				vxB=vB*Math.cos(aB);
				vyB=vB*Math.sin(aB);
			}
			
			// Bars
			yo1=y1-100; yf1=y1+100;
			yo2=y2-100; yf2=y2+100;
			StdDraw.setPenColor(StdDraw.BLACK);
			double[] X1 = { xo1, xf1, xf1, xo1 };
			double[] Y1 = { yo1, yo1, yf1, yf1 };
			StdDraw.filledPolygon(X1, Y1);
			double[] X2 = { xo2, xf2, xf2, xo2 };
			double[] Y2 = { yo2, yo2, yf2, yf2 };
			StdDraw.filledPolygon(X2, Y2);
			
			// Ball
			StdDraw.filledCircle(xB, yB, rB);
			
			// display score
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.textRight((xmax+xmin)/2, ymax+10, (int)n1 + " - " + (int)n2);
			
			StdDraw.show(0);
		}
		
		// Winner
		if (n1==5) { // Wins player 1
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(0.75*(xmin+xmax), 0.5*(ymin+ymax), 0.25*(xmax-xmin), 0.5*(ymax-ymin));
			StdDraw.text(0.25*(xmin+xmax), 0.5*(ymin+ymax), "Winner!");
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(0.75*(xmin+xmax), 0.5*(ymin+ymax), "Loser!");
		}
		if (n2==5) { // Wins player 2
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(0.25*(xmin+xmax), 0.5*(ymin+ymax), 0.25*(xmax-xmin), 0.5*(ymax-ymin));
			StdDraw.text(0.75*(xmin+xmax), 0.5*(ymin+ymax), "Winner!");
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(0.25*(xmin+xmax), 0.5*(ymin+ymax), "Loser!");
		}
		StdDraw.show(0);
	}
}

