//SQLITE JDBC JAVA CONNECTION!
package sqlite1;
import java.sql.*;
import java.sql.Statement;

public class sqlite1 {
	public static void main(String args[]) {
		Connection con = null;
		try {
			
			con = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\muleDB.db");//connecting to database using jdbc
			System.out.println("Successful Connection");
		
			try {
				deleteTable(con);
			}catch(Exception ignored) {
			//if nothing ignore
			}
				createTable(con);
			
				System.out.println("Inserting Data");
				insertmovie(con, "The Shawshank Redemption", "Tim Robbins", "nan" , 1994, "Frank Darabont");
				insertmovie(con, "Spiderman no way home", "Tom Holland", "Zendaya" , 2021, "John Watts");
				insertmovie(con, "Lord of the Rings", "Elijah Wood", "nan", 2003 , "Peter Jackson");
				insertmovie(con, "The Matrix", "Keanu Reeves", "Carrie-Anne Moss", 1999, "The Wachowskis");
				insertmovie(con, "Justice League", "Ben Affleck", "Gal Gadot", 2021, "Zack Snyder");
				insertmovie(con, "Spider-Man","Tobey Maguire","Kristen Dunst", 2002," Sam Raimi");
				insertmovie(con, "Avengers: Endgame", "Robert Downey Jr.", "Scarlett Johannsson", 2019, "The Russo Brothers");
				insertmovie(con, "John Wick", "Keanu Reeves", "Bridget Moynahan", 2014, "Chad Stahelski");
				insertmovie(con, "Sicario", "Josh Brolin", "Emily Blunt", 2015, "Denis Villeneuve");
				insertmovie(con, "War of the worlds", "Tom Cruise", "Dakota Fanning", 2005, "Steven Spielberg");
				insertmovie(con, "Jurassic Park", "Sam Neill", "Laura Dern", 1993, "Steven Spielberg");

				System.out.println();
				System.out.println("Database:");
				displayDatabase(con,"movies");//display the table
				displayKeanu(con,"movies");
			
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getClass().getName()+ ": " + e.getMessage());//print out the errors if any
			}
			finally{
				if (con!=null){
					try {
						con.close();//shutdown the database
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}
	}
	private static void displayDatabase(Connection con,String tableName) throws SQLException{

		String selectSQL="SELECT * from " + tableName; //Querying using the SELECT statement
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(selectSQL);
		System.out.println("********** "+tableName+" *************\n");
		while(rs.next()) {
			System.out.print("Movie :" + rs.getString("title") + ", ");
			System.out.print(rs.getString("lactor") + ", ");
			System.out.print(rs.getString("lactress") + ", ");
			System.out.print(rs.getInt("release") + ", ");
			System.out.println(rs.getString("director"));
			}
		System.out.println("End");
	}
	
	private static void insertmovie(Connection con,String title,String lactor,String lactress,int release,String director) throws SQLException{
	String insertSQL="INSERT INTO movies(title, lactor, lactress, release, director) VALUES(?,?,?,?,?)";//INSERT statement
	PreparedStatement pst= con.prepareStatement(insertSQL);//preparing statement pst to execute insertSQL
	pst.setString(1, title);
	pst.setString(2, lactor);
	pst.setString(3, lactress);
	pst.setInt(4, release);
	pst.setString(5, director);
	pst.executeUpdate();
}
	
	private static void createTable(Connection con) throws SQLException{
	String createTablesql="CREATE TABLE movies (title varchar(30),lactor varchar(30),"
			+ "lactress varchar(30),release integer,director varchar(30));";//CREATE TABLE statement
	Statement stmt=con.createStatement();
	stmt.execute(createTablesql);
}
	
	private static void deleteTable(Connection con) throws SQLException{
	String deleteTablesql ="DROP TABLE movies";//delete table
	Statement stmt=con.createStatement();
	stmt.execute(deleteTablesql);
}
	
	private static void displayKeanu(Connection con,String tableName) throws SQLException{
		String k="Keanu Reeves";
		String selectSQL="SELECT title,lactor from movies WHERE lactor='Keanu Reeves'"; //GET Keanu!
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(selectSQL);
		
		while(rs.next()) {
			System.out.print("Movie :" + rs.getString("lactor") + ", ");
			System.out.println(rs.getString("title"));
			}
		System.out.println("End");
	}
}

