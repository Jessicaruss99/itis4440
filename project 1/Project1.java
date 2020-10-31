
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**start of public class project 1 that extends swindow.*/
public class Project1 extends SWindow {
  
    //creating of top left point
    private Point topLeft; 
     
    /**main method that creates a new project.
    @param args string arguement*/
    static public void main(String args[]) {
        //creation of new project
        new Project1();
    }//end main
     
     /**project constructor.*/
    public Project1() {    
          super("project1");      
    }//end constructor
    
    /**init method that inistantiates the new point, adds the actions for the dropdowns, and
    adds menu items.*/
    public void init() {
        topLeft = new Point(15,15);
       
               
        addAction("File", "New",   () ->    {  new   Project1(); });      
        addAction("File","Close",  ()->     {  closeWindow(); });      
        addAction("project1",   "Quit",  ()->  {  System.exit(0);   });
        //addAction("Edit", "Nudge Right", ()->  {  nudgeRight();});
        //addAction("Edit", "Nudge Left",  ()->  {  nudgeLeft();});
        //addAction("Edit", "Reset", ()->  {  reset();});    
       
        // Adding   menu  items to existing menus    
        JMenu edit  =  getEditMenu();    
        edit.addSeparator();    
        installMenuItem(edit,   new   JMenuItem("Nudge Right"),
            () -> nudgeRight());             
       
        // Adding   menu  items to existing menus    
       
        edit.addSeparator();    
        installMenuItem(edit,   new   JMenuItem("Nudge Left"),
            () ->nudgeLeft());              
       
       
        //  Adding   menu  items to existing menus    
       
        edit.addSeparator();    
        installMenuItem(edit,   new   JMenuItem("Reset"),
            () -> reset());             
    }
       
                
    /**Getter for top left.
    @return top left point*/                 
    public Point getTopLeft(){
        //returns top left point
        return topLeft;
    }//end getter
     
    /**paint canvas method that draws the square and circle using the topleft point
    and of size 50 for width and height.
    @param g graphics*/
    public void paintCanvas(Graphics2D g) {  
        //draw rectangle
        g.drawArc(10, 10, 100, 100, -45, 180);   
        g.drawRect((int)topLeft.getX(),  (int)topLeft.getY(), 50,   50); 
          //draw circle 
        g.drawOval((int)topLeft.getX(),  (int)topLeft.getY(), 50,   50);     
    }//end paint canvas
     
    /**mouse clicked event method.This sets the status to mouse clicked, gets the point from
    the mouse event, sets it to the top left variable, and then repaints the screen which updates
    the location of the circle and square.
    @param e event*/
    public void mouseClicked(MouseEvent e) {
        //set status
        setStatus("mouseClicked");
        //set top left
        topLeft = e.getPoint();
       
        //repaint
        repaint();
    }
     
    /**mouse dragged event method.This sets the status to mouse dragged, gets the point from
    the mouse event, sets it to the top left variable, and then repaints the screen which updates
    the location of the circle and square.
    @param e event*/ 
    public void mouseDragged(MouseEvent e) {
        //set status
        setStatus("mouseDragged");
        //set top left
        topLeft = e.getPoint();
        //repaint
        repaint();
    }
     
    /**method to nudge top left to the right by 50.
    This sets the status to nudge right, updates the top left 
    x coordinate by plus 50, and then repaints the frame.*/  
    public void nudgeRight()      
    {
        //set status
        setStatus("Nudge Right");
        //change top left
        topLeft.setLocation((int) (topLeft.getX()+50), (int)topLeft.getY());
        //repaint
        repaint();        
    }
    
    /**method to nudge the top left to the left by 50.
    This sets the status to nudge right, updates the top left 
    x coordinate by minus 50, and then repaints the frame.*/
    public void nudgeLeft()    
    {
        //set status
        setStatus("Nudge Left");
        //change top left
        topLeft.x = (int) (topLeft.getX()-50);
        //repaint
        repaint();        
    }
    
    /**method to reset top left back to 15,15.
    This sets the status to reset, changes the top left variable back to its
    original 15,15, and then repaints the frame.*/
    public void reset() 
    {
        //set status
        setStatus("Reset");
        //change top left  
        topLeft.move(15,15);
        //repaint
        repaint(); 
    }

}
