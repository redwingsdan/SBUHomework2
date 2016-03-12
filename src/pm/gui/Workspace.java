package pm.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static pm.PropertyType.RECTANGLE_ICON;
import pm.data.DataManager;
import pm.file.FileManager;
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
 * @author Daniel Peterson
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    //ArrayList<Rectangle> rectArray = new ArrayList<Rectangle>();
    
    DataManager data;
    BorderPane pane;
    Pane shapeToolbar;
    Pane colorToolbar;
    Pane arrowToolbar;
    
   GridPane workspaceSplitPane;
    
    double starting_point_x, starting_point_y ;

   Group group_for_rectangles = new Group() ;

   Rectangle new_rectangle = null ;
   
   Rectangle selected_rectangle = null;
   
   Rectangle new_selected_rectangle = null;
   
   Ellipse new_ellipse = null;
   
   Ellipse selected_ellipse = null;
   
   Button clear;
   
   ToggleButton select;
   
   Paint old;

   boolean new_rectangle_is_being_drawn = false ;
   
   boolean new_ellipse_is_being_drawn = false ;
   
   boolean item_selected = false;
   
   String currentValue = "RECTANGLE";
   
   double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
   
   AppMessageDialogSingleton messageDialog;
   AppYesNoCancelDialogSingleton yesNoCancelDialog;
   
            Slider slider = new Slider(0, 10, 5);
            
          final ColorPicker colorPicker = new ColorPicker();
          final ColorPicker colorPicker2 = new ColorPicker();
          final ColorPicker colorPicker3 = new ColorPicker();
   
    EventHandler<MouseEvent> rectangleOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            if(getSelectedItem() == true)
            {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Rectangle)(t.getSource())).getTranslateX();
            orgTranslateY = ((Rectangle)(t.getSource())).getTranslateY();
             if(selected_rectangle != null)
            {
                selected_rectangle.setStroke(old);
            }
       //      if((selected_rectangle.equals((Rectangle)t.getSource())))
        //     {
        //         selected_rectangle = null;
         //    }
          //   else
          //   {
                selected_rectangle = (Rectangle)t.getSource();
         //    }
            if(selected_ellipse != null)
            {
                selected_ellipse.setStroke(old);
            }
            selected_ellipse = null;
            old = selected_rectangle.getStroke();
            selected_rectangle.setStroke(Color.YELLOW);
            colorPicker.setValue((Color)selected_rectangle.getFill());
            colorPicker2.setValue((Color) old);
            slider.setValue(selected_rectangle.getStrokeWidth());
            clear.setDisable(false);
          //  ((Rectangle)(t.getSource())).toFront();
     //       ((Rectangle)(t.getSource())).toBack();
            }
        }
    };
     
    EventHandler<MouseEvent> rectangleOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            if(getSelectedItem() == true)
            {
           
            double offsetX = t.getSceneX() - orgSceneX;
              
            double offsetY = t.getSceneY() - orgSceneY;
          
            double newTranslateX = orgTranslateX + offsetX;
            
            double newTranslateY = orgTranslateY + offsetY;
             
            ((Rectangle)(t.getSource())).setTranslateX(newTranslateX);
            ((Rectangle)(t.getSource())).setTranslateY(newTranslateY);
            }
        }
    };
    
    
    EventHandler<MouseEvent> ellipseOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            if(getSelectedItem() == true)
            {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Ellipse)(t.getSource())).getTranslateX();
            orgTranslateY = ((Ellipse)(t.getSource())).getTranslateY();
            if(selected_ellipse != null)
            {
                selected_ellipse.setStroke(old);
            }
            selected_ellipse = (Ellipse)t.getSource();
            if(selected_rectangle != null)
            {
                selected_rectangle.setStroke(old);
            }
            selected_rectangle = null;          
            old = selected_ellipse.getStroke();
            selected_ellipse.setStroke(Color.YELLOW);
            colorPicker.setValue((Color)selected_ellipse.getFill());
            colorPicker2.setValue((Color)old);
            slider.setValue(selected_ellipse.getStrokeWidth());
            clear.setDisable(false);
            //((Ellipse)(t.getSource())).toFront();
            //((Ellipse)(t.getSource())).toBack();
            }
        }
    };
    
    
    EventHandler<MouseEvent> ellipseOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            if(getSelectedItem() == true)
            {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
             
            ((Ellipse)(t.getSource())).setTranslateX(newTranslateX);
            ((Ellipse)(t.getSource())).setTranslateY(newTranslateY);
            }
        }
    };
   
   void setCurrentValue(String s)
   {
       currentValue = s;
   }
   
   String getCurrentValue()
   {
       return currentValue;
   }

   void setSelectedItem(boolean b) {
        item_selected = b;
    }
   
   boolean getSelectedItem()
   {
       return item_selected;
   }
   //  The following method adjusts coordinates so that the rectangle
   //  is shown "in a correct way" in relation to the mouse movement.

   void adjust_rectangle_properties( double starting_point_x,
                                     double starting_point_y,
                                     double ending_point_x,
                                     double ending_point_y,
                                     Rectangle given_rectangle,
                                     BorderPane pane)
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
      
   /*   
      if((given_rectangle.getY() + given_rectangle.getHeight()) > pane.getPrefHeight())
      {
        given_rectangle.setHeight(pane.getPrefHeight() - 50);
      }
      if(given_rectangle.getWidth() > pane.getPrefWidth())
      {
        given_rectangle.setWidth(pane.getPrefWidth());
      }
  */
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
   
   public VBox addHBox(Slider slider, ColorPicker colorPicker, ColorPicker colorPicker2, ColorPicker colorPicker3, Button snap) {
    VBox hbox = new VBox();
    hbox.setPadding(new Insets(2, 0, 2, 40)); //15
    hbox.setSpacing(20);
    hbox.setMinWidth(200);
    hbox.setMaxWidth(200);
    hbox.setMinHeight(1200);
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
      slider.setLayoutX(50);
      slider.setLayoutY(900);
     
      
      
      colorPicker.setValue(Color.RED);
      colorPicker.setLayoutX(50);
      colorPicker.setLayoutY(400);
      
      
      
      
      colorPicker2.setValue(Color.BLUE);
      colorPicker2.setLayoutX(50);
      colorPicker2.setLayoutY(600);
      
      
      
      
      
     // colorPicker3.setValue(Color.BEIGE);
     colorPicker3.setValue(Color.SNOW);
      colorPicker3.setLayoutX(50);
      colorPicker3.setLayoutY(800);
      
      snap.setPrefWidth(50);
      
      Text t1 = new Text();
      t1.setText("Fill Color");
      t1.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
      
      Text t2 = new Text();
      t2.setText("Border Color");
      t2.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
      
      Text t3 = new Text();
      t3.setText("Background Color");
      t3.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
      
      Text t4 = new Text();
      t4.setText("Border Thickness");
      t4.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
     
   // hbox.getChildren().addAll(rect, ellipse, clear, select);  
    hbox.getChildren().addAll(t3, colorPicker3, t1, colorPicker, t2, colorPicker2, t4, slider, snap);
    

    return hbox;
}
   
   public HBox addVBox(ToggleButton rect, Button ellipse, Button clear, Button select){
    HBox vbox = new HBox();
    vbox.setPadding(new Insets(20, 0, 5, 20));
    vbox.setSpacing(12);
    vbox.setMaxHeight(1200);

       // Button test = new Button();
       // test.setTranslateY(150);
       // test.setTranslateX(-100);
    
     // rect.setText("RECTANGLE");
     // rect.setLayoutY(10);
      
     // ellipse.setText("ELLIPSE");
      //ellipse.setLayoutY(10);
      
     // clear.setText("CLEAR");
      //clear.setLayoutY(10);
      
      //select.setLayoutY(10);
        
      vbox.getChildren().addAll(rect, ellipse, clear, select);


    return vbox;
}
   
    public HBox addVBox2(Button front, Button back){
    HBox vbox = new HBox();
    vbox.setPadding(new Insets(40, 0, 40, 75));
    vbox.setSpacing(25);
    vbox.setMaxHeight(1200);

       // Button test = new Button();
       // test.setTranslateY(150);
       // test.setTranslateX(-100);
    
     // rect.setText("RECTANGLE");
      //rect.setLayoutY(10);
      
     // ellipse.setText("ELLIPSE");
     // ellipse.setLayoutY(10);
      
     // clear.setText("CLEAR");
      //clear.setLayoutY(10);
      
      //select.setLayoutY(10);
        
      vbox.getChildren().addAll(back, front);


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
        colorToolbar = new FlowPane(Orientation.HORIZONTAL);
        arrowToolbar = new FlowPane(Orientation.HORIZONTAL);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //gui.getPrimaryScene()
        gui.getWindow().setTitle("Pose Maker");
        try {
            data = new DataManager(app);
        } catch (Exception ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          Button rect = new Button();
          rect.setMinSize(50, 50);
          Button ellipse = new Button();
          ellipse.setMinSize(50, 50);
          clear = new Button();
          clear.setMinSize(50, 50);
          select = new ToggleButton();
          select.setMinSize(50, 50);
          Button snap = new Button();
         // snap.setMinSize(50, 50);
          Button front = new Button();
          front.setMinSize(50, 50);
          Button back = new Button();
          back.setMinSize(50, 50);
          clear.setDisable(true);
          select.setDisable(true);
          
          //String icon = RECTANGLE_ICON.toString();
          //String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
          Image buttonImage = new Image("file:./images/Rect.png");
          rect.setGraphic(new ImageView(buttonImage));
          
          Image buttonImage2 = new Image("file:./images/Ellipse.png");
          ellipse.setGraphic(new ImageView(buttonImage2));
          
          Image buttonImage3 = new Image("file:./images/Remove.png");
          clear.setGraphic(new ImageView(buttonImage3));
          
          Image buttonImage4 = new Image("file:./images/SelectionTool.png");
          select.setGraphic(new ImageView(buttonImage4));
          
          Image buttonImage5 = new Image("file:./images/Snapshot.png");
          snap.setGraphic(new ImageView(buttonImage5));
          
          Image buttonImage6 = new Image("file:./images/MoveToFront.png");
          front.setGraphic(new ImageView(buttonImage6));
          
          Image buttonImage7 = new Image("file:./images/MoveToBack.png");
          back.setGraphic(new ImageView(buttonImage7));
          
          snap.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
                File file = new File("Pose.png");
                try
                  {
                  ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                  }
                catch(Exception e)
                  {
                      
                  }
            }
        });
          front.setLayoutX(150);
          back.setLayoutX(60);
          front.setLayoutY(100);
          back.setLayoutY(100);
          front.setOnAction((ActionEvent t) -> {
              if(selected_rectangle != null & getSelectedItem() == true)
                  {
                  selected_rectangle.toFront();
                  }
              else if(selected_ellipse != null & getSelectedItem() == true)
                  {
                   selected_ellipse.toFront();  
                  }
              
          });
          
           back.setOnAction((ActionEvent t) -> {
              if(selected_rectangle != null & getSelectedItem() == true)
                  {
                  selected_rectangle.toBack();
                  }
              else if(selected_ellipse != null & getSelectedItem() == true)
                  {
                   selected_ellipse.toBack();  
                  }
              
          });
          
          rect.setOnAction((ActionEvent t) -> {
       setCurrentValue("RECTANGLE");
       select.setSelected(false);
       setSelectedItem(false);
       gui.getPrimaryScene().setCursor(Cursor.CROSSHAIR);
       clear.setDisable(true);
       selected_rectangle = null;
   });
      
       ellipse.setOnAction((ActionEvent t) -> {
       setCurrentValue("ELLIPSE");
       select.setSelected(false);
       setSelectedItem(false);
       gui.getPrimaryScene().setCursor(Cursor.CROSSHAIR);
       clear.setDisable(true);
       selected_ellipse = null;
   });
      
       colorPicker.setOnAction((ActionEvent t) -> {
      //new_rectangle.setFill(colorPicker.getValue());
        if(selected_rectangle != null)
               {
                   selected_rectangle.setFill(colorPicker.getValue());
               }
        if(selected_ellipse != null)
               {
                   selected_ellipse.setFill(colorPicker.getValue());
               }
        });
       
       colorPicker2.setOnAction((ActionEvent t) -> {
      //new_rectangle.setFill(colorPicker2.getValue());
      
      if(selected_rectangle != null)
               {
                   selected_rectangle.setStroke(colorPicker2.getValue());
                   old = selected_rectangle.getStroke();
               }
      if(selected_ellipse != null)
               {
                   selected_ellipse.setStroke(colorPicker2.getValue());
                   old = selected_ellipse.getStroke();
               }
      
        });
       
      
       
        slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue2) -> {
      //        new_rectangle.setStrokeWidth(newValue.intValue());
            //test_rectangle.setStrokeWidth(newValue2.intValue());
            if(selected_rectangle != null)
               {
                   selected_rectangle.setStrokeWidth(slider.valueProperty().intValue());
               }
            if(selected_ellipse != null)
               {
                    selected_ellipse.setStrokeWidth(slider.valueProperty().intValue());
               }
      });
        
        
        colorToolbar.getChildren().add(addVBox(select, clear, rect, ellipse));
        VBox hbox = addHBox(slider, colorPicker, colorPicker2, colorPicker3, snap);
        shapeToolbar.getChildren().add(hbox);
        arrowToolbar.getChildren().add(addVBox2(front, back));
        
        workspaceSplitPane = new GridPane();
        //workspaceSplitPane.setHgap(10);
        workspaceSplitPane.add(colorToolbar, 0, 1);
        workspaceSplitPane.add(arrowToolbar, 0, 2);
        workspaceSplitPane.add(shapeToolbar, 0, 3);
      // workspaceSplitPane.setLeft(shapeToolbar);
       // workspaceSplitPane.getBottom().setStyle("-fx-background-color: #fff5ee;");
        workspaceSplitPane.setStyle("-fx-background-color: #BCBCFF;");
        
        //group_for_rectangles.setTr
        
        
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
        
       pane.setLayoutX(280);
       pane.setLayoutY(0);
       pane.setMinHeight(1120);
       pane.setMinWidth(1900);
       pane.setMaxHeight(1120);
       pane.setMaxWidth(1700);
       pane.setPrefWidth(1700);
       pane.setPrefHeight(1120);
       
       workspace.getChildren().add(workspaceSplitPane);
       workspace.getChildren().add(pane);
       //workspace.getChildren().add(front);
       //workspace.getChildren().add(back);
       Pane x = new Pane();
       x.setLayoutX(1700);
       workspace.getChildren().add(x);
      // workspace.getChildren().add(group_for_rectangles);
        //gui.getPrimaryScene() = new Scene(workspace);
      
      gui.getPrimaryScene().setFill( Color.BLACK ) ;
      //workspace.setStyle();
      
       colorPicker3.setOnAction((ActionEvent t) -> {
        gui.getPrimaryScene().setFill(colorPicker3.getValue());
       // System.out.println(colorPicker3.getValue().toString());
        pane.setStyle("-fx-background-color: #" + colorPicker3.getValue().toString().substring(2,8) + ";");
        DataManager.setBGColor(colorPicker3.getValue().toString().substring(2,8));
       // System.out.println(group_for_rectangles.getStyle());
       // workspace.getChildren().add(group_for_rectangles);
        //group_for_rectangles.setStyle("-fx-background-color: black;");
        
        });
      
      clear.setOnAction((ActionEvent t) ->{
           //pane.getChildren().clear();
          // selected_rectangle.relocate(5000, 5000);
           pane.getChildren().remove(selected_rectangle);
           
            try {
                  DataManager.removeRect(selected_rectangle);
             } catch (Exception ex) {
                 Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
             }   
           selected_rectangle = null;
           pane.getChildren().remove(selected_ellipse);
           selected_ellipse = null;
           gui.getPrimaryScene().setCursor(Cursor.DEFAULT);
           clear.setDisable(true);
           int size = pane.getChildren().size();
           if(pane.getChildren().size() == 1)
              {
                  select.setDisable(true);
              }
           else
              {
                 select.setDisable(false); 
              }
           //select.setSelected(false);
           //setSelectedItem(false);
       // group_for_rectangles.getChildren().addAll(colorPicker, colorPicker2, colorPicker3, slider, rect, ellipse, clear);
       });
      
      select.setOnAction((ActionEvent t) -> {
           //setSelectedItem(!getSelectedItem());
           gui.getPrimaryScene().setCursor(Cursor.DEFAULT);
           select.setSelected(true);
           setSelectedItem(true);
           
          // clear.setDisable(false);
           //System.out.println(getSelectedItem());
       });
      
      snap.setOnAction((ActionEvent t) -> {
         //pane.snapshot(params, image);
      });
      
      pane.setOnMousePressed( ( MouseEvent event ) ->
      {
          //System.out.println(event.getSource().toString());
          
         if ( new_rectangle_is_being_drawn == false & getCurrentValue().equals("RECTANGLE") & getSelectedItem() == false)
         {
            starting_point_x = (event.getSceneX() -210);
            starting_point_y = (event.getSceneY() -75);
           // starting_point_x = event.getScreenX();
           //starting_point_x = event.getX();
          // starting_point_y = event.getY();
           // starting_point_y = event.getScreenY();

            new_rectangle = new Rectangle() ;
            new_rectangle.setOnMousePressed(rectangleOnMousePressedEventHandler);
            new_rectangle.setOnMouseDragged(rectangleOnMouseDraggedEventHandler);

            // A non-finished rectangle has always the same color.
            new_rectangle.setFill( Color.SNOW ) ; // almost white color
            new_rectangle.setStroke( Color.BLACK ) ;

           // group_for_rectangles.getChildren().add( new_rectangle ) ;
           pane.getChildren().add(new_rectangle);
   
            new_rectangle_is_being_drawn = true ;
         }
         
         else if( new_ellipse_is_being_drawn == false & getCurrentValue().equals("ELLIPSE") & getSelectedItem() == false)
         {
             starting_point_x = (event.getSceneX() );
             starting_point_y = (event.getSceneY() );
             
             new_ellipse = new Ellipse();
             new_ellipse.setOnMousePressed(ellipseOnMousePressedEventHandler);
             new_ellipse.setOnMouseDragged(ellipseOnMouseDraggedEventHandler);
          
             new_ellipse.setFill(Color.SNOW);
             new_ellipse.setStroke(Color.BLACK);
             
             pane.getChildren().add(new_ellipse);
             
             new_ellipse_is_being_drawn = true;
         }
      } ) ;

      pane.setOnMouseDragged( ( MouseEvent event ) ->
      {
         if ( new_rectangle_is_being_drawn == true & getCurrentValue().equals("RECTANGLE") & getSelectedItem() == false)
         {
            double current_ending_point_x = (event.getSceneX() +9);
            double current_ending_point_y = (event.getSceneY() +38);
            //double current_ending_point_x = event.getX() ;
            //double current_ending_point_y = event.getY() ;
            //double current_ending_point_x = event.getScreenX();
            //double current_ending_point_y = event.getScreenY();

            if(current_ending_point_y > 920)
              {
                  current_ending_point_y = 920;
              }
            
            if(current_ending_point_x > 1620)
              {
                  current_ending_point_x = 1620;
              }
            
            adjust_rectangle_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         new_rectangle,
                                         pane) ;
         }
         
         if(new_ellipse_is_being_drawn == true & getCurrentValue().equals("ELLIPSE") & getSelectedItem() == false)
         {
            double current_ending_point_x = (event.getSceneX() +9);
            double current_ending_point_y = (event.getSceneY() +38);

            if(current_ending_point_y > 920)
              {
                  current_ending_point_y = 920;
              }
                       
            if(current_ending_point_x > 1620)
              {
                  current_ending_point_x = 1620;
              }
            
              
              if(new_ellipse.getCenterX()+new_ellipse.getRadiusX() > 1700 || new_ellipse.getCenterX()-new_ellipse.getRadiusX() < 0)
              {
                 // new_ellipse.setRadiusX(new_ellipse.getCenterX()-1500);
                   current_ending_point_x = current_ending_point_x = new_ellipse.getCenterX()+new_ellipse.getRadiusX();
                   //current_ending_point_x = 1700;
              }
              
              if(new_ellipse.getCenterY()+new_ellipse.getRadiusY() > 920 || new_ellipse.getCenterY()-new_ellipse.getRadiusY() < 0 )
              {
                 // new_ellipse.setRadiusY(new_ellipse.getCenterY()-720);
                  current_ending_point_y = new_ellipse.getCenterY()+new_ellipse.getRadiusY();
                  ///current_ending_point_y = 920;
              }

            
            adjust_ellipse_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         new_ellipse ) ;
         } 
      } ) ;

      pane.setOnMouseReleased( ( MouseEvent event ) ->
      {
         if ( new_rectangle_is_being_drawn == true & getCurrentValue().equals("RECTANGLE") & getSelectedItem() == false)
         {
            // Now the drawing of the new rectangle is finished.
            // Let's set the final color for the rectangle.

            new_rectangle.setFill( colorPicker.getValue() ) ;
            new_rectangle.setStroke(colorPicker2.getValue());
            new_rectangle.setStrokeWidth(slider.valueProperty().intValue());
   
            // If all colors have been used we'll start re-using colors from the
            // beginning of the array.
            //rectArray.add(new_rectangle);
            
             try {
                 
                  DataManager.addRect(new_rectangle);
             } catch (Exception ex) {
                 Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
             }         
            new_rectangle = null ;
            new_rectangle_is_being_drawn = false ;
            select.setDisable(false); 
         }
         
         if(new_ellipse_is_being_drawn = true & getCurrentValue().equals("ELLIPSE") & getSelectedItem() == false)
         {
             
             new_ellipse.setFill(colorPicker.getValue());
             new_ellipse.setStroke(colorPicker2.getValue());
             new_ellipse.setStrokeWidth(slider.valueProperty().intValue());
             DataManager.addEll(new_ellipse);
             
             new_ellipse = null;
             new_ellipse_is_being_drawn = false;
             select.setDisable(false); 
         }
      } ) ;
      
      
     
           
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
       //workspaceSplitPane.getStyleClass().add("max_pane");
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
       
        ArrayList<Rectangle> rect = FileManager.getNodes();
        ArrayList<Ellipse> ell = FileManager.getNodes2();
        String p = FileManager.getBackground();
        try
        {
        if(rect.size() != 0)
        {
            select.setDisable(false);
            pane.getChildren().clear();
            for(int i = 0; i < rect.size(); i++)
            {
                pane.getChildren().add(rect.get(i));
                DataManager.addRect(rect.get(i));
                rect.get(i).setOnMousePressed(rectangleOnMousePressedEventHandler);
                rect.get(i).setOnMouseDragged(rectangleOnMouseDraggedEventHandler);
            }
        }
        }
        catch(Exception e)
        {
            
        }
        try
        {
        if(ell.size() != 0)
        {
             select.setDisable(false);
            //pane.getChildren().clear();
            for(int i = 0; i < ell.size(); i++)
            {
                pane.getChildren().add(ell.get(i));
                DataManager.addEll(ell.get(i));
                ell.get(i).setOnMousePressed(ellipseOnMousePressedEventHandler);
                ell.get(i).setOnMouseDragged(ellipseOnMouseDraggedEventHandler);
            }
        }
        }
        catch(Exception e)
        {
            
        }
        try
        {
           if(p != null)
           {
        //System.out.println(p.substring(1,7).toString());
        pane.setStyle("-fx-background-color: #" + p.substring(1,7) + ";");
           }
        }
        catch(Exception e)
        {
        }
        
    }
}
