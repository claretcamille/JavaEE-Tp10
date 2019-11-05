/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
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
}
