package org.testerhome.junit5.json.params.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.testerhome.junit5.json.params.JsonFileArgumentsProvider;
import org.testerhome.junit5.json.params.JsonFileSource;

public class JsonFileArgumentsProviderTest {

    @Test
    @DisplayName("default constructor does not throw")
    void defaultConstructor() {
        Assertions.assertDoesNotThrow(JsonFileArgumentsProvider::new);
    }

    /**
     * When passed <code>{"key":"value"}</code>, is executed a single time
     * @param object the parsed JsonObject
     */
    @ParameterizedTest
    @JsonFileSource(resources = "/single-object.json")
    @DisplayName("provides a single object")
    void singleObject(JSONObject object) {
        System.out.println(object.getString("key"));
        Assertions.assertEquals(object.getString("key"), "value");
    }
    /**
     * When passed <code>[{"key":"value1"},{"key","value2"}]</code>, is
     * executed once per element of the array
     * @param object the parsed JsonObject array element
     */
    @ParameterizedTest
    @JsonFileSource(resources = "/array-of-objects.json")
    @DisplayName("provides an array of objects")
    void arrayOfObjects(JSONObject object) {
        Assertions.assertTrue(object.getString("key").startsWith("value"));
    }

    /**
     * When passed <code>[1, 2]</code>, is executed once per array element
     * @param number the parsed JsonNumber for each array element
     */
    @ParameterizedTest
    @JsonFileSource(resources = "/array-of-numbers.json")
    @DisplayName("provides an array of numbers")
    void arrayOfNumbers(Integer number) {
        Assertions.assertTrue(number > 0);
    }

    /**
     * When passed <code>["value1","value2"]</code>, is executed once per array
     * element
     * @param value the parsed JsonString for each array element
     */
    @ParameterizedTest
    @JsonFileSource(resources = "/array-of-strings.json")
    @DisplayName("provides an array of strings")
    void arrayOfStrings(String value) {
        Assertions.assertTrue(value.startsWith("value"));
    }


    /**
     *
     * @param jsonObject
     */
    @ParameterizedTest
    @JsonFileSource(resources = "/complex.json")
    @DisplayName("provides complex json case")
    void complexJson(JSONObject jsonObject) {
        JSONArray topics = jsonObject.getJSONArray("topics");
        for (Object t:
             topics) {
            String title = ((JSONObject) t).getString("title");
            System.out.println(title);
        }
    }
}
