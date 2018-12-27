/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Lukas
 */
public class MyDate {

    private Date d1;
    private Date d2;
    private String date;
    private long dif;
    private DateFormat df;
    private int cd;

    /**
     * Alternative constructor for MyDate object's takes three String arguments
     * that represents consequently a day, a month and a year.
     *
     */
    public MyDate() {
        df = new SimpleDateFormat("dd/MM/yy");
        d1 = new Date();
        date = df.format(d1.getTime());
    }

    /**
     *
     * @param aDate
     */
    public MyDate(String aDate) {
        df = new SimpleDateFormat("dd/MM/yy");
        date = aDate;
        try {
            d2 = df.parse(date);
        } catch (ParseException ex) {
            System.out.println("Icorrect date format");
        }
    }

    /**
     *
     * @return
     */
    public String getCurrentDate() {
        df = new SimpleDateFormat("dd/MM/yy");
        d1 = new Date();
        return df.format(d1.getTime());
    }

    /**
     *
     * @param myDate
     */
    public void setDate(String myDate) {
        df = new SimpleDateFormat("dd/MM/yy");
        try {
            d2 = df.parse(myDate);
            date = df.format(d2);
        } catch (Exception e) {
            System.out.println("Incorrect date format");
        }
    }

    public Date getD1() {
        return d1;
    }

    public Date getD2() {
        return d2;
    }

    /**
     *
     * @param d2
     * @param d1
     * @param timeUnit
     * @return
     */
    public long calcDateDiff(Date d2, Date d1, TimeUnit timeUnit) {
        long diffInMillies = d2.getTime() - d1.getTime();

        dif = timeUnit.convert(diffInMillies, TimeUnit.DAYS);
        dif = (int) (dif / 100000000);

        cd = (int) dif;
        return timeUnit.convert(diffInMillies, TimeUnit.DAYS);
    }

    public int getDateDifference() {
        return cd;
    }


}
