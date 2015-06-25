package example.ajax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainService {

	String url = "jdbc:mysql://localhost/maca?";
	String user = "user=sqluser&";
	String password = "password=sqluserpw";

	public Statement dataStm() throws SQLException {

		Connection myConn = DriverManager.getConnection(url + user + password);

		Statement myStm = myConn.createStatement();

		return myStm;
	}

	public List<Map<String, Object>> listPerson(String sql) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSet myRs = dataStm().executeQuery(sql);

		while (myRs.next()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			String name = myRs.getString("name");
			int age = myRs.getInt("age");
			long id = myRs.getLong("id");
			String city = myRs.getString("city");

			map.put("name", name);
			map.put("age", age);
			map.put("id", id);
			map.put("city", city);

			list.add(map);
		}
		return list;

	}

	public List<Map<String, Object>> listPerson() throws SQLException {
		return listPerson("select * from person");
	}

	public Map<String, Object> getPerson(Map<String, String> params)
			throws SQLException {
		long id = Long.valueOf(params.get("id"));
		String sql = "select * from person where id = " + id;

		try {
			return listPerson(sql).get(0);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> getSqlDataPerson() {

		try {
			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> listBook() throws SQLException {
		return listBook("select * from book");
	}

	public List<Map<String, Object>> listBook(String sql) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSet myRs = dataStm().executeQuery(sql);

		while (myRs.next()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			String title = myRs.getString("title");
			int year = myRs.getInt("year");
			long id = myRs.getLong("id");

			map.put("title", title);
			map.put("year", year);
			map.put("id", id);

			list.add(map);
		}
		return list;

	}

	public List<Map<String, Object>> getSqlDataBook() {
		try {
			return listBook();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> add(Map<String, String> params) {

		String paramName = params.get("name");
		int paramAge = Integer.valueOf(params.get("age"));
		String paramCity = params.get("city");

		try {
			String sql = "insert into person" + "(name,age,city)" + "values('"
					+ paramName + "', " + paramAge + ", '" + paramCity + "')";

			dataStm().executeUpdate(sql);

			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> delete(Map<String, String> params) {
		long i = Long.valueOf(params.get("id"));

		try {

			String sql = "delete from person where id = " + i;

			dataStm().executeUpdate(sql);

			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> editPerson(Map<String, String> params) {

		long i = Long.valueOf(params.get("id"));
		String paramName = params.get("name");
		int paramAge = Integer.valueOf(params.get("age"));
		String paramCity = params.get("city");

		try {

			String sql = "update person set name='" + paramName + "', age="
					+ paramAge + ", city='" + paramCity + "' where id = " + i;

			dataStm().executeUpdate(sql);

			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> searchPerson(Map<String, String> params) {

		String val = params.get("val");

		try {

			String sql = "select * from person where name like '" + val + "%'";

			return listPerson(sql);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

}
