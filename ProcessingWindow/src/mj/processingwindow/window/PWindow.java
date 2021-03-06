package mj.processingwindow.window;

import java.util.ArrayList;

import mj.processing.button.PComponent;
import mj.processing.button.PIComponent;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.event.MouseEvent;

/**
 * a first attempt to get something like a draggable window within processing.
 * used rcectMode(CORNER)
 * 
 * 1. the heading area is for dragging, its only possible to drag a window if the mouse is inside the dragging area
 * 2. on the right side of the dragging area is a area to open or close the window but not feasible for dragging
 * 3. different contains methods are needed 
 *    first is the window content area for as it is implemented now
 *    contains darggable area and contains opened close area
 * 
 * TODO: set at title
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
	
	//to indicate if the window area is visible 
	private boolean opened; 
	
	//a window title
	private PFont labelFont; 
	private String label;
	
	//Array to add some components
	private ArrayList<PIComponent> compArray;
	
	/**
	 * parameterized constructor. 
	 * @param p the PApplet to draw the PWindow on
	 */
	public PWindow(PApplet p){
		this.p = p;
		this.locked = false;
		this.label = "Window";
		labelFont = p.createFont("Arial", 14, true);
		this.compArray = new ArrayList<PIComponent>();
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
	 * sets if the window is opened = true or closed = false.
	 * @param opened boolean
	 */
	public void setOpened(boolean opened){
		this.opened = opened;
	}
	
	/**
	 * 
	 * @return the status if opened or closed
	 */
	public boolean isOpened(){
		return this.opened;
	}
	
	/**
	 * toggle the opened status
	 */
	public void toggleOpened(){
		if(this.opened){
			this.opened = false;
		}else{
			this.opened = true;
		}
	}
	
	/**
	 * sets the label or title of the window
	 * @param label the label or title
	 */
	public void setLable(String label){
		this.label = label;
	}
	
	/**
	 * retrieves the label or title of the window
	 * @return the label or title as String
	 */
	public String getLabel(){
		return this.label;
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
		//set the lock of the window to true if the window is clicked or the mouse button pressed
		if (event.getAction() == 1) {
			//if (contains(event.getX(), event.getY())){
			if (containsHeadArea(event.getX(), event.getY())){
				setLocked(true);
				xOffset = event.getX() - this.getX();
				yOffset = event.getY() - this.getY();
			}else if(containsOpenedArea(event.getX(), event.getY())){
				toggleOpened();
			}
		}
		//drags the window around 
		if (event.getAction() == 4) {
			if (this.isLocked()) {
				this.setLocation(event.getX() - xOffset, event.getY() - yOffset);
				for(int i = 0; i < compArray.size(); i++){
					compArray.get(i).setLocation(event.getX() - xOffset + 12, event.getY() - yOffset + 15 + (i * 25));
				}
			}
		}
		//unlock if the mouse is released
		if (event.getAction() == 2) {
			this.setLocked(false);
		}
	}
	
	/**
	 * to draw the window onto the canvas
	 */
	@Override
	public void draw(){
		p.rectMode(PConstants.CORNER);
		//draw head not belonging to the display area
		p.stroke(210);
		p.fill(210);
		p.rect(getX(), getY() - 25, getWidth(), 25);
		p.stroke(190);
		p.fill(190);
		p.rect(getX() + getWidth() - 25, getY() - 25, 25, 25);
		
		//draw label
		p.textAlign(PConstants.LEFT, PConstants.BOTTOM);
		p.textFont(labelFont);
		p.fill(150);
		p.text(label, getX() + 3, getY() - 4);
		
		// draw window display area
		if(isOpened()){
			drawUpArrow(getX() + getWidth() - 16, getY() - 15);
			p.stroke(240);
			p.fill(240);
			p.rect(getX(), getY(), getWidth(), getHeight());
			//draw all the components
			for(int i = 0; i < compArray.size(); i++){
				compArray.get(i).draw();
			}
		}else {
			drawDownArrow(getX() + getWidth() - 16, getY() - 15);
		}
	}
	
	/**
	 * checks whether the coordinate (x, y) falls within the window. 
	 * works only if the window is opened.
	 * @param x the coordinate's x value
	 * @param y the coordinate's y value
	 * @return true if the coordinate are within the window otherwise false
	 */
	@Override
	public boolean contains(float x, float y){
		boolean returnValue = false;
		if(isOpened()){
			if (x > this.getX() && x < this.getX() + this.getWidth()) {
				if (y > this.getY() && y < this.getY() + this.getHeight()) {
					returnValue = true;
				}
			}
		}
		return returnValue;
	}	
	
	/**
	 * check whether the coordinate (x, y) falls inside the opened area
	 * 
	 * @param x the coordinates x value
	 * @param y the coordinates y value
	 * @return true if the coordinate are within the opened area
	 */
	public boolean containsOpenedArea(float x, float y){
		boolean opened = false;
		if (x > this.getX() + this.getWidth() - 25 && x < this.getX() + this.getWidth()) {
			if (y > this.getY() - 25 && y < this.getY()) {
				opened = true;
			}
		}
		
		return opened;
	}
	
	/**
	 * checks whether the coordinate (x, y) falls inside the head area
	 * 
	 * @param x the coordinates x value
	 * @param y the coordinate y value
	 * @return true or false
	 */
	public boolean containsHeadArea(float x, float y){
		boolean returnValue = false;
		
		if (x > this.getX() && x < this.getX() + this.getWidth() - 25) {
			if (y > this.getY() - 25 && y < this.getY()) {
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	/**
	 * to add some components to the window
	 * @param component the PIComponent to add
	 */
	public void add(PIComponent component){
		component.setLocation(this.getX() + 12, this.getY() + 15 + (compArray.size() * 25));
		compArray.add(component);
	}
	
	/**
	 * to remove components from the window
	 * @param component the PIComponent to remove
	 */
	public void remove(PIComponent component){
		compArray.remove(component);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	private void drawDownArrow(float x, float y){
		p.stroke(255);
		p.fill(255);
		p.triangle(x, y, x + 8, y, x + 4, y + 7);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	private void drawUpArrow(float x, float y){
		p.stroke(255);
		p.fill(255);
		p.triangle(x, y + 7, x + 8, y + 7, x + 4, y);
	}
	
}
