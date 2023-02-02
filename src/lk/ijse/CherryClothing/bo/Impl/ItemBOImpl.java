package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.ItemBO;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.ItemDAO;
import lk.ijse.CherryClothing.dto.ItemDTO;
import lk.ijse.CherryClothing.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO  = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item>all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item i : all){
            allItems.add(new ItemDTO(i.getId(),i.getType(),i.getUnitPrice(),i.getQtyOnHand()));
        }
        return allItems;
    }
    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }
    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add (new Item (dto.getId(),dto.getType(), dto.getUnitPrice(),dto.getQtyOnHand()));

    }
    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item (dto.getId(),dto.getType(), dto.getUnitPrice(),dto.getQtyOnHand()));

    }
    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }
    @Override
    public String generateNewItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewId();
    }
    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getId(),i.getType(),i.getUnitPrice(),i.getQtyOnHand());
    }

}
