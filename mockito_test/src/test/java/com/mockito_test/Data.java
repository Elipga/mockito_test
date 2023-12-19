package com.mockito_test;

import com.mockito_test.Domain.Exam;

import java.util.Arrays;
import java.util.List;

public class Data {
     public final static List<Exam> EXAMS = Arrays.asList(new Exam(5L, "maths"), new Exam(6L, "science"),
            new Exam(7L, "history"));

     public final static List<Exam> EXAMS_ID_NULL = Arrays.asList(new Exam(null, "maths"), new Exam(null, "science"),
             new Exam(null, "history"));

     public final static List<String> QUESTIONS = Arrays.asList("aritmetica",
             "integrales", "derivadas", "trigonometria", "geometria");

     public final static Exam EXAM = new Exam(null, "physics");
}
