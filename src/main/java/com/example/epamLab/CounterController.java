package com.example.epamLab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;

@RestController
@Validated
public class CounterController {
    private Log logger = LogFactory.getLog(CounterController.class);

    @GetMapping("/counter")
    public LettersCounter showForm(@RequestParam(value = "word", required = true) @Pattern(regexp="^[a-zA-Z]+$") @NotNull String word,
                                   @RequestParam(value = "letter", required = true) @NotNull Character letter) {
        logger.info("Successfully logged(GET)");
        Integer c = 0;
        for (int i = 0; i < word.length(); i++) {
            if (letter == word.toCharArray()[i])
                c++;
        }
        return new LettersCounter(word, letter, c);
    }

//    @PostMapping("/counter")
//    public String countLetters(@ModelAttribute("counter") LettersCounter counter) {
//        //if (bindingResult.hasErrors())
//        //    return  "form";
//
//        int c = 0;
//        for (int i = 0; i < counter.getWord().length(); i++) {
//            if (counter.getLetter() == counter.getWord().toCharArray()[i])
//                c++;
//            counter.setAmountOfLetters(c);
//        }
//
//        logger.info("Successfully logged(POST)");
//        return "form";
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        logger.error("Error 400 - ", exception);
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        logger.error("Error 400 - letter has to be a character", exception);
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        logger.error("Error 400 - Required request parameters are not present", exception);
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInnerException(Exception exception){
        ExceptionInfo exceptionInfo = new ExceptionInfo(exception.getMessage(), 500);
        logger.error("Error 500", exception);
        return new ResponseEntity<>(exceptionInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
