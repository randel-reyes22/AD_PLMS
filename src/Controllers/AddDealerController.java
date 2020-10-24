package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Classes.Dealers_loan;
import sample.Classes.Entities.Customer;
import sample.Classes.Entities.Dealer;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Utility.LoanUtils;
import sample.WindowState.Close;
import sample.WindowState.Open;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddDealerController extends DealersController implements Initializable {

    @FXML private ImageView QrCode;

    @FXML private TextField tbFname;

    @FXML private TextField tbLname;

    @FXML private TextField tbAddress;

    @FXML private TextField tbNumber;

    @FXML private DatePicker tbBday;

    @FXML private Label lbCaption;

    @FXML private Button btnSave;

    private final Dealers_loan dealers_loan = new Dealers_loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LoanUtils.Action_classifier.equals("Update")){
            lbCaption.setText("Update Dealer");
            btnSave.setText("Update");

            for(Dealer dealer: LoanUtils.ObDealer){
                if(dealer.getDealer_id() == LoanUtils.getDealer_PK()){
                    tbFname.setText(dealer.getFirstname());
                    tbLname.setText(dealer.getLastname());
                    tbNumber.setText(dealer.getMobile());
                    tbAddress.setText(dealer.getAddress());
                    tbBday.setValue(LocalDate.parse(dealer.getBday()));
                    QrCode.setImage(dealer.getQrcode());
                    return;
                }
            }
        }
    }

    private boolean CheckEmptyFields(){
        LinkedList<TextField> tb = new LinkedList<>();
        tb.add(tbFname);
        tb.add(tbLname);
        tb.add(tbAddress);
        tb.add(tbNumber);

        for(TextField t: tb){
            if(t.getText().isEmpty())
                return true;
        }

        return false;
    }


    @FXML
    void SaveDealer(MouseEvent event) {
        if(!CheckEmptyFields() && !String.valueOf(tbBday.getValue()).isEmpty()){
            LoanUtils.LLDealer.addFirst(new Dealer(tbFname.getText(), tbLname.getText(), tbNumber.getText(),
                                                   tbAddress.getText(), String.valueOf(tbBday.getValue())));

            /*add the new dealer to the table*/
            dealers_loan.addDealer();

            //return to add
            LoanUtils.Action_classifier = "Add";

            /*after adding do the ff*/
            LoanUtils.ObDealer.clear();
            super.GetDealersData();
            Open.Dealers(); //open the dealers window
            Close.ThisWindow(event); //close this window after
        }else{
            MessageBox.ShowInformation("Supply all fields");
        }
    }

    @FXML
    void GoBack(MouseEvent event) {
        LoanUtils.Action_classifier = "Add";
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
