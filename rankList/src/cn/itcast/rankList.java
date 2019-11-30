package cn.itcast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class rankList extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //    static final String DB_URL = "jdbc:mysql://192.168.145.128:3306/music_island";
    static final String DB_URL = "jdbc:mysql://localhost:3306/musicIsland";
    static final String USER = "root";
    static final String PASS = "FZU2019Musland";
//    static final String PASS = "130255";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray respJsonArr = new JSONArray();
        try {
            String tempCounter = req.getParameter("count");
            int count = Integer.parseInt(tempCounter);
            System.out.println(count);
            respJsonArr = JSONArray.parseArray(getRankList(count));
        } catch (NumberFormatException nfe) {
            JSONObject errorObjet = new JSONObject();
            errorObjet.put("status", "0000");
            respJsonArr.add(errorObjet);
        }
        resp.getWriter().write(respJsonArr.toJSONString());
        resp.getWriter().close();
        System.out.println(respJsonArr.toJSONString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static String getRankList(int count) {
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
            sql = "SELECT usrname,points FROM account ORDER BY points desc ";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                if (count == 0)
                    break;
                // 通过字段检索
                String usrname = rs.getString("usrname");
                BigDecimal points = rs.getBigDecimal("points");
                JSONObject temp = new JSONObject();
                temp.put("usrname", usrname);
                temp.put("points", points);
                temp.put("rank", rank + "");
                count--;
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
        return rankJson.toJSONString();
    }
}

