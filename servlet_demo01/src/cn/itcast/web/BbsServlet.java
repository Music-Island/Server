package cn.itcast.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BbsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------>>GET:BbsServlet find All........");
        String value = req.getServletContext().getInitParameter("project_para");
        System.out.println(value);
        String id = req.getSession().getId();
        System.out.println("SessionID:" + id);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------------>>POST:BbsServlet find All........");
    }
}

