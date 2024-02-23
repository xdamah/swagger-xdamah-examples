package com.example.test.model;

public class Tuple<E, F> {
	E x;
	public Tuple() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tuple(E x, F y) {
		super();
		this.x = x;
		this.y = y;
	}
	F y;
	public E getX() {
		return x;
	}
	public void setX(E x) {
		this.x = x;
	}
	public F getY() {
		return y;
	}
	public void setY(F y) {
		this.y = y;
	}
	

}
