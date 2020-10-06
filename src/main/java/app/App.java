package app;

import models.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX-based inventory management application, for educational purposes.
 * <p>
 * <b>Features to extend functionality in future versions</b>
 * <ul>
 * <li>Persist data after application shutdown through a database connection</li>
 * <li>Parse data feed from POS terminal for real-time inventory updates</li>
 * <li>Connect outsourced parts to supplier order API and enable automatic
 * reorder when inventory drops below minimum acceptable levels</li>
 * </ul>
 */
public class App extends Application {
    private static Scene scene;

    /**
     * Instantiate a public Inventory object to hold parts and products added
     * with GUI.
     */
    public static Inventory inventory = new Inventory();

    /**
     * JavaFX method to load home screen of application.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/Views/Home"), 1100, 650);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * JavaFX method to change root scene of application.
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     *
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Entry point to JavaFX application.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}