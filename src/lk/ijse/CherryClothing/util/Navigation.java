package lk.ijse.CherryClothing.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane ancLogin;
    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.ancLogin = pane;
        Navigation.ancLogin.getChildren().clear();
        Stage window = (Stage) Navigation.ancLogin.getScene().getWindow();

        switch (route) {
            case LOGIN:
                window.setTitle("Login Form");
                initUI("LoginForm.fxml");
                break;
            case CASHIER:
                window.setTitle("Cashier Form");
                initUI("CashierForm.fxml");
                break;
            case CUSTOMER:
                window.setTitle("Customer Form");
                initUI("CustomerForm.fxml");
                break;

            case PLACEORDER:
                window.setTitle("PlaceOrder Form");
                initUI("PlaceOrderForm.fxml");
                break;
            case CCLOTHES:
                window.setTitle("Cashier Clothes Form");
                initUI("ItemForm.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.ancLogin.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/lk/ijse/CherryClothing/view/" + location)));
    }
}

