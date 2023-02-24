package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.PlaceOrderBO;
import lk.ijse.CherryClothing.dto.*;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.view.tm.OrderDetailTM;
import lk.ijse.CherryClothing.util.Routes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlaceOrderFormController {
    @FXML
    public AnchorPane ancPlaceOrder;
    @FXML
    public JFXComboBox<String> cmbItemId;
    @FXML
    public JFXButton btnAdd;
    @FXML
    public JFXButton btnPlaceOrder;
    @FXML
    public JFXTextField txtCustomerName;
    @FXML
    public JFXTextField txtDescription;
    @FXML
    public JFXTextField txtQtyOnHand;
    @FXML
    public JFXTextField txtUnitPrice;
    @FXML
    public Label lblId;
    @FXML
    public Label lblDate;
    @FXML
    public Label lblTotal;
    @FXML
    private TableView<OrderDetailTM> tblOrder;
    @FXML
    public TableColumn ColId;
    @FXML
    public TableColumn colType;
    @FXML
    public TableColumn colQty;
    @FXML
    public TableColumn colPrice;
    @FXML
    public JFXTextField txtQty;
    @FXML
    public TableColumn colTotal;
    @FXML
    public JFXComboBox<String> cmbCustomerId;
    @FXML
    public TableColumn colAction;
    private String orderId;
    //private ObservableList<OrderDetailTM> obList = FXCollections.observableArrayList();
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    public void initialize() throws SQLException, ClassNotFoundException {

        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrder.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrder.getItems().remove(param.getValue());
                tblOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblId.setText("order_id " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
        txtQty.setEditable(false);
        btnAdd.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        //Search Customer

                        CustomerDTO customerDTO = placeOrderBO.searchCustomer(newValue + "");
                        txtCustomerName.setText(customerDTO.getName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAdd.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    ItemDTO item = placeOrderBO.searchItem(newItemCode + "");

                    txtDescription.setText(item.getType());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailTM> optOrderDetail = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemId.setDisable(true);
                cmbItemId.setValue(selectedOrderDetail.getItemId());
                btnAdd.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAdd.setText("Add");
                cmbItemId.setDisable(false);
                cmbItemId.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadCustomerIds();
        loadItemCodes();
    }


    public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancPlaceOrder);
    }


    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancPlaceOrder);
    }

    public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancPlaceOrder);
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancPlaceOrder);
    }

    public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancPlaceOrder);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancPlaceOrder);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrder.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getItemId(), tm.getQty(), tm.getType(),tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblId.setText("order_id " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return placeOrderBO.existItem(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return placeOrderBO.existCustomer(id);
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails)throws SQLException, ClassNotFoundException {
        OrderDTO orderDTO = new OrderDTO(orderId,orderDate,customerId,orderDetails);
        return placeOrderBO.purchaseOrder(orderDTO);
    }


    private void loadCustomerIds() {
        try {

            ArrayList<CustomerDTO> allCustomers = placeOrderBO.getAllCustomers();
            for (CustomerDTO c : allCustomers) {
                cmbCustomerId.getItems().add(c.getId());
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadItemCodes() {
        try {
            ArrayList<ItemDTO> allItems = placeOrderBO.getAllItems();
            for (ItemDTO i : allItems) {
                cmbItemId.getItems().add(i.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String itemId = cmbItemId.getSelectionModel().getSelectedItem();
        String type = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getItemId().equals(itemId));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(itemId)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrder.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrder.refresh();
        } else {
            OrderDetailTM orderDetailTM = new OrderDetailTM(itemId, type, qty, unitPrice, total);
            tblOrder.getItems().add(orderDetailTM);
            //System.out.println(orderDetailTM);
        }
        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrder.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " + total);
    }
    public String generateNewOrderId() {
        try {
           /* Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

            */

            return placeOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "O0001";
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancPlaceOrder);
    }

    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancPlaceOrder);
    }
}

