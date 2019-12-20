package cn.itcast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class addPoints extends HttpServlet {
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
                int points = addPointsObject.getIntValue("points");
                if (checkAccount.updatePoints(token, String.valueOf(points))) {
                    respJson.put("status", "1005");
                } else
                    respJson.put("status", "1006");
            } else {
                respJson.put("status", "0000");
            }

        } catch (NullPointerException | NumberFormatException npe) {
            respJson.put("status", "0000");
        }
        resp.getWriter().write(respJson.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
