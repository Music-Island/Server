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
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            String sql;
            System.out.println(token);
            sql = "SELECT * FROM account WHERE token='" + token + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // չ����������ݿ�
            while (rs.next()) {
                // ͨ���ֶμ���
                System.out.println("----------------------------------------------------------------------");
                usrname = rs.getString("usrname");
            }
            // ��ɺ�ر�
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch (Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        } finally {
            // �ر���Դ
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // ʲô������
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
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM comment WHERE mapname='" + mapname + "' and usrname = '" + usrname + "' and time_stamp = " + time_stamp;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // չ����������ݿ�
            while (rs.next()) {
                // ͨ���ֶμ���
                if (rs.getString("usrname").compareTo(usrname) == 0)
                    flag = true;
                // �������
            }
            // ��ɺ�ر�
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch (Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        } finally {
            // �ر���Դ
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // ʲô������
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
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT comment values ('" + mapname + "','" + usrname + "','" + comment + "'," + System.currentTimeMillis() + ")";
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // ��ɺ�ر�

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch (Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        } finally {
            // �ر���Դ
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // ʲô������
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
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE comment SET comment = '" + comment + " ' WHERE usrname='" + usrname + "' and mapname = '" + mapname + "' and time_stamp =" + timestamp;
            System.out.println(sql);
            int count = stmt.executeUpdate(sql);
            if (count == 1)
                flag = true;
            // ��ɺ�ر�
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch (Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        } finally {
            // �ر���Դ
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // ʲô������
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
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT usrname,comment,time_stamp FROM comment WHERE mapname = '" + mapname + "' ORDER BY time_stamp asc ";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // չ����������ݿ�
            while (rs.next()) {
//                if (count == 0)
//                    break;
                // ͨ���ֶμ���
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
            // ��ɺ�ر�
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch (Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        } finally {
            // �ر���Դ
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // ʲô������
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
