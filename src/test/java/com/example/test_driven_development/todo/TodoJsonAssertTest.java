package com.example.test_driven_development.todo;

import static com.jayway.jsonpath.JsonPath.read;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import org.skyscreamer.jsonassert.JSONAssert;

public class TodoJsonAssertTest {

    @Test
    void shouldCompareJson() throws JSONException {

        var data = getRestData();
        var expected = """
                            {
                    "todos":[
                        {
                            "name":"Test 1",
                            "completed": true
                        },
                    {
                        "name":"Test 2",
                        "completed": false
                    }
                    ]
                }

                        """;

        JSONAssert.assertEquals(expected, data, false);

    }

    @Test
    void shouldCompareJsonPath() {
        var json = """
                    {
                                "todos": [
                                    {
                                        "name":"Test 1",
                                        "completed": true
                                    },
                                    {
                                        "name": "Test 2",
                                        "completed":false
                                    },

                                ]
                }
                            """;
        int length = read(json, "$.todos.length()");
        String name = read(json,"$.todos[1].name");
        assertEquals(2, length);
        assertEquals("Test 2", name);
    }

    private String getRestData() {
        return """
                {
                    "todos":[
                        {
                            "completed":true,
                            "name": "Test 1"
                        },
                        {
                            "completed": false,
                            "name": "Test 2"
                        }

                    ]
                }
                """;
    }

}
