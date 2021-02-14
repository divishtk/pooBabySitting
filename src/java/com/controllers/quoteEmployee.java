/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nevets
 */
public class quoteEmployee extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //not tested yet
        //use bootstrap notify for notifications
        HashMap<String, String> hm = new HashMap<>();
        String empId = request.getParameter("empId");
        String quote = request.getParameter("quote");
        String hire_date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        HttpSession sess = request.getSession();
        String email = (String) sess.getAttribute("email");
        try {
            Connection dbConn = ConnectionManager.getConnection();
            PreparedStatement ps = dbConn.prepareStatement("Select * from emp_appointments "
                    + "where employee_id = ? and app_date = ? and status = 'BOOKED'");
            ps.setString(1, empId);
            ps.setString(2, hire_date);
            ResultSet rs = ps.executeQuery();
            if (rs.getRow() == 0) {
                try {
                    PreparedStatement psmt = dbConn.prepareStatement(""
                            + "INSERT INTO `emp_appointments`(`employee_id`, `app_date`, `start_time`, `end_time`, `registered_date`, `customer_id`, `status`, `updated_date`, `apt_id`) VALUES "
                            + "(?,?,?,?,?,"
                            + "(Select userId from users where emailAddress = ?),"
                            + "?,?,"
                            + "(Select * from (Select COALESCE(max(apt_id)+1,1) from emp_appointments) as t))");
                    psmt.setString(1, empId);
                    psmt.setString(2, hire_date);
                    psmt.setString(3, startTime);
                    psmt.setString(4, endTime);
                    psmt.setString(5, getCurrentDate());
                    psmt.setString(6, email);
                    psmt.setString(7, "PENDING");
                    psmt.setString(8, getCurrentDate());
                    int j = psmt.executeUpdate();
                    if (j > 0) {
                        PreparedStatement psmt1 = dbConn.prepareStatement(""
                                + "INSERT INTO `NotificationsBlock`"
                                + "(`notifyid`, `from_id`, `to_id`, `quote_amount`, `hired_date`,`start_time`,`end_time`, `sent_date`) VALUES "
                                + "((Select * from (Select COALESCE(max(notifyid)+1,1) from NotificationsBlock) as t),"
                                + "(Select userId from users where emailAddress = ?),"
                                + "?,?,?,?,?,?)");
                        psmt1.setString(1, email);
                        psmt1.setString(2, empId);
                        psmt1.setString(3, quote);
                        psmt1.setString(4, hire_date);
                        psmt1.setString(5, startTime);
                        psmt1.setString(6, endTime);
                        psmt1.setString(7, getCurrentDate());
                        int k = psmt1.executeUpdate();
                        if (k > 0) {
                            hm.put("response", "Message Sent Successfully");
                            new Gson().toJson(hm, response.getWriter());
                        }
                    } else {
                        hm.put("response", "Failed to enter record.");
                        new Gson().toJson(hm, response.getWriter());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                hm.put("response", "Sorry, The BabySitter is already booked at that day!");
                new Gson().toJson(hm, response.getWriter());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public String getCurrentDate() {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(dt);
    }
}
