package sample.Classes.Tools;

import javafx.scene.control.Alert;

public class MessageBox {

    public static void ShowInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Loan Management");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void ShowError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Product Loan Management");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void ShowWarning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Product Loan Management");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
