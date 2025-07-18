package com.data.dao;

import com.data.connection.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean login(String username, String password) {
        try (Connection conn = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            return rs.next(); // true nếu có kết quả
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
