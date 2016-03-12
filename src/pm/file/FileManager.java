package pm.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import pm.data.DataManager;
import saf.AppTemplate;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author Daniel Peterson
 * @version 1.0
 */
public class FileManager implements AppFileComponent {

     static ArrayList<Rectangle> nodes;
     static ArrayList<Ellipse> nodes2;
     static String background;
    /**
     * This method is for saving user work, which in the case of this
     * application means the data that constitutes the page DOM.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {

   /*    
      */  
        //System.out.println("SAVE WAS CALLED");
        StringWriter sw = new StringWriter();

	// BUILD THE HTMLTags ARRAY
	DataManager dataManager = (DataManager)data;

	// THEN THE TREE
        //int size = DataManager.getRects().size();
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(int i = 0; i < DataManager.getRects().size(); i++)
        {
            Rectangle root = (Rectangle) DataManager.getRects().get(i);
            //JsonArray rectArray = arrayBuilder.build();
            //JsonObject tagObject = makeTagJsonObject(root, 1);
           // arrayBuilder.add(root);
            JsonObject rectObject = makeTagJsonObject(root);
            arrayBuilder.add(rectObject);      
        }
	JsonArray rectArray = arrayBuilder.build();
	// THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
        for(int i = 0; i < DataManager.getElls().size(); i++)
        {
            Ellipse root2 = (Ellipse) DataManager.getElls().get(i);
            //JsonArray rectArray = arrayBuilder.build();
            //JsonObject tagObject = makeTagJsonObject(root, 1);
           // arrayBuilder.add(root);
            JsonObject ellObject = makeTagJsonObject2(root2);
            arrayBuilder2.add(ellObject);      
        }
        JsonArray ellArray = arrayBuilder2.build();
        
        JsonArrayBuilder arrayBuilder3 = Json.createArrayBuilder();
        JsonObject backgroundObject = makeTagJsonObject3(DataManager.getBGColor());
        arrayBuilder3.add(backgroundObject);
        JsonArray backgroundArray = arrayBuilder3.build();
        
        
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add("rectangles", rectArray)
                .add("ellipses", ellArray)
                .add("background", backgroundArray)
		//.add("y_location", dataManager.getCSSText())
                //ellipses
		.build();
	
        
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
    }
    
    private JsonObject makeTagJsonObject(Rectangle rect) {
	//String rectName;
	//HashMap<String, String> attributes = rect.getAttributes();
	//ArrayList<String> legalParents = rect.getLegalParents();
	JsonObject jso = Json.createObjectBuilder()
		//.add("name", rect.getTagName())
                
		.add("x_location", rect.getX())
		.add("y_location", rect.getY())
                .add("height", rect.getHeight())
                .add("width", rect.getWidth())
		.add("fill_color_string", rect.getFill().toString())
		.add("border_color_string", rect.getStroke().toString())
		.add("border_width", rect.getStrokeWidth())
		//.add(JSON_TAG_PARENT_INDEX, tag.getParentIndex())
		.build();
	return jso;
    } 
    
     private JsonObject makeTagJsonObject2(Ellipse ell) {
	//String rectName;
	//HashMap<String, String> attributes = rect.getAttributes();
	//ArrayList<String> legalParents = rect.getLegalParents();
	JsonObject jso = Json.createObjectBuilder()
		//.add("name", rect.getTagName())
                
		.add("x_location", ell.getCenterX())
		.add("y_location", ell.getCenterY())
                .add("height", ell.getRadiusX())
                .add("width", ell.getRadiusY())
		.add("fill_color_string", ell.getFill().toString())
		.add("border_color_string", ell.getStroke().toString())
		.add("border_width", ell.getStrokeWidth())
		//.add(JSON_TAG_PARENT_INDEX, tag.getParentIndex())
		.build();
	return jso;
    } 
     
     private JsonObject makeTagJsonObject3(String s)
     {
        JsonObject jso = Json.createObjectBuilder()
                .add("background", s)
                .build();
        return jso;
     }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
        //System.out.println("LOAD IS CALLDE");
	DataManager dataManager = (DataManager)data;
	dataManager.reset();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// LOAD THE TAG TREE
	JsonArray jsonTagsArray = json.getJsonArray("rectangles");
	//loadTreeTags(jsonTagTreeArray, dataManager);
        nodes = new ArrayList();
	
	// FIRST UPDATE THE ROOT
        for(int j = 0; j < jsonTagsArray.size(); j++)
        {
	JsonObject rootJso = jsonTagsArray.getJsonObject(j);
        Rectangle rootData = new Rectangle();
      //  for(int i = 0; i < rootJso.size(); i++)
      //  {
           // System.out.println(rootJso.get("x_location").toString());
            Double x = Double.parseDouble(rootJso.get("x_location").toString());
            rootData.setX(x);
            
            
           // System.out.println(rootJso.get("y_location").toString());
            Double y = Double.parseDouble(rootJso.get("y_location").toString());
            rootData.setY(y);
            
          //  System.out.println(rootJso.get("height").toString());
            Double h = Double.parseDouble(rootJso.get("height").toString());
            rootData.setHeight(h);
            
          //  System.out.println(rootJso.get("width").toString());
            Double w = Double.parseDouble(rootJso.get("width").toString());
            rootData.setWidth(w);
            
          //  System.out.println(rootJso.get("fill_color_string").toString().substring(3,9));
            //Double f = Double.parseDouble(rootJso.get("fill_color_string").toString());
            Paint p = Paint.valueOf(rootJso.get("fill_color_string").toString().substring(3,9));
            
            rootData.setFill(p);
            
           // System.out.println(rootJso.get("border_color_string").toString().substring(3,9));
            //Double f = Double.parseDouble(rootJso.get("fill_color_string").toString());
            Paint bor = Paint.valueOf(rootJso.get("border_color_string").toString().substring(3,9));
            rootData.setStroke(bor);
            
           // System.out.println(rootJso.get("border_width").toString());
            Double b = Double.parseDouble(rootJso.get("border_width").toString());
            rootData.setStrokeWidth(b);
           // rootData.setX();
       // }
	//Rectangle rootData = loadRect(rootJso);
	//TreeItem root = dataManager.getHTMLRoot();
            nodes.add(rootData);
        }
        JsonArray jsonTagsArray2 = json.getJsonArray("ellipses");
	//loadTreeTags(jsonTagTreeArray, dataManager);
        nodes2 = new ArrayList();
	
	// FIRST UPDATE THE ROOT
        for(int j = 0; j < jsonTagsArray2.size(); j++)
        {
	JsonObject rootJso = jsonTagsArray2.getJsonObject(j);
        Ellipse rootData = new Ellipse();
      //  for(int i = 0; i < rootJso.size(); i++)
      //  {
           // System.out.println(rootJso.get("x_location").toString());
            Double x = Double.parseDouble(rootJso.get("x_location").toString());
            rootData.setCenterX(x);
            
            
          //  System.out.println(rootJso.get("y_location").toString());
            Double y = Double.parseDouble(rootJso.get("y_location").toString());
            rootData.setCenterY(y);
            
           // System.out.println(rootJso.get("height").toString());
            Double h = Double.parseDouble(rootJso.get("height").toString());
            rootData.setRadiusX(h);
            
           // System.out.println(rootJso.get("width").toString());
            Double w = Double.parseDouble(rootJso.get("width").toString());
            rootData.setRadiusY(w);
            
           // System.out.println(rootJso.get("fill_color_string").toString().substring(3,9));
            //Double f = Double.parseDouble(rootJso.get("fill_color_string").toString());
            Paint p = Paint.valueOf(rootJso.get("fill_color_string").toString().substring(3,9));
            
            rootData.setFill(p);
            
           // System.out.println(rootJso.get("border_color_string").toString().substring(3,9));
            //Double f = Double.parseDouble(rootJso.get("fill_color_string").toString());
            Paint bor = Paint.valueOf(rootJso.get("border_color_string").toString().substring(3,9));
            rootData.setStroke(bor);
            
           // System.out.println(rootJso.get("border_width").toString());
            Double b = Double.parseDouble(rootJso.get("border_width").toString());
            rootData.setStrokeWidth(b);
           // rootData.setX();
       // }
	//Rectangle rootData = loadRect(rootJso);
	//TreeItem root = dataManager.getHTMLRoot();
            nodes2.add(rootData);
        }
        
        JsonArray jsonTagsArray3 = json.getJsonArray("background");
	//root.getChildren().clear();
	//root.setValue(rootData);
            String bg = (jsonTagsArray3.getJsonObject(0).get("background").toString());
            background = bg;
	
	// AND GET THE CSS CONTENT
	//String cssContent = json.getString(JSON_CSS_CONTENT);
	//dataManager.setCSSText(cssContent);
    }
    
     private Rectangle loadRect(JsonObject tag) {
	    Rectangle tagToLoad = new Rectangle();
            JsonArray attributesArray = tag.getJsonArray("rectangles");
	for (int k = 0; k < attributesArray.size(); k++) {
		JsonObject attributeJso = attributesArray.getJsonObject(k);
                String attributeName = attributeJso.getString("x_location");
		//String attributeValue = attributeJso.getString(JSON_TAG_ATTRIBUTE_VALUE);
                
              //  tagToLoad.setX(tag..getX());
		
               // .add("y_location", rect.getY())
              //  .add("height", rect.getHeight())
               // .add("width", rect.getWidth())
		//.add("fill_color_string", rect.getFill().toString())
		//.add("border_color_string", rect.getStroke().toString())
		//.add("border_width", rect.getStrokeWidth())
                
	    }
	    //tagToLoad.setNodeIndex(tag.getInt(JSON_TAG_NODE_INDEX));
	   // tagToLoad.setParentIndex(tag.getInt(JSON_TAG_PARENT_INDEX));
            
	    return tagToLoad;
            
    }
    
    

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method exports the contents of the data manager to a 
     * Web page including the html page, needed directories, and
     * the CSS file.
     * 
     * @param data The data management component.
     * 
     * @param filePath Path (including file name/extension) to where
     * to export the page to.
     * 
     * @throws IOException Thrown should there be an error writing
     * out data to the file.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {

    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// NOTE THAT THE Web Page Maker APPLICATION MAKES
	// NO USE OF THIS METHOD SINCE IT NEVER IMPORTS
	// EXPORTED WEB PAGES
    }
    
   public static ArrayList<Rectangle> getNodes()
   {
       return nodes;
   }
    public static ArrayList<Ellipse> getNodes2()
   {
       return nodes2;
   }
    public static String getBackground()
    {
        return background;
    }
}
