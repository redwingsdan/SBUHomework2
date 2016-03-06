package pm.controller;

import java.io.IOException;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebEngine;
import properties_manager.PropertiesManager;
import saf.ui.AppMessageDialogSingleton;
import static pm.PropertyType.ADD_ELEMENT_ERROR_MESSAGE;
import static pm.PropertyType.ADD_ELEMENT_ERROR_TITLE;
import static pm.PropertyType.ATTRIBUTE_UPDATE_ERROR_MESSAGE;
import static pm.PropertyType.ATTRIBUTE_UPDATE_ERROR_TITLE;
import static pm.PropertyType.CSS_EXPORT_ERROR_MESSAGE;
import static pm.PropertyType.CSS_EXPORT_ERROR_TITLE;
import static pm.PropertyType.ILLEGAL_NODE_REMOVAL_ERROR_MESSAGE;
import static pm.PropertyType.ILLEGAL_NODE_REMOVAL_ERROR_TITLE;
import static pm.PropertyType.REMOVE_ELEMENT_ERROR_MESSAGE;
import static pm.PropertyType.REMOVE_ELEMENT_ERROR_TITLE;
import pm.PoseMaker;
import pm.data.DataManager;
import pm.file.FileManager;
import static pm.file.FileManager.TEMP_CSS_PATH;
import static pm.file.FileManager.TEMP_PAGE;
import pm.gui.Workspace;

/**
 * This class provides event programmed responses to workspace interactions for
 * this application for things like adding elements, removing elements, and
 * editing them.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class PageEditController {

    // HERE'S THE FULL APP, WHICH GIVES US ACCESS TO OTHER STUFF
    PoseMaker app;

    // WE USE THIS TO MAKE SURE OUR PROGRAMMED UPDATES OF UI
    // VALUES DON'T THEMSELVES TRIGGER EVENTS
    private boolean enabled;

    /**
     * Constructor for initializing this object, it will keep the app for later.
     *
     * @param initApp The JavaFX application this controller is associated with.
     */
    public PageEditController(PoseMaker initApp) {
	// KEEP IT FOR LATER
	app = initApp;
    }

    /**
     * This mutator method lets us enable or disable this controller.
     *
     * @param enableSetting If false, this controller will not respond to
     * workspace editing. If true, it will.
     */
    public void enable(boolean enableSetting) {
	enabled = enableSetting;
    }

    /**
     * This function responds live to the user typing changes into a text field
     * for updating element attributes. It will respond by updating the
     * appropriate data and then forcing an update of the temp site and its
     * display.
     *
     * @param selectedTag The element in the DOM (our tree) that's currently
     * selected and therefore is currently having its attribute updated.
     *
     * @param attributeName The name of the attribute for the element that is
     * currently being updated.
     *
     * @param attributeValue The new value for the attribute that is being
     * updated.
     */
   

    /**
     * This function responds to when the user tries to add an element to the
     * tree being edited.
     *
     * @param element The element to add to the tree.
     */
    

    /**
     * This function responds to when the user requests to remove an element
     * from the tree. It responds by removing he currently selected node.
     */
    

    /**
     * This function provides a response to when the user changes the CSS
     * content. It responds but updating the data manager with the new CSS text,
     * and by exporting the CSS to the temp css file.
     *
     * @param cssContent The css content.
     *
     * @throws IOException Thrown should an error occur while writing out to the
     * CSS file.
     */
    public void handleCSSEditing(String cssContent) {
	if (enabled) {
	    try {
		// MAKE SURE THE DATA MANAGER GETS THE CSS TEXT
		DataManager dataManager = (DataManager) app.getDataComponent();
		dataManager.setCSSText(cssContent);

		// WRITE OUT THE TEXT TO THE CSS FILE
		FileManager fileManager = (FileManager) app.getFileComponent();
		fileManager.exportCSS(cssContent, TEMP_CSS_PATH);

		// REFRESH THE HTML VIEW VIA THE ENGINE
		Workspace workspace = (Workspace) app.getWorkspaceComponent();
		WebEngine htmlEngine = workspace.getHTMLEngine();
		htmlEngine.reload();
	    } catch (IOException ioe) {
		AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		dialog.show(props.getProperty(CSS_EXPORT_ERROR_TITLE), props.getProperty(CSS_EXPORT_ERROR_MESSAGE));
	    }
	}
    }
}