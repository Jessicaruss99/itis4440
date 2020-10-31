/**
 * File: SActionState.java
 * ITIS 4440 User Interface Design and Implementation
 * @author: manuel a perez-quinones
 * Compile: javac SActionState.java
 * see copyright at end of file
 *
 * Wrapper around SAction to add state information.
 * Actions can be enabled/disabled.
 * 
 */


public class SActionState
{
	private SAction action;		// functional interface
	private boolean enabled;	// state

    /**
     * Constructor - action should not be NULL or
     * undefine things will happen.
     * @param action to be stored internally
     */
	public SActionState(SAction action)
	{
		this.action = action;
		enabled = true;
	}

    /**
     * Execution method for this action, checks the enabled
     * flag and if enabled, then calls the its SAction.
     */
	public void doAction() {
		if (isEnabled() && (action != null))
			action.doAction();
	}

    /**
     * Returns the action for this object
     * @return SAction stored in the constructor
     */
    SAction getAction() {
        return action;
    }

    /**
     * Returns true if this action is enabled.
     * @return true if enabled, false otherwise
     */
	public boolean isEnabled() { return enabled; }

    /**
     * Set the enabled state to true
     */
	public void enable() { enabled = true; }

    /**
     * Set the enabled state to false (disabled)
     */
	public void disable() { enabled = false; }
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