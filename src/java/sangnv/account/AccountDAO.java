/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import sangnv.utils.DBHelper;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class AccountDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    public void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public AccountDTO authenticationAccount(String email, String password) throws NamingException, SQLException {
        AccountDTO result = null;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT email, name, roleId"
                    + " FROM Account"
                    + " WHERE email LIKE ?"
                    + " AND password LIKE ?"
                    + " AND statusId = '" + MyConstants.ACCOUNT_STATUS_ACTIVE + "'";
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = new AccountDTO();
                result.setName(rs.getString("name"));
                result.setEmail(rs.getString("email"));
                result.setRoleId(rs.getInt("roleId"));
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean isDuplicate(String email) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "SELECT email"
                    + " FROM Account"
                    + " WHERE email LIKE ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean registerAccount(AccountDTO dto) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "INSERT INTO Account (email, name, password, roleId, statusId, authenCode)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getEmail());
            stm.setString(2, dto.getName());
            stm.setString(3, dto.getPassword());
            stm.setInt(4, MyConstants.ROLE_MEMBER);
            stm.setInt(5, MyConstants.ACCOUNT_STATUS_NEW);
            stm.setString(6, dto.getAuthenCode());
            int insertCounter = stm.executeUpdate();
            if (insertCounter > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

    public boolean activateAcocunt(String email, String authenCode) throws NamingException, SQLException {
        boolean result = false;
        conn = DBHelper.getConnect();
        try {
            String sql = "UPDATE Account"
                    + " SET statusId = ?"
                    + " WHERE email = ?"
                    + " AND authenCode = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, MyConstants.ACCOUNT_STATUS_ACTIVE);
            stm.setString(2, email);
            stm.setString(3, authenCode);
            int updateCounter = stm.executeUpdate();
            if (updateCounter > 0) {
                result = true;
            }
        } finally {
            closeDB();
        }
        return result;
    }

}
