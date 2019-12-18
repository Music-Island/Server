package cn.itcast;

import java.math.BigDecimal;
import java.sql.*;

public class AccountRelated {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //    static final String DB_URL = "jdbc:mysql://192.168.145.128:3306/music_island";
    static final String DB_URL = "jdbc:mysql://localhost:3306/musicIsland";
    static final String USER = "root";
    static final String PASS = "FZU2019Musland";

    public static boolean updatePoints(String usrname, int points, String mapname) {
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
            sql = "UPDATE pointList SET points = " + points + " WHERE usrname='" + usrname + "' and mapname = '" + mapname + "'";
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

    public static boolean addPoints(String usrname, String mapname, int points) {
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
            sql = "INSERT pointList values ('" + usrname + "','" + mapname + "'," + points + ")";
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

    public static int checkPoint(String usrname, String mapname) {
        // TODO Auto-generated method stub
        Connection conn = null;
        Statement stmt = null;
        boolean flag = false;
        int points = -1;
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
            sql = "SELECT * FROM pointList WHERE usrname='" + usrname + "' and mapname = '" + mapname + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            // չ����������ݿ�
            while (rs.next()) {
                // ͨ���ֶμ���
                points = rs.getBigDecimal("points").intValue();
                if (usrname.compareTo(rs.getString("usrname")) == 0)
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
        if (flag)
            return points;
        else
            return -1;
    }
}
