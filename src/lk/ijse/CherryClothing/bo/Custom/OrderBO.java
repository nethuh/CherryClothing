package lk.ijse.CherryClothing.bo.Custom;

import lk.ijse.CherryClothing.bo.SuperBO;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.OrderDTO;
import lk.ijse.CherryClothing.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException;
}
