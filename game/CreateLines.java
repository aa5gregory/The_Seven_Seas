package game;

import graphicslib3D.Point3D;

import java.awt.Color;

import sage.scene.shape.Line;

public class CreateLines {
	
	private Line xDraw;
	private Line yDraw;
	private Line zDraw;
	public CreateLines(){
	Point3D origin = new Point3D(0,0,0);
	Point3D xAxis = new Point3D(100,0,0);
	Point3D yAxis = new Point3D(0,100,0);
	Point3D zAxis = new Point3D(0,0,100);
	xDraw = new Line(origin, xAxis,Color.red,2);
	yDraw = new Line(origin, yAxis,Color.green,2);
	zDraw = new Line(origin, zAxis,Color.blue,2);
	
	}
	public Line getXAxis(){
		return xDraw;
	}
	public Line getYAxis(){
		return yDraw;
	}
	public Line getZAxis(){
		return zDraw;
	}
}
