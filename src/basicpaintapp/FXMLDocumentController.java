/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicpaintapp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
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
        

    private String selectedShape="LINE";
    private Color selectedColor=Color.WHITE;
    double srtX=0, srtY=0,srtW=0, srtH=0;
    double srtArcWidth=20,srtArcHeight=20;
    double endX=0, endY=0, endW, endH;
    double length=20, startAngle=20, arcAngle=20;
    private Label label;
    @FXML
    private ColorPicker mColorPicker;
    private Group c;
    @FXML
    public Canvas mCanvas;
    @FXML
    private Slider mSlider;
    @FXML
    private Slider bSlider;
    @FXML
    private Text BrushSlider;
    @FXML
    private Text LineSlider;
    @FXML
    private Button Line;
    @FXML
    private Button Rect;
    @FXML
    private Button Circle;
    @FXML
    private Button RoundedRec;
    @FXML
    private Button Erase;
    @FXML
    private Button NewCanvas;
    @FXML
    private Button Curve;
    @FXML
    private Button Text;
    @FXML
    private Button Brush;

@FXML 
private void SetClose(ActionEvent event){
exit();

}
    @FXML
    private void openFile(ActionEvent event) {
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
    private void setShape(ActionEvent event) {
        Button btn =(Button)event.getSource();
         
         switch(btn.getText()){
             case   "Line": selectedShape="LINE";       break;
             case   "Rect": selectedShape="RECT";       break;
             case   "Circle": selectedShape="CIRCLE";   break;
             case   "Rounded Rec": selectedShape="ROUNDED_REC"; break;
             case   "Erase": selectedShape="ERASER"; break;
             case   "Curve": selectedShape="CURVE";break;
             case "setBrush": selectedShape="setBrush";break;
         }
    }


    @FXML
    private void startShape(MouseEvent event) {
        srtX=event.getX();
        srtY=event.getY();
        
        
    }
      
@FXML
    private void startDraw(MouseEvent event) {
        endX=event.getX();
        endY=event.getY();
        GraphicsContext gc= mCanvas.getGraphicsContext2D();
        gc.setStroke(selectedColor);
        System.out.println(""+selectedColor);
        gc.setLineWidth(mSlider.getValue());
        switch(selectedShape){
          case "LINE":   gc.strokeLine(srtX,srtY,endX,endY);break;
          case "RECT":  gc.strokeRect(srtX,srtY,endX-srtX,endY-srtY); break;
          case "CIRCLE":  gc.strokeOval(srtX,srtY,endX-srtX,endY-srtY); break;
          case "ROUNDED_REC": gc.strokeRoundRect(srtX, srtY, endX-srtX, endY-srtY,srtArcWidth,srtArcHeight ); break;
          case "ERASER": gc.strokeLine(srtX,srtY,endX,endY);break;
          case "CURVE":  gc.strokeArc(srtX, srtY, srtX+srtY,srtX+srtY,  endX-srtX, endY-srtY, ArcType.OPEN);break;
          case "Text": createText(); break;
       
        
       
        }
  
    }
    @FXML
    public void newcanvas(ActionEvent e){
        
    TextField getCanvasWidth =new TextField(); 
    getCanvasWidth.setPromptText("Width");
    getCanvasWidth.setPrefWidth(150);
    getCanvasWidth.setAlignment(Pos.CENTER);
    
    TextField getCanvasHeighth =new TextField(); 
    getCanvasHeighth.setPromptText("Width");
    getCanvasHeighth.setPrefWidth(150);
    getCanvasHeighth.setAlignment(Pos.CENTER);
    
    Button createButton= new Button();
    createButton.setText("Create Canvas");
    
    VBox vBox=new VBox();
    vBox.setSpacing(5);
    vBox.setAlignment(Pos.CENTER);
    vBox.getChildren().addAll(getCanvasWidth,getCanvasHeighth,createButton);
    
    Stage createStage= new Stage ();
    AnchorPane root= new AnchorPane();
    root.setPrefWidth(200);
    root.setPrefHeight(200);
    root.getChildren().add(vBox);
    
    Scene CanvasScene =new Scene(root);
    createStage.setTitle("Create Canvas");
    createStage.setScene(CanvasScene);
    createStage.show();
    
    createButton.setOnAction(new EventHandler<ActionEvent>(){
    @Override
    public void handle(ActionEvent event){double canvasWidthReccived=Double.parseDouble(getCanvasWidth.getText());
                                          double canvasHeightReccived=Double.parseDouble(getCanvasHeighth.getText());
                                          mCanvas=new Canvas();
                                          mCanvas.setWidth(canvasWidthReccived);
                                          mCanvas.setHeight(canvasHeightReccived);
                                          vBox.getChildren().add(mCanvas);
                                          createStage.close();
            }
    } );
    
    }
@FXML
    private void SetErase(ActionEvent event) {
       selectedColor=WHITE ;
       GraphicsContext gc= mCanvas.getGraphicsContext2D();
       gc.setLineWidth(100);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void selectColor(ActionEvent event) {
        selectedColor = mColorPicker.getValue();
        
    }

    @FXML
    private void setBrush(ActionEvent event) {
       
         GraphicsContext gc = mCanvas.getGraphicsContext2D();
        mCanvas.setOnMouseDragged(e -> {
        double size = Double.valueOf(bSlider.getValue());
        srtX=e.getX();
        srtY=e.getY();
        
        double x = (double) (e.getX() -  size / 2);
        double y = (double) (e.getY() -  size / 2);
       
         gc.setFill(mColorPicker.getValue());
         gc.fillRect(x,y, size, size);
        });
        
     
        
    }
    @FXML
    void setText(ActionEvent event) {
       selectedShape="Text";
       
    }
    @FXML
    private void createText() {
        String txt;
        TextField tf2;
        tf2 = new TextField("");
        tf2.setMinWidth(300);
        tf2.setAlignment(Pos.CENTER);
        Pane gd = new Pane(tf2);
        Stage createText= new Stage();
        Scene TextScene =new Scene(gd, 300, 30);
        createText.setScene(TextScene);
        createText.show();
        
        tf2.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int b = 0;
                if( event.getCode() == KeyCode.ENTER ) {
                    b = 1;
                }
                if (b==1){
                GraphicsContext gc= mCanvas.getGraphicsContext2D();
                gc.strokeText(tf2.getText(), srtX, srtY);
                createText.close();
                }
            }
        }
        );
    }

    
}