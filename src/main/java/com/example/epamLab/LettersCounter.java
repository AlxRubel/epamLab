package com.example.epamLab;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LettersCounter {

    private String word;

    private Character letter;

    private Integer amountOfLetters;

    public LettersCounter(String word, Character letter, Integer amountOfLetters) {
        this.word = word;
        this.letter = letter;
        this.amountOfLetters = amountOfLetters;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public Integer getAmountOfLetters() {
        return amountOfLetters;
    }

    public void setAmountOfLetters(Integer amountOfLetters) {
        this.amountOfLetters = amountOfLetters;
    }
}
