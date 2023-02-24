package lk.ijse.CherryClothing.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.CustomerBO;
import lk.ijse.CherryClothing.bo.Custom.OrderBO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.OrderDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.Routes;
import lk.ijse.CherryClothing.view.tm.CustomerTM;
import lk.ijse.CherryClothing.view.tm.OrderDetailTM;
import lk.ijse.CherryClothing.view.tm.OrderTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierFormController {
    public AnchorPane ancCashier;
    public TableView<OrderTM> tblOrder;
    public TableColumn colOoId;
    public TableColumn colDate;
    public TableColumn ColCusId;
    public TableView<OrderDetailTM> tblOrderDetail;
    public TableColumn colOId;
    public TableColumn ColItemId;
    public TableColumn colQty;
    public TableColumn colPrice;
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    public void initialize() throws SQLException, ClassNotFoundException {
        colOoId.setCellValueFactory(new PropertyValueFactory("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory("Date"));
        ColCusId.setCellValueFactory(new PropertyValueFactory("CusId"));

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

    }
    private void loadAllOrder() throws SQLException, ClassNotFoundException {
        tblOrder.getItems().clear();
        /*Get all customers*/
        try {

            ArrayList<OrderDTO> allCustomers = orderBO.getAllOrder();

            for (OrderDTO customer : allCustomers) {
                tblOrder.getItems().add(new OrderTM(customer.getOrderId(), customer.getDate(), customer.getCustomerId()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

        public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancCashier);
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancCashier);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancCashier);
    }

    public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancCashier);
    }

    public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancCashier);
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancCashier);
    }

    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancCashier);
    }
}


