
/**   
 * @Title: SpringCronExpression.java 
 * @Package: com.smart.platform.toolkit 
 * @Description: TODO
 * @author Administrator  
 * @date 2016年7月27日 下午11:02:58 
 * @version 1.3.1 
 */


package com.smart.platform.toolkit;

import java.util.Date;

/** Seconds:可出现,-  *  / 四个字符，有效范围为0-59的整数    
  * Minutes:可出现,-  *  / 四个字符，有效范围为0-59的整数    
  * Hours:可出现,-  *  / 四个字符，有效范围为0-23的整数    
  * DayofMonth:可出现,-  *  / ? L W C八个字符，有效范围为0-31的整数     
  * Month:可出现,-  *  / 四个字符，有效范围为1-12的整数或JAN-DEc    
  * DayofWeek:可出现,-  *  / ? L C #四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推    
  * Year:可出现,-  *  / 四个字符，有效范围为1970-2099年  
  * @author Administrator
  * @date 2016年7月27日 下午11:02:58 
  * @version V1.3.1
 */

public final class CronExpression
{
    public static String getCronWithUrgent()
    {
        Date newDate = DateUtil.addMinute(new Date(), 1);
        String[] timeArray = DateUtil.getTime(newDate).split(StringUtil.SPLIT_TIME_MARK);
        String[] dateArray = DateUtil.getDate(newDate).split(StringUtil.SPLIT_DATE_MARK);
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(Integer.parseInt(timeArray[1])).append(" ");
        sdber.append(timeArray[0]).append(" ").append(dateArray[2]).append(" ");
        sdber.append(dateArray[1]).append(" ? ").append(dateArray[0]);
        return sdber.toString();
    }
    
    public static String geCronWithHhMm(String hh,String mm)
    {
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(mm).append(" ").append(hh).append(" ? * *");
        return sdber.toString();
    }
    
    public static String getCronWithYyyyMmDdHhMm(String yyyy,String MM,String dd,String hh,String mm)
    {
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(mm).append(" ").append(hh).append(" ");
        sdber.append(dd).append(" ").append(MM).append(" ? ").append(yyyy);
        return sdber.toString();
    }
    
    public static String getCronWithWeekDayHhMm(String weekDay,String hh,String mm)
    {
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(mm).append(" ").append(hh).append(" ");
        sdber.append("? * ").append(weekDay).append(" *");
        return sdber.toString();
    }
    
    public static String getCronWithMonthDayHhMm(String monthDay,String hh,String mm)
    {
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(mm).append(" ").append(hh).append(" ");
        sdber.append(monthDay).append(" * ? *");
        return sdber.toString();
    }
    
    public static String getCronWithMonthWeekDayHhMm(String selectWeek, String weekDay,String hh,String mm)
    {
        StringBuilder sdber = new StringBuilder();
        sdber.append("0 ").append(mm).append(" ").append(hh).append(" ");
        sdber.append("? ").append(" * ").append(weekDay).append("#").append(selectWeek);
        return sdber.toString();
    }
}
