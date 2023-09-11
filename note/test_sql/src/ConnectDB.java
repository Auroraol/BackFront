import java.sql.*;


public class ConnectDB {

	static final String DRIVER =  "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String URL =  "jdbc:sqlserver://localhost:1433;databaseName=test6";
	private static final String DB_NAME = "test6";
	static final String DB_USER = "sa";
	static final String DB_PASSWORD = "741106lfj";

	private  Connection conn = null;   //连接接口
	Statement stmt = null; //
	private  PreparedStatement ps = null;    // 预编译 SQL 语句对象
	ResultSet rs = null; // 结果集对象
;

	// 连接数据库
	public  void getConnection() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, DB_USER,DB_PASSWORD);
			System.out.println("成功连接到数据库"); //加入判断
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//关闭连接
	public void close() throws SQLException {
		try {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null){
				stmt.close();
			}
			if (conn != null) {
				System.out.println("关闭连接!");
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 	   1题
	 **/
	// 创建表
	public void createTables() throws SQLException {
		try {
			String sql1 = "CREATE TABLE student (" +
					"sno VARCHAR(9) NOT NULL PRIMARY KEY," +
					"sname VARCHAR(20) NOT NULL," +
					"ssex VARCHAR(2)," +
					"sage SMALLINT," +
					"sdept VARCHAR(20)" +
					")";
			String sql2 = "CREATE TABLE course (" +
					"cno CHAR(4) NOT NULL PRIMARY KEY," +
					"cname CHAR(40) NOT NULL," +
					"cpno CHAR(4)," +
					"ccredit SMALLINT" +
					")";
			String sql3 = "CREATE TABLE sc (" +
					"sno CHAR(9) NOT NULL," +
					"cno CHAR(4) NOT NULL," +
					"grade SMALLINT," +
					"PRIMARY KEY (sno, cno)" +
					")";

			stmt = conn.createStatement();
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 	   2题
	 **/
	//对学生表student添加学生
	private  void addStudent(String sno, String sname, String ssex, int age, String sdept)throws SQLException {
		try {
			//学生id已存在,直接返回
			if (searchStudent(sno) == 1){
				return;
			}
			String sql = "INSERT INTO student (sno,sname,ssex,sage,sdept) VALUES (?,?,?,?,?)"; // SQL 插入语句
			ps = conn.prepareStatement(sql); // 预编译 SQL 语句

			ps.setString(1, sno); // 设置参数值
			ps.setString(2, sname);
			ps.setString(3, ssex);
			ps.setInt(4, 20);
			ps.setString(5, sdept);

			int n = ps.executeUpdate(); // 执行插入操作，返回受影响的行数
			if (n > 0) {
				System.out.println("添加学生成功！");
			} else {
				System.out.println("添加学生失败！");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//对学生表student删除学生
	private  void  deleteStudent(String sno) throws SQLException {
		try {
			String sql = "DELETE FROM student WHERE sno=?"; // SQL 删除语句
			ps = conn.prepareStatement(sql); // 预编译 SQL 语句

			ps.setString(1, sno); // 设置参数值

			int n = ps.executeUpdate(); // 执行删除操作，返回受影响的行数
			if (n > 0) {
				System.out.println("删除学生成功！");
			} else {
				System.out.println("删除学生失败！");
			}
		}catch (SQLException e) {
				throw new RuntimeException(e);
		}
	}
	//修改学生年龄
	void  updateStudent(int sage, String sno) throws SQLException {
		try {
			String sql = "UPDATE student SET sage=? WHERE sno=?"; // SQL 更新语句
			ps = conn.prepareStatement(sql); // 预编译 SQL 语句

			ps.setInt(1, sage); // 设置参数值
			ps.setString(2, sno);

			int n = ps.executeUpdate(); // 执行更新操作，返回受影响的行数
			if (n > 0) {
				System.out.println("修改学生成功！");
			} else {
				System.out.println("修改学生失败！");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 根据学号查询学生数据
	void  queryStudent(String sno) throws SQLException {
		try {
			// 没有学生信息就返回
			if (searchStudent(sno) == 0){
				return;
			}
			String sql = "SELECT * FROM student WHERE sno=?"; // SQL 查询语句
			ps = conn.prepareStatement(sql); // 预编译 SQL 语句

			ps.setString(1, sno); // 设置参数值

			rs = ps.executeQuery(); // 执行查询操作，返回结果集

			if (rs.next()) {
				System.out.println("学号：" + rs.getString("sno"));
				System.out.println("姓名：" + rs.getString("sname"));
				System.out.println("性别：" + rs.getString("ssex"));
				System.out.println("年龄：" + rs.getInt("sage"));
				System.out.println("专业：" + rs.getString("sdept"));
			} else {
				System.out.println("未找到该学生的数据！");
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 搜索
	private int searchStudent(String sno) throws SQLException {
		String sql = "SELECT * FROM student WHERE sno=?"; // SQL 查询语句
		ps = conn.prepareStatement(sql); // 预编译 SQL 语句
		ps.setString(1, sno); // 设置参数值
		rs = ps.executeQuery(); // 执行查询操作，返回结果集
		if (rs.next()) {
			return 1;
		}
		return 0;
	}

	//使用java、jdbc实现对学生表student添加学生、删除学生、修改学生年龄、根据学号查询学生数据的功能。
	public  void studentOperation() throws SQLException {

		// 添加
		System.out.println("---------------student------------------");
		addStudent("201215121", "李勇", "男", 20, "CS");
		addStudent("201215122", "刘晨", "女", 19, "CS");
		addStudent("201215123", "王敏", "女", 19, "MA");
		addStudent("201215125", "张立", "男", 19, "IS");
		addStudent("201215126", "张三(测试)", "男", 20, "CS");

		//删除
		deleteStudent("201215126");

		//修改学生年龄
	    updateStudent(60, "201215121");

		//根据学号查询学生数据
		queryStudent("201215121");
	}

	/**
	 * 	   3题
	 **/
	//课程表course添加课程
	private void addCourse(String cno, String cname, String cpno, int ccredit)  {
		try {
			// 课程id存在,直接返回
			if (searchCourse(cno) == 1) {
				return;
			}
			String sql = "INSERT INTO course VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);

			ps.setString(1, cno);
			ps.setString(2, cname);
			ps.setString(3, cpno);
			ps.setInt(4, ccredit);

			int n = ps.executeUpdate();  //执行
			if (n > 0) {
				System.out.println("添加课程成功！");
			} else {
				System.out.println("添加课程失败！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//课程表course删除课程
	private void deleteCourse(String cno) throws SQLException {
		try {
			String sql = "DELETE FROM course WHERE cno=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cno);
			int n = ps.executeUpdate();  //执行
			if (n > 0) {
				System.out.println("删除课程成功！");
			} else {
				System.out.println("删除课程失败！");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 搜索
	private int searchCourse(String cno) throws SQLException {
		String sql = "SELECT * FROM course WHERE cno=?"; // SQL 查询语句
		ps = conn.prepareStatement(sql); // 预编译 SQL 语句
		ps.setString(1, cno); // 设置参数值
		rs = ps.executeQuery(); // 执行查询操作，返回结果集
		if (rs.next()) {
			return 1;
		}
		return 0;
	}

	//使用java、jdbc实现对课程表course添加课程、删除课程的功能。
	public  void courseOperation() throws SQLException {
		System.out.println("---------------course------------------");
		//添加课程
		addCourse("1", "数据库", "1", 4);
		addCourse("2", "信息系统", "1", 2);
		addCourse("3", "数学", "1", 3);
		addCourse("4", "数据结构", "1", 4);
		//删除课程
		deleteCourse("4");

	}

	/**
	 * 	   4题
	 **/
	private void addSCRecord(String sno, String cno, int grade) throws SQLException {
		try {
		// 学生id和课程id有一个不存在则返回
		if (searchStudent(sno) == 0 || searchCourse(cno) == 0){
			System.out.println("该学生/该课程没有");
			return;
		}
		String sql = "INSERT INTO sc VALUES (?, ?, ?)";
		ps = conn.prepareStatement(sql);

		ps.setString(1, sno);
		ps.setString(2, cno);
		ps.setInt(3, grade);

		int n = ps.executeUpdate();  //执行
		if (n > 0) {
			System.out.println("选课表SC添加选课记录成功！");
		} else {
			System.out.println("选课表SC添加选课记录失败！");
		}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//	使用java、jdbc实现对选课表SC添加选课记录的功能。
	public  void scOperation() throws SQLException {
		System.out.println("---------------SC------------------");
		addSCRecord("201215121", "1", 92);
		addSCRecord("201215121", "2", 88);
		addSCRecord("201215121", "3", 90);
		addSCRecord("201215122", "2", 67);
		addSCRecord("201215122", "3", 62);
	}


	public static void main(String[] args) throws SQLException {
		ConnectDB connectDB = new ConnectDB();

		connectDB.getConnection();   // 连接数据库
		connectDB.createTables();      // 创建表
		connectDB.studentOperation();  // 学生表操作
		connectDB.courseOperation();  // 课程表操作
		connectDB.scOperation();         //sc表操作
		connectDB.close();  //关闭数据库
	}
}