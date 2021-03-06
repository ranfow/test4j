package org.test4j.json.encoder.single.spec;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.test4j.json.helper.JSONFeature;
import org.test4j.junit.Test4J;
import org.test4j.tools.commons.DateHelper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DateEncoderTest extends Test4J {
    @Test
    public void testEncode() throws Exception {
        Date date = DateHelper.parse("2011-08-01 08:11:41");

        DateEncoder encoder = DateEncoder.instance;
        encoder.setFeatures(JSONFeature.UseSingleQuote);
        StringWriter writer = new StringWriter();
        encoder.encode(date, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("{#class:'Date',#value:'2011-08-01 08:11:41'}");
    }

    @Test
    public void testEncode_NotFlagClazz() throws Exception {
        Date date = DateHelper.parse("2011-08-01 08:11:41");

        DateEncoder encoder = DateEncoder.instance;
        encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);
        StringWriter writer = new StringWriter();
        encoder.encode(date, writer, new ArrayList<String>());
        String json = writer.toString();
        System.out.println(json);
        want.string(json).eqIgnoreSpace("'2011-08-01 08:11:41'");
    }

    @Test
    public void testEncode_SQLDate() throws Exception {
        Date time = DateHelper.parse("2011-08-01 08:11:41");
        java.sql.Date date = new java.sql.Date(time.getTime());

        DateEncoder encoder = DateEncoder.instance;
        encoder.setFeatures(JSONFeature.UseSingleQuote);
        StringWriter writer = new StringWriter();
        encoder.encode(date, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("{#class:'java.sql.Date@82ae8779',#value:'2011-08-01 08:11:41'}");
    }

    @Test
    public void testEncode_SQLDate_NotFlagClazz() throws Exception {
        Date time = DateHelper.parse("2011-08-01 08:11:41");
        java.sql.Date date = new java.sql.Date(time.getTime());

        DateEncoder encoder = DateEncoder.instance;
        encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);
        StringWriter writer = new StringWriter();
        encoder.encode(date, writer, new ArrayList<String>());
        String json = writer.toString();
        System.out.println(json);
        want.string(json).eqIgnoreSpace("{#value:'2011-08-01 08:11:41'}");
    }
}
