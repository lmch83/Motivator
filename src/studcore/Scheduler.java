package studcore;

import utilities.StopWatch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 *
 * @author luc
 */
public class Scheduler {

    private int timerActive;
    private String tsec;
    private String tmin;
    private String thour;
    private String eTime;
    private final Collection<String> sc;

    public Scheduler() {
        timerActive = 0;
        tsec = "00";
        tmin = "00";
        thour = "00";
        sc = new HashSet<>();
    }



    /**
     * Starts a clock.
     */
    public void start() {
        calculateElapsedTime();

        timerActive = 1;

        tsec = "00";
        tmin = "00";
        thour = "00";
        new Thread() {
            @Override
            public void run() {

                StopWatch sw = new StopWatch();
                long sec;
                long min = 0;
                long hour = 0;

                while (timerActive == 1) {
                    sec = (int) sw.elapsedTime();

                    if (sec < 10) {
                        tsec = "0" + sec;
                    } else {
                        tsec = "" + sec;
                    }

                    if (sec == 60) {
                        min++;
                        sw = new StopWatch();
                        if (min < 10) {
                            tmin = "0" + min;
                        } else {
                            tmin = "" + min;
                        }
                    }

                    if (min == 60) {
                        hour++;
                        sw = new StopWatch();
                        min = 0;
                        if (hour < 10) {
                            thour = "0" + hour;
                        } else {
                            thour = "" + hour;
                        }

                    }

                }
            }
        }.start();

    }

    /**
     *
     * @param anEvent
     */
    public void removeEvent(String anEvent) {
        String mod, type, date;
        Scanner scr = new Scanner(anEvent);
        mod = scr.next();
        type = scr.next();
        date = scr.next();
        String complete = mod + ";" + type + ";" + date;
        for (String s : sc) {
            if (s.equals(complete)) {
                sc.remove(s);
            }
        }
        saveEvents();
    }

    public Collection<String> getEvents() {
        String mc, et, da;

        Collection<String> ss = new TreeSet<>();

        for (String s : sc) {
            Scanner scr = new Scanner(s);
            scr.useDelimiter(";");
            mc = scr.next();
            et = scr.next();
            da = scr.next();
            ss.add(mc + " " + et + " " + da);
        }

        return ss;
    }

    public String getCurrentTime() {
        String aTime = thour + ":" + tmin + ":" + tsec;
        return aTime;
    }

    public String getElapsedTime() {
        return eTime;
    }

    /**
     *
     * @return
     */
    public int calculateProgress() {
        File aFile = new File("reqTime.txt");
        BufferedReader bufferedFileReader = null;
        String tim = "";

        try {

            bufferedFileReader = new BufferedReader(new FileReader(aFile));
            tim = bufferedFileReader.readLine();

        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bufferedFileReader.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }

        Scanner s = new Scanner(getElapsedTime());
        s.useDelimiter(":");
        String hour = s.next();
        return (Integer.parseInt(tim) - Integer.parseInt(hour));
    }

    /**
     * Stops the clock and saves study eTime to file.
     */
    public void stop() {

        timerActive = 0;

        saveTime();

    }

    /**
     *
     */
    public void loadEvents() {

        File aFile = new File("events.txt");
        BufferedReader bufferedFileReader = null;
        String mod;
        String typ;
        String dat;

        try {
            Scanner ls;
            bufferedFileReader = new BufferedReader(new FileReader(aFile));
            String currentLine = bufferedFileReader.readLine();

            while (currentLine != null) {
                ls = new Scanner(currentLine);
                ls.useDelimiter(";");
                mod = ls.next();
                typ = ls.next();
                dat = ls.next();

                sc.add(mod + ";" + typ + ";" + dat);
                currentLine = bufferedFileReader.readLine();

            }
        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bufferedFileReader.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }
    }

    public void saveEvents() {

        File aFile = new File("events.txt");
        BufferedWriter bfw = null;

        try {
            bfw = new BufferedWriter(new FileWriter(aFile));
            for (String g : sc) {
                bfw.write(g);
                bfw.newLine();
            }
        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bfw.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }
    }

    /**
     *
     * @param aModule
     * @param eventType
     * @param aDate
     */
    public void saveNewEvent(String aModule, String eventType, String aDate) {

        File aFile = new File("events.txt");
        BufferedWriter bfw = null;
        String mod = aModule;
        String typ = eventType;
        String dat = aDate;

        String full = mod + ";" + typ + ";" + dat;
        if (sc.contains(full)) {
        } else {
            sc.add(full);
        }

        try {
            bfw = new BufferedWriter(new FileWriter(aFile));
            for (String g : sc) {
                bfw.write(g);
                bfw.newLine();
            }
        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bfw.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }
    }

    /**
     * Saves current study eTime to file.
     */
    void saveTime() {

        File aFile = new File("time.txt");
        BufferedWriter bufferedFileWriter = null;
        try {
            bufferedFileWriter = new BufferedWriter(new FileWriter(aFile));
            bufferedFileWriter.write(eTime);
            bufferedFileWriter.newLine();
        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bufferedFileWriter.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }

    }

    /**
     * Reads study eTime from a file.
     */
    public String loadElapsedTime() {
        File aFile = new File("time.txt");
        BufferedReader bufferedFileReader = null;
        String aTime = "00:00:00";
        try {

            Scanner lineScanner;
            bufferedFileReader = new BufferedReader(new FileReader(aFile));
            String currentLine = bufferedFileReader.readLine();

            while (currentLine != null) {
                lineScanner = new Scanner(currentLine);
                aTime = lineScanner.next();
                currentLine = bufferedFileReader.readLine();

            }
        } catch (Exception anException) {
            System.out.println("Error: " + anException);
        } finally {
            try {
                bufferedFileReader.close();
            } catch (Exception anException) {
                System.out.println("Error: " + anException);
            }
        }
        return aTime;
    }

    public void calculateElapsedTime() {
        String hr, mn, st;
        Scanner scan = new Scanner(loadElapsedTime());
        scan.useDelimiter(":");
        hr = scan.next();
        mn = scan.next();
        st = scan.next();
        int hour = Integer.parseInt(hr);
        int min = Integer.parseInt(mn);
        int sec = Integer.parseInt(st);
        int h = hour + Integer.parseInt(thour);
        int m = min + Integer.parseInt(tmin);
        int s = sec + Integer.parseInt(tsec);

        if (s < 10) {
            st = "0" + s;
        } else {
            st = "" + s;

        }

        if (s >= 60) {
            s -= 60;
            m++;
            if (s < 10) {
                st = "0" + s;
            } else {
                st = "" + s;
            }
        }

        if (m < 10) {
            mn = "0" + m;
        } else {
            mn = "" + s;

        }

        if (m >= 60) {
            m -= 60;
            h++;
            if (m < 10) {
                mn = "0" + m;
            } else {
                mn = "" + s;

            }
        }

        if (h < 10) {
            hr = "0" + h;
        } else {
            hr = "" + h;
        }

        eTime = hr + ":" + mn + ":" + st;
    }
}
