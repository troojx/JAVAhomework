package jdbc;

import jdbc.utils.DBUtil;

import java.sql.*;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //测试第一种增删改
        //增
        String sql1="insert into user(username,password,phone) value(?,?,?)";
        Object[] objs1={"小红","123mima",139555};
        DBUtil.update(sql1,objs1);
        //删除
        String sql2="delete from user where username=?";
        Object[] objs2={"小红"};
        DBUtil.update(sql2,objs2);
        //修改
        String sql3="update user set password=?,phone=? where username=?";
        Object[] objs3={"anew",111333,"zz"};
        DBUtil.update(sql3,objs3);
        //测试第二种增加
        DBUtil.add("newuser","newpw",185605);
        //测试第二种删除
        //DBUtil.deleteUser("小红");
        //测试第二种改user的密码和电话
        DBUtil.alterPwAndPhone("zz","aanew",185605);
        //测试查询所有用户名
        DBUtil.selectUser();
        //测试查询所有信息
        DBUtil.selectAll();



    }
}
