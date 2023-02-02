package lk.ijse.CherryClothing.Model;

import lk.ijse.CherryClothing.dto.OrderDetailDTO;
import lk.ijse.CherryClothing.dto.ItemDTO;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    /*public static ArrayList<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT item_id FROM Item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> codeList = new ArrayList<>();

        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Item WHERE item_id = ?";
        ResultSet result = CrudUtil.execute(sql, code);

        if (result.next()) {
            return new ItemDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getBigDecimal(3),
                    result.getInt(4)
            );
        }
        return null;
    }

    public static boolean updateQty(ArrayList<OrderDetailDTO> orderDetailDTOS) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            if (!updateQty(new ItemDTO(orderDetailDTO.getItemId(), orderDetailDTO.getType(), orderDetailDTO.getUnitPrice(), orderDetailDTO.getQty()))) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET  qty_on_hand =  qty_on_hand - ? WHERE item_id = ?";
        return CrudUtil.execute(sql, itemDTO.getQtyOnHand(), itemDTO.getId());
    }

     */
}


