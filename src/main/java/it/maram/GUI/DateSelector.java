package it.maram.GUI;

import it.maram.utils.ListUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateSelector extends JPanel {
    private static final List<String> t1 = ListUtils.listOfStrings("Jan", "Mar","May", "Jul", "Aug", "Oct", "Dec");
    private static final List<String> t0 = ListUtils.listOfStrings("Apr", "Jun", "Sep", "Nov");
    private static final String feb = "Feb";
    private static final List<String> months = ListUtils.listOfStrings("Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Sep", "Aug", "Oct", "Nov", "Dec");
    private static Integer[] getDays(String month, int year){
        boolean bi = year%4==0;
        int i = 31;
        if(t0.contains(month)){
            i = 30;
        }
        else if (month.equals(feb)) {
            i = bi ? 29 : 28;
        }
        System.out.println(month + " has days: " + i);
        Integer[] days = new Integer[i];
        for(int j = 1 , h = 0; h<i; j++, h++){
            days[h] = j;
        }
        return days;
    }
    private static Integer[] getYears(){
        int first = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[50];
        for(int i = 0; i<50; i++){
            years[i] = first+i;
        }
        return years;
    }
    private int year;
    private int month;
    private int day;
    private JComboBox<Integer> daySelector;
    private JComboBox<String> monthSelector;
    private JComboBox<Integer> yearSelector;
    public DateSelector(String text, int width, int height){
        setPreferredSize(new Dimension(width,height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel label = new JLabel(text);
        yearSelector = new JComboBox<>(getYears());
        monthSelector = new JComboBox<>(ListUtils.asStringArray(months));
        yearSelector.setSelectedIndex(0);
        monthSelector.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        daySelector = new JComboBox<>(getDays((String)monthSelector.getSelectedItem(), (int)yearSelector.getSelectedItem()));//never null
        daySelector.setSelectedIndex(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
        yearSelector.addActionListener(a->{
            setDayField();
        });
        monthSelector.addActionListener(a->{
           setDayField();
        });
        add(label); add(daySelector); add(monthSelector); add(yearSelector);
    }
    private void setDayField(){
        int current = daySelector.getSelectedIndex();
        daySelector.setModel(new DefaultComboBoxModel<>(getDays((String)monthSelector.getSelectedItem(), (int) yearSelector.getSelectedItem())));
        if(daySelector.getItemCount()>current) daySelector.setSelectedIndex(current);
        else daySelector.setSelectedIndex(0);
        daySelector.revalidate();
        revalidate();
    }
    public Date getDate(){
        year = (int) yearSelector.getSelectedItem();
        month = monthSelector.getSelectedIndex();
        day = (int) daySelector.getSelectedItem();
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.set(year, month, day,0,0);
        return c.getTime();
    }
}
