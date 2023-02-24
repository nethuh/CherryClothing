package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.SupplierBO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.SupplierDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.RegexUtil;
import lk.ijse.CherryClothing.util.Routes;
import lk.ijse.CherryClothing.view.tm.CustomerTM;
import lk.ijse.CherryClothing.view.tm.SupplierTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXButton btnAdd;
    public TableView<SupplierTM> tblSup;
    public TableColumn ColId;
    public TableColumn ColName;
    public TableColumn ColContact;
    public JFXButton btnAddNew;
    public AnchorPane ancSupplier;
    public JFXButton btnDelete;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    public void initialize() throws SQLException, ClassNotFoundException {
        ColId.setCellValueFactory(new PropertyValueFactory("id"));
        ColName.setCellValueFactory(new PropertyValueFactory("name"));
        ColContact.setCellValueFactory(new PropertyValueFactory("contact"));



        initUI();

        tblSup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnAdd.setText(newValue != null ? "Update" : "Save");
            btnAdd.setDisable(newValue == null);



            if (newValue != null) {
                txtId.setText(newValue.getSupId());
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                txtId.setDisable(false);
                txtName.setDisable(false);
                txtContact.setDisable(false);


            }
        });

        txtContact.setOnAction(event -> btnAdd.fire());
        loadAllSuppliers();
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtContact.setDisable(true);
        txtId.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void loadAllSuppliers() throws SQLException, ClassNotFoundException {
        tblSup.getItems().clear();
        /*Get all supplier*/
        try {

            ArrayList<SupplierDTO> allCustomers = supplierBO.getAllSupplier();

            for (SupplierDTO customer : allCustomers){
                tblSup.getItems().add(new SupplierTM(customer.getSupId(),customer.getName(),customer.getContact()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    public void OnActionAddNew(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtContact.setDisable(false);
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtName.requestFocus();
        btnAdd.setDisable(false);
        btnAdd.setText("Save");
        txtId.setText(generateNewId());
        tblSup.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return supplierBO.generateNewSupplierID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblSup.getItems().isEmpty()) {
            return "S0001";
        } else {
            String id = getLastSupplierId();
            int newCustomerId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S0%03d", newCustomerId);
        }

    }

    private String getLastSupplierId() {
        List<SupplierTM> tempSupppliersList = new ArrayList<>(tblSup.getItems());
        Collections.sort(tempSupppliersList);
        return tempSupppliersList.get(tempSupppliersList.size() - 1).getSupId();
    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierBO.existSupplier(id);
    }


    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancSupplier);
    }

    public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancSupplier);
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancSupplier);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancSupplier);
    }

    public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancSupplier);
    }

    public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancSupplier);
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancSupplier);
    }

    public void OnActionDelete(ActionEvent actionEvent) {
        String id = tblSup.getSelectionModel().getSelectedItem().getSupId();
        try {
            if (!existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such supplier associated with the id " + id).show();
            }

            supplierBO.deleteSupplier(id);

            tblSup.getItems().remove(tblSup.getSelectionModel().getSelectedItem());
            tblSup.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the supplier " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void OnActionAdd(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();

        try {
            if (existSupplier(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
            }
            supplierBO.addSupplier(new SupplierDTO(id,name,contact));
            tblSup.getItems().add(new SupplierTM(id, name, contact));

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the supplier " + e.getMessage()).show();
            System.out.println(e);
        }
        {

            /*Update supplier*/
            try {
                if (!existSupplier(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such supplier associated with the id " + id).show();
                }

                supplierBO.updateSupplier(new SupplierDTO(id,name,contact));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the supplier " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            SupplierTM selectedSupplier = tblSup.getSelectionModel().getSelectedItem();
            selectedSupplier.setName(name);
            selectedSupplier.setContact(contact);
            tblSup.refresh();
        }

        btnAddNew.fire();
    }


    public void mobileK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtContact.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}")) {
            btnAdd.setDisable(false);
            txtContact.setFocusColor(Paint.valueOf("blue"));
        } else {
            btnAdd.setDisable(true);
            txtContact.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void nameK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtName.getText(), "\\b([a-z]|[A-Z])+")) {
            btnAdd.setDisable(false);
            txtName.setFocusColor(Paint.valueOf("blue"));
        } else {
            btnAdd.setDisable(true);
            txtName.setFocusColor(Paint.valueOf("red"));

        }
    }
}
