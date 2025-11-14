package model;

import java.util.ArrayList;


public class Map {
	private String keyFigure;
	private int iteration;
	private ArrayList<State> states;
	double[] range;
	
	
	public Map() {
		this.states = new ArrayList<State>();
	};
	
	
	/***
	 * Berechnet fue alle Staaten die Verschiebungsvektoren und verschiebt anschließend die Mittelpunkte.<br><br>
	 * 
	 * 1. Iteriere fuer jeden Staat durch jedes Nachbarland<br> 
	 * 2. Bestimme den Richtungsvektor zwischen den Mittelpunkten<br>
	 * 3. Berechne die Laenge des Richtungsvektors<br>
	 * 4. Berechne die Laenge der Verschiebung<br>
	 * 5. Berechne den Verschiebungsvektor fuer den Staat mit der Laenge und dem normierten Richtungsvektor<br>
	 * 6. Berechne den Verschiebungsvektor fuer den Nachbarstaat als inventierten Verschiebungsvektor des Staates<br>
	 * 7. Fuege die Vektoren jeweils der Verschiebungsliste hinzu<br>
	 * 8. Addiere fuer jeden Staat alle Verschiebungsvektoren aufeinander<br>
	 * 9. Dibidiere den summierten Verschiebungsvektor durch die Anzahl der Verschiebungsvektoren<br>
	 * 10. Verschiebe den Mittelpunkt um diesen Wert<br>
	 */
	public void calForces() {
		for (State s : this.states) {
			for (State n : s.getNeighbors()) {
				Point r = Point.getDirVector(s.getM(), n.getM());
				double d = Point.getLengthOfVector(r);
				double l = 0.5*(d-(s.getR()+n.getR()));
				Point v = new Point(r.getX()*l/d, r.getY()*l/d);
				Point v2 = new Point((-1)*v.getX(), (-1)*v.getY());
				s.getShifts().add(v);
				n.getShifts().add(v2);
			}
		}
		
		
		for (State s : this.states) {
			Point v = new Point(0.0, 0.0);
			for (Point p : s.getShifts()) {
				Point.addTo(v, p);
			}		
			int n = s.getShifts().size();
			if (n != 0) {
				v.setX(v.getX()/n);
				v.setY(v.getY()/n);
			}		
			Point.addTo(s.getM(), v);
			s.getShifts().clear();
		}
	}
	
	
	/***
	 * Eine zweite Algorithmus-Idee, welche jeweils die letzte Verschiebung in der nächsten berücksichtigt. 
	 * Wurde allerdings zu Gunsten den 1. Algorithmus nicht weiter verfolgt.
	 */
	public void calcForces2() {
		for (State s : this.states) {
			for (State n : s.getNeighbors()) {
				Point currM = Point.add(s.getM(), s.getVs());
				Point r = Point.getDirVector(currM, Point.add(n.getM(), n.getVs()));
				double d = Point.getLengthOfVector(r);
				double l = 0.5*(d-(s.getR()+n.getR()));
				Point v = new Point(r.getX()*l/d, r.getY()*l/d);
				s.setVs(Point.add(s.getVs(), v));
				Point v2 = new Point((-1)*v.getX(), (-1)*v.getY());
				n.setVs(Point.add(n.getVs(), v2));
			}
		}
		
		for (State s : this.states) {
			s.setM(Point.add(s.getM(), s.getVs()));
		}
	}
	
	
	/***
	 * Berechnet eine Voriteration, um die Mittelpunkte der Laender auseinander zu schieben. <br> <br>
	 * 
	 * 1. Berechne den maximalen Abstand, den zwei Kreise haben.<br>
	 * 2. Bestimme die Min/Max von x und y<br>
	 * 3. Erstelle den Mittelpunkt des aktuellen Graphen<br>
	 * 4. Verschiebe alle Mittelpunkte der Laender vom Mittelpunkt um die maximale Distanz weg<br>
	 */
	public void preCalculation() {
		if (this.states.size() > 1) {
			double maxL = this.calcCircleDistSN();
			this.calcRange();
			Point m  = new Point(0.5*(this.getRange()[1]+this.getRange()[0]), 0.5*(this.getRange()[3]+this.getRange()[2]));
			this.shiftStatesM(m, 2.5*maxL);
		}
	}
	
	
	/***
	 * Verschiebt die Mittelpunkte aller Staaten um eine uebergebene Laenge. <br> <br>
	 * 
	 * 1. Berechne für jeden Statt den Richtungsvektor zwischen m un dem Staat<br>
	 * 2. Bestimme die Laenge des Richtungsvektors<br>
	 * 3. Ist der Absand groesser als 0.1<br>
	 * 3.1 ja! Verschiebe den Mittelpunkt der Statt<br>
	 * 
	 * @param m ein Punkt
	 * @param l eine (Verschiebe-)Laenge
	 */
	public void shiftStatesM(Point m, double l) {
		for (State s : this.states) {
			Point r = Point.getDirVector(m, s.getM());
			double d = Point.getLengthOfVector(r);
			//Zum Ausschluss, dass der Mittelpunkt des Landes gleich dem uebergebenen Punkt ist
			if (d > 0.01) {
				r.setX(r.getX()/d*l*(-1));
				r.setY(r.getY()/d*l*(-1));
				s.setM(Point.add(s.getM(), r));
			}
			
		}
		
	}
	
	
	/***
	 * Berechnet die maximale Distanz zwischen den Kreisen aller Staaten und Nachbarn. <br><br>
	 * 
	 * 1. Fuer alle Staaten und deren Nachbarn berchne die Distanz zwischen den Kreisen<br>
	 * 2. Ist diese Distanz die bisher groesste gefundene?<br>
	 * 2.1 Ja! Setzt diese auf den bisher groesst-gefundenen Wert<br>
	 * 
	 * @return die maximale Distanz
	 */
	public double calcCircleDistSN() {
		double maxL = Double.MAX_VALUE;
		for (State s : this.states) {
			for (State n : s.getNeighbors()) {
				double d = Point.distance(s.getM(), n.getM());
				double l = d-(s.getR()+n.getR());
				
				if (l < maxL) {
					maxL = l;
				}
			}
		}
		return maxL;
	}
	
	
	
	/**
	 * Ermittelt für einen Staat alle Nachbar-Staaten. <br><br>
	 * 
	 * 1. Extrahiere aus dem uebergegbenen String die Laender-Codes des Staats und seiner Nachbar-Staaten (als String)<br>
	 * 2. Suche in der Staaten-Liste nach dem passenden Staat.<br>
	 * 3. Suche fuer jeden extrahierten Laender-Code den richtigen Nachbar-Staat heraus.<br>
	 * 
	 * @param nl ein String, welcher die Nachbarschaftsbezihungen eines Staates enthaelt
	 */
	public void addNeighborsToState(String nl) {
		//Staat
		String stateCC = nl.split(": ")[0];
		//alle Nachbarn
		String[] neighboarsCC = nl.split(": ")[1].split(" ");
		
		//Der Staat 
		State s = this.states.stream()
				.filter(t -> stateCC.equals(t.getCc()))
				.findAny()
				.orElse(null);
		
		// Die Nachbarstaaten 
		for (String element : neighboarsCC) {
			State n = this.states.parallelStream()
					.filter(t -> element.equals(t.getCc()))
					.findAny()
					.orElse(null);
			s.getNeighbors().add(n);
		}
		
	}
	
	
	/***
	 * Passt Min/Max der x-/y-Werte an, um bei der Ausgabe eine gute Achsenskalierung zu erhalten.<br><br>
	 * 
	 * 1. Gehe durch alle Staaten und berechnet min/max von x und y.<br>
	 * 2. Berechne jeweils die Spannweite der x-/y-Werte<br>
	 * 3. Ist die Spannweite von x kleiner als von y?<br>
	 * 3.1 JA! Passe x_min / x_max so an, dass die Spannweite mit y uebereinstimmt<br>
	 * 3.2 NEIN! Passe y_min / y_max so an, dass die Spannweite mit y uebereinstimmt<br>
	 */
	public void calcRange() {
		this.range = new double[]{Double.MAX_VALUE, -Double.MAX_VALUE, Double.MAX_VALUE, -Double.MAX_VALUE};
		for (State s : this.states) {
			double x = s.getM().getX();
			double y = s.getM().getY();
			double r = s.getR();
			
			if ( (x-r) < range[0] ) {
				range[0] = x - r;
			}
			if ( (x+r) > range[1] ) {
				range[1] = x + r;
			}
			if ( (y-r) < range[2] ) {
				range[2] = y - r;
			}
			if ( (y+r) > range[3] ) {
				range[3] = y + r;
			}
		}
		
		double dx = range[1] - range[0];
		double dy = range[3] - range[2];
		
		if (dx < dy) {
			range[0] -= 0.5*(dy-dx);
			range[1] += 0.5*(dy-dx);
		} else {
			range[2] -= 0.5*(dx-dy);
			range[3] += 0.5*(dx-dy);
		}
		
	}
	

	public double[] getRange() {
		return range;
	}


	public void setRange(double[] range) {
		this.range = range;
	}


	public String getKeyFigure() {
		return keyFigure;
	}
	public void setKeyFigure(String keyFigure) {
		this.keyFigure = keyFigure;
	}
	public int getIteration() {
		return iteration;
	}
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public ArrayList<State> getStates() {
		return states;
	}
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	
}
