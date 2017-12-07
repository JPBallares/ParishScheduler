import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ParishSchedulerController {
	private Connection connection;
    private Statement statement;
    private PreparedStatement ps;
    private ResultSet resultSet;
    private String sql;
	
	public void dbaseConnect(String url, String user, String pass) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
    }
	
	public void createIntension(String type, String toName, String fromName,String timeSchedID) throws Exception{
		sql = "Insert into `intention` (`type`,`for_name`,`from_name`,`time_sched`) values(?,?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, type);
		ps.setString(2, toName);
		ps.setString(3, fromName);
		ps.setString(4, timeSchedID);
		ps.execute();
	}
	
	public void createSchedule(String time, String day, String type, String priestID) throws Exception{
		sql = "Insert into `masssched` (`time`,`day`,`mass_type`,`priest_id`) values (?,?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, time);
		ps.setString(2, day);
		ps.setString(3, type);
		ps.setString(4, priestID);
		ps.execute();
	}
}