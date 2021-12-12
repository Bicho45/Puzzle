import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("puzzle 15 drawing");
        Group root = new Group();
        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, Color.DARKGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
        Puzzle puzzle15 = new Puzzle(canvas.getGraphicsContext2D());
        puzzle15.draw();
    }
    public static void main(String[] args) {
        launch(args);
    }
}