package com.mockito_test.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class Exam {
    private Long id;
    private String name;
    private List<String> questions;

    public Exam(Long id, String name) {
        this.id = id;
        this.name = name;
        this.questions = new ArrayList<>();
    }
}
