package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Loan;
import sample.WindowState.Open;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private PasswordField tbPassword;

    @FXML private TextField tbUsername;

    @FXML private Button Login;

    //classes
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void AuthLogin(ActionEvent event) {
        switch (loan.Login(tbUsername.getText(), tbPassword.getText()))
        {
            case "success": //if login is successful
                Open.Dashboard();
                ((Node)(event.getSource())).getScene().getWindow().hide();
                break;

            case "failed"://if login details is incorrect
                MessageBox.ShowInformation("Your username or password is incorrect.");
                break;

            case "error"://if have an exception
                MessageBox.ShowError("Something went wrong.");
                break;
        }
    }

}
