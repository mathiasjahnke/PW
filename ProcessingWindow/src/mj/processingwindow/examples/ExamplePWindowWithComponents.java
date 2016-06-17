package mj.processingwindow.examples;

import mj.processing.button.PButtonGroup;
import mj.processing.button.PCheckBox;
import mj.processing.button.PRadioButton;
import mj.processing.constants.UIConstants;
import mj.processingwindow.window.PWindow;
import mj.processingwindow.windowgroup.PWindowGroup;
import processing.core.PApplet;

public class ExamplePWindowWithComponents extends PApplet{

	private PWindow w1;
	private PWindow w2;
	
	private PCheckBox cb1;
	private PCheckBox cb2;
	
	private PRadioButton rb1;
	private PRadioButton rb2;
	private PButtonGroup bg1;
	
	private PWindowGroup wg;

	public static void main(String[] args) {
		PApplet.main(ExamplePWindowWithComponents.class.getName());
	}
	
	public void settings(){
		size(1250, 750);
	}
	
	public void setup(){
		
		//components to add Radio Buttons
		rb1 = new PRadioButton(50, 50, 6, "first radio button", this);
		rb1.setChecked(true);
		rb2 = new PRadioButton(50, 75, 6, "second radio button", this);
		
		bg1 = new PButtonGroup();
		this.registerMethod("mouseEvent", bg1);
		bg1.add(rb1);
		bg1.add(rb2);
		
		//the components to add CheckBoxes
		cb1 = new PCheckBox(100, 100, 12, "first checkBox", this);
		cb1.setMarkerSymbol(UIConstants.CROSS);
		this.registerMethod("mouseEvent", cb1);
		
		cb2 = new PCheckBox(100, 120, 12, "second checkBox", this);
		cb2.setMarkerSymbol(UIConstants.TICKMARK);
		this.registerMethod("mouseEvent", cb2);
		
		//the first window
		w1 = new PWindow(this);
		w1.setLocation(width/2, height/2);
		w1.setSize(300, 200);
		w1.setLable("Window 1");
		w1.setOpened(true);
		this.registerMethod("mouseEvent", w1);
		
		w1.add(cb1);
		w1.add(cb2);
		
		//the second window
		w2 = new PWindow(this);
		w2.setLocation(30, 30);
		w2.setSize(300, 200);
		w2.setLable("Window 2");
		w2.setOpened(true);
		this.registerMethod("mouseEvent", w2);
		
		w2.add(rb1);
		w2.add(rb2);
		
		//the window group
		wg = PWindowGroup.getInstance();
		this.registerMethod("mouseEvent", wg);
		wg.add(w1);
		wg.add(w2);
	}
	
	public void draw(){
		background(255);
		//w1.draw();
		//w2.draw();
		wg.draw();
		
	}
}
