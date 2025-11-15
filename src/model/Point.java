package model;

import java.lang.Math;

public class Point {
	

	private double x;
	private double y;
	
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/***
	 * Erstellt einen Richtungsvektor aus zwei gegebenen Punkten.<br><br>
 	 * 
	 * 1. Berechne den Richtungsvektor von P1 nach P2
	 * 
	 * @param p1 Punkt 1
	 * @param p2 Punkt 2
	 * @return der Richtungsvektor non P1 nach P2
	 */
	public static Point getDirVector(Point p1, Point p2) {
		Point r = new Point( (p2.x - p1.x) , (p2.y - p1.y));
		return r;
	}
	
	
	/***
	 * Berchnet die Laenge eines Vektors. <br><br>
	 * 
	 * 1. Berechne die Laenge des Vektors mit Hilfe von Pythagoras
	 * 
	 * @param p ein Vektor
	 * @return die Laenge des Vektors
	 */
	public static double getLengthOfVector(Point p) {
		double l = 0.0;
		l = Math.sqrt( Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2));
		return l;
	}
	
	
	/***
	 * Addiert zwei Vektoren. <br><br>
	 * 
	 * 1. Erstelle einen neuen Punkte, der den addieren Vektor aus p1 und p2 enthaelt.  
	 * 
	 * @param p1 Vektor 1
	 * @param p2 Vektor 2
	 * @return der addierte Vektor
	 */
	public static Point add(Point p1, Point p2) {
		Point p = new Point(p1.getX()+p2.getX(), p1.getY()+p2.getY());
		return p;
	}
	
	
	/***
	 * Addiert einen Vektor auf den anderen auf.<br><br>
	 * 
	 * 1. Addiere die x- und y-Koordinaten von p2 auf p1. 
	 * 
	 * @param p1 Vektor 1 auf den addiert wird
	 * @param p2 Vektor 2 
	 */
	public static void addTo(Point p1, Point p2) {
		p1.setX(p1.getX()+p2.getX());
		p1.setY(p1.getY()+p2.getY());
	}
	
	
	/***
	 * Berechnet den Abstand zwischen zwei Punkten. <br><br>
	 * 
	 * 1. Berchne den Abstand zwischen zwei Punkten mittels Pythagoras.
	 * 
	 * @param p1 Punkt 1
	 * @param p2 Punkt 2
	 * @return der Abstand
	 */
	public static double distance(Point p1, Point p2) {
		double d = 0.0;
		d = Math.sqrt( Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY()-p2.getY(), 2));	
		return d;
	}
	
	/* Hilfsfunktion zum loggen */
	public static String toString(Point p) {
		String tmpString; 
		
		tmpString = "P(" + Double.toString(p.getX()) + "|" + Double.toString(p.getY()) + ")";
		
		return tmpString;
	}
	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	
	

}
