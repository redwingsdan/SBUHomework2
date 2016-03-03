package pm.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import pm.PropertyType;
import static pm.PropertyType.UPDATE_ERROR_MESSAGE;
import static pm.PropertyType.UPDATE_ERROR_TITLE;
import pm.data.DataManager;
import pm.file.FileManager;
import properties_manager.PropertiesManager;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.AppYesNoCancelDialogSingleton;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    static final String CLASS_MAX_PANE = "max_pane";
    static final String CLASS_TAG_BUTTON = "tag_button";
    static final String EMPTY_TEXT = "";
    static final int BUTTON_TAG_WIDTH = 75;
    
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    //PageEditController pageEditController;
    
    Pane workspacePane;
    
    BorderPane leftPane;
    
    Pane shapeToolbar;
    
    Button colorButton;
    
    Button shapeButton;
    
    ArrayList<Button> shapeButtons;
    HashMap<String, Button> shapes;
    
    TreeView htmlTree;
    TreeItem<Button>htmlRoot;
    
    Label shapeEditorLabel;
    ArrayList<Label> shapePropertyLabels;
    ArrayList<TextField> shapePropertyTextFields;
    
    VBox editVBox;
    
    ScrollPane shapeScrollPane;
    ScrollPane shapeEditorScrollPane;
    ScrollPane shapeToolbarScrollPane;
    
    GridPane shapeEditorPane;
    
    WebEngine htmlEngine;
    WebView htmlView;
    
    
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
        
        PropertiesManager propsSingleton = PropertiesManager.getPropertiesManager();
        
        workspace = new BorderPane();
        leftPane = new BorderPane();
        
       // pageEditController = new PageEditController((PoseMaker) app);
       
       shapeToolbar = new FlowPane(Orientation.VERTICAL);
       
       shapeToolbarScrollPane = new ScrollPane(shapeToolbar);
       shapeToolbarScrollPane.setFitToHeight(true);
       
       shapeButton = gui.initChildButton(shapeToolbar, PropertyType.SELECTION_TOOL_ICON.toString(), PropertyType.SELECTION_TOOL_TOOLTIP.toString(), true);
       shapeButton.setMaxWidth(50);
       shapeButton.setMinWidth(50);
       shapeButton.setPrefWidth(50);
       //shapeButton.setOnAction(e -> {
       //    pageEditController.handleSelectElementRequest();
      // });
       
      FileManager fileManager = (FileManager) app.getFileComponent();
      DataManager datamanager = (DataManager) app.getDataComponent();
      reloadWorkspace();
      
      
      
      shapeToolbar.getChildren().add(shapeButton); /////
      
      
      
      shapeEditorPane = new GridPane();
      shapeEditorScrollPane = new ScrollPane(shapeEditorPane);
      
      leftPane.setLeft(shapeToolbarScrollPane);
      leftPane.setCenter(shapeScrollPane);
      leftPane.setBottom(shapeEditorScrollPane);
      
      workspacePane = new Pane();
      
      workspace = new Pane();
      workspace.getChildren().add(workspacePane);
      
      workspaceActivated = false;
      
   //   dataManager.setHTMLRoot(htmlRoot);
   //   fileManager.exportData(dataManager, TEMP_PAGE);
   //     loadTempPage();
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        shapeToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        shapeButton.getStyleClass().add("tag_button");
        
        leftPane.getStyleClass().add("max_pane");
        
        
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        try {
            //pageEditController.enable(false);
            
           // shapeEditorPane.getChildren().clear();
            //shapeEditorPane.add(tagEditorLabel, 0, 0, 2, 1);
            
         //   DataManager dataManager = (DataManager) app.getDataComponent();
            
         //   FileManager fileManager = (FileManager) app.getFileComponent();
	   // fileManager.exportData(dataManager, TEMP_PAGE);
           
           
     //      pageEditController.enable(true);
            
        }
        catch(Exception e)
        {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	    dialog.show(props.getProperty(UPDATE_ERROR_TITLE), props.getProperty(UPDATE_ERROR_MESSAGE)); 
        }
    }
    
    
}
