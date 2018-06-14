import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Client {
	Connection cn;

	// currentResults holds current results from a search() so that other methods can access them
	ResultSet currentResults;

	// currentItem holds the row number of an itme the user is looking at (so we can use currentResults.absolute(currentItem) to go to it
	Integer currentItem;

	public Client(String dbname, String userid, String password) throws SQLException {
		cn = null;
		currentResults = null;
		currentItem = null;

		try
		{
		    
		    try
		    {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +dbname , userid, password);
		    }
		    catch (Exception e)
		    {
			System.out.println("connection failed: " + e);
		    }
		    try
		    {
			System.out.println("show databases");
			Statement st1 = cn.createStatement();
			ResultSet rs1 = st1.executeQuery("show databases");
			while (rs1.next())
			{
			    System.out.println("Database: "+rs1.getString(1));
			}
			st1.close();
		    }
		    catch (SQLException e) {
			System.out.println("Query failed: " + e);
		    }
		    try
		    {
			System.out.println("use " + dbname);
			Statement st2 = cn.createStatement();
			st2.executeUpdate("use " + dbname);
		    }
		    catch (SQLException e) {
			System.out.println("Update failed: " + e);
		    }
		    boolean i=true;
		    while(i)
		    {
		    System.out.println("Select an option to continue:\n"
		    		+ "1. Place a New Order\n"
		    		+ "2. Show all Employee details\n"
		    		+ "3. Show all Chef details\n"
		    		+ "4. Show all the Customer details\n"
		    		+ "5. Show all the Orders Placed\n"
		    		+ "6. Show details of a specific Employee\n"
		    		+ "7. Show details of a particular Chef\n"
		    		+ "8. Show details of a customer\n"
		    		+ "9. show details of an order\n"
		    		+ "0. Exit\n");
		    System.out.println("Enter a Value:\n");
		    Scanner val= new Scanner(System.in);
		    while(!val.hasNextInt()) val.next();
		    {
		    int value=val.nextInt();	
		    switch (value)
		    {
		   
		    case 0:
		    	 try
				    {
					
					Statement st1 = cn.createStatement();
					ResultSet rs1 = st1.executeQuery("select * from employee_list");
					st1.close();
					System.exit(0);
				    }
				    catch (SQLException e) {
					System.out.println("Query failed: " + e);
				    }
				    break;
		    	
		    case 1:
		    	try
			    {
				System.out.println("Enter Order details:");
				System.out.println("Enter Customer Name:");
				Scanner cust= new Scanner(System.in);
				String customer= cust.next();
				System.out.println("Enter Order Number/ID:");
				Scanner ord= new Scanner(System.in);
				String order= ord.next();
				System.out.println("Enter Employee Serving:");
				Scanner emp= new Scanner(System.in);
				String employee= emp.next();
				System.out.println("Enter Bill Amount:");
				Scanner bil= new Scanner(System.in);
				int bill= bil.nextInt();
				System.out.println("Enter Chef name:");
				Scanner chf= new Scanner(System.in);
				String chef= chf.next();
				System.out.println("Enter Item name:");
				Scanner itm= new Scanner(System.in);
				String item= itm.next();
				System.out.println("Enter Qunatity:");
				Scanner quant= new Scanner(System.in);
				int quantity= quant.nextInt();
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("insert into place_order values('" +customer +"','" +order +"','" +employee +"','" +bill +"')");
				st1.close();
				Statement st2= cn.createStatement();
				ResultSet rs2= st2.executeQuery("insert into order_details values ('" +order +"','" +item +"'," +quantity +",'" +chef +"')");
				System.out.println("Table Updated\n");
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    
		    	
		    case 2:
		    try
		    {
			System.out.println("Showing Employee Details:");
			Statement st1 = cn.createStatement();
			ResultSet rs1 = st1.executeQuery("select * from employee_list");
			while (rs1.next())
			{
				System.out.println("\t\t" +"Employee ID" +"\t\t" +"Employee name" +"\t\t" +"Phone Number" +"\t\t" +"Salary" +"\t\t" +"Manager");
			    System.out.println("Employee Details: "+rs1.getString("employee_id") +"\t\t" +rs1.getString("e_name") +"\t\t" +rs1.getString("phone_no") +"\t\t" +rs1.getString("salary") +"\t\t" +rs1.getString("manager"));
			}
			st1.close();
		    }
		    catch (SQLException e) {
			System.out.println("Query failed: " + e);
		    }
		    break;
		    
		    case 3:
		    	try
			    {
				System.out.println("Showing Chef Details:");
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from chef_list");
				while (rs1.next())
				{
					   System.out.println("\t\t"+"chef_id" +"\t\t" +"Name" +"\t\t" +"Cuisine" +"\t\t" +"Salary");
				    System.out.println("Chef Details: "+rs1.getString("chef_id") +"\t\t" +rs1.getString("ch_name") +"\t\t" +rs1.getString("cuisine") +"\t\t" +rs1.getString("salary"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
			    
		    case 4:
		    	try
			    {
				System.out.println("Showing Customer Details:");
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from customer_list");
				while (rs1.next())
				{
					System.out.println("\t\t" +"Customer ID" +"\t\t" +"Name" +"\t\t" +"Phone Number" +"\t\t" +"Email ID");
				    System.out.println("Customer Details: "+rs1.getString("customer_id") +"\t\t" +rs1.getString("c_name") +"\t\t" +rs1.getString("phone_no") +"\t\t" +rs1.getString("email_id"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    case 5:
		    	try
			    {
				System.out.println("Showing Order Details:");
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from place_order");
				while (rs1.next())
				{
					System.out.println("\t\t" +"Customer ID" +"\t\t" +"Order ID" +"\t\t" +"Attended By" +"\t\t" +"Bill Amount");
				    System.out.println("Orders Placed: "+rs1.getString("customer_id") +"\t\t" +rs1.getString("order_id") +"\t\t" +rs1.getString("employee_id") +"\t\t" +rs1.getString("bill_amount"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    case 8:
		    	try
			    {
				System.out.println("Enter Customer Phone Number:");
				Scanner phn= new Scanner(System.in);
				int number= phn.nextInt();
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from customer_list where phone_no='" +number +"'");
				while (rs1.next())
				{
					System.out.println("\t\t" +"Customer ID" +"\t\t" +"Name" +"\t\t" +"Phone Number" +"\t\t" +"Email ID");
				    System.out.println("Customer Details: "+rs1.getString("customer_id") +"\t\t" +rs1.getString("c_name") +"\t\t" +rs1.getString("phone_no") +"\t\t" +rs1.getString("email_id"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    case 6:
		    	try
			    {
				System.out.println("Enter Employee ID:");
				Scanner val1= new Scanner(System.in);
				String val2= val1.next();
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from employee_list where employee_id='" +val2 +"'");
				while (rs1.next())
				{
					System.out.println("\t\t" +"Employee ID" +"\t\t" +"Employee name" +"\t\t" +"Phone Number" +"\t\t" +"Salary" +"\t\t" +"Manager");
				    System.out.println("Employee Details: "+rs1.getString("employee_id") +"\t\t" +rs1.getString("e_name") +"\t\t" +rs1.getString("phone_no") +"\t\t" +rs1.getString("salary") +"\t\t" +rs1.getString("manager"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    case 7:
		    	try
			    {
				System.out.println("Enter Chef ID:");
				Scanner val1= new Scanner(System.in);
				String val2= val1.next();
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from chef_list where chef_id='" +val2 +"'");
				while (rs1.next())
				{
					System.out.println("\t\t"+"chef_id" +"\t\t" +"Name" +"\t\t" +"Cuisine" +"\t\t" +"Salary");
				    System.out.println("Chef Details: "+rs1.getString("chef_id") +"\t\t" +rs1.getString("ch_name") +"\t\t" +rs1.getString("cuisine") +"\t\t" +rs1.getString("salary"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    case 9:
		    	try
			    {
				System.out.println("Enter Customer ID:");
				Scanner val1= new Scanner(System.in);
				String val2= val1.next();
				System.out.print("Enter Employee ID:");
				Scanner val3= new Scanner(System.in);
				String val4= val3.next();
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from place_order where customer_id='" +val2 +"' " +"and employee_id='" +val4 +"'");
				while (rs1.next())
				{
					System.out.println("\t\t" +"Customer ID" +"\t\t" +"Order ID" +"\t\t" +"Employee ID" +"\t\t" +"Bill Amount");
				    System.out.println("Customer Details: "+rs1.getString("customer_id") +"\t\t" +rs1.getString("order_id") +"\t\t" +rs1.getString("employee_id") +"\t\t" +rs1.getString("bill_amount"));
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	break;
		    	
		    	
			    
		    
		}
	    }
		    }
		    cn.close();
	}
		
		catch (SQLException e)
		{
		    System.out.println("Some other error: " + e);
		}
	}

	public static void main (String[] args) throws SQLException {
		String dbname = "restaurantdb";
		String userid = "tejaswi";
		String password = "zooghoow";

	
			Client	exam=new Client(dbname, userid, password);
			

		
	}
}
