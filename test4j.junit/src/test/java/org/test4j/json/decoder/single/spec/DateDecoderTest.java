package org.test4j.json.decoder.single.spec;

import java.util.Date;

import org.junit.Test;
import org.test4j.json.JSON;
import org.test4j.junit.Test4J;
import org.test4j.tools.commons.DateHelper;

public class DateDecoderTest extends Test4J {
    @Test
    public void testEncode() throws Exception {
        Date expected = DateHelper.parse("2011-08-01 08:11:41");

        Date actual = JSON.toObject("{'#class':'java.util.Date','#value':'2011-08-01 08:11:41'}");

        want.date(actual).isEqualTo(expected);
    }

    @Test
    public void testEncode_NotFlagClazz() throws Exception {
        Date expected = DateHelper.parse("2011-08-01 08:11:41");

        Date actual = JSON.toObject("'2011-08-01 08:11:41'", Date.class);

        want.date(actual).isEqualTo(expected);
    }

    @Test
    public void testEncode_SQLDate() throws Exception {
        Date time = DateHelper.parse("2011-08-01 08:11:41");
        java.sql.Date expected = new java.sql.Date(time.getTime());

        java.sql.Date actual = JSON.toObject("{'#class':'java.sql.Date','#value':'2011-08-01 08:11:41'}");

        want.date(actual).isEqualTo(expected);
    }

    @Test
    public void testEncode__SQLDate_NotFlagClazz() throws Exception {
        Date time = DateHelper.parse("2011-08-01 08:11:41");
        java.sql.Date expected = new java.sql.Date(time.getTime());

        java.sql.Date actual = JSON.toObject("{'#value':'2011-08-01 08:11:41'}", java.sql.Date.class);

        want.date(actual).isEqualTo(expected);
    }
}
