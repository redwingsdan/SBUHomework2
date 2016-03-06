package pm.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TreeItem;
import saf.components.AppDataComponent;
import saf.AppTemplate;
import pm.file.FileManager;
import pm.gui.Workspace;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DataManager implements AppDataComponent {

    // THIS FILE HAS THE LIST OF TAGS OUR APPLICATION WILL USE
    static final String TAG_TYPES_FILE_PATH = "data/tags.json";

    // THESE ARE ALL THE AVAILABLE TAGS FROM WHICH WE WILL CLONE

    // THIS IS THE ROOT OF THE TREE, FROM WHICH WE CAN
    // ACCESS THE ENTIRE TREE
    TreeItem htmlRoot;
    
    // THE FULL CONTENTS OF THE CSS FILE
    String cssText;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
	app = initApp;

	// WE'LL STORE THE TAGS HERE


	// NOW LOAD ALL THE TAGS WE'LL USE
	FileManager fileManager = (FileManager) app.getFileComponent();
	fileManager.loadHTMLTags(this, TAG_TYPES_FILE_PATH);
    }
    
    /**
     * Accessor method for getting the CSS text.
     * 
     * @return The contents of the CSS file for the page.
     */
    public String getCSSText() {
	return cssText;
    }
    
    /**
     * Mutator method for setting css text.
     * 
     * @param initCSSText The text to set for the css text.
     */
    public void setCSSText(String initCSSText) {
	cssText = initCSSText;
    }

    /**
     * Accessor method for getting the tree's root node.
     *
     * @return The root of the tree.
     */
    public TreeItem getHTMLRoot() {
	return htmlRoot;
    }

    /**
     * Mutator method for setting the tree's root node.
     *
     * @param initHTMLRoot The value to set as the root of the tree.
     */
    public void setHTMLRoot(TreeItem initHTMLRoot) {
	htmlRoot = initHTMLRoot;
    }

    /**
     * Accessor method for getting a tag.
     *
     * @param tagName The name of the tag to return.
     *
     * @return The HTMLTagPrototype object that has tagName as its name.

    /**
     * This method adds the tag argument to the set of tags.
     *
     * @param tag A tag representing an HTML element.

    /**
     * Accessor method for getting all the tags.


    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void reset() {
	// LET'S BUILD OUR START TAGS
	
	// NOW MAKE THE NODES
	Workspace workspace = (Workspace) app.getWorkspaceComponent();


	// FIRST CLEAR OUT ANY OLD STUFF
	htmlRoot.getChildren().clear();

	// AND ARRANGE THEM IN THE TREE

	
	// AND FINALLY CLEAR THE CSS
	cssText = "";
    }
}
