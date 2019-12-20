package cn.itcast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Random;

public class checkAccount {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //    static final String DB_URL = "jdbc:mysql://192.168.145.128:3306/music_island";
    static final String DB_URL = "jdbc:mysql://localhost:3306/musicIsland";
    static final String USER = "root";
    static final String PASS = "FZU2019Musland";
//    static final String PASS = "130255";

    /**
     * @param pass_wd
     * @param user_id
     */
    public static String checkPassword(String user_id, String pass_wd) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        String token = null;
        String temp_userid = null;
        String usrname = null;
        boolean flag = false;
        JSONObject loginJson = new JSONObject();
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM account WHERE userid='" + user_id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String userid = rs.getString("userid");
                usrname = rs.getString("usrname");
                String passwd = rs.getString("passwd");
                BigDecimal points = rs.getBigDecimal("points");
                if (passwd.compareTo(pass_wd) == 0) {
                    temp_userid = user_id;
                    flag = true;
                }
                // 输出数据
                System.out.println("账号: " + userid + ", 用户名: " + usrname + ", 得分: " + points);
            }
            if (flag) {
                while (true) {
                    token = RandomStringUtils.randomAlphanumeric(30);
                    if (checkToken(token) == null) {
                        saveToken(temp_userid, token);
                        break;
                    }
                }
                loginJson.put("usrname", usrname);
                loginJson.put("token", token);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (flag)
            return loginJson.toJSONString();
        else
            return null;
    }

    public static boolean checkExisted(String user_id) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM account WHERE userid='" + user_id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                String userid = rs.getString("userid");
                if (userid.compareTo(user_id) == 0)
                    flag = true;
                // 输出数据
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return flag;
    }

    public static boolean addAcount(String user_id, String pass_wd, String usr_name) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT account values ('" + user_id + "','" + usr_name + "','" + pass_wd + "',0,null)";
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // 完成后关闭

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return flag;
    }

    public static boolean updatePoints(String token, String points) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE account SET points = points+" + points + " WHERE token='" + token + "'";
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return flag;
    }

    public static String checkToken(String token) {
        // TODO Auto-generated method stub
        String userid = null;
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM account WHERE token='" + token + "'";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                userid = rs.getString("userid");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userid;
    }

    public static boolean saveToken(String userid, String token) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE account SET token='" + token + "' WHERE userid='" + userid + "'";
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return flag;
    }

    public static boolean cancelToken(String userid) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE account SET token=NULL WHERE userid='" + userid + "'";
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return flag;
    }
}
