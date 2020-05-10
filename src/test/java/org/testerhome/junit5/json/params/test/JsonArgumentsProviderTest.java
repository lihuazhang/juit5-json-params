package org.testerhome.junit5.json.params.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.testerhome.junit5.json.params.JsonArgumentsProvider;
import org.testerhome.junit5.json.params.JsonSource;

public class JsonArgumentsProviderTest {

    @Test
    @DisplayName("default constructor does not throw")
    void defaultConstructor() {
        Assertions.assertDoesNotThrow(JsonArgumentsProvider::new);
    }

    /**
     * When passed <code>{"key":"value"}</code>, is executed a single time
     * @param object the parsed JsonObject
     */
    @ParameterizedTest
    @JsonSource("{\"key\":\"value\"}")
    @DisplayName("provides a single object")
    void singleObject(JSONObject object) {
        Assertions.assertEquals(object.getString("key"), "value");
    }

    /**
     * When passed <code>[{"key":"value1"},{"key","value2"}]</code>, is
     * executed once per element of the array
     * @param object the parsed JsonObject array element
     */
    @ParameterizedTest
    @JsonSource("[{\"key\":\"value1\"},{\"key\":\"value2\"}]")
    @DisplayName("provides an array of objects")
    void arrayOfObjects(JSONObject object) {
        Assertions.assertTrue(object.getString("key").startsWith("value"));
    }

    /**
     * When passed <code>[1, 2]</code>, is executed once per array element
     * @param number the parsed JsonNumber for each array element
     */
    @ParameterizedTest
    @JsonSource("[1,2]")
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
    @JsonSource("[\"value1\",\"value2\"]")
    @DisplayName("provides an array of strings")
    void arrayOfStrings(String value) {
        Assertions.assertTrue(value.startsWith("value"));
    }

    @ParameterizedTest
    @JsonSource("{'key':'value'}")
    @DisplayName("handles simplified json")
    void simplifiedJson(JSONObject object) {
        Assertions.assertTrue(object.getString("key").equals("value"));
    }
}
