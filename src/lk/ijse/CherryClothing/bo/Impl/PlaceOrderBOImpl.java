package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.PlaceOrderBO;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.CustomerDAO;
import lk.ijse.CherryClothing.dao.custom.ItemDAO;
import lk.ijse.CherryClothing.dao.custom.OrderDAO;
import lk.ijse.CherryClothing.dao.custom.OrderDetailDAO;
import lk.ijse.CherryClothing.db.DBConnection;
import lk.ijse.CherryClothing.dto.CustomerDTO;
import lk.ijse.CherryClothing.dto.ItemDTO;
import lk.ijse.CherryClothing.dto.OrderDTO;
import lk.ijse.CherryClothing.dto.OrderDetailDTO;
import lk.ijse.CherryClothing.entity.Customer;
import lk.ijse.CherryClothing.entity.Item;
import lk.ijse.CherryClothing.entity.Order;
import lk.ijse.CherryClothing.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getId(),c.getName(),c.getAddress(), c.getContact());
    }


    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getId(),i.getType(),i.getUnitPrice(),i.getQtyOnHand());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> convertToDto = new ArrayList<>();
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        for (Customer c : customerEntityData){
            convertToDto.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(), c.getContact()));
        }
        return convertToDto;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> entityTypeData = itemDAO.getAll();
        ArrayList<ItemDTO> dtoTypeData = new ArrayList<>();
        for (Item i :entityTypeData ){
            dtoTypeData.add(new ItemDTO(i.getId(),i.getType(),i.getUnitPrice(),i.getQtyOnHand()));
        }
        return dtoTypeData;
    }

    public boolean purchaseOrder(OrderDTO dto) {
        /*Transaction*/
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            boolean b1 = orderDAO.exist(dto.getOrderId());
            /*if order id already exist*/
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
           /* stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
            stm.setString(1, orderId);
            stm.setDate(2, Date.valueOf(orderDate));
            stm.setString(3, customerId);

            if (stm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }*/

            boolean b2 = orderDAO.add(new Order(dto.getOrderId(), dto.getDate(), dto.getCustomerId()));

            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

           /* PreparedStatement stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

             for (OrderDetailDTO detail : orderDetails) {
                stm.setString(1, orderId);
                stm.setString(2, detail.getItemCode());
                stm.setBigDecimal(3, detail.getUnitPrice());
                stm.setInt(4, detail.getQty());

                if (stm.executeUpdate() != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }*/

            for (OrderDetailDTO d : dto.getOrderDetails()) {
                OrderDetail orderDetail = new OrderDetail(d.getOrderId(),d.getItemId(),d.getQty(),d.getType(),d.getUnitPrice());
                boolean b3 = orderDetailsDAO.add(orderDetail);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

//                //Search & Update Item
                ItemDTO item = findItem(d.getItemId());
                item.setQtyOnHand(item.getQtyOnHand() - d.getQty());

                boolean b = itemDAO.update(new Item(item.getId(),item.getType(),item.getUnitPrice(),item.getQtyOnHand()));

               /* PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
                pstm.setString(1, item.getDescription());
                pstm.setBigDecimal(2, item.getUnitPrice());
                pstm.setInt(3, item.getQtyOnHand());
                pstm.setString(4, item.getCode());

                */

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ItemDTO findItem(String code) {
        try {
            /*Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
            pstm.setString(1, code);
            ResultSet rst = pstm.executeQuery();
            rst.next();
            return new ItemDTO(code, rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

             */

            Item i = itemDAO.search(code);
            return new ItemDTO(i.getId(),i.getType(),i.getUnitPrice(),i.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
