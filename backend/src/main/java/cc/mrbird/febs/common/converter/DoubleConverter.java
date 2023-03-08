package cc.mrbird.febs.common.converter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.wuwenze.poi.convert.ReadConverter;
import com.wuwenze.poi.exception.ExcelKitReadConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoubleConverter implements ReadConverter {
    @Override
    public Double convert(Object value) throws ExcelKitReadConverterException {
        if(value!=null && !value.toString().equals("") && !value.toString().trim().equals("")){
            try {
                return Convert.toDouble(value.toString().trim().replace("*",""));
            }
            catch (Exception ex){
                return  null;
            }
        }
        else{
            return  null;
        }
    }
}

