package cn.itcast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream reqInputStream = req.getInputStream();
        InputStreamReader isr = new InputStreamReader(reqInputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String temp = null;
        StringBuffer sb = new StringBuffer();
        while ((temp = br.readLine()) != null)
            sb.append(temp);
        br.close();
        isr.close();
        reqInputStream.close();
        JSONObject registerJson = JSON.parseObject(sb.toString());
        JSONObject respJson = new JSONObject();
        try {

            String user_id = registerJson.getString("userid");
            String pass_wd = registerJson.getString("passwd");
            String usr_name = registerJson.getString("usrname");

            if (checkAccount.checkExisted(user_id)) {
                respJson.put("status", "1004");
            } else {
                if (checkAccount.addAcount(user_id, pass_wd, usr_name))
                    respJson.put("status", "1003");
                else {
                    respJson.put("status", "1004");
                }
            }
            System.out.println(respJson.toJSONString());
        } catch (NullPointerException npe) {
            respJson.put("status", "0000");
        }
        resp.getWriter().write(respJson.toJSONString());
        resp.getWriter().close();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
