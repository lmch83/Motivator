/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studcore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lukas
 */
public class Quoter
{

    public String getQuote()
    {
        String q;
        File aFile = new File("quotes.txt");
        BufferedReader bfr = null;
        Map<Integer, String> m = new HashMap<>();
        try
        {
            bfr = new BufferedReader(new FileReader(aFile));
            String currentLine = bfr.readLine();
            int nt = 1;
            while (currentLine != null)
            {
                m.put(nt, currentLine);
                nt++;
                currentLine = bfr.readLine();
            }
        }
        catch (Exception anException)
        {
            System.out.println("Error: " + anException);
        }
        finally
        {
            try
            {
                bfr.close();
            }
            catch (Exception anException)
            {
                System.out.println("Error: " + anException);
            }
        }

        String str = "";
        int r = (int) (Math.random() * 100);
        if (r > 100)
        {
            r = (int) (Math.random() * 100);
        }
        int i = 1;
        for (String stg : m.values())
        {
            if (i == r)
            {
                str = stg;
            }
            i++;
        }
        return str;
    }
}
