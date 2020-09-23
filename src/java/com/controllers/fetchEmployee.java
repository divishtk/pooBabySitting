/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import com.model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nevets url pattern /fetchEmployee
 */
public class fetchEmployee extends HttpServlet {

    private Connection con = null;
    private ResultSet rs = null;
    Employee e = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //blank
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Employee> empAl = new ArrayList<Employee>();
        con = ConnectionManager.getConnection();
        if (con != null) {
            try {
                con.setAutoCommit(false);
                PreparedStatement ps = con.prepareStatement(""
                        + "Select u1.userId, userName, contactNumber, emailAddress, addressInfo, "
                        + "pinCode, empProfileImage, userAge, gender, u2.skills, u2.empBio from users u1 inner join useremployee u2 "
                        + "on u1.userId = u2.Users_userId");
                rs = ps.executeQuery();
                while (rs.next()) {
                    e = new Employee();
                    e.setUserId(rs.getInt(1));
                    e.setName(rs.getString(2));
                    e.setContact(rs.getString(3));
                    e.setEmail(rs.getString(4));
                    e.setAddress(rs.getString(5));
                    e.setPincode(rs.getInt(6));
                    e.setProfileImage(rs.getBlob(7));
                    e.setAge(rs.getString(8));
                    e.setGender(rs.getString(9));
                    e.setSkills(rs.getString(10));
                    e.setBio(rs.getString(11));
                    empAl.add(e);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();

        gson.toJson(empAl, response.getWriter());
    }
}
