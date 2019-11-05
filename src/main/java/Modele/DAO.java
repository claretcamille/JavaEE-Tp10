/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 *
 * @author camilleclaret
 */
public class DAO {
    private DataSource myDao;
    
    public DAO(DataSource DataSource){
        this.myDao  =  DataSource;
    }
   
    public ArrayList<ArrayList<String>> discountCodeList() throws SQLException{
        ArrayList<ArrayList<String>> result =new ArrayList<ArrayList<String>>();
        String sql ="SELECT * FROM DISCOUNT_CODE";
        try(
                Connection c = this.myDao.getConnection();
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                ){
            while(rs.next()){
                ArrayList<String> val = new ArrayList<String>();
               val.add(rs.getString(1));
               val.add(rs.getString(2));
               result.add(val);
               
            }
            
        }
        return result;
    }
    
    public void changeRate(String code, String rate) throws SQLException{
        String sql = "UPDATE DISCOUNT_CODE SET RATE = ? WHERE DISCOUNT_CODE = ?";
        try(
                Connection c = this.myDao.getConnection();
                PreparedStatement stmt =  c.prepareStatement(sql);
                ){
            c.setAutoCommit(false);
            try{
                stmt.setString(1, rate);
                stmt.setString(2, code);
                stmt.execute();
            }catch (Exception ex) {
                c.rollback();
                throw ex; 
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
    
    public void createVal(String code, String rate)throws SQLException{
        String sql = "INSERT INTO DISCOUNT_CODE(DISCOUNT_CODE, RATE) VALUES(?,?)";
        try(
                Connection c = this.myDao.getConnection();
                PreparedStatement stmt =  c.prepareStatement(sql);
                ){
            c.setAutoCommit(false);
            try{
                stmt.setString(1, code);
                stmt.setString(2, rate);
                stmt.execute();
            }catch (Exception ex) {
                c.rollback();
                throw ex; 
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
    
    public void delVal(String code)throws SQLException {
       String sql = "DELETE FROM DISCOUNT_CODE WHERE DISCOUNT_CODE = ?";
       try(
                Connection c = this.myDao.getConnection();
                PreparedStatement stmt =  c.prepareStatement(sql);
                ){
            c.setAutoCommit(false);
            try{
                stmt.setString(1, code);
                stmt.execute();
            }catch (Exception ex) {
                c.rollback();
                throw ex; 
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}
