package org.testerhome.junit5.json.params;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

public class JsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonSource> {

    private String value;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        Stream<Object> arguments = getArguments(this.value);
        if(arguments == null) {
            throw new Exception("json 入参有错误");
        }
        return getArguments(this.value).map(Arguments::of);
    }

    @Override
    public void accept(JsonSource jsonSource) {
        this.value = jsonSource.value();
    }

    private Stream<Object> getArguments(String value) {
        Object jsonObject = JSONObject.parse(value);
        return getObjectStream(jsonObject);
    }
    static Stream<Object> getObjectStream(Object jsonObject) {
        if (jsonObject instanceof JSONArray) {
            return ((JSONArray) jsonObject).stream();
        } else if (jsonObject instanceof JSONObject) {
            return Stream.of(jsonObject);
        }
        return null;
    }
}
