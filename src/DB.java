import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
/**
*
* @author cgg
*/
public class DB
{
    /**
    * Connect to database
    * @return Connection to database
    * @throws java.lang.Exception
    */
    
    private Connection test;
    private static DB db;
    
    private Connection connect(){
    Connection con = null;
    String url = "jdbc:mysql://localhost:3306/pointofsale";
    String driver = "com.mysql.jdbc.Driver";
    String user = "root";
    String pass = "";
    try {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, pass);
        if (con == null) {
            System.out.println("Connection cannot be established");
        }
        return con;
    } catch (Exception e) {
        System.out.println(e);
    }
    return null;
   }
    
    public static DB getDB() 
    {
        if (db == null)
            db = new DB();
        return db;
    }
    
    public void executeQuery(String query){
        try{
            test = this.getCon();
            Statement stmt = test.createStatement();
            stmt.executeUpdate(query);
            test.close();
        } catch(SQLException ex){
        }                
    }
    
    public String getResult(String query, String columnName) {
        ResultSet rs = null;
        String result = "";
        try{
            test = this.getCon();
            Statement stmt = test.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next())
                result = rs.getString(columnName);
            
            test.close();
        } catch(SQLException ex){
        }   

        return result;
    }
    
    public int getInteger(String query) {
        ResultSet rs = null;
        int result = 0;
        try{
            test = this.getCon();
            Statement stmt = test.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next())
                result = rs.getInt(1);
            
            test.close();
        } catch(SQLException ex){
        }   

        return result;
    }
    
    public Connection getCon() throws SQLException{
        return test=this.connect();
    }
    
    public Vector ProductReport()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        /*Statement st;
        ResultSet rs;
        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";

        rs = st.executeQuery(sql);*/
        PreparedStatement pre = conn.prepareStatement("select * from product");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(rs.getString(2)); //PProduct
            product.add(""+rs.getInt(3)); //PPrice
            product.add(""+rs.getInt(4));//PQuantity
            ProductVector.add(product);
        }

        /*Close the connection after use (MUST)*/
        
        conn.close();

        return ProductVector;
    }
    
    public Vector InventoryReport()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        /*Statement st;
        ResultSet rs;
        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";

        rs = st.executeQuery(sql);*/
        PreparedStatement pre = conn.prepareStatement("select * from inventory");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> inventory = new Vector<String>();
            inventory .add(""+rs.getInt(1)); //id
            inventory .add(rs.getString(2)); //ingredient
            inventory .add(rs.getString(3)); //type
            inventory .add(""+rs.getInt(4));//PQuantity
            ProductVector.add(inventory );
        }

        /*Close the connection after use (MUST)*/
        
        conn.close();

        return ProductVector;
    }
    
    public Vector DeliveryReport()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        /*Statement st;
        ResultSet rs;
        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";

        rs = st.executeQuery(sql);*/
        PreparedStatement pre = conn.prepareStatement("select * from delivery");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> delivery = new Vector<String>();
            delivery.add(""+rs.getInt(1)); //PID
            delivery.add(rs.getString(2)); //PCompany
            delivery.add(rs.getString(3)); //Pingredient
            delivery.add(""+rs.getInt(4));//PQuantity
            delivery.add(""+rs.getDate(5));//PDateOrdered
            delivery.add(""+rs.getDate(6));//PDateOrdered
            
            ProductVector.add(delivery);
        }

        /*Close the connection after use (MUST)*/
        
        conn.close();

        return ProductVector;
    }
    
    public Vector Table1Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        
        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='1'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            
            //product.add(""+rs.getInt(3)); //PPrice

            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
    public Vector Table2Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();

        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='2'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
    public Vector Table3Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();

        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='3'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
    public Vector Table4Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();

        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='4'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
    public Vector Table5Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();

        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='5'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
    public Vector Table6Report()/*String S)*/throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();

        PreparedStatement pre = conn.prepareStatement("select * from pending where tablenum='6'");

        ResultSet rs = pre.executeQuery();

        while(rs.next())
        {
            Vector<String> product = new Vector<String>();
            product.add(""+rs.getInt(1)); //PID
            product.add(""+rs.getInt(2)); //TablueNum
            product.add(rs.getString(3)); //PProduct
            product.add(""+rs.getInt(4));//PQuantity
                  
            PreparedStatement lol = conn.prepareStatement("select * from product");    
            ResultSet lol2 = lol.executeQuery();         
            while(lol2.next()){
                String compare1 = lol2.getString(2);
                String compare2 = rs.getString(3);
                if(compare1.equals(compare2)){
                    int amount1 = lol2.getInt(3);
                    int amount2 = rs.getInt(4);
                    int amount = amount1*amount2;
                    product.add(""+amount);
                }
            }
            ProductVector.add(product);
        }     
        conn.close();

        return ProductVector;
    }
    
     public Vector ProductReportByName(String S)throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        /*Statement st;
        ResultSet rs;
        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";

        rs = st.executeQuery(sql);*/
        PreparedStatement pre = conn.prepareStatement("select * from inventory where ingredient= '"+S+"'");

        ResultSet rs = pre.executeQuery();
        
        while(rs.next())
        {
            Vector<String> inventory = new Vector<String>();
            inventory.add(""+rs.getInt(1)); //id
            inventory.add(rs.getString(2)); //ingredient
            inventory.add(rs.getString(3)); //PType
            inventory.add(""+rs.getInt(4));//PQuantity
            ProductVector.add(inventory);
        }

        /*Close the connection after use (MUST)*/
        
        conn.close();

        return ProductVector;
    }
     
     public Vector ProductReportByType(String S)throws Exception
    {
        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();

        Connection conn = connect();
        /*Statement st;
        ResultSet rs;
        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";

        rs = st.executeQuery(sql);*/
        PreparedStatement pre = conn.prepareStatement("select * from inventory where type= '"+S+"'");

        ResultSet rs = pre.executeQuery();
        
        while(rs.next())
        {
            Vector<String> inventory = new Vector<String>();
            inventory.add(""+rs.getInt(1)); //id
            inventory.add(rs.getString(2)); //ingredient
            inventory.add(rs.getString(3)); //PType
            inventory.add(""+rs.getInt(4));//PQuantity
            ProductVector.add(inventory);
        }

        /*Close the connection after use (MUST)*/
        
        conn.close();

        return ProductVector;
    }
    
}

//public class DB
//{
//   
//    public Connection dbConnection()throws Exception
//    {
//        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//        String myDB ="jdbc:odbc:PointOfSale";
//        return DriverManager.getConnection(myDB);
//    }
//
//    /**
//    * This method will load vector of vector of string and load all the data in
//    * the vector
//    * @return vector of vector of string
//    * @throws java.lang.Exception
//    */
//    public Vector ProductReport()/*String S)*/throws Exception
//    {
//        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        /*Statement st;
//        ResultSet rs;
//        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";
//
//        rs = st.executeQuery(sql);*/
//        PreparedStatement pre = conn.prepareStatement("select * from Product");
//
//        ResultSet rs = pre.executeQuery();
//
//        while(rs.next())
//        {
//            Vector<String> Product = new Vector<String>();
//            Product.add(""+rs.getInt(1)); //PID
//            Product.add(rs.getString(2)); //PName
//            Product.add(""+rs.getInt(3)); //PPrice
//            Product.add(rs.getString(4)); //PType
//            Product.add(rs.getString(5)); //PManufacturer
//            Product.add(""+rs.getInt(6));//PQuantity
//            ProductVector.add(Product);
//        }
//
//        /*Close the connection after use (MUST)*/
//        
//        conn.close();
//
//        return ProductVector;
//    }
//    
//    public Vector PaymentReport()throws Exception
//    {
//        Vector<Vector<String>> SalesVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        PreparedStatement pre = conn.prepareStatement("select * from Sales");
//
//        ResultSet rs = pre.executeQuery();
//
//        while(rs.next())
//        {
//            Vector<String> Sales = new Vector<String>();
//            Sales.add(""+rs.getInt(1)); //PID
//            Sales.add(""+rs.getInt(2));
//            Sales.add(""+rs.getInt(3));
//            Sales.add(""+rs.getInt(4));
//            SalesVector.add(Sales);
//        }
//
//        /*Close the connection after use (MUST)*/
//        if(conn!=null)
//        conn.close();
//
//        return SalesVector;
//    }
//    
//    public Vector CustomerReport()throws Exception
//    {
//        Vector<Vector<String>> CustomerVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        PreparedStatement pre = conn.prepareStatement("select * from Customer");
//
//        ResultSet rs = pre.executeQuery();
//
//        while(rs.next())
//        {
//            Vector<String> Customer = new Vector<String>();
//            Customer.add(""+rs.getInt(1)); //CID
//            Customer.add(rs.getString(2)); //CName
//            Customer.add(rs.getString(3)); //CCNIC
//            Customer.add(rs.getString(4)); //CAddress
//            Customer.add(rs.getString(5)); //CContact
//            CustomerVector.add(Customer);
//        }
//
//        /*Close the connection after use (MUST)*/
//        if(conn!=null)
//        conn.close();
//
//        return CustomerVector;
//    }   
//    public Vector ProductReportByType(String S)throws Exception
//    {
//        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        /*Statement st;
//        ResultSet rs;
//        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";
//
//        rs = st.executeQuery(sql);*/
//        PreparedStatement pre = conn.prepareStatement("select * from Product where [PType]= '"+S+"'");
//
//        ResultSet rs = pre.executeQuery();
//        
//        while(rs.next())
//        {
//            Vector<String> Product = new Vector<String>();
//            Product.add(""+rs.getInt(1)); //PID
//            Product.add(rs.getString(2)); //PName
//            Product.add(""+rs.getInt(3)); //PPrice
//            Product.add(rs.getString(4)); //PType
//            Product.add(rs.getString(5)); //PManufacturer
//            Product.add(""+rs.getInt(6));//PQuantity
//            ProductVector.add(Product);
//        }
//
//        /*Close the connection after use (MUST)*/
//        
//        conn.close();
//
//        return ProductVector;
//    }
//    
//    public Vector ProductReportByName(String S)throws Exception
//    {
//        Vector<Vector<String>> ProductVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        /*Statement st;
//        ResultSet rs;
//        st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        String sql = "SELECT * FROM Product WHERE [PName] ='"+S+"'";
//
//        rs = st.executeQuery(sql);*/
//        PreparedStatement pre = conn.prepareStatement("select * from Product where [PName]= '"+S+"'");
//
//        ResultSet rs = pre.executeQuery();
//        
//        while(rs.next())
//        {
//            Vector<String> Product = new Vector<String>();
//            Product.add(""+rs.getInt(1)); //PID
//            Product.add(rs.getString(2)); //PName
//            Product.add(""+rs.getInt(3)); //PPrice
//            Product.add(rs.getString(4)); //PType
//            Product.add(rs.getString(5)); //PManufacturer
//            Product.add(""+rs.getInt(6));//PQuantity
//            ProductVector.add(Product);
//        }
//
//        /*Close the connection after use (MUST)*/
//        
//        conn.close();
//
//        return ProductVector;
//    }
//    
//    public Vector CustomerByName(String S)throws Exception
//    {
//        Vector<Vector<String>> CustomerVector = new Vector<Vector<String>>();
//
//        Connection conn = dbConnection();
//        PreparedStatement pre = conn.prepareStatement("select * from Customer where [CName]= '"+S+"'");
//
//        ResultSet rs = pre.executeQuery();
//
//        while(rs.next())
//        {
//            Vector<String> Customer = new Vector<String>();
//            Customer.add(""+rs.getInt(1)); //CID
//            Customer.add(rs.getString(2)); //CName
//            Customer.add(rs.getString(3)); //CCNIC
//            Customer.add(rs.getString(4)); //CAddress
//            Customer.add(rs.getString(5)); //CContact
//            CustomerVector.add(Customer);
//        }
//
//        /*Close the connection after use (MUST)*/
//        if(conn!=null)
//        conn.close();
//
//        return CustomerVector;
//    }
//}