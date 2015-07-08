package example.ajax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

	public PreparedStatement dataPreStm(String sql) throws SQLException {

		Connection myConn = DriverManager.getConnection(url + user + password);

		PreparedStatement stm = myConn.prepareStatement(sql);

		return stm;
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

	public Map<String, Object> searchResult(Map<String, String> params)
			throws SQLException {
		long id = Long.valueOf(params.get("id"));
		String table = params.get("table");

		try {
			if (table.equals("person")) {
				return listPerson("select * from" + table + "where id=" + id)
						.get(0);

			}
			return listBook("select * from" + table + "where id=" + id).get(0);

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

	public List<Map<String, Object>> add(Map<String, String> params) {
		String paramName = params.get("name");
		int paramAge = Integer.valueOf(params.get("age"));
		String paramCity = params.get("city");

		try {
			PreparedStatement stm = dataPreStm("insert into person (name, age, city) values(?, ?, ?)");
			stm.setString(1, paramName);
			stm.setInt(2, paramAge);
			stm.setString(3, paramCity);
			stm.executeUpdate();

			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> addBook(Map<String, String> params) {
		String paramTitle = params.get("title");
		int paramYear = Integer.valueOf(params.get("year"));

		try {
			PreparedStatement stm = dataPreStm("insert into book (title,year) values(?,?)");
			stm.setString(1, paramTitle);
			stm.setInt(2, paramYear);
			stm.executeUpdate();

			return listBook();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> delete(Map<String, String> params) {
		long id = Long.valueOf(params.get("id"));
		String table = params.get("table");

		try {
			PreparedStatement stm = dataPreStm("delete from " + table
					+ " where id = ?");
			stm.setLong(1, id);
			stm.executeUpdate();

			if (table.equals("person")) {
				return listPerson();
			}
			return listBook();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> editPerson(Map<String, String> params) {

		String paramName = params.get("name");
		int paramAge = Integer.valueOf(params.get("age"));
		String paramCity = params.get("city");
		long id = Long.valueOf(params.get("id"));

		try {
			PreparedStatement stm = dataPreStm("update person set name=?, age=?, city=? where id=?");
			stm.setString(1, paramName);
			stm.setInt(2, paramAge);
			stm.setString(3, paramCity);
			stm.setLong(4, id);
			stm.executeUpdate();

			return listPerson();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> editBook(Map<String, String> params) {

		String paramTitle = params.get("title");
		int paramYear = Integer.valueOf(params.get("year"));
		long id = Long.valueOf(params.get("id"));

		try {

			PreparedStatement stm = dataPreStm("update book set title=?,year=? where id=?");
			stm.setString(1, paramTitle);
			stm.setInt(2, paramYear);
			stm.setLong(3, id);
			stm.executeUpdate();

			return listBook();

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

	public List<Map<String, Object>> searchBook(Map<String, String> params) {
		String val = params.get("val");
		
		try {
			
			String sql = "select * from book where title like '" + val + "%'";
			return listBook(sql);

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

}
