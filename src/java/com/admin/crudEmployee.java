/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import com.model.Customer;
import com.model.Employee;
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
public class crudEmployee extends HttpServlet {

    private Connection con = null;
    private ResultSet rs = null;
    Employee e = null;

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
                        + "pinCode, userAge, gender, u2.skills, u2.empBio from users u1 inner join useremployee u2 "
                        + "on u1.userId = u2.Users_userId where u1.userId = ?");
                ps.setString(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    e = new Employee();
                    e.setUserId(rs.getInt(1));
                    e.setName(rs.getString(2));
                    e.setContact(rs.getString(3));
                    e.setEmail(rs.getString(4));
                    e.setAddress(rs.getString(5));
                    e.setPincode(rs.getInt(6));
                    e.setAge(rs.getString(7));
                    e.setGender(rs.getString(8));
                    e.setSkills(rs.getString(9));
                    e.setBio(rs.getString(10));
                    found = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();
        if (found == true) {
            gson.toJson(e, response.getWriter());
        } else {
            response.getWriter().print("No Records found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        con = ConnectionManager.getConnection();
        Boolean deleted = false;
        if (con != null && !id.equals("")) {
            try {
                PreparedStatement ps1 = con.prepareStatement(""
                        + "delete from useremployee where Users_userId = ?");
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
        Employee e = new Employee();
        e.setUserId(Integer.parseInt(req.getParameter("id")));
        e.setName(req.getParameter("name"));
        e.setContact(req.getParameter("contact"));
        e.setEmail(req.getParameter("email"));
        e.setAddress(req.getParameter("addr"));
        e.setPincode(Integer.parseInt(req.getParameter("pin")));
        e.setAge(req.getParameter("age"));
        e.setGender(req.getParameter("gender"));
        e.setSkills(req.getParameter("skills"));
        e.setBio(req.getParameter("bio"));

        con = ConnectionManager.getConnection();
        Boolean updated = false;
        if (con != null && !req.getParameter("id").equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Update users set userName = ?, contactNumber = ?, emailAddress = ?, "
                        + "addressInfo = ?, pinCode = ?, password = ?, userAge = ?, gender = ?"
                        + "where userId = ?");
                ps.setString(1, e.getName());
                ps.setString(2, e.getContact());
                ps.setString(3, e.getEmail());
                ps.setString(4, e.getAddress());
                ps.setString(5, String.valueOf(e.getPincode()));
                ps.setString(6, e.getPassword());
                ps.setString(7, e.getAge());
                ps.setString(8, e.getGender());
                ps.setInt(9, e.getUserId());
                int i = ps.executeUpdate();
                if (i > 0) {
                    updated = true;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (con != null && !req.getParameter("id").equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Update useremployee set skills = ?, empBio = ?"
                        + "where Users_userId = ?");
                ps.setString(1, e.getSkills());
                ps.setString(2, e.getBio());
                ps.setInt(3, e.getUserId());
                int i = ps.executeUpdate();
                if (i > 0) {
                    updated = true;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
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
    }// </editor-fold>

}
