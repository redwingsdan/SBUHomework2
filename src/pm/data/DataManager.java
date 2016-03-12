package pm.data;

import java.util.ArrayList;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import saf.components.AppDataComponent;
import saf.AppTemplate;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author Daniel Peterson
 * @version 1.0
 */
public class DataManager implements AppDataComponent {
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    final static ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    final static ArrayList<Ellipse> ells = new ArrayList<Ellipse>();
    static String bgColor = null;
    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
	app = initApp;
        //rects = new ArrayList();
        
    }
    
    public static void addRect(Rectangle tag) {
	rects.add(tag);
      //  System.out.println(rects.size());
	//hashTags.put(tag.tagName, tag);
    }
    
    public static void removeRect(Rectangle tag) {
	rects.remove(tag);
     //   System.out.println(rects.size());
	//hashTags.put(tag.tagName, tag);
    }

    public static void addEll(Ellipse tag) {
	ells.add(tag);
       // System.out.println(rects.size());
	//hashTags.put(tag.tagName, tag);
    }
    
    public static void removeEll(Ellipse tag) {
	ells.remove(tag);
        //System.out.println(rects.size());
	//hashTags.put(tag.tagName, tag);
    }
    
    /**
     * Accessor method for getting all the tags.
     *
     * @return A list containing all the tags used by this data manager.
     */
    public static ArrayList<Rectangle> getRects() {
      //  System.out.println(rects.size());
	return rects;        
    }
    
    public static ArrayList<Ellipse> getElls() {
     //   System.out.println(ells.size());
	return ells;        
    }
    
    public static void setBGColor(String color)
    {
        bgColor = color;
    }
    
    public static String getBGColor()
    {
        return bgColor;
    }
    
    public AppTemplate getApp()
    {
        return app;
    }
    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void reset() {

    }
}
