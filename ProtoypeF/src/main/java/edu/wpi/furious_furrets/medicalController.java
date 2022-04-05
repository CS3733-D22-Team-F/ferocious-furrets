package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller for medical equipment/lab request scene
 *
 * @see returnHomePage
 * @see Initializable
 */
public class medicalController extends returnHomePage implements Initializable {

    private Stage stage;
    private Scene scene;

    // @FXML private JFXButton scanButton;

    /**
     * switch to the lab scene
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToLab(ActionEvent event) throws IOException {
        scene = SceneManager.getInstance().setScene("labRequestPage.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to the scan scene
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToScan(ActionEvent event) throws IOException {
        scene = SceneManager.getInstance().setScene("scanPage.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * inits
     * TODO?
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // BackgroundImage scanImage = new BackgroundImage(new
        // Image(getClass().getResource("Images/Scanimage.jpeg")));
        // scanButton.setStyle("-fx-background-image: 'Images/Scanimage.jpeg'");
    }
}
