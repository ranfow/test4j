package org.test4j.json.decoder.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.test4j.json.decoder.IDecoder;
import org.test4j.json.helper.JSONMap;
import org.test4j.json.helper.JSONObject;
import org.test4j.module.core.utility.MessageHelper;
import org.test4j.tools.commons.ClazzHelper;

/**
 * json串解码器基类<br>
 * 解码：从json字符串反序列为java对象<br>
 * 加码：将java对象序列化为json字符串<br>
 * 
 * @author darui.wudr
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseDecoder implements IDecoder {

    protected DecoderException getError(String value, String to) {
        String msg = String.format("couldn't convert \"%s\" to %s", value, to);
        return new DecoderException(msg);
    }

    protected DecoderException getError(String value, String to, Throwable e) {
        String msg = String.format("couldn't convert \"%s\" to %s", value, to);
        return new DecoderException(msg, e);
    }

    /**
     * 获取第n个泛型变量，n从0开始
     * 
     * @param type
     * @param index
     * @return
     */
    protected Type getArgType(ParameterizedType type, int index) {
        if (type == null || index < 0) {
            return null;
        }
        Type[] types = type.getActualTypeArguments();
        if (types == null || index >= types.length) {
            return null;
        }
        return types[index];
    }

    protected Class getRawType(Type type, Class _default) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            Type raw = ((ParameterizedType) type).getRawType();
            return this.getRawType(raw, _default);
        } else {
            return _default == null ? Object.class : _default;
        }
    }

    protected boolean isInterfaceOrAbstract(Type type) {
        Class raw = this.getRawType(type, null);
        return ClazzHelper.isInterfaceOrAbstract(raw);
    }

    protected <T> T newInstance(Type type) {
        Class claz = this.getRawType(type, null);
        Object o = ClazzHelper.newInstance(claz);
        return (T) o;
    }

    protected Type getComponent(JSONObject jo, Type toType, int genericIndex) {
        Type argType = this.getComponent(toType, genericIndex);
        if (!(jo instanceof JSONMap)) {
            return argType;
        }
        Type type = ((JSONMap) jo).getClazzFromJSONFProp(argType);
        return type;
    }

    protected Type getComponent(Type toType, int genericIndex) {
        if (toType instanceof Class) {
            return HashMap.class;
        } else if (toType instanceof ParameterizedType) {
            try {
                return this.getArgType((ParameterizedType) toType, genericIndex);
            } catch (RuntimeException e) {
                MessageHelper.error("find component type from " + toType.toString() + "[index=" + genericIndex
                        + "] error:" + e.getMessage(), e);
                return HashMap.class;
            }
        } else {
            throw new DecoderException("the CollectionDecoder only accpt collection type, but actual is:" + toType);
        }
    }
}
