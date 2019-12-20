import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.sql.*;

public class DBrelated {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/musicIsland";
    static final String USER = "root";
    static final String PASS = "FZU2019Musland";

    public static String checkToken(String token) {
        // TODO Auto-generated method stub
        String usrname = null;
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
            System.out.println(token);
            sql = "SELECT * FROM account WHERE token='" + token + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                System.out.println("----------------------------------------------------------------------");
                usrname = rs.getString("usrname");
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
        return usrname;
    }

    public static boolean commentExisted(String usrname, String mapname, long time_stamp) {
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
            sql = "SELECT * FROM comment WHERE mapname='" + mapname + "' and usrname = '" + usrname + "' and time_stamp = " + time_stamp;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                if (rs.getString("usrname").compareTo(usrname) == 0)
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

    public static boolean addComment(String usrname, String mapname, String comment) {
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
            sql = "INSERT comment values ('" + mapname + "','" + usrname + "','" + comment + "'," + System.currentTimeMillis() + ")";
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

    public static boolean updateComment(String usrname, String mapname, String comment, long timestamp) {
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
            sql = "UPDATE comment SET comment = '" + comment + " ' WHERE usrname='" + usrname + "' and mapname = '" + mapname + "' and time_stamp =" + timestamp;
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

    public static String getComment(String mapname) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        int rank = 1;
        JSONArray rankJson = new JSONArray();
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
            sql = "SELECT usrname,comment,time_stamp FROM comment WHERE mapname = '" + mapname + "' ORDER BY time_stamp asc ";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
//                if (count == 0)
//                    break;
                // 通过字段检索
                String usrname = rs.getString("usrname");
                BigDecimal timeStamp = rs.getBigDecimal("time_stamp");
                JSONObject temp = new JSONObject();
                temp.put("usrname", usrname);
                temp.put("timpstamp", timeStamp + "");
                temp.put("comment", rs.getString("comment"));
                temp.put("rank", rank + "");
//                count--;
                rank++;
                rankJson.add(temp);
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
        System.out.println(rankJson.toJSONString());
        return rankJson.toJSONString();
    }
}
