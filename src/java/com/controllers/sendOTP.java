/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.google.gson.Gson;
import com.model.otpSender;
import java.io.IOException;
import java.io.PrintWriter;
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
public class sendOTP extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sendOTP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sendOTP at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int rand = 0;
        rand = otpSender.sendMail("stevenferns96@gmail.com");
        HttpSession sess = request.getSession();
        HashMap<String, String> hm = new HashMap<>();
        if (rand > 0) {
            sess.setAttribute("OTP", rand);
            hm.put("Resp", "Message Was Sent Successfully");
            new Gson().toJson(hm, response.getWriter());
        } else {
            hm.put("Resp", "Could not send email. \nContact the technical team");
            new Gson().toJson(hm, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession();
        String OTP = request.getParameter("sentOTP");
        String sessOTP = String.valueOf(sess.getAttribute("OTP"));
        HashMap<String, String> hm = new HashMap<>();
        if (!"".equals(OTP) && !"".equals(sessOTP)) {
            if (OTP.equals(sessOTP)) {
                hm.put("response", "Email Verified! Thank You");
            } else {
                hm.put("response", "Incorrect OTP. Failed to Verify OTP. Try Again!");
            }
        }else{
            hm.put("response", "OTP"+sessOTP);
        }
        new Gson().toJson(hm,response.getWriter());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}