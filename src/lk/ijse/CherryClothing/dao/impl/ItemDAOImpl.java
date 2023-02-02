package lk.ijse.CherryClothing.dao.impl;

import lk.ijse.CherryClothing.dao.custom.ItemDAO;
import lk.ijse.CherryClothing.entity.Item;
import lk.ijse.CherryClothing.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("item_id"), rst.getString("type"), rst.getBigDecimal("unit_price"), rst.getInt("qty_on_hand")));
        }
        return allItems;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE item_id=?", code);
    }


    @Override
    public boolean add(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Item (item_id, type, unit_price, qty_on_hand) VALUES (?,?,?,?)",entity.getId(),entity.getType(),entity.getUnitPrice(),entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET type=?, unit_price=?,qty_on_Hand=? WHERE item_id=?",entity.getType(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getId());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT item_id FROM Item WHERE item_id=?", code);
        return rst.next();
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT item_id FROM Item ORDER BY item_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("item_id");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I0%03d", newItemId);
        } else {
            return "I0001";
        }
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst  = CrudUtil.execute("SELECT * FROM Item WHERE item_id=?",code+"");
        rst.next();
        return new Item(code + "", rst.getString("type"), rst.getBigDecimal("unit_price"), rst.getInt("qty_on_Hand"));
    }

}
