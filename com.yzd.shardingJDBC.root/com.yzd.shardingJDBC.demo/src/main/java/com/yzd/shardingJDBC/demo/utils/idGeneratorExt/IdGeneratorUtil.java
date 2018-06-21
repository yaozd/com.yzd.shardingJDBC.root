package com.yzd.shardingJDBC.demo.utils.idGeneratorExt;

import java.util.Date;

/***
 * id与时间的相互转换
 */
public class IdGeneratorUtil {
    //新纪元--此值一定要与IdGenerator.SJDBC_EPOCH保持相同，目前2016-11-01是当当，当前定义的。
    //IdGenerator.SJDBC_EPOCH=2016-11-01 00:00:00=1477929600000L(时间戳)
    static final long SJDBC_EPOCH = 1477929600000L;

    /***
     *
     * @param t1
     * @return
     */
    public Date IdGeneratorToDate(Long t1) {
        long t2 = t1 >> 22;
        long t3 = SJDBC_EPOCH + t2;
        Date date = new Date(t3);
        return date;
    }

    /***
     *
     * @param d1
     * @return
     */
    public Long DateToIdGenerator(Date d1) {
        long currentDate = d1.getTime();
        long t1 = Long.valueOf((currentDate - SJDBC_EPOCH) << 22);
        return t1;
    }
}
