package com.example.epamLab;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class CounterControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void positiveTest() {
        String actual = restTemplate.getForObject("http://localhost:8080/counter?word=asdfasdf&letter=a", String.class);
        String expected =
                "{\"word\":\"asdfasdf\",\"letter\":\"a\",\"amountOfLetters\":2}";
        assertEquals(expected, actual);
    }

    @Test
    public void handleConstraintViolationException() {
        String actual =
                restTemplate.getForObject("http://localhost:8080/counter?word=132&letter=a", String.class);
        String expected =
                "{\"message\":\"showForm.word: must match \\\"^[a-zA-Z]+$\\\"\",\"code\":400}";
        assertEquals(expected, actual);
    }

    @Test
    public void handleMethodArgumentTypeMismatchException() {
        String actual =
                restTemplate.getForObject("http://localhost:8080/counter?word=asd&letter=as", String.class);
        String expected = "{\"message\":\"Failed to convert value of type 'java.lang.String'" +
                " to required type 'java.lang.Character'; nested exception is java.lang.IllegalArgumentException: " +
                "String [as] with length 2 cannot be converted to char type: " +
                "neither Unicode nor single character\",\"code\":400}";
        assertEquals(expected, actual);
    }

    @Test
    public void handleMissingServletRequestParameterExceptionWord() {
        String actual = restTemplate.getForObject("http://localhost:8080/counter?", String.class);
        String expected =
                "{\"message\":\"Required request parameter 'word' for method parameter type String is not present\",\"code\":400}";
        assertEquals(expected, actual);
    }

    @Test
    public void handleMissingServletRequestParameterExceptionLetter() {
        String actual = restTemplate.getForObject("http://localhost:8080/counter?word=asd&letter=", String.class);
        String expected =
                "{\"message\":\"Required request parameter 'letter' for method parameter type Character is present but converted to null\",\"code\":400}";
        assertEquals(expected, actual);
    }
}