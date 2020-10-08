/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import com.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nevets
 */
public class crudCustomer extends HttpServlet {

    private Connection con = null;
    private ResultSet rs = null;
    Customer c = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        con = ConnectionManager.getConnection();
        Boolean found = false;
        if (con != null && !id.equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Select u1.userId, userName, contactNumber, emailAddress, addressInfo, "
                        + "pinCode, userAge, gender, u2.noOfKids from users u1 inner join usercustomer u2 "
                        + "on u1.userId = u2.Users_userId where u1.userId = ?");
                ps.setString(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    c = new Customer();
                    c.setUserId(rs.getInt(1));
                    c.setName(rs.getString(2));
                    c.setContact(rs.getString(3));
                    c.setEmail(rs.getString(4));
                    c.setAddress(rs.getString(5));
                    c.setPincode(rs.getInt(6));
                    c.setAge(rs.getString(7));
                    c.setGender(rs.getString(8));
                    c.setNoOfKids(Integer.parseInt(rs.getString(9)));
                    found = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();
        if (found == true) {
            gson.toJson(c, response.getWriter());
        } else {
            response.getWriter().print("No Records found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        con = ConnectionManager.getConnection();
        Boolean deleted = false;
        if (con != null && !id.equals("")) {
            try {

                PreparedStatement ps1 = con.prepareStatement(""
                        + "delete from usercustomer where Users_userId = ?");
                ps1.setString(1, id);
                int i2 = ps1.executeUpdate();
                if (i2 > 0) {
                    deleted = true;
                }
                PreparedStatement ps = con.prepareStatement(""
                        + "Delete from users where userId = ?");
                ps.setString(1, id);
                int i = ps.executeUpdate();
                if (i > 0) {
                    deleted = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();
        if (deleted == true) {
            gson.toJson("Successfully Removed Record", resp.getWriter());
        } else {
            resp.getWriter().print("No Records found");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer c = new Customer();
        c.setUserId(Integer.parseInt(req.getParameter("id")));
        c.setName(req.getParameter("name"));
        c.setContact(req.getParameter("contact"));
        c.setEmail(req.getParameter("email"));
        c.setAddress(req.getParameter("addr"));
        c.setPincode(Integer.parseInt(req.getParameter("pin")));
        c.setAge(req.getParameter("age"));
        c.setGender(req.getParameter("gender"));
        c.setNoOfKids(Integer.parseInt(req.getParameter("kids")));
        con = ConnectionManager.getConnection();
        Boolean updated = false;
        if (con != null && !req.getParameter("id").equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Update users set userName = ?, contactNumber = ?, emailAddress = ?, "
                        + "addressInfo = ?, pinCode = ?, password = ?, userAge = ?, gender = ?"
                        + "where userId = ?");
                ps.setString(1, c.getName());
                ps.setString(1, c.getContact());
                ps.setString(1, c.getEmail());
                ps.setString(1, c.getAddress());
                ps.setString(1, String.valueOf(c.getPincode()));
                ps.setString(1, c.getAge());
                ps.setString(1, c.getGender());
                int i = ps.executeUpdate();
                if (i > 0) {
                    updated = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (con != null && !req.getParameter("id").equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Update usercustomer set noOfKids = ?"
                        + "where Users_userId = ?");
                ps.setString(1, String.valueOf(c.getNoOfKids()));
                ps.setInt(2, c.getUserId());
                int i = ps.executeUpdate();
                if (i > 0) {
                    updated = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();
        if (updated == true) {
            gson.toJson("Successfully Updated", resp.getWriter());
        } else {
            resp.getWriter().print("No Records found");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
