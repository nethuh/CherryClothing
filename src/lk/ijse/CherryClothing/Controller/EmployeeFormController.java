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
import lk.ijse.CherryClothing.bo.Custom.EmployeeBO;
import lk.ijse.CherryClothing.dto.EmployeeDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.RegexUtil;
import lk.ijse.CherryClothing.util.Routes;
import lk.ijse.CherryClothing.view.tm.EmployeeTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//form eka mokaddd
public class EmployeeFormController {
    public AnchorPane ancDeliver;
    public JFXTextField txtId;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;
    public JFXTextField txtSalary;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public TableView<EmployeeTM> tblEmp;
    public TableColumn ColEmp;
    public TableColumn ColName;
    public TableColumn ColAddress;
    public TableColumn ColSalary;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    public void initialize() throws SQLException, ClassNotFoundException {
        ColEmp.setCellValueFactory(new PropertyValueFactory("id"));
        ColName.setCellValueFactory(new PropertyValueFactory("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory("address"));
        ColSalary.setCellValueFactory(new PropertyValueFactory("salary"));

        initUI();

        tblEmp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnAdd.setText(newValue != null ? "Update" : "Save");
            btnAdd.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtSalary.setText(newValue.getSalary().setScale(2).toString());
                txtId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtSalary.setDisable(false);

            }
        });

        txtSalary.setOnAction(event -> btnAdd.fire());
        loadAllEmployee();
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtSalary.setDisable(true);
        txtId.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);

    }
    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        tblEmp.getItems().clear();
        /*Get all employee*/
        try {
            ArrayList<EmployeeDTO> allEmployees = employeeBO.getAllEmployees();

            for (EmployeeDTO employee : allEmployees){
                tblEmp.getItems().add(new EmployeeTM(employee.getId(),employee.getName(),employee.getAddress(),employee.getSalary()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancDeliver);
    }

    public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancDeliver);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancDeliver);
    }

    public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancDeliver);
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancDeliver);
    }

    public void addressK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtAddress.getText(),"\\b([a-z]|[A-Z])+")){
            btnAdd.setDisable(false);
            txtAddress.setFocusColor(Paint.valueOf("blue"));
        }else {
            btnAdd.setDisable(true);
            txtAddress.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void OnActionDelete(ActionEvent actionEvent) {
        String id = tblEmp.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }

            employeeBO.deleteEmployee(id);

            tblEmp.getItems().remove(tblEmp.getSelectionModel().getSelectedItem());
            tblEmp.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeBO.existEmployee(id);
    }

    public void OnActionAdd(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        BigDecimal salary = new BigDecimal(txtSalary.getText()).setScale(2);

        try {
            if (existEmployee(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
            }
            employeeBO.addEmployee(new EmployeeDTO(id,name,address,salary));
            tblEmp.getItems().add(new EmployeeTM(id, name,address,salary));

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the employee " + e.getMessage()).show();
            System.out.println(e);
        }
        {

            /*Update employee*/

            try {
                if (!existEmployee(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
                }

                employeeBO.updateEmployee(new EmployeeDTO(id,name,address,salary));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the employee " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void OnActionAddNew(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtSalary.setDisable(false);
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtSalary.requestFocus();
        btnAdd.setDisable(false);
        btnAdd.setText("Save");
        txtId.setText(generateNewId());
        tblEmp.getSelectionModel().clearSelection();
    }
    private String generateNewId() {
        try {
            return employeeBO.generateNewEmployeeID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblEmp.getItems().isEmpty()) {
            return "E0001";
        } else {
            String id = getLastEmployeeId();
            int newDeliveryId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E0%03d", newDeliveryId);
        }

    }

    private String getLastEmployeeId() {
        List<EmployeeTM> tempDeliveryList = new ArrayList<>(tblEmp.getItems());
        Collections.sort(tempDeliveryList);
        return tempDeliveryList.get(tempDeliveryList.size() - 1).getId();
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancDeliver);
    }

    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancDeliver);
    }

    public void nameK(KeyEvent keyEvent) {
        if (RegexUtil.regex(txtName.getText(),"\\b([a-z]|[A-Z])+")){
            btnAdd.setDisable(false);
            txtName.setFocusColor(Paint.valueOf("blue"));
        }else {
            btnAdd.setDisable(true);
            txtName.setFocusColor(Paint.valueOf("red"));

        }
    }

    public void salaryK(KeyEvent keyEvent) {
    }
}
