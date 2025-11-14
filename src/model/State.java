package model;

import java.util.ArrayList;
import java.lang.Math;

public class State {
	private double r;
	private Point m;
	private double area;
	private String cc;
	private ArrayList<State> neighbors;
	private ArrayList<Double> d;
	private ArrayList<Point> shifts;
	// Nur fuer calcForces2()
	private Point vs;
	
	
	public State(String cc, double area, double x, double y) {
		super();
		this.cc = cc;
		this.area = area;
		this.m = new Point(x, y);	
		this.neighbors = new ArrayList<State>();
		this.shifts = new ArrayList<Point>();
		this.d = new ArrayList<Double>();
		this.r = Math.sqrt(area/Math.PI);
		this.vs = new Point(0.0, 0.0);
	}
	

	public Point getVs() {
		return vs;
	}


	public void setVs(Point vs) {
		this.vs = vs;
	}


	public ArrayList<Double> getD() {
		return d;
	}


	public void setD(ArrayList<Double> d) {
		this.d = d;
	}


	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public Point getM() {
		return m;
	}

	public void setM(Point m) {
		this.m = m;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public ArrayList<State> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<State> neighbors) {
		this.neighbors = neighbors;
	}

	public ArrayList<Point> getShifts() {
		return shifts;
	}

	public void setShifts(ArrayList<Point> shifts) {
		this.shifts = shifts;
	}
	

}
