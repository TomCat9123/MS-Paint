package basicpaintapp1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author MoaathAlrajab
 */
public class FXMLDocumentController implements Initializable {

    private String selectedShape = "LINE";
    private Color selectedColor = Color.BLUE;
    private double scale;
    double srtX = 0, srtY = 0;
    double endX = 0, endY = 0;
    Stack<Image> undoStack = new Stack<>();
    private Label label;
    
    @FXML
    private ColorPicker mColorPicker;
    private Group c;
    @FXML
    private Canvas mCanvas;
    
    private GraphicsContext gc;
    
    @FXML
    private Slider mSlider;

    @FXML
    private void openFile(ActionEvent event) throws IOException {
                        
        //gets & creates the stage to open the save dialog window from the mslider
        Stage s = (Stage) mSlider.getScene().getWindow();
        
        //takes the snapshot of the canvas
        //WritableImage image = mCanvas.snapshot(new SnapshotParameters(), null);
                
        //the fileChooser window and the extension (PNG) that the snapshot can be saved as
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fs.setTitle("Save Window");
        
        //Allows for the fileChoose dialog window to be shown.
        File file = fs.showOpenDialog(s);
          

        try 
        {
           if(file != null){
               double cWidth = mCanvas.getWidth();
               double cHeight = mCanvas.getHeight();
               String imagePath = (file.toURI().toString());
               Image image = new Image(imagePath);
               mCanvas.getGraphicsContext2D().drawImage(image, 0, 0, cWidth, cHeight);
           }
        } catch ( Exception ex)
        {
            System.out.println(ex.toString());
        }

    }

    @FXML
    private void saveFile(ActionEvent event) {
                
        //gets & creates the stage to open the save dialog window from the mslider
        Stage s = (Stage) mSlider.getScene().getWindow();
        
        //takes the snapshot of the canvas
        WritableImage image = mCanvas.snapshot(new SnapshotParameters(), null);
                
        //the fileChooser window and the extension (PNG) that the snapshot can be saved as
        FileChooser fs = new FileChooser();
        fs.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fs.setTitle("Save Window");
        
        //Allows for the fileChoose dialog window to be shown.
        File file = fs.showSaveDialog(s);
        
        try 
        {
           if(file != null){
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
           }
        } catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void setShape(ActionEvent event
    ) {
        Button btn = (Button) event.getSource();

        switch (btn.getText()) {
            case "Line":
                selectedShape = "LINE";
                break;
            case "Rect":
                selectedShape = "RECT";
                break;
            case "Circle":
                selectedShape = "CIRCLE";
                break;
        }
    }

    @FXML
    private void startShape(MouseEvent event) {
        srtX = event.getX();
        srtY = event.getY();
    }

    @FXML
    private void startDraw(MouseEvent event) {
        pushUndo();
        endX = event.getX();
        endY = event.getY();
        gc = mCanvas.getGraphicsContext2D();
        gc.setStroke(selectedColor);
        System.out.println("" + selectedColor);
        gc.setLineWidth(mSlider.getValue());
        switch (selectedShape) {
            case "LINE":
                gc.strokeLine(srtX, srtY, endX, endY);
                break;
            case "RECT":
                gc.strokeRect(srtX, srtY, (endX - srtX), (endY - srtY));
                break;
            case "CIRCLE":
                gc.strokeOval(srtX, srtY, Math.abs(endX - srtX), Math.abs(endY - srtY));
                break;

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void selectColor(ActionEvent event) {
        selectedColor = mColorPicker.getValue();
    }

    //creates a new file by clearing the canvas of the previous drawings.
    @FXML
    private void newFile(ActionEvent event) {
        double cWidth = mCanvas.getWidth();
        double cHeight = mCanvas.getHeight();
        System.out.println(cWidth);
        System.out.println(cHeight);
        gc.clearRect(0, 0, cWidth, cHeight);
    }

    @FXML
    private void exitFunc(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void undoFuncs(ActionEvent event) {
        if(!undoStack.empty()){
            Image undoImage = undoStack.pop();
            mCanvas.getGraphicsContext2D().drawImage(undoImage, 0, 0);
        }
    }

    @FXML
    private void zoomIn(ActionEvent event) {
        scale += 0.1;
        mCanvas.setScaleX(mCanvas.getScaleX() + scale);
    }

    @FXML
    private void zoomOut(ActionEvent event) {
        scale += 0.1;
        mCanvas.setScaleX(mCanvas.getScaleX() + scale);
    }

    private void pushUndo(){
        Image snapshot = mCanvas.snapshot(null, null);
        undoStack.push(snapshot);
    }
    
}
