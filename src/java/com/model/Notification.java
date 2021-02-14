/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Nevets
 */
public class Notification {
    int notifyid, from_id, to_id, og_amount1;
    String hired_date, sent_date, status, sendertype;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendertype() {
        return sendertype;
    }

    public void setSendertype(String sendertype) {
        this.sendertype = sendertype;
    }
    
    public int getNotifyid() {
        return notifyid;
    }

    public void setNotifyid(int notifyid) {
        this.notifyid = notifyid;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public int getOg_amount1() {
        return og_amount1;
    }

    public void setOg_amount1(int og_amount1) {
        this.og_amount1 = og_amount1;
    }

    public String getHired_date() {
        return hired_date;
    }

    public void setHired_date(String hired_date) {
        this.hired_date = hired_date;
    }

    public String getSent_date() {
        return sent_date;
    }

    public void setSent_date(String sent_date) {
        this.sent_date = sent_date;
    }
    
    
}
