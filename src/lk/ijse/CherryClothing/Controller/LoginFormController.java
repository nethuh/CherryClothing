package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.Routes;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane ancLogin;
    public JFXTextField txtUser;
    public JFXTextField txtPassword;
    public Label lblforgot;

    public void OnActionLogin(ActionEvent actionEvent) throws IOException, ClassNotFoundException, SQLException {

       if (txtUser.getText().equals("Cashier") && txtPassword.getText().equals("1234")) {
            Navigation.navigate(Routes.PLACEORDER, ancLogin);

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Incorrect Password");
            alert.setHeaderText("Invalid Username or Password !");
            alert.setContentText("Please Check Your Username and Password , and Try again !");
            alert.showAndWait();
        }
    }


    public void OnActiontxtPassword(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
         if (txtUser.getText().equals("Cashier") && txtPassword.getText().equals("1234")) {
            Navigation.navigate(Routes.PLACEORDER, ancLogin);

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Incorrect Password");
            alert.setHeaderText("Invalid Username or Password !");
            alert.setContentText("Please Check Your Username and Password , and Try again !");
            alert.showAndWait();
        }
    }
}
