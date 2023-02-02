package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException ;
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;
    public boolean existItem(String code) throws SQLException, ClassNotFoundException;
    public String generateNewItemId() throws SQLException, ClassNotFoundException;
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;
}
