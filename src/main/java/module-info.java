module rpg.randompasswordgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires org.jetbrains.annotations;


    opens rpg to javafx.fxml;
    exports rpg;
}