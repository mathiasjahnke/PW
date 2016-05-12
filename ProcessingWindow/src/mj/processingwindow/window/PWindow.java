package mj.processingwindow.window;

import mj.processing.button.PComponent;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.MouseEvent;

/**
 * a first attempt to get something like a draggable window within processing.
 * 
 * TODO: a WindowGroup is needed to set the drawing order if more than one window exists within a sketch.
 * TODO: show and hide function as well as a close function.
 * TODO: make the window resizable.
 * 
 * @author Mathias Jahnke, Technische Universit&auml;t M&uuml;nchen, <a href="http://www.lfk.bgu.tum.de">Chair of Cartography</a>
 * @version 0.0.1
 * @since 30.03.2016
 *
 */
public class PWindow extends PComponent{
	
	private PApplet p;
	
	private boolean locked;
	
	private float xOffset;
	private float yOffset;
	
	/**
	 * parameterized constructor. 
	 * @param p the PApplet to draw the PWindow on
	 */
	public PWindow(PApplet p){
		this.p = p;
		this.locked = false;
	}
	
	/**
	 * if the PWindow is locked
	 * @param locked true if locked otherwise false
	 */
	public void setLocked(boolean locked){
		this.locked = locked;
	}
	
	/**
	 * retrieves the locked status of the PWindow. <br>
	 * 
	 * @return true if locked otherwise false
	 */
	public boolean isLocked(){
		return this.locked;
	}
	
	/**
	 * the mouseEvent() method is needed to bring mouse behavior to objects. <br>
	 * this method is needed instead of using MouseListener implementations.
	 * implementation based on Processing's <a href="https://processing.org/examples/mousefunctions.html">mouse functions</a> example.
	 * mouse actions:
	 * PRESS = 1;
  	 * RELEASE = 2;
     * CLICK = 3;
     * DRAG = 4;
     * MOVE = 5;
     * ENTER = 6;
     * EXIT = 7;
     * WHEEL = 8;
	 * @param event the mouse event
	 */
	public void mouseEvent(MouseEvent event){
		//System.out.println(event.toString());
		if (event.getAction() == 1) {
			if (contains(event.getX(), event.getY())){
				setLocked(true);
				xOffset = event.getX() - this.getX();
				yOffset = event.getY() - this.getY();
			}
		}
		
		if (event.getAction() == 4) {
			if (this.isLocked()) {
				this.setLocation(event.getX() - xOffset, event.getY() - yOffset);
			}
		}
		
		if (event.getAction() == 2) {
			this.setLocked(false);
		}
	}
	
	
	//*********************************
	//Override PComponent Methods
	//*********************************
	/**
	 * to draw the window onto the canvas
	 */
	@Override
	public void draw(){
		// draw window display area
		p.stroke(0);
		p.fill(255);
		p.rectMode(PConstants.CORNER);
		p.rect(getX(), getY() - 20, getWidth(), getHeight() + 20);
		
		//draw head not belonging to the display area
		p.fill(200);
		p.rect(getX(), getY() - 20, getWidth(), 20);
	}
	
	/**
	 * checks whether the coordinate (x, y) falls within the window
	 * @param x the coordinate's x value
	 * @param y the coordinate's y value
	 * @return true if the coordinate are within the window otherwise false
	 */
	@Override
	public boolean contains(float x, float y){
		boolean returnValue = false;
		if (x > this.getX() && x < this.getX() + this.getWidth()) {
			if (y > this.getY() && y < this.getY() + this.getHeight()) {
				returnValue = true;
			}
		}
		return returnValue;
	}	
}
