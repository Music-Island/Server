import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitComment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONObject respJson = new JSONObject();
        String token = null;
        String usrname = null;
        token = req.getHeader("Auth-Token");
        System.out.println(token);
        if (token != null) {
            InputStreamReader isr = new InputStreamReader(req.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            isr.close();
            JSONObject reqJson = JSON.parseObject(sb.toString());
            System.out.println(reqJson.toJSONString());
            usrname = DBrelated.checkToken(token);
            if (usrname.compareTo(reqJson.getString("usrname")) == 0) {

//                System.out.println(usrname);
                System.out.println("name is right");
                if (DBrelated.commentExisted(usrname, reqJson.getString("mapname"), Long.parseLong(reqJson.getString("timestamp")))) {
                    System.out.println("existed");
                    if (DBrelated.updateComment(usrname, reqJson.getString("mapname"), reqJson.getString("comment"), Long.parseLong(reqJson.getString("timestamp")))) {
                        respJson.put("status", "1009");
                    } else {
                        respJson.put("status", "1010");
                    }
                } else {
                    System.out.println("non-existed");
                    if (DBrelated.addComment(usrname, reqJson.getString("mapname"), reqJson.getString("comment"))) {
                        respJson.put("status", "1009");
                    } else {
                        respJson.put("status", "1010");
                    }
                }
            } else {
                respJson.put("status", "1010");
            }
        } else
            respJson.put("status", "0000");
        resp.getWriter().write(respJson.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
