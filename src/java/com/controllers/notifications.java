/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import com.model.Employee;
import com.model.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nevets
 */
public class notifications extends HttpServlet {

    private Connection con = null;
    private ResultSet rs = null;
    Notification noti = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String notifyId = "";
        String action = request.getParameter("action");
        ArrayList<Notification> notifyList = new ArrayList<>();
        switch (action) {
            case "all":
                con = ConnectionManager.getConnection();
                if (con != null) {
                    try {
                        PreparedStatement ps = con.prepareStatement(""
                                //                                Insert Code
                                //                                + "Insert into NotificationsBlock "
                                //                                + "(notifyid, from_id, to_id, og_amount1, hired_date, sent_date) values "
                                //                                + "((Select * from (Select COALESCE(max(notifyid)+1,101) from NotificationsBlock) as i),"
                                //                                + "?,?,?,?,?)");
                                + "Select notifyid, from_id, to_id, quote_amount, hired_date, sent_date, status, sendertype from NotificationsBlock");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            noti = new Notification();
                            noti.setNotifyid(rs.getInt(1));
                            noti.setFrom_id(rs.getInt(2));
                            noti.setTo_id(rs.getInt(3));
                            noti.setOg_amount1(rs.getInt(4));
                            noti.setHired_date(rs.getString(5));
                            noti.setSent_date(rs.getString(6));
                            noti.setStatus(rs.getString(7));
                            noti.setSendertype(rs.getString(8));
                            notifyList.add(noti);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    new Gson().toJson(notifyList, response.getWriter());
                }
                break;
            case "byId":
                if (!"".equals(request.getParameter("notifyId"))) {
                    notifyId = request.getParameter("notifyId");
                    con = ConnectionManager.getConnection();
                    if (con != null) {
                        try {
                            PreparedStatement ps = con.prepareStatement(""
                                    //                                Insert Code
                                    //                                + "Insert into NotificationsBlock "
                                    //                                + "(notifyid, from_id, to_id, og_amount1, hired_date, sent_date) values "
                                    //                                + "((Select * from (Select COALESCE(max(notifyid)+1,101) from NotificationsBlock) as i),"
                                    //                                + "?,?,?,?,?)");
                                    + "Select notifyid, from_id, to_id, quote_amount, hired_date, sent_date from NotificationsBlock "
                                    + "where notifyid = ?");
                            ps.setString(1, notifyId);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                noti = new Notification();
                                noti.setNotifyid(rs.getInt(1));
                                noti.setFrom_id(rs.getInt(2));
                                noti.setTo_id(rs.getInt(3));
                                noti.setOg_amount1(rs.getInt(4));
                                noti.setHired_date(rs.getString(5));
                                noti.setSent_date(rs.getString(6));
                                notifyList.add(noti);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        new Gson().toJson(notifyList, response.getWriter());
                    } else {
                        response.getWriter().print("Connection failed to connect!");
                    }
                } else {
                    response.getWriter().print("'notifyId' parameter is missing!");
                }
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
