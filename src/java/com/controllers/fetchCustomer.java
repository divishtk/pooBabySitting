/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.database.ConnectionManager;
import com.google.gson.Gson;
import com.model.Customer;
import com.model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nevets
 */
public class fetchCustomer extends HttpServlet {

    private Connection con = null;
    private ResultSet rs = null;
    Customer c = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("fetchCustomer Serlvet");
        ArrayList<Customer> custAl = new ArrayList<Customer>();
        con = ConnectionManager.getConnection();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(""
                        + "Select u1.userId, userName, contactNumber, emailAddress, addressInfo, "
                        + "pinCode, userAge, gender, noOfKids from users u1 inner join usercustomer u2 "
                        + "on u1.userId = u2.Users_userId");
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
//                    c.setProfileImage(rs.getBlob(7));
                    custAl.add(c);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Gson gson = new Gson();
        gson.toJson(custAl, response.getWriter());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
