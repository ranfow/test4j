package org.test4j.spec.scenario;

import java.util.List;

import org.test4j.spec.inner.IScenario;
import org.test4j.spec.inner.IScenarioStep;
import org.test4j.tools.datagen.DataProviderIterator;

/**
 * 场景文件解析错误时，返回一个错误原因的场景列表
 * 
 * @author darui.wudr 2012-7-12 下午4:57:01
 */
public final class ExceptionJSpecScenario implements IScenario {
    private RuntimeException exception;

    private ExceptionJSpecScenario(Throwable e) {
        this.exception = new RuntimeException("parse scenario error", e);
    }

    @Override
    public void validate() throws Throwable {
        throw exception;
    }

    public static DataProviderIterator<IScenario> iterator(Throwable e) {
        DataProviderIterator<IScenario> it = new DataProviderIterator<IScenario>();
        it.data(new ExceptionJSpecScenario(e));
        return it;
    }

    @Override
    public List<IScenarioStep> getSteps() {
        throw exception;
    }

    @Override
    public String getName() {
        return exception == null ? ExceptionJSpecScenario.class.getName() : exception.getMessage();
    }

    int index = 0;

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        String msg = exception.getMessage();
        return String.format("%02d - %s", index, msg.replaceAll("\\s", " "));
    }
}
