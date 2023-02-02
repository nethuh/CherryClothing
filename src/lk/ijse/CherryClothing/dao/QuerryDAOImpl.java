package lk.ijse.CherryClothing.dao;

import lk.ijse.CherryClothing.entity.CustomEntity;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QuerryDAOImpl implements QuerryDAO {

        @Override
        public ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException {
            ResultSet rst= CrudUtil.execute("SELECT Orders.oid,Orders.date,Orders.customerID,OrderDetails.oid,OrderDetails.itemCode,OrderDetails.qty,OrderDetails.unitPrice from Orders inner join OrderDetails on Orders.oid=OrderDetails.oid where Orders.oid=?",oid);
            ArrayList<CustomEntity> allRecords= new ArrayList<>();
            while (rst.next()) {
                String oid1 = rst.getString("oid");
                String date = rst.getString("date");
                String customerID = rst.getString("customerID");
                String itemCode = rst.getString("itemCode");
                int qty = rst.getInt("qty");
                double unitPrice = rst.getDouble("unitPrice");

                CustomEntity customEntity = new CustomEntity(oid1, LocalDate.now(), customerID, itemCode, qty, unitPrice);
                allRecords.add(customEntity);
            }
            return allRecords;
        }
}
