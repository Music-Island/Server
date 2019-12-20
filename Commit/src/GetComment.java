import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetComment extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        JSONArray respJsonArr = new JSONArray();
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
            System.out.println(usrname);
            if (usrname.compareTo(reqJson.getString("usrname")) == 0) {
                respJsonArr = JSONArray.parseArray(DBrelated.getComment(reqJson.getString("mapname")));
//                respJson.put("status", "1011");
//                respJsonArr.add(respJson);
            } else {
                respJson.put("status", "1012");
                respJsonArr.add(respJson);
            }
        } else {
            respJson.put("status", "0000");
            respJsonArr.add(respJson);
        }
        System.out.println(respJsonArr.toJSONString());
        resp.getWriter().write(respJsonArr.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
