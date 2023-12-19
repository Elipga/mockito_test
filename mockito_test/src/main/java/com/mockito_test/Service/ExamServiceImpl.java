package com.mockito_test.Service;

import com.mockito_test.Domain.Exam;
import com.mockito_test.Repository.ExamRepository;
import com.mockito_test.Repository.ExamRepositoryOther;
import com.mockito_test.Repository.QuestionRepository;

import java.util.List;
import java.util.Optional;


public class ExamServiceImpl implements ExamService {
    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Exam> findExamByName(String name) {
        return examRepository.findAll()
                .stream()
                .filter(e -> e.getName().contains(name))
                .findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestions(String name) {
        Optional<Exam> examOptional = findExamByName(name);
        Exam exam = null;
        if(examOptional.isPresent()){
            exam = examOptional.orElseThrow();
            List<String> questions = questionRepository.findQuestionByExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }

    @Override
    public Exam save(Exam exam) {
        if(!exam.getQuestions().isEmpty()){
            questionRepository.saveQuestions(exam.getQuestions());
        }
        return examRepository.save(exam);
    }


}
