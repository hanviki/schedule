package cc.mrbird.febs.common.converter;

import cn.hutool.Hutool;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.wuwenze.poi.convert.ReadConverter;
import com.wuwenze.poi.exception.ExcelKitReadConverterException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class DateReadConverter2 implements ReadConverter{
    @Override
    public Date convert(Object value) throws ExcelKitReadConverterException {
        if(value!=null && value.toString()!=""){
            if(NumberUtil.isNumber(value.toString())) {
                try {
                    BigDecimal bd = new BigDecimal(value.toString());
                    int days = bd.intValue();

                    Calendar calendar = new GregorianCalendar(1900, 0, -1);
                    Date d = calendar.getTime();
                    Date df = DateUtil.offsetDay(d, days);
                    return df;
                } catch (Exception ex) {
                    return null;
                }
            }
            else{
                return DateUtil.parse(value.toString().replace("T00:00:00.000",""));
            }
        }
        else{
            return  null;
        }
    }
}
