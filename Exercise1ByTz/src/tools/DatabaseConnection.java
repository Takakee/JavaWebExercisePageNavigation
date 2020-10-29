package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import vo.User;

public class DatabaseConnection {
	// 数据库参数配置文件
	private static final String JDBCPROPERTY = "jdbc.properties";
	// 准备数据库的四大参数：
	private static String DBDRIVER = "";
	private static String DBURL = "";
	private static String DBUSER = "";
	private static String PASSWORD = "";

	private Connection conn; // 准备一个数据库连接对象

	static {
		try {
			Properties property = new Properties();

			// 获取字节码目录，并且转成stream
			String path = User.class.getClassLoader().getResource("jdbc.properties").getPath();
			InputStream is = new BufferedInputStream(new FileInputStream(path));
			property.load(new InputStreamReader(is, "utf-8"));
			is.close();

			DBDRIVER = property.getProperty("DBDRIVER");
			DBURL = property.getProperty("DBURL");
			DBUSER = property.getProperty("DBUSER");
			PASSWORD = property.getProperty("PASSWORD");

			// 加载驱动，只需注册一次就行
			Class.forName(DBDRIVER);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 构造方法，实例化对象时创建连接对象
	public DatabaseConnection() {
		try {
			this.conn = DriverManager.getConnection(DBURL, DBUSER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 直接返回实例化对象时创建的连接对象
	public Connection getConnection() {
		return this.conn;
	}

	// 关闭连接对象
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
