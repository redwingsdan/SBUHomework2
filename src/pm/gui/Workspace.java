package pm.gui;

import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static pm.PropertyType.RECTANGLE_ICON;
import properties_manager.PropertiesManager;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
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

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    BorderPane pane;
    Pane shapeToolbar;
    Pane colorToolbar;
    
    BorderPane workspaceSplitPane;
    
    double starting_point_x, starting_point_y ;

   Group group_for_rectangles = new Group() ;

   Rectangle new_rectangle = null ;
   
   Ellipse new_ellipse = null;

   boolean new_rectangle_is_being_drawn = false ;
   
   boolean new_ellipse_is_being_drawn = false ;
   
   String currentValue = "RECTANGLE";
   
   AppMessageDialogSingleton messageDialog;
   AppYesNoCancelDialogSingleton yesNoCancelDialog;
   
   void setCurrentValue(String s)
   {
       currentValue = s;
   }
   
   String getCurrentValue()
   {
       return currentValue;
   }

   //  The following method adjusts coordinates so that the rectangle
   //  is shown "in a correct way" in relation to the mouse movement.

   void adjust_rectangle_properties( double starting_point_x,
                                     double starting_point_y,
                                     double ending_point_x,
                                     double ending_point_y,
                                     Rectangle given_rectangle )
   {
      given_rectangle.setX( starting_point_x ) ;
      given_rectangle.setY( starting_point_y ) ;
      given_rectangle.setWidth( ending_point_x - starting_point_x ) ;
      given_rectangle.setHeight( ending_point_y - starting_point_y ) ;

      if ( given_rectangle.getWidth() < 0 )
      {
         given_rectangle.setWidth( - given_rectangle.getWidth() ) ;
         given_rectangle.setX( given_rectangle.getX() - given_rectangle.getWidth() ) ;
      }

      if ( given_rectangle.getHeight() < 0 )
      {
         given_rectangle.setHeight( - given_rectangle.getHeight() ) ;
         given_rectangle.setY( given_rectangle.getY() - given_rectangle.getHeight() ) ;
      }
   }
   
   
   void adjust_ellipse_properties( double starting_point_x,
                                     double starting_point_y,
                                     double ending_point_x,
                                     double ending_point_y,
                                     Ellipse given_ellipse )
   {
       //given_ellipse.centerXProperty()
      given_ellipse.setCenterX(starting_point_x) ;
      given_ellipse.setCenterY( starting_point_y ) ;
      given_ellipse.setRadiusX(ending_point_x - starting_point_x ) ;
      given_ellipse.setRadiusY( ending_point_y - starting_point_y ) ;

      if ( given_ellipse.getRadiusY() < 0 )
      {
         given_ellipse.setRadiusY( - given_ellipse.getRadiusY() ) ;
         //given_ellipse.setRadiusX( given_ellipse.getCenterX() - given_ellipse.getRadiusX() ) ;
      }

      if ( given_ellipse.getRadiusX() < 0 )
      {
         given_ellipse.setRadiusX( - given_ellipse.getRadiusX() ) ;
        // given_ellipse.setRadiusY( given_ellipse.getCenterY() - given_ellipse.getRadiusX() ) ;
      }
   }
   
   public VBox addHBox(Slider slider, ColorPicker colorPicker, ColorPicker colorPicker2, ColorPicker colorPicker3) {
    VBox hbox = new VBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    hbox.setMinWidth(200);
    hbox.setMaxWidth(200);
    hbox.setMinHeight(2000);
    hbox.setMaxHeight(2000);
    //hbox.setStyle("-fx-background-color: #336699;");

   // Button buttonCurrent = new Button("Current");
   // buttonCurrent.setPrefSize(100, 20);

    //Button buttonProjected = new Button("Projected");
    //buttonProjected.setPrefSize(100, 20);
    
      slider.setShowTickMarks(true);
      slider.setShowTickLabels(true);
      slider.setMajorTickUnit(2);
      slider.setMinorTickCount(1);
      slider.setBlockIncrement(1);
      slider.setLayoutX(0);
      slider.setLayoutY(1100);
     
      
      
      colorPicker.setValue(Color.RED);
      colorPicker.setLayoutX(0);
      colorPicker.setLayoutY(500);
      
      
      
      
      colorPicker2.setValue(Color.BLUE);
      colorPicker2.setLayoutX(0);
      colorPicker2.setLayoutY(700);
      
      
      
      
      
      colorPicker3.setValue(Color.BEIGE);
      colorPicker3.setLayoutX(0);
      colorPicker3.setLayoutY(900);
      
      Text t1 = new Text();
      t1.setText("Fill Color");
      
      Text t2 = new Text();
      t2.setText("Border Color");
      
      Text t3 = new Text();
      t3.setText("Background Color");
      
      Text t4 = new Text();
      t4.setText("Border Thickness");
      
    hbox.getChildren().addAll(t1, colorPicker, t2, colorPicker2, t3, colorPicker3, t4, slider);

    return hbox;
}
   
   public HBox addVBox(Button rect, Button ellipse, Button clear){
    HBox vbox = new HBox();
    vbox.setPadding(new Insets(10));
    vbox.setSpacing(8);

    
     // rect.setText("RECTANGLE");
      rect.setLayoutY(20);
      
     // ellipse.setText("ELLIPSE");
      ellipse.setLayoutY(50);
      
     // clear.setText("CLEAR");
      clear.setLayoutY(50);
        
      vbox.getChildren().addAll(rect, ellipse, clear);


    return vbox;
}

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
        
        workspace = new BorderPane();
        pane = new BorderPane();
        
        shapeToolbar = new FlowPane(Orientation.VERTICAL);
        colorToolbar = new FlowPane(Orientation.VERTICAL);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //gui.getPrimaryScene()
        gui.getWindow().setTitle("Pose Maker");
        
          Slider slider = new Slider(0, 10, 5);
          final ColorPicker colorPicker = new ColorPicker();
          final ColorPicker colorPicker2 = new ColorPicker();
          final ColorPicker colorPicker3 = new ColorPicker();
          Button rect = new Button();
          Button ellipse = new Button();
          Button clear = new Button();
          
          //String icon = RECTANGLE_ICON.toString();
          //String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
          Image buttonImage = new Image("file:./images/Rect.png");
          rect.setGraphic(new ImageView(buttonImage));
          
          Image buttonImage2 = new Image("file:./images/Ellipse.png");
          ellipse.setGraphic(new ImageView(buttonImage2));
          
          Image buttonImage3 = new Image("file:./images/Remove.png");
          clear.setGraphic(new ImageView(buttonImage3));
          
          rect.setOnAction((ActionEvent t) -> {
       setCurrentValue("RECTANGLE");
   });
      
       ellipse.setOnAction((ActionEvent t) -> {
       setCurrentValue("ELLIPSE");
   });
      
       colorPicker.setOnAction((ActionEvent t) -> {
      //new_rectangle.setFill(colorPicker.getValue());
        });
       
       colorPicker2.setOnAction((ActionEvent t) -> {
      //new_rectangle.setFill(colorPicker2.getValue());
        });
       
      
       
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue2) -> {
      //        new_rectangle.setStrokeWidth(newValue.intValue());
            //test_rectangle.setStrokeWidth(newValue2.intValue());
      });
        
        
        colorToolbar.getChildren().add(addVBox(clear, rect, ellipse));
        VBox hbox = addHBox(slider, colorPicker, colorPicker2, colorPicker3);
        shapeToolbar.getChildren().add(hbox);
        
        workspaceSplitPane = new BorderPane();
        workspaceSplitPane.setLeft(colorToolbar);
        workspaceSplitPane.setBottom(shapeToolbar);
        workspaceSplitPane.setStyle("-fx-background-color: #8fd8d8;");
        
        
        pane.setRight(group_for_rectangles);
        
      //  pane.setBottom(shapeToolbar);
      //  pane.setLeft(colorToolbar);
        //pane.setCenter(group_for_rectangles);
        
      //  GridPane grid = new GridPane();
       // grid.setHgap(50);
       // grid.setVgap(50);
       // grid.add(group_for_rectangles, 2, 2);
        
        workspace = new Pane();
       // workspace.getChildren().add(pane);
       // workspace.getChildren().add(group_for_rectangles);
        
       pane.setLayoutX(200);
       pane.setLayoutY(0);
       pane.setMinHeight(50);
       pane.setMinWidth(50);
       pane.setMaxHeight(1000);
       pane.setMaxWidth(1000);
       workspace.getChildren().add(workspaceSplitPane);
       workspace.getChildren().add(pane);
      // workspace.getChildren().add(group_for_rectangles);
        //gui.getPrimaryScene() = new Scene(workspace);
      
      gui.getPrimaryScene().setFill( Color.BLACK ) ;
      //workspace.setStyle();
      
       colorPicker3.setOnAction((ActionEvent t) -> {
        gui.getPrimaryScene().setFill(colorPicker3.getValue());
        System.out.println(colorPicker3.getValue().toString());
        pane.setStyle("-fx-background-color: #" + colorPicker3.getValue().toString().substring(2,8) + ";");
        System.out.println(group_for_rectangles.getStyle());
       // workspace.getChildren().add(group_for_rectangles);
        //group_for_rectangles.setStyle("-fx-background-color: black;");
        
        });
      
      clear.setOnAction((ActionEvent t) ->{
           group_for_rectangles.getChildren().clear();
       // group_for_rectangles.getChildren().addAll(colorPicker, colorPicker2, colorPicker3, slider, rect, ellipse, clear);
       });
      
      
      gui.getPrimaryScene().setOnMousePressed( ( MouseEvent event ) ->
      {
          System.out.println(getCurrentValue());
         if ( new_rectangle_is_being_drawn == false & getCurrentValue().equals("RECTANGLE"))
         {
            starting_point_x = (event.getSceneX() );
            starting_point_y = (event.getSceneY() );
           // starting_point_x = event.getScreenX();
           //starting_point_x = event.getX();
          // starting_point_y = event.getY();
           // starting_point_y = event.getScreenY();

            new_rectangle = new Rectangle() ;

            // A non-finished rectangle has always the same color.
            new_rectangle.setFill( Color.SNOW ) ; // almost white color
            new_rectangle.setStroke( Color.BLACK ) ;

            group_for_rectangles.getChildren().add( new_rectangle ) ;
   
            new_rectangle_is_being_drawn = true ;
         }
         
         else if( new_ellipse_is_being_drawn == false & getCurrentValue().equals("ELLIPSE"))
         {
             starting_point_x = event.getSceneX() ;
             starting_point_y = event.getSceneY() ;
             
             new_ellipse = new Ellipse();
             
             new_ellipse.setFill(Color.SNOW);
             new_ellipse.setStroke(Color.BLACK);
             
             group_for_rectangles.getChildren().add(new_ellipse);
             
             new_ellipse_is_being_drawn = true;
         }
      } ) ;

      gui.getPrimaryScene().setOnMouseDragged( ( MouseEvent event ) ->
      {
         if ( new_rectangle_is_being_drawn == true & getCurrentValue().equals("RECTANGLE"))
         {
            double current_ending_point_x = (event.getSceneX());
            double current_ending_point_y = (event.getSceneY());
            //double current_ending_point_x = event.getX() ;
            //double current_ending_point_y = event.getY() ;
            //double current_ending_point_x = event.getScreenX();
            //double current_ending_point_y = event.getScreenY();

            adjust_rectangle_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         new_rectangle ) ;
         }
         
         if(new_ellipse_is_being_drawn == true & getCurrentValue().equals("ELLIPSE"))
         {
            double current_ending_point_x = event.getSceneX() ;
            double current_ending_point_y = event.getSceneY() ;

            adjust_ellipse_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         new_ellipse ) ;
         } 
      } ) ;

      gui.getPrimaryScene().setOnMouseReleased( ( MouseEvent event ) ->
      {
         if ( new_rectangle_is_being_drawn == true & getCurrentValue().equals("RECTANGLE"))
         {
            // Now the drawing of the new rectangle is finished.
            // Let's set the final color for the rectangle.

            new_rectangle.setFill( colorPicker.getValue() ) ;
            new_rectangle.setStroke(colorPicker2.getValue());
            new_rectangle.setStrokeWidth(slider.valueProperty().intValue());
   
            // If all colors have been used we'll start re-using colors from the
            // beginning of the array.

            new_rectangle = null ;
            new_rectangle_is_being_drawn = false ;
         }
         
         if(new_ellipse_is_being_drawn = true & getCurrentValue().equals("ELLIPSE"))
         {
             new_ellipse.setFill(colorPicker.getValue());
             new_ellipse.setStroke(colorPicker2.getValue());
             new_ellipse.setStrokeWidth(slider.valueProperty().intValue());
             
             new_ellipse = null;
             new_ellipse_is_being_drawn = false;
         }
      } ) ;
      
      new_rectangle = new Rectangle();
      new_rectangle.setX( 0 ) ;
      new_rectangle.setY( 0 ) ;
      new_rectangle.setWidth( 10 ) ;
      new_rectangle.setHeight( 10 ) ;
       group_for_rectangles.getChildren().add( new_rectangle ) ;
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
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {

    }
}
