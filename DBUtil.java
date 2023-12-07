package jdbc.utils;
import java.sql.*;

public class DBUtil {
    public static final String DRIVER_NAME="com.mysql.cj.jdbc.Driver";
    public static final String URL="jdbc:mysql://localhost:3306/demo";
    public static final String USERNAME="root";
    public static final String PASSWORD="zjx2006";

    //静态块 先于构造器执行 只执行一次
    static {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn=null;
        conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
        //返回目标对象
        return conn;
    }

    /**
     * 获取执行语句PreparedStatement
     * @param sql 准备的sql
     * @return
     */
    public static PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException {
        //调用getConnection 获取conn对象
        Connection conn=getConnection();
        PreparedStatement pstmt=null;
        try {
            pstmt=conn.prepareStatement(sql);
        } catch (SQLException e) {
            //放在工具类 抓
            e.printStackTrace();
        }
        //返回目标对象
        return pstmt;
    }


    /**
     * 设置参数的方法
     * @param pstmt  sql语句
     * @param params  参数
     */
    public static void setParam(PreparedStatement pstmt,Object[] params) {
        if(params!=null&&params.length>0){
            for (int i = 0; i < params.length; i++) {
                try {
                    pstmt.setObject(i+1,params[i]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    /**
     * 增删改 基于上面的setparam
     * @param sql 语句
     * @param params  变量
     * @return
     */
    public static void update(String sql,Object[] params) throws SQLException, ClassNotFoundException {
        //调用getConnection 获取conn对象
        Connection conn=getConnection();
        //调用ps
        PreparedStatement pstmt=getPreparedStatement(sql);
        //调用通用的设置参数的方法 向sql语句设置参数
        setParam(pstmt,params);
        //执行sql语句
        try {
            int rows=pstmt.executeUpdate();
            System.out.println(rows>0?"成功":"失败");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //关闭连接 调用封装
        finally {
            close(null,pstmt,conn);
        }
    }


    /**
     * 增
     * @param username
     * @param password
     * @param phone
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void add(String username,String password,Integer phone) throws SQLException, ClassNotFoundException {
        //1.获取连接
        Connection conn=getConnection();
        //2.获取执行语句并处理
        String sql="insert into user(username,password,phone) value(?,?,?)";
        PreparedStatement pstmt=getPreparedStatement(sql);
        //3.绑定参数
        pstmt.setString(1,username);
        pstmt.setObject(2,password);
        pstmt.setInt(3,phone);
        //4.执行添加操作并得到影响行数
        int rows= pstmt.executeUpdate();
        //5.判断是否成功
        System.out.println(rows==1?"添加成功":"添加失败");
        //6.关闭连接
        close(null,pstmt,conn);
    }

    /**
     * 删除用户名为user的信息
     * @param USER
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteUser(String USER) throws SQLException, ClassNotFoundException {
        Connection conn=getConnection();
        String sql="delete from user where username=?";
        PreparedStatement pstmt=getPreparedStatement(sql);
        pstmt.setString(1,USER);
        int rows=pstmt.executeUpdate();
        System.out.println(rows==1?"删除成功":"删除失败");
        close(null,pstmt,conn);
    }

    /**
     * 修改user的密码和电话
     * @param username
     * @param newpassword
     * @param newphone
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void alterPwAndPhone(String username,String newpassword,Integer newphone) throws SQLException, ClassNotFoundException {
        Connection conn=getConnection();
        String sql="update user set password=?,phone=? where username=?";
        PreparedStatement pstmt=getPreparedStatement(sql);
        pstmt.setString(1,newpassword);
        pstmt.setInt(2, Integer.parseInt(newpassword));
        pstmt.setString(3,username);
        int rows=pstmt.executeUpdate();
        System.out.println(rows==1?"删除成功":"删除失败");
        close(null,pstmt,conn);
    }

    /**
     * 查询所有用户名
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void selectUser() throws SQLException, ClassNotFoundException {
        Connection conn=getConnection();
        String sql="select username from user";
        PreparedStatement pstmt=getPreparedStatement(sql);
        ResultSet resultSet=pstmt.executeQuery(sql);
        while(resultSet.next()){
            String user_name=resultSet.getString("username");
            System.out.println(user_name);
        }
        close(resultSet,pstmt,conn);

    }

    /**
     * 查询所有信息
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static void selectAll() throws SQLException, ClassNotFoundException {
        Connection conn=getConnection();
        String sql="select * from user";
        PreparedStatement pstmt=getPreparedStatement(sql);
        ResultSet resultSet=pstmt.executeQuery(sql);
        while(resultSet.next()){
            String user_name=resultSet.getString("username");
            System.out.println(user_name);
        }
        close(resultSet,pstmt,conn);

    }







    /**
     * 关闭连接
     * @param rs 结果集
     * @param stmt 执行语句
     * @param conn 连接
     */
    public static void close(ResultSet rs ,Statement stmt,Connection conn) {
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }
}

