package defaultPackage;
import java.sql.*;

public class DAO {
	private Connection connection;
	private Statement statement;

	public DAO() throws Exception {
		String user = "mysql";
		String password="prac";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection=DriverManager.getConnection("jdbc:mysql://localhost/ts1?user="+user+"&password="+password);
		statement=connection.createStatement();
	}

	// execute queries
	public ResultSet executeSQL(String query) throws SQLException {
		System.out.println("Catching data of query \""+query+"\" from database");
		return statement.executeQuery(query);
	}

	// TODO: code for updates for Assignments 2, 3 and 4.
	public void disconnectBD() throws SQLException {
		statement.close();
		connection.close();
	}
}