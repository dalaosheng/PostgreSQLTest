package PostgreSQLTest.PostgreSQLTest;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Demo1 {

public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
	final String name = "org.postgresql.Driver" ;
	
	final String url = "jdbc:postgresql://192.168.2.201:5432/test" ;
	
	final String user = "postgres" ;
	
	final String password = "root" ;
	
	Connection conn = null ;
	
	Class.forName(name); //指定连接类型
	
	conn = DriverManager.getConnection(url, user, password); //获取连接
	
	if (conn!= null ) {
	
	System.out.println( "获取连接成功" );
	
	insert(conn);
	
	} else {
	
	System.out.println( "获取连接失败" );
	
	}
	
	}
	
	public static void insert(Connection conn) {
	
	// 开始时间
	
	Long begin = new Date().getTime();
	
	// sql前缀
	
//	String prefix = "INSERT INTO analog_config (id,NAME,device_second_id,address,"
//				+ "refname,fc,device_primary_id,analog_type_id,unit_id,decimal_digit,"
//				+ "modulus,OFFSET,zero_value,one_up,two_up,one_low,two_low,back,artificial_set,"
//				+ "artificial_value,STORAGE,unreal,refresh,one_judge,two_judge,one_up_lvl,one_up_sound,"
//				+ "one_low_lvl,one_low_sound,two_up_lvl,two_up_sound,two_low_lvl,two_low_sound,v_up_lvl,"
//				+ "v_up_sound,v_low_lvl,v_low_sound,description,refresh_deadband,max_min_value,MAX_VALUE,"
//				+ "min_value,gradient_limit,gradient_limit_value,zero_value_set,ref_id,change_save,"
//				+ "integral_save,save_unit,sortnum) VALUES " ;
	
	String prefix = "INSERT INTO his_analog_5000w (id,record_date,analog_id,VALUE,state,limit_state,"
			+ "device_second_id,station_id,point_name) VALUES " ;
	try {
	
	// 保存sql后缀
	
	StringBuffer suffix = new StringBuffer();
	
	// 设置事务为非自动提交
	
	conn.setAutoCommit( false );
	
	// 比起st，pst会更好些
	
	Statement pst = (Statement) conn.createStatement(); //准备执行语句
	
	// 外层循环，总提交事务次数
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	 Calendar c = Calendar.getInstance();
	int x=0;
	for ( int i = 1 ; i <= 500 ; i++) {
	
	suffix = new StringBuffer();
	
	// 第j次提交步长
	
	for ( int j = 1 ; j <= 100000 ; j++) {
	x+=1;
	
	// 构建SQL后缀
//		UUID uuid = UUID.randomUUID();
//		String s = UUID.randomUUID().toString();
//	suffix.append( "('"+i*j+"','空'"+","+"'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+""
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空')," );

//	System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	suffix.append( "('" +x+ "','"+df.format(c.getTime())+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"')," );
	c.add(Calendar.HOUR_OF_DAY, 1);
	}
	
	// 构建完整SQL
	
	String sql = prefix + suffix.substring( 0 , suffix.length() - 1 );
	
	// 添加执行SQL
	
	pst.addBatch(sql);
	
	// 执行操作
	
	pst.executeBatch();
	
	// 提交事务
	
	conn.commit();
	
	// 清空上一次添加的数据
	
	suffix = new StringBuffer();
	
	}
	
	// 头等连接
	
	pst.close();
	
	conn.close();
	
	} catch (SQLException e) {
	
	e.printStackTrace();
	
	}
	
	// 结束时间
	
	Long end = new Date().getTime();
	
	// 耗时
	
	System.out.println( "100万条数据插入花费时间 : " + (end - begin) / 1000 + " s" );
	
	System.out.println( "插入完成" );
	
	}

}

