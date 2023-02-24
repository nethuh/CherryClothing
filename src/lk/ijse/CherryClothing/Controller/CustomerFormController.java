package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.CustomerBO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.RegexUtil;
import lk.ijse.CherryClothing.util.Routes;
import javafx.scene.paint.Paint;
import lk.ijse.CherryClothing.view.tm.CustomerTM;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFormController {
    public AnchorPane ancCustomer;
    public JFXTextField txtContact;
    public JFXTextField txtID;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public Label lblEmailWarning;
    public JFXButton btnAdd;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn ColId;
    public TableColumn ColName;
    public TableColumn ColAddress;
    public TableColumn ColContact;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public void initialize() throws SQLException, ClassNotFoundException {
        ColId.setCellValueFactory(new PropertyValueFactory("Id"));
        ColName.setCellValueFactory(new PropertyValueFactory("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory("address"));
        ColContact.setCellValueFactory(new PropertyValueFactory("contact"));



        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
           btnAdd.setText(newValue != null ? "Update" : "Save");
            btnAdd.setDisable(newValue == null);



            if (newValue != null) {
                txtID.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                txtID.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);


            }
        });

        txtContact.setOnAction(event -> btnAdd.fire());
        loadAllCustomers();
    }

    private void initUI() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtID.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtID.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        tblCustomer.getItems().clear();
        /*Get all customers*/
        try {
            /*Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
          while (rst.next()) {
                tblCustomer.getItems().add(new CustomerTM(rst.getString("cus_id"), rst.getString("cus_name"), rst.getString("address"), rst.getString("contact")));
            }

             */

            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();

            for (CustomerDTO customer : allCustomers){
                tblCustomer.getItems().add(new CustomerTM(customer.getId(),customer.getName(),customer.getAddress(),customer.getContact()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
        public void OnActionCustomer (ActionEvent actionEvent) throws IOException {
            Navigation.navigate(Routes.CUSTOMER, ancCustomer);

        }


        public void OnActionAdd (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            String id = txtID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();

            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                }
                customerBO.addCustomer(new CustomerDTO(id,name,address,contact));
                tblCustomer.getItems().add(new CustomerTM(id, name, address, contact));

            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
                System.out.println(e);
            }
            {

                /*Update customer*/

                try {
                    if (!existCustomer(id)) {
                        new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                    }
                   /* Connection connection = DBConnection.getInstance().getConnection();
                    PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET cus_name=?, address=?, contact=? WHERE cus_id=?");
                    pstm.setString(1, name);
                    pstm.setString(2, address);
                    pstm.setString(3, contact);
                    pstm.setString(4, id);
                    pstm.executeUpdate();*/

                    customerBO.updateCustomer(new CustomerDTO(id,name,address,contact));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                /*CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
                selectedCustomer.setName(name);
                selectedCustomer.setAddress(address);
                selectedCustomer.setContact(contact);
                tblCustomer.refresh();

                 */
            }
        }






    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        /*Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cus_id FROM Customer WHERE cus_id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();

         */
        return customerBO.existCustomer(id);
    }




        public void OnActionDelete (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
            String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }

                customerBO.deleteCustomer(id);

                tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
                tblCustomer.getSelectionModel().clearSelection();
                initUI();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }



        /*public void OnActionSearch (ActionEvent actionEvent) throws SQLException {
            String id = txtID.getText();
            try {
                CustomerDTO customerDTO = CustomerModel.search(id);
                if (customerDTO != null) {
                    fillData(customerDTO);
                }
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }

       private void fillData (CustomerDTO customerDTO){
            txtID.setText(customerDTO.getId());
            txtName.setText(customerDTO.getName());
            txtAddress.setText(customerDTO.getAddress());
            txtContact.setText(customerDTO.getContact());

        }

        */

        public void OnActionPlace (ActionEvent actionEvent) throws IOException {
            Navigation.navigate(Routes.PLACEORDER, ancCustomer);
        }

        public void OnActionOut (ActionEvent actionEvent) throws IOException {
            Navigation.navigate(Routes.LOGIN, ancCustomer);
        }

        public void OnActionCClothes (ActionEvent actionEvent) throws IOException {
            Navigation.navigate(Routes.CCLOTHES, ancCustomer);
        }

        public void OnActionCDash (ActionEvent actionEvent) throws IOException {
            Navigation.navigate(Routes.CASHIER, ancCustomer);
        }


        public void mobileK (KeyEvent keyEvent){
            if (RegexUtil.regex(txtContact.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}")) {
                btnAdd.setDisable(false);
                txtContact.setFocusColor(Paint.valueOf("blue"));
            } else {
                btnAdd.setDisable(true);
                txtContact.setFocusColor(Paint.valueOf("red"));

            }
        }

        public void addressK (KeyEvent keyEvent){
            if (RegexUtil.regex(txtAddress.getText(), "\\b([a-z]|[A-Z])+")) {
                btnAdd.setDisable(false);
                txtAddress.setFocusColor(Paint.valueOf("blue"));
            } else {
                btnAdd.setDisable(true);
                txtAddress.setFocusColor(Paint.valueOf("red"));

            }
        }

        public void nameK (KeyEvent keyEvent){
            if (RegexUtil.regex(txtName.getText(), "\\b([a-z]|[A-Z])+")) {
                btnAdd.setDisable(false);
                txtName.setFocusColor(Paint.valueOf("blue"));
            } else {
                btnAdd.setDisable(true);
                txtName.setFocusColor(Paint.valueOf("red"));

            }
        }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtID.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.requestFocus();
        btnAdd.setDisable(false);
        btnAdd.setText("Save");
        txtID.setText(generateNewId());
        tblCustomer.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomer.getItems().isEmpty()) {
            return "C0001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C0%03d", newCustomerId);
        }

    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getId();
    }

    public void txtCustomerIdOnAction(ActionEvent actionEvent) {
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancCustomer);
    }

    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancCustomer);
    }
}










