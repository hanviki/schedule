package cc.mrbird.febs.common.generator;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestStudy {

    @FunctionalInterface
    public interface  Han<T>{
        T say(T msg);
    }

    public static void main(String[] args) {

        String value="44076.371319444399";
        BigDecimal bd= new BigDecimal(value);
        int days= bd.intValue();

        Calendar calendar = new GregorianCalendar(1900,0,-1);
        Date d = calendar.getTime();
Date df= DateUtil.offsetDay(d,days);
//        Integer g= exceute(j->{
//            return  "哈哈,是他妈这么用吗";
//        });
        System.out.println(DateUtil.format(df,"yyyy-MM-dd"));
    }
    private static Integer exceute(Han<String> test){
        String msg= "哈哈,是他妈这么用吗";
        if(msg== test.say(msg)) return  1;
        return  0;
    }
}
