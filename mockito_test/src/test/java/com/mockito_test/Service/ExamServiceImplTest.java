package com.mockito_test.Service;

import com.mockito_test.Data;
import com.mockito_test.Domain.Exam;
import com.mockito_test.Repository.ExamRepository;
import com.mockito_test.Repository.ExamRepositoryOther;
import com.mockito_test.Repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //1: enable anotations
class ExamServiceImplTest {

    @Mock //1
    private ExamRepository repository;
    @Mock //1
    private QuestionRepository questionRepository;
    @InjectMocks //1
    private ExamServiceImpl service; //1
    //private ExamService service; //2
    //create reference and injects both mocks. It needs constructor with both arguments


    @BeforeEach
    void setUp() {
        //Enable anotations //1
        //MockitoAnnotations.openMocks(this); //1
        //Other form to do this is with @ExtendWith(MockitoExtension.class)

        /*repository = mock(ExamRepository.class); //2
        questionRepository = mock(QuestionRepository.class);//2
        service = new ExamServiceImpl(repository, questionRepository);*/ //2

    }

    @Test
    void findExamByName() {

        when(repository.findAll()).thenReturn(Data.EXAMS);

        Optional<Exam> exam = service.findExamByName("maths");

        assertTrue(exam.isPresent());
        assertEquals(5L, exam.orElseThrow().getId());
        assertEquals("maths", exam.orElseThrow().getName());
    }

    @Test
    void findExamByNameEmptyList() {
        List<Exam> exams = Collections.emptyList();

        when(repository.findAll()).thenReturn(exams);

        Optional<Exam> exam = service.findExamByName("Maths");

        assertFalse(exam.isPresent());
    }

    @Test
    void testExamQuestions(){
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        Exam exam = service.findExamByNameWithQuestions("maths");
        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("aritmetica"));
    }

    @Test
    void testExamQuestionsVerify(){
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        Exam exam = service.findExamByNameWithQuestions("maths");
        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("aritmetica"));
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(5L);
    }

    @Test
    void testNotExistExamVerify(){
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        //when
        Exam exam = service.findExamByNameWithQuestions("maths2");
        //then
        assertNull(exam);
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(5L);
    }

    @Test
    void testSaveExam() {
        //given
        Exam newExam = Data.EXAM;
        newExam.setQuestions(Data.QUESTIONS);

        when(repository.save(any(Exam.class))).then(new Answer<Exam>(){ //autoincrement
            Long sequence = 8L;

            @Override
            public Exam answer(InvocationOnMock invocation) throws Throwable {
                Exam exam = invocation.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
        }); //set id

        //when
        Exam exam = service.save(newExam);

        //then

        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("physics", exam.getName());

        verify(repository).save(any(Exam.class));
        verify(questionRepository).saveQuestions(anyList());
    }

    @Test
    void testHandleException() {
        //given
        when(repository.findAll()).thenReturn(Data.EXAMS_ID_NULL);
        when(questionRepository.findQuestionByExamId(isNull())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamByNameWithQuestions("maths");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(null);
    }
}