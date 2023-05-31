package com.example.bskl_kotlin.manager;

/**
 * Created by krishnaraj on 24/07/18.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCalendar {
    private Calendar c;
    private List<String> output;

    public MyCalendar()
    {
        c = Calendar.getInstance();

        output =  new ArrayList<String>();
    }

    public List<String> getCalendar()
    {
        //Get current Day of week and Apply suitable offset to bring the new calendar
        //back to the appropriate Monday, i.e. this week or next
        switch (c.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.SUNDAY:
                c.add(Calendar.DATE, 1);
                break;

            case Calendar.MONDAY:
                //Don't need to do anything on a Monday
                //included only for completeness
                break;

            case Calendar.TUESDAY:
                c.add(Calendar.DATE,-1);
                break;

            case Calendar.WEDNESDAY:
                c.add(Calendar.DATE, -2);
                break;

            case Calendar.THURSDAY:
                c.add(Calendar.DATE,-3);
                break;

            case Calendar.FRIDAY:
                c.add(Calendar.DATE,-4);
                break;

            case Calendar.SATURDAY:
                c.add(Calendar.DATE,2);
                break;
        }

        //Add the Monday to the output
        output.add(c.getTime().toString());
        for (int x = 1; x <8; x++)
        {
            //Add the remaining days to the output
            c.add(Calendar.DATE,x);
            output.add(c.getTime().toString());
        }
        return this.output;
    }

}