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
		w1 = new PWindow(this);
		w1.setLocation(width/2, height/2);
		w1.setSize(150, 150);
		this.registerMethod("mouseEvent", w1);
		
		w2 = new PWindow(this);
		w2.setLocation(30, 30);
		w2.setSize(200, 100);
		this.registerMethod("mouseEvent", w2);
		
		w3 = new PWindow(this);
		w3.setLocation(500, 500);
		w3.setSize(150, 150);
		this.registerMethod("mouseEvent", w3);
		
		wg = PWindowGroup.getInstance();
		this.registerMethod("mouseEvent", wg);
		wg.addPWindow(w1);
		wg.addPWindow(w2);
		wg.addPWindow(w3);
	}
	
	public void draw(){
		background(255);
		//w1.draw();
		//w2.draw();
		wg.draw();
		
	}
	
//	public void mousePressed(){
//		if (window.contains(mouseX, mouseY)) {
//			window.setLocked(true);
//		}
//		xOffset = mouseX - window.getX();
//		yOffset = mouseY - window.getY();
//	
//	}
//	
//	public void mouseDragged(){
//		if (window.isLocked()) {
//			window.setLocation(mouseX - xOffset, mouseY - yOffset);
//		}
//	}
//	
//	public void mouseReleased(){
//		window.setLocked(false);
//	}

}
