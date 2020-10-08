/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.database.ConnectionManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

//@WebServlet("/registerCustomerServlet")
//@WebServlet(urlPatterns = {"/pooBabySitting/AuthUserServlet"})
@MultipartConfig(maxFileSize = 16177215)
public class AuthUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("GET login servlet");

        HttpSession hs = null;
        String email = request.getParameter("userId");
        String pass = request.getParameter("password");

        boolean flag = false, exist = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConn = ConnectionManager.getConnection();
//            dbConn.setAutoCommit(false);
            PreparedStatement ps = dbConn.prepareStatement(
                    "select emailAddress, password from users where password = ? and emailAddress = ?");
            ps.setString(1, pass);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                exist = true;
                String email1 = rs.getString(1);
                String pass1 = rs.getString(2);
                flag = (email.equalsIgnoreCase(email1) && pass.equalsIgnoreCase(pass1) ? true : false);

                if (flag) {
                    hs = request.getSession();
                    hs.setAttribute("email", email1);
                    hs.setAttribute("pass", pass1);
                }
            }
            response.setContentType("text/html;charset=UTF-8");

            if (exist) {
                if (flag) {
//                    RequestDispatcher rd = request.getRequestDispatcher("/pooBabySitting/views/home.html");
//                    rd.forward(request, response);
                    response.sendRedirect("/pooBabySitting/views/home.html");
                } else {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet loginServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1> The User/Password doesn't match. </h1>");
                        out.println("<span><a href='/pooBabySitting/index.jsp'> click to go back. </a></span>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet loginServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1> The User doesn't exist. </h1>");
                    out.println("<span><a href='/pooBabySitting/'> click to go back. </a></span>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Post Register Servlet");

        String fn, sn;
        String password;
        String nokids, code, contactNo, email, address, road, landmark, countrycode, pin, direction, bio, age, gender, skills;
        String userType = request.getParameter("userType");

        fn = request.getParameter("fn");
        sn = request.getParameter("sn");
        password = request.getParameter("pa");
        nokids = request.getParameter("noofkids");
        code = request.getParameter("code");
        contactNo = request.getParameter("number");
        email = request.getParameter("email");
        address = request.getParameter("address");
        road = request.getParameter("road");
        landmark = request.getParameter("landmark");
        countrycode = request.getParameter("countrycode");
        pin = request.getParameter("pin");
        direction = request.getParameter("direction");
        bio = request.getParameter("extraBio");
        age = request.getParameter("age");
        gender = request.getParameter("gender");
        skills = request.getParameter("skills");

        if (validLoginInput(request)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection dbConn = ConnectionManager.getConnection();
//            dbConn.setAutoCommit(false);
                PreparedStatement ps = dbConn.prepareStatement(
                        "insert into users(userId, userName, password, contactNumber, emailAddress, addressInfo, pincode, extrasDirection, userAge, gender) "
                        + "values ((Select * from (Select COALESCE(max(userId)+1,101) from users) as t),?,?,?,?,?,?,?,?,?)");
                ps.setString(1, String.join(" ", fn,sn));
                ps.setString(2, password);
                ps.setString(3, contactNo);
                ps.setString(4, email);
                ps.setString(5, String.join(", ", address,road,landmark,countrycode,pin));
                ps.setString(6, pin);
                ps.setString(7, direction);
                ps.setString(8, age);
                ps.setString(9, gender);

                int i = ps.executeUpdate();

                if (i > 0) {
                    if ("Customer".equals(userType)) {
                        //Insert query for the user customer table
                        PreparedStatement ps1 = dbConn.prepareStatement(
                                "insert into usercustomer(userId, noOfKids, users_userId) values "
                                + "((Select * from (Select COALESCE(max(userId)+1,101) from usercustomer) as t),"
                                + "?,"
                                + "(Select * from (Select userId from users where emailAddress = ?) as u))");
                        ps1.setString(1, nokids);
                        ps1.setString(2, email);

                        i = ps1.executeUpdate();
                        if (i > 0) {
                            System.out.println("Value in second table inserted");
//                          dbConn.commit();
                            response.sendRedirect("/pooBabySitting/");
//                          RequestDispatcher rd = request.getRequestDispatcher("registerCustPage.html");
//                          rd.forward(request, response);
                        }

                    } else if ("Employee".equals(userType)) {

                        InputStream inputStream1_1, inputStream1_2, inputStream1_3, inputStream2, inputStream3 = null;

                        if (validFileInput(request)) {
                            Part p_1 = request.getPart("inputGroupFile01-1");
                            Part p_2 = request.getPart("inputGroupFile01-2");
                            Part p_3 = request.getPart("inputGroupFile01-3");

                            Part p2 = request.getPart("inputGroupFile02");
                            Part p3 = request.getPart("inputGroupFile03");

                            // obtains input stream of the upload file
                            inputStream1_1 = p_1.getInputStream();
                            inputStream1_2 = p_2.getInputStream();
                            inputStream1_3 = p_3.getInputStream();

                            inputStream2 = p2.getInputStream();
                            inputStream3 = p3.getInputStream();

                            //insert query for employee table
                            PreparedStatement ps2 = dbConn.prepareStatement(
                                    "insert into useremployee(userId, empPARCard1,empPARCard2,empPARCard3, empCertifcates, empProfileImage, skills, empBio, Users_userId)"
                                    + " values ((Select * from (Select COALESCE(max(userId)+1,101) from useremployee) as t),?,?,?,?,?,?,?,"
                                    + "(Select * from (Select userId from users where emailAddress = ?) as u))");

                            if (inputStream1_1 != null && inputStream1_2 != null && inputStream1_3 != null && inputStream2 != null && inputStream3 != null) {
                                ps2.setBlob(1, inputStream1_1);
                                ps2.setBlob(2, inputStream1_2);
                                ps2.setBlob(3, inputStream1_3);
                                ps2.setBlob(4, inputStream2);
                                ps2.setBlob(5, inputStream3);
                            }
                            
                            ps2.setString(6, skills);
                            ps2.setString(7, bio);
                            ps2.setString(8, email);

                            int row = ps2.executeUpdate();
                            if (row > 0) {
//                        dbConn.commit();
                                System.out.println("Images Uploaded in Mysql Successfulyy");
                                response.sendRedirect("/pooBabySitting/");
                                //  RequestDispatcher rd = request.getRequestDispatcher("registerEmpPage.html");
                                //  rd.forward(request, response);
                            }
                        } else {
                            System.out.println("File invalid");
                        }
                    }
                } else {
                    System.out.println("Failed to Update");
                    if ("Customer".equals(userType)) {
                        response.sendRedirect("/pooBabySitting/views/registerCustPage.html");
                    } else {
                        response.sendRedirect("/pooBabySitting/views/registerEmpPage.html");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if ("Customer".equals(userType)) {
                response.sendRedirect("/pooBabySitting/views/registerCustPage.html");
            } else {
                response.sendRedirect("/pooBabySitting/views/registerEmpPage.html");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));
    }

    private boolean validLoginInput(HttpServletRequest request) {
        String fn, sn;
        String password;
        String nokids, code, contactNo, email, address, road, landmark, countrycode, pin, direction, bio;
        String userType = request.getParameter("userType");

        fn = request.getParameter("fn");
        password = request.getParameter("pa");
        nokids = request.getParameter("noofkids");
        code = request.getParameter("code");
        contactNo = request.getParameter("number");
        email = request.getParameter("email");
        address = request.getParameter("address");
        direction = request.getParameter("direction");
        bio = request.getParameter("extraBio");

        if ("Customer".equals(userType)) {
            if (fn != null && password != null && contactNo != null && nokids != null && email != null && address != null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (fn != null && password != null && contactNo != null && email != null && address != null) {
                return true;
            } else {
                return false;
            }

        }

    }

    private boolean validFileInput(HttpServletRequest request) throws IOException, ServletException {
        Part p_1 = request.getPart("inputGroupFile01-1");
        Part p_2 = request.getPart("inputGroupFile01-2");
        Part p_3 = request.getPart("inputGroupFile01-3");

        Part p2 = request.getPart("inputGroupFile02");
        Part p3 = request.getPart("inputGroupFile03");
        if (p_1 != null && p_2 != null && p_3 != null && p2 != null && p3 != null) {
            if (p_1.getSize() / 1024 / 1024 < 16 && p_2.getSize() / 1024 / 1024 < 16 && p_3.getSize() / 1024 / 1024 < 16 && p2.getSize() / 1024 / 1024 < 16 && p3.getSize() / 1024 / 1024 < 16
                    && "application/pdf".equalsIgnoreCase(p_1.getContentType()) && "application/pdf".equalsIgnoreCase(p_2.getContentType()) && "application/pdf".equalsIgnoreCase(p_3.getContentType()) && "application/pdf".equalsIgnoreCase(p2.getContentType())
                    && "image/jpeg".equalsIgnoreCase(p3.getContentType())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
