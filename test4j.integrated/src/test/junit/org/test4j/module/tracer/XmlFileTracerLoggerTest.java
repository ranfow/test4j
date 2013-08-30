package org.test4j.module.tracer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import mockit.Mock;

import org.junit.Test;
import org.test4j.hamcrest.matcher.string.StringMode;
import org.test4j.junit.Test4J;
import org.test4j.tools.commons.ResourceHelper;

public class XmlFileTracerLoggerTest implements Test4J {

    @Test
    public void testXmlTracer() throws FileNotFoundException {
        final StringWriter writer = new StringWriter();
        new MockUp<TracerLogger>() {
            @Mock
            public Writer getWriter(String surfix) throws IOException {
                return writer;
            }
        };
        TracerLogger log = new XmlFileTracerLogger();

        log.writerMethodInputInfo(TracerServiceDemo.class, "sayHello", new Object[] { 1, 2, "name", true });
        log.writerMethodInputInfo(TracerServiceDemo.class, "sayHelloInternal", new Object[] { 1, 2, "name", true });
        log.writerSqlStatement("select * from tdd_user", "");
        log.writerSqlStatement("update tdd_user set first_name='xxxx' where id=124", "");
        log.writerMethodReturnValue(TracerServiceDemo.class, "sayHelloInternal", "your value");
        log.writerMethodException(TracerServiceDemo.class, "sayHello", new RuntimeException("call error"));

        log.close();
        String xml = writer.toString();
        String expected = ResourceHelper.readFromFile(XmlFileTracerLoggerTest.class, "XmlFileTracerLoggerTest.xml");
        want.string(xml).isEqualTo(expected, StringMode.IgnoreSpace);
    }
}
