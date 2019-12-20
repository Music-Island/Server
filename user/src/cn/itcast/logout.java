package cn.itcast;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class logout extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Auth-Token");
        JSONObject respJson = new JSONObject();
        if (token != null) {
            String userid = checkAccount.checkToken(token);
            if (userid != null) {
                checkAccount.cancelToken(userid);
                respJson.put("userid", userid);
                respJson.put("status", "1008");
            } else {
                respJson.put("status", "1009");
            }
        } else {
            respJson.put("status", "0000");
        }
        resp.getWriter().write(respJson.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
