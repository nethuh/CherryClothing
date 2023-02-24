package lk.ijse.CherryClothing.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.CherryClothing.bo.BOFactory;
import lk.ijse.CherryClothing.bo.Custom.ItemBO;
import lk.ijse.CherryClothing.dto.ItemDTO;
import lk.ijse.CherryClothing.util.Navigation;
import lk.ijse.CherryClothing.util.Routes;
import lk.ijse.CherryClothing.view.tm.CClothesTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemFormController {
    public AnchorPane ancCClothes;
    public JFXTextField txtId;
    public JFXTextField txtType;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public TableView <CClothesTM>tblItem;
    public TableColumn ColId;
    public TableColumn ColType;
    public TableColumn ColPrice;
    public TableColumn ColQty;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton AddNewItem;
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize() throws SQLException, ClassNotFoundException {
        ColId.setCellValueFactory(new PropertyValueFactory("Id"));
        ColType.setCellValueFactory(new PropertyValueFactory("Type"));
        ColPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        ColQty.setCellValueFactory(new PropertyValueFactory("qty"));


        initUI();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnAdd.setText(newValue != null ? "Update" : "Save");
            btnAdd.setDisable(newValue == null);



            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtType.setText(newValue.getType());
                txtPrice.setText(newValue.getPrice().setScale(2).toString());
                txtQty.setText(newValue.getQty() + "");
                txtId.setDisable(false);
                txtType.setDisable(false);
                txtPrice.setDisable(false);
                txtQty.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnAdd.fire());
        loadAllItem();
    }

    private void initUI() {
        txtId.clear();
        txtType.clear();
        txtPrice.clear();
        txtQty.clear();
        txtId.setDisable(true);
        txtType.setDisable(true);
        txtPrice.setDisable(true);
        txtQty.setDisable(true);
        txtId.setEditable(false);
        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllItem() throws SQLException, ClassNotFoundException {
        tblItem.getItems().clear();
        /*Get all customers*/
        try {


            ArrayList<ItemDTO> allItems = itemBO.getAllItem();

            for (ItemDTO item : allItems){
                //tblItem.getItems().add(new CClothesTM(item.getString("item_id"), rst.getString("type"), rst.getString("qty_on_hand"), rst.getString("unit_price")));
                tblItem.getItems().add(new CClothesTM(item.getId(),item.getType(),item.getQtyOnHand(), item.getUnitPrice()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

        public void OnActionPlace(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PLACEORDER, ancCClothes);
    }

    public void OnActionCustomer(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER, ancCClothes);
    }

    public void OnActionOut(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, ancCClothes);
    }

    /*public void OnActionSearch(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            ItemDTO itemDTO = ItemModel.search(id);
            if (itemDTO != null) {
                fillData(itemDTO);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void fillData(ItemDTO itemDTO) {
        txtId.setText(itemDTO.getId());
        txtType.setText(itemDTO.getType());
        txtPrice.setText(itemDTO.getUnitPrice());
        txtQty.setText(itemDTO.getQtyOnHand());

    }

     */


    public void OnActionDelete(ActionEvent actionEvent) {
        String id = tblItem.getSelectionModel().getSelectedItem().getId();

        try {
            /*Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM Item WHERE item_id=?";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setObject(1, txtId.getText());
            int executeUpdate = pre.executeUpdate();

             */

            if (!existItem(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, " Deleted!").show();

            }
            itemBO.deleteItem(id);

            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    boolean existItem(String id) throws SQLException, ClassNotFoundException {
        /*Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cus_id FROM Customer WHERE cus_id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();

         */
        return itemBO.existItem(id);
    }



    public void OnActionAdd(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        String type = txtType.getText();
        BigDecimal price = new BigDecimal(txtPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());

        try {
            if (existItem(id)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            }
            itemBO.addItem(new ItemDTO(id, type, price, qty));
            tblItem.getItems().add(new CClothesTM(id, type, qty, price));

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            System.out.println(e);
        }
        {

            /*Update customer*/

            try {
                if (!existItem(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
                }
                itemBO.updateItem(new ItemDTO(id, type, price, qty));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the item " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }





public void OnActionCClothes(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CCLOTHES, ancCClothes);
    }

    public void OnActionCDash(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER, ancCClothes);
    }

    public void OnActionAddNew(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtType.setDisable(false);
        txtQty.setDisable(false);
        txtPrice.setDisable(false);
        txtId.clear();
        txtType.clear();
        txtQty.clear();
        txtPrice.clear();
        txtType.requestFocus();
        btnAdd.setDisable(false);
        btnAdd.setText("Save");
        txtId.setText(generateNewId());
        tblItem.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return itemBO.generateNewItemId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblItem.getItems().isEmpty()) {
            return "I0001";
        } else {
            String id = getLastItemId();
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I0%03d", newItemId);
        }

    }

    private String getLastItemId() {
        List<CClothesTM> tempItemList = new ArrayList<>(tblItem.getItems());
        Collections.sort(tempItemList);
        return tempItemList.get(tempItemList.size() - 1).getId();
    }

    public void OnActionSearch(ActionEvent actionEvent) {
    }

    public void OnActionEmployee(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE, ancCClothes);
    }

    public void OnActionSuplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, ancCClothes);
    }
}

