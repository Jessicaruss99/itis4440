/**
 * File: SWindow.java
 * ITIS 4440 User Interface Design and Implementation
 * @author: manuel a perez-quinones
 * Compile: javac SWindow.java
 * Javadoc: 
 javadoc -d javadoc SWindow.java SAction*.java \
     -windowtitle 'SFramework for ITIS4440 UNCC' \
     -doctitle 'SFramework for ITIS4440 UNCC' \
     -header '<b>ITIS4440</b>'
 * see copyright at end of file
 *
 * Reusable Window class that includes a panel, default
 * behavior on close, and provides a hook for a subclass
 * to provide the drawing behavior. It provides support
 * for installing lambda expressions as actions for menu
 * items.
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SWindow extends MouseInputAdapter 
{
  private JFrame window;
  private JPanel canvas;
  private JMenuBar menuBar;
  private JMenu appMenu, fileMenu, editMenu;
  private JLabel status;
  private JMenuItem menuItem;

  private HashMap<SActionKey, SActionState> actions;

  static protected ArrayList<SWindow> theWindowList = new ArrayList<SWindow>();
  static private int windowCount = 0;

  /**
   * Constructor, creates an SWindow. Does the work by 
   * calling the constructor with String/Boolean as
   * arguments using by default name "SWindow"
   */
  public SWindow() 
  {
    this("SWindow");
  }

  /**
   * Constructor, creates an SWindow. This is the
   * constructor that creates the window and connects
   * all the pieces.
   * @param appName of the app, used in menus and window titles
   */
  public SWindow(String appName) 
  {
    SWindow self = this;
    theWindowList.add(this);
    windowCount++;

    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {

          // with Lambda Expressions!
          actions = new HashMap<SActionKey, SActionState>();

          window = new JFrame(appName+"-"+windowCount);
          Container contentPane = window.getContentPane();
          contentPane.setLayout(new BorderLayout());

          //Create the menu bar.
          menuBar = new JMenuBar();

          //Build the app menu: About/Quit
          appMenu = new JMenu(appName);
          installMenuItem(appMenu, new JMenuItem("About"));
          appMenu.addSeparator();
          installMenuItem(appMenu, new JMenuItem("Quit"));
          menuBar.add(appMenu);

          //Build the File
          fileMenu = new JMenu("File");
          installMenuItem(fileMenu, new JMenuItem("New"));
          installMenuItem(fileMenu, new JMenuItem("Open"));
          installMenuItem(fileMenu, new JMenuItem("Save"));
          fileMenu.addSeparator();
          installMenuItem(fileMenu, new JMenuItem("Close"));
          menuBar.add(fileMenu);

          editMenu = new JMenu("Edit");
          installMenuItem(editMenu, new JMenuItem("Undo"));
          editMenu.addSeparator();
          installMenuItem(editMenu, new JMenuItem("Cut"));
          installMenuItem(editMenu, new JMenuItem("Copy"));
          installMenuItem(editMenu, new JMenuItem("Paste"));
          menuBar.add(editMenu);


          // Drawing Canvas
          canvas = new JPanel() {
            protected void paintComponent(Graphics g) {
              paintCanvas((Graphics2D) g);
            }
          };
          canvas.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

          // Adding SWindow as the listener for mouse actions 
          canvas.addMouseListener(self);
          canvas.addMouseMotionListener(self);

          status = new JLabel("Status area");
          contentPane.add(canvas, BorderLayout.CENTER);
          contentPane.add(status, BorderLayout.SOUTH);

          window.setJMenuBar(menuBar);

          window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
              closeWindow();
            }
          });
 
          // set the preferred size and the location and then
          // pack the window (force an internal layout)
          window.setPreferredSize(new Dimension(275, 300));
          int loc = 20 + (windowCount * 50);
          window.setLocation(loc, loc);
          window.pack();

          // Let a subclass do further configurations
          // like adding listeners
          init();

          // then show the window...
          window.setVisible(true);
          setStatus("Loaded");
        }
      }
    );
  }

  /**
   * Installs a menu item in the given menu providing
   * a default action that sets the status message with
   * the name of the menu item installed. The message lasts
   * for 2 seconds.
   * @param menu where to install the item
   * @param item to install at the end of the menu
   */
  public void installMenuItem(JMenu menu, JMenuItem item)
  {
    String menuName = menu.getText();
    String itemName = item.getText();
    installMenuItem(menu, item, ()-> {
      setStatus("Menu "+itemName, 2);
    });
  }

  /**
   * Installs a menu item in the given menu with the
   * provided action.
   * @param menu where to install the item
   * @param item to install at the end of the menu
   * @param action that is executed when menu item is selected
   */
  public void installMenuItem(JMenu menu, JMenuItem item, SAction action)
  {
    String menuName = menu.getText();
    String itemName = item.getText();

    menu.add(item);
    addAction(menuName, itemName, action);
    item.addActionListener((e) -> doAction(menuName, itemName));
  }

  /**
   * Init the window, once all parts have been created but
   * before it is made visible. The default method does
   * nothing. The intention is to use this method to add
   * listeners and do further configuration of the window
   * before it is displayed.
   */
  public void init() {}

  /**
   * Returns the JFrame used in the example.
   * @return JFrame in the app
   */
  public JFrame getWindow() { return window; }

  /**
   * Returns the title of the window (JFrame)
   * @return title of the window
   */
  public String getTitle() { return window.getTitle(); }

  /**
   * Returns the JPanel where the drawing takes place.
   * @return JPanel that is used for drawing
   */
  public JPanel getCanvas() { return canvas; }

  /**
   * Returns the JMenuBar installed in this window.
   * @return JMenuBar
   */
  public JMenuBar getMenuBar() { return menuBar; }

  /**
   * Returns the AppMenu installed in this window.
   * @return JMenu
   */
  public JMenu getAppMenu() { return appMenu; }

  /**
   * Returns the FileMenu installed in this window.
   * @return JMenu
   */
  public JMenu getFileMenu() { return fileMenu; }

  /**
   * Returns the EditMenu installed in this window.
   * @return JMenu
   */
  public JMenu getEditMenu() { return editMenu; }

  /**
   * Returns the text in the status message area
   * @return string with the message from the status area
   */
  
  public String getStatus()
  {
    return status.getText();
  }

  /**
   * Sets the message in the status area of this window
   * @param message to be displayed in the status area
   */
  public void setStatus(String message)
  {
    // display message in status bar
    status.setText(message);
    status.repaint();
  }

  /**
   * Sets the message in the status bar of this window
   * and after specified seconds, clears it.
   * @param message to be displayed in the status area
   * @param seconds after which the status is cleared
   */
  public void setStatus(String message, int seconds)
  {
    setStatus(message, seconds, null);
  }

  /**
   * Sets the message in the status bar of this window
   * and after specified seconds, sets another message
   * that will stay until another setStatus call changes it.
   * @param message to be displayed in the status area
   * @param seconds after which the status is changed
   * @param toMessage change status to this message. If null
   * the message is cleared
   */
  public void setStatus(String message, int seconds, String toMessage)
  {
    setStatus(message);

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
        public void run() {
            if (toMessage == null)
              clearStatus();  // clear the message
            else
              setStatus(toMessage);
            timer.cancel(); // Terminate the timer thread
        }
      }, seconds*1000);
  }

  /**
   * Clears the message in the status bar of this window
   */
  public void clearStatus()
  {
    setStatus("");
  }

  /**
   * Method that gets called to draw the window content.
   * Default method does nothing. Note that this method is
   * not public.
   * @param g used for drawing
   */
  void paintCanvas(Graphics2D g) {}

  /**
   * Convenience method to force a window repaint.
   */
  public void repaint()
  {
    getWindow().repaint();
  }


  /**
   * Action Support: adds an action with the context:name
   * @param context for the action
   * @param name for the action
   * @param action functional interface to an action
   */
  public void addAction(String context, String name, SAction action)
  {
    actions.put(
      new SActionKey(context, name), 
      new SActionState(action));
  }

  /**
   * Action Support: given a context:name, returns the action
   * @param context for the action
   * @param name for the action
   * @return action associated with context:name
   */
  public SAction getAction(String context, String name)
  {
    SActionState a = actions.get(new SActionKey(context, name));
    if (a != null)
      return a.getAction();
    else
      return null;
  }

  /**
   * Action Support: given a context:name, execute the action
   * @param context for the action
   * @param name for the action
   */
  public void doAction(String context, String name)
  {
    SActionState a = actions.get(new SActionKey(context, name));
    if (a != null)
        a.doAction();
  }

  /**
   * Action Support: given a context:name, enable the action
   * @param context for the action
   * @param name for the action
   */
  public void enableAction(String context, String name)
  {
    SActionState a = actions.get(new SActionKey(context, name));
    if (a != null)
      a.enable();
  }

  /**
   * Action Support: given a context:name, disable the action
   * @param context for the action
   * @param name for the action
   */
  public void disableAction(String context, String name)
  {
    SActionState a = actions.get(new SActionKey(context, name));
    if (a != null)
      a.disable();
  }

  /**
   * Action Support: given a context:name, remove the action
   * @param context for the action
   * @param name for the action
   */
  // public void removeAction(String context, String name)
  // {
  //   System.out.println("Action not implemented yet.");
  // }

  /**
   * Window Support: given an action, call this action
   * on each window
   * @param action consumer action to perform on all windows
   */
  static public void forEachWindow(Consumer<SWindow> action)
  {
    theWindowList.forEach(action);
  }

  /**
   * Window Support: get number of windows
   * @return the number of windows opened
   */
  static public int getNumWindows()
  {
    return theWindowList.size();
  }

  /**
   * Returns the window in front of the list
   * @return SWindow object
   */
  static public SWindow getFrontMost()
  {
    if (theWindowList.size() > 0)
      return theWindowList.get(0);
    else
      return null;
  }

  /**
   * Window Support: removes the window passed from
   * the application list of windows.
   * @param window to be removed from list
   */
  static public void removeWindow(SWindow window)
  {
      theWindowList.remove(window);
  }

  /**
   * Window Support: brings this window to the front by
   * calling toFront() on the JFrame.
   */
  public void bringToFrontWindow()
  {
    this.getWindow().toFront();
  }

  /**
   * Window Support: Sends this window to the back by
   * calling toBack() on the JFrame.
   */
  public void sendToBackWindow()
  {
    this.getWindow().toBack();
  }

  /**
   * Window Support: maximize this window by setting the
   * extended state to MAXIMIZED_BOTH on the JFrame.
   */
  public void maximizeWindow()
  {
    this.getWindow().setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  /**
   * Window Support: minimize this window by setting the
   * extended state to ICONIFIED on the JFrame.
   */
  public void minimizeWindow()
  {
    this.getWindow().setExtendedState(JFrame.ICONIFIED);
  }

  /**
   * Window Support: closes this window, existing to
   * the OS if there are no other windows opened.
   */
  public void closeWindow()
  {
    closeWindow(true);
  }

  /**
   * Window Support: closes this window. As an indirect
   * result, if there was an acion installed, it gets
   * called.
   * @param exitIfLast - if this is the last window,
   * it will terminate the application and exit to the OS
   */
  public void closeWindow(boolean exitIfLast)
  {
    // First, remove this window from the window list
    removeWindow(this);

    // if the window list is empty, then we quit
    // the program.
    if (exitIfLast && theWindowList.size() == 0) {
      // we are closing the last window
      System.exit(0);
    }
    else {
      // we hide this window, and then dispose it
      getWindow().setVisible(false);
      getWindow().dispose();
    }
  }

  /**
   * Window Support: removes the window passed from
   * the application list of windows.
   * @param window to be removed from list
   */
  public void removeWindow()
  {
      theWindowList.remove(this);
  }
}

/*
 * Copyright: This programming assignment specification and the 
 * provided sample code are protected by copyright. The professor 
 * is the exclusive owner of copyright of this material. You are 
 * encouraged to take notes and make copies of the specification 
 * and the source code for your own educational use. However, you 
 * may not, nor may you knowingly allow others to reproduce or 
 * distribute the materials publicly without the express written 
 * consent of the professor. This includes providing materials to 
 * commercial course material suppliers such as CourseHero and 
 * other similar services. Students who publicly distribute or 
 * display or help others publicly distribute or display copies or 
 * modified copies of this material may be in violation of 
 * University Policy 406, The Code of Student Responsibility
 * https://legal.uncc.edu/policies/up-406.
 */