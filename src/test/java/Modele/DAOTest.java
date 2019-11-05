/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

/**
 *
 * @author camilleclaret
 */
public class DAOTest {
    
    private DataSource myDataSource;
    private Connection myConnection ;
    private DAO myDAO;

    @Before
    public void setUp()  throws SQLException, IOException , SqlToolError  {
        
       // On crée la connection vers la base de test "in memory"
       this.myDataSource = getTestDataSource();
       this.myConnection = myDataSource.getConnection();
      // On crée le schema de la base de test
      executeSQLScript(this.myConnection, "schema.sql");
       // On y met des données
       executeSQLScript(this.myConnection, "bigtestdata.sql");		
       this.myDAO = new DAO(this.myDataSource);
        
    }
    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
       this. myConnection.close(); // La base de données de test est détruite ici
        this.myDAO = null; // Pas vraiment utile
    }
    
    public void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
       // String sqlFilePath = this.getClass().getResource(filename).getFile();
       System.out.print(DAOTest.class.getResource(filename));
        String sqlFilePath = DAOTest.class.getResource(filename).getFile();
      
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();		
    }
    
    @Test
    public void discountCodeListTest() throws SQLException{
        System.out.print("discountCodeListTest");
        String listCode[]  ={"H","L","M","N"};
        String listRate[] ={"16.00","7.00","11.00","0.00"};
        ArrayList<ArrayList<String>> resultExpectend = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< listCode.length;i++){
            ArrayList<String> rs =new ArrayList<String>();
            rs.add(listCode[i]);
            rs.add(listRate[i]);
            resultExpectend.add(rs);
        }
        ArrayList<ArrayList<String>> resultFonction =this.myDAO.discountCodeList();
       assertEquals(resultExpectend, resultFonction);
        
    }
  
    @Test 
    public void changeRateTest() throws SQLException{
        String code ="M";
        String rate = "0.16";
        this.myDAO.changeRate(code, rate);
        String listCode[]  ={"H","L","M","N"};
        String listRate[] ={"16.00","7.00","0.16","0.00"};
        ArrayList<ArrayList<String>> resultExpectend = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< listCode.length;i++){
            ArrayList<String> rs =new ArrayList<String>();
            rs.add(listCode[i]);
            rs.add(listRate[i]);
            resultExpectend.add(rs);
        }
        ArrayList<ArrayList<String>> resultFonction =this.myDAO.discountCodeList();
       assertEquals(resultExpectend, resultFonction);
        
    }
    
    @Test 
    public void createValTest() throws SQLException{
        String code ="A";
        String rate = "0.16";
        this.myDAO.createVal(code, rate);
        String listCode[]  ={"H","M","L","N","A"};
        String listRate[] ={"16.00","11.00","7.00","0.00","0.16"};
        ArrayList<ArrayList<String>> resultExpectend = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< listCode.length;i++){
            ArrayList<String> rs =new ArrayList<String>();
            rs.add(listCode[i]);
            rs.add(listRate[i]);
            resultExpectend.add(rs);
        }
        ArrayList<ArrayList<String>> resultFonction =this.myDAO.discountCodeList();
       assertEquals(resultExpectend, resultFonction);
    }
    
    @Test
    public void delValTest() throws SQLException{
        String code ="A";
        this.myDAO.delVal(code);
         String listCode[]  ={"H","L","M","N"};
        String listRate[] ={"16.00","7.00","11.00","0.00"};
        ArrayList<ArrayList<String>> resultExpectend = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< listCode.length;i++){
            ArrayList<String> rs =new ArrayList<String>();
            rs.add(listCode[i]);
            rs.add(listRate[i]);
            resultExpectend.add(rs);
        }
        ArrayList<ArrayList<String>> resultFonction =this.myDAO.discountCodeList();
       assertEquals(resultExpectend, resultFonction);
        
    }
   
    public static DataSource getTestDataSource() {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
    
}
