import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public void createSchedule(String schedId, String time, String date, String type, String priestID) throws Exception{
		sql = "INSERT INTO `masssched` (`schedid`, `time`, `date`, `mass_type`, `priest_id`) VALUES (?, ?, ?, ?, ?);";
		ps = connection.prepareStatement(sql);
		ps.setString(1, schedId);
		ps.setString(2, time);
		ps.setString(3, date);
		ps.setString(4, type);
		ps.setString(5, priestID);
		ps.execute();
	}
	
	public void createPriestInfo(String lastName, String firstName, String middleInitial) throws Exception{
		/*just edit the sql which will contain the statement that will insert another record to the db
		the codes above will serve as guide*/
		sql = "";
		ps = connection.prepareStatement(sql);
		ps.execute();
	}
	
	public void createMassIntention(String intentionID, String schedID) {
		
	}
	
	public ResultSet getPriestInfo(String priestLName, String priestFName) throws Exception {
        sql = "select * from priest where f_name = ? and l_name = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, priestFName);
        ps.setString(2, priestLName);
        return ps.executeQuery();
	}
	
	public ResultSet getAllSched() throws Exception {
		statement = connection.createStatement();
        sql = "select * from masssched";
        return statement.executeQuery(sql);
	}
	
	public ResultSet getMassSched() throws Exception {
		statement = connection.createStatement();
        sql = "select date, time, mass_type, concat(f_name,\" \",l_name) as name from masssched natural join priest order by 1,2";
        return statement.executeQuery(sql);
	}
	
	
	
	public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (ps != null) {
            	ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {

        }
    }
}
