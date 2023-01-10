package rpg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) {
        Generator generator = new Generator();

        UI ui = new UI(generator);
        ui.buildLayout();

        Scene scene = new Scene(ui.getGridPane());
        stage.setTitle("Random Password Generator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}