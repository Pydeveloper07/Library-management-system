package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AddStudentWindowController {
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<String> facultyCombo = new ComboBox<>();
    public void initialize ()
    {
        facultyCombo.getItems().addAll("CSE", "ICE", "SOL");
        facultyCombo.setPromptText("CSE");
    }
    public void cancelButtonHandler()
    {
        ((Stage)cancelBtn.getScene().getWindow()).close();
    }
}
