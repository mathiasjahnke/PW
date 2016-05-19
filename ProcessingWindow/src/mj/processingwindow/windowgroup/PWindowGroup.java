package mj.processingwindow.windowgroup;

import java.util.ArrayList;
import java.util.Iterator;
import mj.processingwindow.window.PWindow;
import processing.event.MouseEvent;

/**
 * to handle somehow the windows and the focus. should draw the windows in a distinct order with the focus window on top
 * 
 * @author Mathias Jahnke, Technische Universit&auml;t M&uuml;nchen, <a href="http://www.lfk.bgu.tum.de">Chair of Cartography</a>
 * @version 0.0.1
 * @since 17.05.2016
 *
 */
public final class PWindowGroup {
	
	//the instance of the group
	private static PWindowGroup wg;
	
	//the list of windows
	private ArrayList<PWindow> array;
	
	private PWindow focusWindow;
	
	/**
	 * private constructor to make at singleton 
	 */
	private PWindowGroup(){
		this.array = new ArrayList<PWindow>();
	}
	
	/**
	 * get an instance of WindowGroup 
	 * @return
	 */
	public static PWindowGroup getInstance(){
		
		if(wg == null){
			wg = new PWindowGroup();
		}
		return wg;
	}
	
	/**
	 * to add a window to the WindowGroup
	 * @param window a window object
	 */
	public void addPWindow(PWindow window){
		array.add(window);
		System.out.println(window.getComponentId());
	}
	
	/**
	 * mouse actions:
	 * PRESS = 1;
  	 * RELEASE = 2;
     * CLICK = 3;
     * DRAG = 4;
     * MOVE = 5;
     * ENTER = 6;
     * EXIT = 7;
     * WHEEL = 8;
     * 
	 * @param event the MouseEvent
	 */
	public void mouseEvent(MouseEvent event){
		Iterator<PWindow> iter = array.iterator();
		//if event.getAction == 1 (mouse button PRESS)
		if(event.getAction() == 1){
			while(iter.hasNext()){
				PWindow pw = (PWindow) iter.next();
				if(pw.contains(event.getX(), event.getY()) || pw.containsHeadArea(event.getX(), event.getY()) || pw.containsOpenedArea(event.getX(), event.getY())){
					this.focusWindow = pw;
					
				}
			}
			updateOrder();
		}
	}
	
	/**
	 * draws the windows within the window group
	 * 
	 */
	public void draw(){
		for(int i = 0; i < array.size(); i++){
			array.get(i).draw();
		}
		
//		Iterator<PWindow> iter = array.iterator();
//		while(iter.hasNext()){
//			PWindow w = (PWindow)iter.next();
//			w.draw();
//		}
	}
	
	/**
	 * sets the new focus window at the first position of the array list 
	 * add the win at last position that it is drawn last
	 */
	private void updateOrder(){
		array.remove(focusWindow);
		array.add(focusWindow);
	}
	
	
	

}
