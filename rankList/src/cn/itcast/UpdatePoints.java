package cn.itcast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdatePoints extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = req.getHeader("Auth-Token");
        System.out.println(token);
        InputStreamReader isr = new InputStreamReader(req.getInputStream(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        isr.close();
        JSONObject addPointsObject = JSON.parseObject(sb.toString());
        JSONObject respJson = new JSONObject();
        try {
            if (token != null) {
                int points = Integer.parseInt(addPointsObject.getString("points"));
                String mapname = addPointsObject.getString("mapname");
                String usrname = AccountRelated.checkToken(token);
                System.out.println("username:" + usrname);
                System.out.println("mapname:" + mapname);
                System.out.println("points:" + points);
                if (usrname != null && mapname != null && points >= 0) {
                    int tempPoints = AccountRelated.checkPoint(usrname, mapname);
                    System.out.println("tempPoints:" + tempPoints);
                    if (tempPoints != -1) {
                        if (tempPoints < points) {
                            if (AccountRelated.updatePoints(usrname, points, mapname)) {
                                respJson.put("status", "1005");
                            } else {
                                respJson.put("status", "1006");
                            }
                        } else
                            respJson.put("status", "1005");
                    } else {
                        if (AccountRelated.addPoints(usrname, mapname, points)) {
                            respJson.put("status", "1005");
                        } else {
                            respJson.put("status", "1006");
                        }
                    }
                } else {
                    System.out.println("error");
                    respJson.put("status", "1006");
                }

            } else {
                respJson.put("status", "0000");
            }

        } catch (NullPointerException | NumberFormatException npe) {
            respJson.put("status", "0000");
        }
        resp.getWriter().write(respJson.toJSONString());
    }
}
