package org.test4j.testng.database.environment;

import mockit.Mock;

import org.test4j.module.database.environment.TableMeta;
import org.test4j.testng.Test4J;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "test4j", "database" })
public class TableMetaTest extends Test4J {

    @Test(dataProvider = "dataTruncate")
    public void testTruncateString(String input, String expected) {
        TableMeta meta = reflector.newInstance(TableMeta.class);
        new MockUp<TableMeta>() {
            @Mock
            public int getCloumnSize(String column) {
                want.string(column).isEqualTo("columnName");
                return 5;
            }
        };
        String value = meta.truncateString("columnName", input);
        want.object(value).isEqualTo(expected);
    }

    @DataProvider
    public DataIterator dataTruncate() {
        return new DataIterator() {
            {
                data("123456", "12345");
                data("123", "123");
                data("12345", "12345");
            }
        };
    }
}
