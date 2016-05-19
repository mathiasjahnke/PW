package mj.processingwindow.examples;

import mj.processingwindow.window.PWindow;
import mj.processingwindow.windowgroup.PWindowGroup;
import processing.core.PApplet;

public class ExamplePWindow extends PApplet{
	

	private PWindow w1;
	private PWindow w2;
	private PWindow w3;
	
	private PWindowGroup wg;

	public static void main(String[] args) {
		PApplet.main(ExamplePWindow.class.getName());
	}
	
	public void settings(){
		size(1250, 750);
	}
	
	public void setup(){
		//the first window
		w1 = new PWindow(this);
		w1.setLocation(width/2, height/2);
		w1.setSize(150, 150);
		w1.setLable("Window 1");
		this.registerMethod("mouseEvent", w1);
		
		//the second window
		w2 = new PWindow(this);
		w2.setLocation(30, 30);
		w2.setSize(200, 100);
		w2.setLable("Window 2");
		w2.setOpened(true);
		this.registerMethod("mouseEvent", w2);
		
		//the third window
		w3 = new PWindow(this);
		w3.setLocation(500, 500);
		w3.setSize(150, 150);
		w3.setLable("Window 3");
		this.registerMethod("mouseEvent", w3);
		
		//the window group
		wg = PWindowGroup.getInstance();
		this.registerMethod("mouseEvent", wg);
		wg.add(w1);
		wg.add(w2);
		wg.add(w3);
	}
	
	public void draw(){
		background(255);
		//w1.draw();
		//w2.draw();
		wg.draw();
		
	}
}
