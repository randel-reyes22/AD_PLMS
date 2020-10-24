package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Classes.Entities.Account;
import sample.Classes.Tools.Hash;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;
import sample.WindowState.Open;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddAccountController implements Initializable {

    @FXML private TextField tbFirstname;

    @FXML private TextField tbLastname;

    @FXML private TextField tbUsername;

    @FXML private PasswordField tbPassword;

    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void AddNewAccount(ActionEvent event) {
        if(!CheckFields()){
            //add account to the linked list
            //encode to hash the password
            LoanUtils.LLAccount.addFirst( new Account(tbFirstname.getText(), tbLastname.getText(),
                            tbUsername.getText(), Hash.Encode(tbPassword.getText())));

            //invoke method to insert in the db
            loan.AddUserAccount();
        }else
            MessageBox.ShowWarning("Supply all fields");
    }

    @FXML
    void GoBack(MouseEvent event) {
        Open.Dashboard();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private boolean CheckFields(){
        LinkedList<TextField> textFields = new LinkedList<>();
        textFields.add( tbFirstname );
        textFields.add( tbLastname );
        textFields.add( tbUsername );
        textFields.add( tbPassword );

        for(TextField t: textFields){
            if(t.getText().isEmpty())
                return true;
        }

        return false;
    }
}
