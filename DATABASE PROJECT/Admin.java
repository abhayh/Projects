import java.io.*;
import java.sql.*;
			
import java.util.Scanner;

public class Admin {
	Connection cn;

	// currentResults holds current results from a search() so that other methods can access them
	ResultSet currentResults;

	// currentItem holds the row number of an itme the user is looking at (so we can use currentResults.absolute(currentItem) to go to it
	Integer currentItem;

	public Admin(String dbname, String userid, String password) {
		cn = null;
		currentResults = null;
		currentItem = null;

		try
		{
		    
		    try
		    {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, userid, password);
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
			while(i==true){
		    System.out.println("Select an option to continue:\n"
		    		+ "1. Add a waiter to Employees\n"
		    		+ "2. Add a Chef\n"
		    		+ "3. Add a Manager to Employees\n"
		    		+ "4. Remove a Manager\n"
		    		+ "5. Remove a Chef\n"
		    		+ "6. Remove an Employee\n"
		    		+ "0. Exit");
		    System.out.println("Enter a Value:\n");
		    Scanner val= new Scanner(System.in);
		    while(!val.hasNextInt()) val.next();
		    {
		    int value=val.nextInt();	
		    switch (value)
		    {
		     
		    case 1:
		    	try
			    {
				System.out.println("Enter Employee details:");
				System.out.println("Enter Employee ID:");
				Scanner cust= new Scanner(System.in);
				String customer= cust.next();
				System.out.println("Enter Employee Name:");
				Scanner ord= new Scanner(System.in);
				String order= ord.next();
				System.out.println("Enter Employee Phone Number:");
				Scanner emp= new Scanner(System.in);
				String employee= emp.next();
				System.out.println("Enter Salary:");
				Scanner bil= new Scanner(System.in);
				int bill= bil.nextInt();
				System.out.println("Enter Manager ID:");
				Scanner chf= new Scanner(System.in);
				String chef= chf.next();
				System.out.println("Enter Job Status:");
				Scanner itm= new Scanner(System.in);
				String item= itm.next();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("insert into employee_list values('" +customer +"','" +order +"','" +employee +"','" +bill +"','" +chef +"')");
				st1.close();
				Statement st2= cn.createStatement();
				int rs2= st2.executeUpdate("insert into waiter_list values ('" +customer +"','" +item +"')");
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
				break;
		    	
		    case 2:
		    try
			    {
				System.out.println("Enter Chef details:");
				System.out.println("Enter Chef ID:");
				Scanner cust= new Scanner(System.in);
				String customer= cust.next();
				System.out.println("Enter Chef Name:");
				Scanner ord= new Scanner(System.in);
				String order= ord.next();
				System.out.println("Enter Chef  type:");
				Scanner emp= new Scanner(System.in);
				String employee= emp.next();
				System.out.println("Enter Salary:");
				Scanner bil= new Scanner(System.in);
				int bill= bil.nextInt();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("insert into chef_list values('" +customer +"','" +order +"','" +employee +"','" +bill +"')");
				st1.close();
				
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
break;
		    
		    case 3:
		    	try
			    {
				System.out.println("Enter Employee details:");
				System.out.println("Enter Employee ID:");
				Scanner cust= new Scanner(System.in);
				String customer= cust.next();
				System.out.println("Enter Employee Name:");
				Scanner ord= new Scanner(System.in);
				String order= ord.next();
				System.out.println("Enter Employee Phone Number:");
				Scanner emp= new Scanner(System.in);
				String employee= emp.next();
				System.out.println("Enter Salary:");
				Scanner bil= new Scanner(System.in);
				int bill= bil.nextInt();
				System.out.println("Enter Manager ID:");
				Scanner chf= new Scanner(System.in);
				String chef= chf.next();
				System.out.println("Enter Skill:");
				Scanner itm= new Scanner(System.in);
				String item= itm.next();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("insert into employee_list values('" +customer +"','" +order +"','" +employee +"','" +bill +"','" +chef +"')");
				st1.close();
				Statement st2= cn.createStatement();
				int rs2= st2.executeUpdate("insert into order_details values ('" +customer +"','" +item +"')");
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
break;
			    
		    case 4:
		    	try
			    {
				System.out.println("Enter Employee ID:");
				Scanner val1= new Scanner(System.in);
				int val2= val1.nextInt();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("delete from employee_list where employee_id='" +val2 +"'");
				Statement st2 = cn.createStatement();
				int rs2= st2.executeUpdate("delete from manager_list where employee_id='" +val2 +"'");
			
				{
				    System.out.println("Manager Deleted");
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
				System.out.println("Enter Chef ID:");
				Scanner val1= new Scanner(System.in);
				int val2= val1.nextInt();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("delete from chef_list where chef_id='" +val2 +"'");
				
				{
				    System.out.println("Chef Deleted");
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
				int val2= val1.nextInt();
				Statement st1 = cn.createStatement();
				int rs1 = st1.executeUpdate("delete from employee_list where employee_id='" +val2 +"'");
				Statement st2 = cn.createStatement();
				int rs2 = st2.executeUpdate("delete from waiter_list where employee_id='" +val2 +"'");
			
				{
				    System.out.println("Waiter Deleted");
				}
				st1.close();
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
break;
			case 0:
			try{

				System.exit(0);
				}
			catch(Exception e)
			{
				System.out.println("Exit Failed: ");
			}
		    	
		    	
			 }   
		    cn.close();
		}
	    }
	}
		
		catch (SQLException e)
		{
		    System.out.println("Some other error: " + e);
		}
	}

	public static void main (String[] args) {
		String dbname = "restaurantdb";
		String userid = "tejaswi";
		String password = "zooghoow";
		Admin app = new Admin(dbname, userid, password);

		
	}
}