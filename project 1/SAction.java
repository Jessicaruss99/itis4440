/**
 * File: SAction.java
 * ITIS 4440 User Interface Design and Implementation
 * @author: manuel a perez-quinones
 * Compile: javac SAction.java
 * see copyright at end of file
 *
 * Reusable action class used as a functional interface.
 * A functional interface is an interface with a single
 * method. Note that Java 8 has some pre-defined functional
 * interfaces, but we have opted to declare our own for
 * simplicity of use.
 *
 * SActions are stored in a hashap that pairs an SActionKey
 * with an SActionState. An SActionKey is simply two strings
 * uniquely identifying an action. Typical use is "File" and
 * "Open" identifies the open routine (action) that is called
 * when the open command it selected from the file menu.
 *
 * An SActionState wraps state information around an SAction.
 * The state information allows actions to be disabled/enabled
 * to reflect context when those actions are available in the
 * program.
 */



/**
 * functional interface for SAction
 */
public interface SAction 
{
    /**
     * Execute the action via functional interface
     */
	public void doAction();
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