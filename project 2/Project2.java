
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.JColorChooser;
import java.util.Random;

/**
 * project 2 class.
 */
public class Project2 extends SWindow {

    // create an array list of shapes
    private ArrayList<SShape> list;
    private ArrayList<SShape> list2;

    private SShape a;

    private Point lastPt;

    private Color current;
    private Random random;

    /**
     * main method that creates a new project.
     * 
     * @param args string arguement
     */
    static public void main(String args[]) {
        // creation of new project
        new Project2();
    }// end main

    /** project constructor. */
    public Project2() {
        super("Project2");
    }// end constructor

    /**
     * init method that inistantiates the new point, adds the actions for the
     * dropdowns, and adds menu items.
     */
    public void init() {

        // initialize shapes list
        list = new ArrayList<SShape>();
        // initialize shapes list
        list2 = new ArrayList<SShape>();

        addAction("File", "New", () -> {
            new Project2();
        });
        addAction("File", "Close", () -> {
            closeWindow();
        });
        addAction("Project2", "Quit", () -> {
            System.exit(0);
        });

        JMenu shapes = new JMenu("Shapes");

        getMenuBar().add(shapes);

        shapes.addSeparator();

        installMenuItem(shapes, new JMenuItem("New Circle"), () -> newCircle());
        installMenuItem(shapes, new JMenuItem("New Rectangle"), () -> newRectangle());
        installMenuItem(shapes, new JMenuItem("New Line"), () -> newLine());
        installMenuItem(shapes, new JMenuItem("New Triangle"), () -> newTriangle());

        shapes.addSeparator();

        installMenuItem(shapes, new JMenuItem("Set Fill Color"), () -> setFillColors());

        shapes.addSeparator();

        installMenuItem(shapes, new JMenuItem("Fill"), () -> fill());
        installMenuItem(shapes, new JMenuItem("Unfill"), () -> unfill());
        installMenuItem(shapes, new JMenuItem("Delete"), () -> delete());

        shapes.addSeparator();

        installMenuItem(shapes, new JMenuItem("Bring to Front"), () -> bringToFront());
        installMenuItem(shapes, new JMenuItem("Send to Back"), () -> sendToBack());

        shapes.addSeparator();

        installMenuItem(shapes, new JMenuItem("Fill in Colors"), () -> fillInColors());
        installMenuItem(shapes, new JMenuItem("Fill in Monochrome"), () -> fillInMonochrome());

        updateMenus();

    }// end of init

    /**
     * method for if mouse is clicked.
     * 
     * @param e string arguemen
     */
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).contains(e.getPoint())) {
                list.get(i).select(true);
            } else {
                list.get(i).select(false);
            }
        }
        repaint();
        updateMenus();
    }

    /**
     * method for if mouse is pressed.
     * 
     * @param e string arguemen
     */
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).contains(e.getPoint()) || (list.get(i).inHandle(e.getPoint()) != -1)) {

                list.get(i).grabbed(e.getPoint());
                list.get(i).select(true);
            } else {
                list.get(i).select(false);
            }
        }

        // save this point
        lastPt = e.getPoint();
        repaint();
        updateMenus();
    }

    /**
     * method for mouse dragged.
     * 
     * @param e string arguemen
     */
    public void mouseDragged(MouseEvent e) {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).isSelected()) {
                int dx = (int) e.getPoint().getX() - lastPt.x;
                int dy = (int) e.getPoint().getY() - lastPt.y;
                list.get(i).dragging(dx, dy);
            }
        }
        lastPt = e.getPoint();
        repaint();
        updateMenus();
    }

    /**
     * method for mouse released.
     * 
     * @param e string arguemen
     */
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).isSelected()) {
                list.get(i).released(e.getPoint());
            }
        }

        // leave the object selected
        repaint();
        updateMenus();
    }

    /**
     * paint canvas method.
     * 
     * @param g graphics arguemen
     */
    public void paintCanvas(Graphics2D g) {
        for (SShape s : list) {
            s.draw(g);
        }
    }

    /**
     * new circle method.
     */
    public void newCircle() {
        a = new SCircle(100, 100, 50);
        list.add(a);
        repaint();
        updateMenus();
    }// end of new circle method

    /**
     * new rectangle method.
     */
    public void newRectangle() {
        a = new SRectangle(100, 100, 100, 100);
        list.add(a);
        repaint();
        updateMenus();
    }// end of new rectangle method

    /**
     * new line method.
     */
    public void newLine() {
        a = new SLine(50, 50, 100, 100);
        list.add(a);
        repaint();
        updateMenus();
    }// end of new line method

    /**
     * new triangle method.
     */
    public void newTriangle() {
        a = new STriangle(100, 100, 100, 100);
        list.add(a);
        repaint();
        updateMenus();
    }// end of new triangle method

    /**
     * set fill color method.
     */
    public void setFillColors() {
        Color colorChooser = JColorChooser.showDialog(null, "Choose", current);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isFilled()) {
                list.get(i).setFillColor(colorChooser);
            }
        } // end for

        // current = colorChooser.getColor();

    }// end of set fill color

    /**
     * set fill method.
     */
    public void fill() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                list.get(i).setFill(true);
            } // end if
        } // end for

        repaint();
        updateMenus();

    }// end of fill method

    /**
     * set unfill method.
     */
    public void unfill() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                list.get(i).setFill(false);
            } // end if
        } // end for

        repaint();
        updateMenus();
    }// end of unfill method

    /**
     * delete method.
     */
    public void delete() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                // list.get(i).setVisible(false);
                list.remove(i);
            } // end if
        } // end for

        repaint();
        updateMenus();

    }// end of delete method

    /**
     * bring to front method.
     */
    public void bringToFront() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                list2.add(list.get(i));
                list.remove(i);
            } // end if
        } // end for

        for (int j = 0; j < list2.size(); j++) {
            list.add(list2.get(j));
        } // end for

        repaint();
        updateMenus();

    }// end of bring to front

    /**
     * send to back method.
     */
    public void sendToBack() {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isSelected()) {
                list2.add(list.get(i));
                list.remove(i);
            } // end if
        } // end for

        for (int j = 0; j < list2.size(); j++) {
            list.add(list2.get(j));
        } // end for

        repaint();
        updateMenus();

    }// end of send to back method

    /**
     * fill in colors method.
     */
    public void fillInColors() {
        int red;
        int blue;
        int green;
        Color newFillColors;
        Color colorChooser;

        for (int i = 0; i < list.size(); i++) {
            random = new Random();
            red = random.nextInt(255);
            blue = random.nextInt(255);
            green = random.nextInt(255);
            newFillColors = new Color(red, blue, green);
            colorChooser = newFillColors;

            list.get(i).setFillColor(colorChooser);
            list.get(i).setFill(true);

        } // end for

        repaint();
        updateMenus();

    }// end of fill colors method

    /**
     * fill in monochrome method.
     */
    public void fillInMonochrome() {
        int monochromeColor;
        Color newFillColors;
        Color colorChooser;

        for (int i = 0; i < list.size(); i++) {
            random = new Random();
            monochromeColor = random.nextInt(255);
            newFillColors = new Color(monochromeColor, monochromeColor, monochromeColor);
            colorChooser = newFillColors;

            list.get(i).setFillColor(colorChooser);
            list.get(i).setFill(true);

        } // end for

        repaint();
        updateMenus();

    }// end of fill in monochrone method

    /**
     * update menu method.
     */
    public void updateMenus() {
        enableAction("Shapes", "Fill", false);
        enableAction("Shapes", "Unfill", false);
        enableAction("Shapes", "Delete", false);

        enableAction("Shapes", "Bring to Front", false);

        enableAction("Shapes", "Send to Back", false);

        for (SShape a : list) {
            if (a.isSelected()) {
                enableAction("Shapes", "Fill", true);
                enableAction("Shapes", "Unfill", true);
                enableAction("Shapes", "Delete", true);

                enableAction("Shapes", "Bring to Front", true);

                enableAction("Shapes", "Send to Back", true);

            }
        }
    }

    /**
     * method to return the number of shapes.
     * 
     * @return numberShapes the number of shapes in the list
     */
    public int numShapes() {
        int numberShapes = 0;
        for (int i = 0; i < list.size(); i++) {
            numberShapes++;
        } // end for loop

        return numberShapes;
    }

}// end of project 2 class