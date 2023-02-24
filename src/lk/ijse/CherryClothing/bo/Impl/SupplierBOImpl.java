package lk.ijse.CherryClothing.bo.Impl;

import lk.ijse.CherryClothing.bo.Custom.SupplierBO;
import lk.ijse.CherryClothing.dao.DAOFactory;
import lk.ijse.CherryClothing.dao.custom.SupplierDAO;
import lk.ijse.CherryClothing.dto.SupplierDTO;
import lk.ijse.CherryClothing.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all) {
            allSuppliers.add(new SupplierDTO(s.getSupId(), s.getName(),s.getContact()));
        }
        return allSuppliers;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getSupId(), dto.getName(),dto.getContact()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getSupId(), dto.getName(),dto.getContact()));
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewId();
    }

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier s = supplierDAO.search(id);
        return new SupplierDTO(s.getSupId(), s.getName(),s.getContact());
    }
}
