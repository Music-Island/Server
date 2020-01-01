package cn.itcast;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getAndCheckEmail extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf8");
        JSONObject respJson = new JSONObject();
        try {
            String emailHost = req.getHeader("emailHost");
            String userid = req.getHeader("userid");
            if (emailHost == null || userid == null)
                throw new NullPointerException();
            String emailToken = RandomStringUtils.randomAlphanumeric(6);
            System.out.println(emailHost + " " + userid + " " + emailToken);
            try {
                if (!EmailCheck.checkEmail(emailHost) && !EmailCheck.checkEmailTemp(emailHost)) {
                    System.out.println("send Token");
                    EmailCheck.sendToken(emailHost, emailToken);
                    if (EmailCheck.addEmailTemp(userid, emailHost, emailToken))
                        respJson.put("status", "2001");
                    else
                        respJson.put("status", "2002");
                } else {
                    respJson.put("status", "2002");
                }
            } catch (Exception e) {
                System.out.println("exception");
                e.printStackTrace();
            }
            System.out.println("ok");
            Thread delThread = new Thread(new delEmailTemp(emailHost));
            delThread.start();
        } catch (NullPointerException npe) {
            respJson.put("status", "0000");
        }
        resp.getWriter().write(respJson.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
