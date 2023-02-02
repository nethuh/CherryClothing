package lk.ijse.CherryClothing.dao;

import lk.ijse.CherryClothing.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuerryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException;
}
