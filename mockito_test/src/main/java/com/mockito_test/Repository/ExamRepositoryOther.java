package com.mockito_test.Repository;

import com.mockito_test.Domain.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamRepositoryOther implements ExamRepository{

    @Override
    public Exam save(Exam exam) {
        return null;
    }

    @Override
    public List<Exam> findAll() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
