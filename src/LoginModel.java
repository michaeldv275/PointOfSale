
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mico
 */
public class LoginModel {
    private static DB db;
    public boolean checkUsername(String username){
        db = db.getDB();
        boolean checker = false;
        String sqlUserChecker = "SELECT * from employee WHERE username = '" +username+"' ";
            
            
            try{
                Statement st = db.getCon().createStatement();
                ResultSet rs = st.executeQuery(sqlUserChecker);
                
                if(rs.next()){
                    checker = true;
                } 
                
            } catch(Exception a){
                a.printStackTrace();
            }  
        return checker;
    }
    public boolean checkLast(String username, String last){
        DB db = new DB();
        boolean checker = false;
        String sqlUserChecker = "SELECT * from employee WHERE username = '" +username+"' ";
            
            
            try{
                Statement st = db.getCon().createStatement();
                ResultSet rs = st.executeQuery(sqlUserChecker);
                
                if(rs.next()){
                    if(rs.getString("last").matches(last))
                        checker = true;
                } 
                
            } catch(Exception a){
                a.printStackTrace();
            }  
        return checker;
    }
    
    public boolean checkEmployeeID(String employeeid){
        db = db.getDB();
        boolean checker = false;
        String sqlUserChecker = "SELECT * from employee WHERE employeeid = '" +employeeid+"' ";
            
            
            try{
                Statement st = db.getCon().createStatement();
                ResultSet rs = st.executeQuery(sqlUserChecker);
                
                if(rs.next()){
                    checker = true;
                } 
                
            } catch(Exception a){
                a.printStackTrace();
            }  
        return checker;
    }
        
//    public String checkType(String id){
//        DB db = new DB();
//        String type = null;
//        String sqlUserChecker = "SELECT type from employee WHERE id = '" +id+"' ";
//            
//            
//            try{
//                Statement st = db.getCon().createStatement();
//                ResultSet rs = st.executeQuery(sqlUserChecker);
//                
//                if(rs.next())
//                    type = rs.getString("type");   
//                
//            } catch(Exception a){
//                a.printStackTrace();
//            }  
//        return type;
//    }
    
}
