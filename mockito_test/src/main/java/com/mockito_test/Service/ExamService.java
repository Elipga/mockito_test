package com.mockito_test.Service;

import com.mockito_test.Domain.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findExamByName(String name);

    Exam findExamByNameWithQuestions(String name);

    Exam save(Exam exam);

}
