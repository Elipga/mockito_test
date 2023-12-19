package com.mockito_test.Repository;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionByExamId(Long id);

    void saveQuestions(List<String> questions);

}
