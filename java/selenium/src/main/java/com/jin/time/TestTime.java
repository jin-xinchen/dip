package com.jin.time;

/**
 * https://www.timeanddate.com/time/zone/china/shanghai?year=1900
 * https://mp.weixin.qq.com/s/RVx37ol-qOCXpjSY3mSayA
 */
public class TestTime {
    public static void mainCalendar() {
        //https://bugs.openjdk.java.net/browse/JDK-6281408
        //To capture information, java.text.SimpleDateFormat is also affected along with java.util.Calendar.
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.clear();
        c.set(java.util.Calendar.YEAR, 1900);
        System.out.println(c.getTimeZone().getID());
        System.out.println(c.getTime());
        c.add(java.util.Calendar.HOUR, 24);
        System.out.println(c.getTime());
    }
    public static void main(String[] args) throws Exception {
        //listTimeZones();
        //LocaleDefault();
        //Dif19000101();
        Dif1900();Dif1927();mainCalendar();
    }
    public static void Dif19000101() throws Exception{
        java.text.SimpleDateFormat sDF = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = sDF.parse("1900-01-01 08:00:00");
        System.out.println(sDF.format(date));
        System.out.println(date.toString());
    }
    public static void LocaleDefault(){
//        sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=19,lastRule=null]
//        Locale(Locale.getDefault()): zh_CN
        System.out.println(java.util.Locale.getDefault());
//        en_US
//        sun.util.calendar.ZoneInfo[id="America/Toronto",offset=-18000000,dstSavings=3600000,useDaylight=true,transitions=231,lastRule=java.util.SimpleTimeZone[id=America/Toronto,offset=-18000000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]]
        System.out.println(java.util.TimeZone.getDefault());

    }
    public static void Dif1900() throws Exception{
        //https://mp.weixin.qq.com/s/RVx37ol-qOCXpjSY3mSayA
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1900-01-01 08:05:42";
        String str4 = "1900-01-01 08:05:43";
        java.util.Date sDt3 = sf.parse(str3);
        java.util.Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() /1000;
        long ld4 = sDt4.getTime() /1000;
        System.out.println(ld4-ld3);
    }
    public static void Dif1927() throws Exception{
        //https://stackoverflow.com/questions/6841333/why-is-subtracting-these-two-times-in-1927-giving-a-strange-result
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1927-12-31 23:54:07";
        String str4 = "1927-12-31 23:54:08";
        java.util.Date sDt3 = sf.parse(str3);
        java.util.Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() /1000;
        long ld4 = sDt4.getTime() /1000;
        System.out.println(ld4-ld3);
    }
    public static void listTimeZones() throws Exception {
        long startOf1900Utc = -2208988800000L;
        for (String id : java.util.TimeZone.getAvailableIDs()) {
            java.util.TimeZone zone = java.util.TimeZone.getTimeZone(id);
            System.out.println(id);
            if (zone.getRawOffset() != zone.getOffset(startOf1900Utc - 1)) {
                System.out.println(id);
            }
        }
    }
}
