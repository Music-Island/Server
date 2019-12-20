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
import java.nio.Buffer;

public class login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        InputStreamReader isr = new InputStreamReader(req.getInputStream(), "UTF-8");
        BufferedReader loginBR = new BufferedReader(isr);
        StringBuffer loginSB = new StringBuffer();
        String temp = null;
        while ((temp = loginBR.readLine()) != null)
            loginSB.append(temp);
        loginBR.close();
        isr.close();
        JSONObject loginJson = JSON.parseObject(loginSB.toString());
        JSONObject resqJson = new JSONObject();

        try {
            String userid = loginJson.getString("userid");
            String passwd = loginJson.getString("passwd");
            if ((temp = checkAccount.checkPassword(userid, passwd)) != null) {
//                resqJson.put("token",temp);
                resqJson = JSON.parseObject(temp);
                resqJson.put("status", "1001");
            } else
                resqJson.put("status", "1002");
        } catch (NullPointerException npe) {
            resqJson.put("status", "0000");
        }
        resp.getWriter().write(resqJson.toJSONString());
        resp.getWriter().close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
