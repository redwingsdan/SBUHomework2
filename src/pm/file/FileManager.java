package pm.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;
import pm.data.DataManager;
import static pm.file.FileManager.JSON_TAG_NAME;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class FileManager implements AppFileComponent {
    // FOR JSON LOADING
    static final String JSON_TAGS_ARRAY_NAME = "tags";
    static final String JSON_TAG_NAME = "tag";
    static final String JSON_TAG_ATTRIBUTES = "attributes";
    static final String JSON_TAG_ATTRIBUTE_NAME = "attribute_name";
    static final String JSON_TAG_ATTRIBUTE_VALUE = "attribute_value";
    static final String JSON_TAG_LEGAL_PARENTS = "legal_parents";
    static final String JSON_TAG_HAS_CLOSING_TAG = "has_closing_tag";
    static final String JSON_TAG_TREE = "tag_tree";
    static final String JSON_TAG_NUMBER_OF_CHILDREN = "number_of_children";
    static final String JSON_TAG_NODE_INDEX = "node_index";
    static final String JSON_TAG_PARENT_INDEX = "parent_index";
    static final String JSON_CSS_CONTENT = "css_content";
    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    
    // THIS IS THE TEMP PAGE FOR OUR SITE
    public static final String INDEX_FILE = "index.html";
    public static final String CSS_FILE = "home.css";
    public static final String PATH_CSS = "./temp/css/";
    public static final String TEMP_CSS_PATH = PATH_CSS + CSS_FILE;
    public static final String PATH_TEMP = "./temp/";
    public static final String TEMP_PAGE = PATH_TEMP + INDEX_FILE;
    
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
	StringWriter sw = new StringWriter();

	// BUILD THE HTMLTags ARRAY
	DataManager dataManager = (DataManager)data;

	// THEN THE TREE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	TreeItem root = dataManager.getHTMLRoot();
	JsonArray nodesArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_TAG_TREE, nodesArray)
		.add(JSON_CSS_CONTENT, dataManager.getCSSText())
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
    
    // HELPER METHOD FOR SAVING DATA TO A JSON FORMAT
    
    
    int maxNodeCounter;
    
    // HELPER METHOD FOR SAVING DATA TO A JSON FORMAT
   
    
    // HELPER METHOD FOR SAVING DATA TO A JSON FORMAT
    private JsonArray buildJsonArray(ArrayList<String> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (String d : data) {
           jsb.add(d);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
  
    // HELPER METHOD FOR SAVING DATA TO A JSON FORMAT
    private JsonArray makeAttributesJsonArray(HashMap<String,String> attributes) {
	Set<String> keys = attributes.keySet();
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	for (String attributeName : keys) {
	    String attributeValue = attributes.get(attributeName);
	    JsonObject jso = Json.createObjectBuilder()
		.add(JSON_TAG_ATTRIBUTE_NAME, attributeName)
		.add(JSON_TAG_ATTRIBUTE_VALUE, attributeValue)
		.build();
	    arrayBuilder.add(jso);
	}
	JsonArray jA = arrayBuilder.build();
	return jA;
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
   
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
   
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    
static String s = "";
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
 
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
   
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
	// WALK THE TREE
	DataManager pageData = ((DataManager)data);
	TreeItem root = pageData.getHTMLRoot();
	
	// WRITE OUT THE HTML
	PrintWriter out = new PrintWriter(filePath);
	out.print(DEFAULT_DOCTYPE_DECLARATION);
	out.close();
    }
    
    /**
     * This function writes the CSS content out to the CSS file
     * that is found using the filePath argument.
     * 
     * @param cssContent The CSS content to write.
     * 
     * @param filePath The path to the CSS file.
     * 
     * @throws IOException Thrown should there be any problems writing
     * to the CSS File.
     */
    public void exportCSS(String cssContent, String filePath) throws IOException {
	PrintWriter out = new PrintWriter(filePath);
	out.print(cssContent);
	out.close();
    }
    
    // HELPER METHOD FOR EXPORTING THE SITE
    
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
    
    /**
     * This method loads the HTML tags that the application will let the
     * user build with into the data manager.
     * 
     * @param data The data management component for this application. This method
     * will load tags from the file into this object.
     * 
     * @param filePath File path and name/extension of the JSON file that 
     * contains the list of tags to be used for editing.
     * 
     * @throws IOException Thrown should there be a problem reading from
     * the JSON file.
     */
    public void loadHTMLTags(DataManager data, String filePath) throws IOException {
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// GET THE ARRAY
	JsonArray tagsArray = json.getJsonArray(JSON_TAGS_ARRAY_NAME);	
	
	// AND LOAD ALL THE ITEMS IN
	

	    // AND NOW GET THE LEGAL PARENTS
	  
    }
    
    /**
     * This function clears the contents of the file argument.
     * 
     * @param filePath The file to clear.
     * 
     * @throws IOException Thrown should there be an issue writing
     * to the file to clear.
     */
    public void clearFile(String filePath) throws IOException {
	PrintWriter out = new PrintWriter(filePath);
	out.print("");
	out.close();
    }

    @Override
    public void loadData(AppDataComponent adc, String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
