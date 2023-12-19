package com.mockito_test.Repository;

import com.mockito_test.Domain.Exam;
import com.mockito_test.Service.ExamService;

import java.util.List;

public interface ExamRepository {
    Exam save (Exam exam);
    List<Exam> findAll();
}
