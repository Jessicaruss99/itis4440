/**
 * File: SActionKey.java
 * ITIS 4440 User Interface Design and Implementation
 * @author: manuel a perez-quinones
 * Compile: javac SActionKey.java
 * see copyright at end of file
 *
 * Key to be used in the hashmap that stores SAction
 * objects. This method provides the required support
 * to use objects of this type as keys in a hashmap.
 * The hash code to be used is simply the String hascode
 * for the key which is a concatenation of the 
 * context and name fields.
 * 
 */


public class SActionKey
{
	private String context;    // typically the file, e.g. File
	private String name;       // typically the menu items, e.g. Open
	private String key;        // concat of above two

    /**
     * Constructor
     * @param context for the key
     * @param name for the key
     */
	public SActionKey(String context, String name)
	{
		this.context = context;
		this.name = name;
		key = this.context.concat(this.name);
	}

    /**
     * Returns the context for this action key.
     * @return context
     */
	public String getContext() { return context; }

    /**
     * Returns the name for this action.
     * @return name (string)
     */
	public String getName() { return name; }

    /**
     * Returns the hashcode for his action key.
     * @return the hashcode as computed by String class
     */
    @Override
    public int hashCode() {
        return key.hashCode();
    }

    /**
     * Compares this object with parameter o
     * @param o to compare against
     * @return true if context and name are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        SActionKey action = (SActionKey) o;
        return (context.equals(action.context) 
          && name.equals(action.name));
    }

    /**
     * Returns a string representation of the context and
     * name for this object. Built by concatenating the
     * two with a ":" as separator.
     * @return string
     */
    @Override
    public String toString() {
    	return getContext()+":"+getName();
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