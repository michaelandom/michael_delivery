package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.QuestionsDTO;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final QuestionOptionsRepository questionOptionsRepository;

    public QuestionsService(final QuestionsRepository questionsRepository,
            final QuestionOptionsRepository questionOptionsRepository) {
        this.questionsRepository = questionsRepository;
        this.questionOptionsRepository = questionOptionsRepository;
    }

    public List<QuestionsDTO> findAll() {
        final List<Questions> questionses = questionsRepository.findAll(Sort.by("questionId"));
        return questionses.stream()
                .map(questions -> mapToDTO(questions, new QuestionsDTO()))
                .toList();
    }

    public QuestionsDTO get(final Long questionId) {
        return questionsRepository.findById(questionId)
                .map(questions -> mapToDTO(questions, new QuestionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final QuestionsDTO questionsDTO) {
        final Questions questions = new Questions();
        mapToEntity(questionsDTO, questions);
        return questionsRepository.save(questions).getQuestionId();
    }

    public void update(final Long questionId, final QuestionsDTO questionsDTO) {
        final Questions questions = questionsRepository.findById(questionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionsDTO, questions);
        questionsRepository.save(questions);
    }

    public void delete(final Long questionId) {
        questionsRepository.deleteById(questionId);
    }

    private QuestionsDTO mapToDTO(final Questions questions, final QuestionsDTO questionsDTO) {
        questionsDTO.setQuestionId(questions.getQuestionId());
        questionsDTO.setImageUrl(questions.getImageUrl());
        questionsDTO.setQuestionText(questions.getQuestionText());
        questionsDTO.setDescription(questions.getDescription());
        return questionsDTO;
    }

    private Questions mapToEntity(final QuestionsDTO questionsDTO, final Questions questions) {
        questions.setImageUrl(questionsDTO.getImageUrl());
        questions.setQuestionText(questionsDTO.getQuestionText());
        questions.setDescription(questionsDTO.getDescription());
        return questions;
    }

    public ReferencedWarning getReferencedWarning(final Long questionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Questions questions = questionsRepository.findById(questionId)
                .orElseThrow(NotFoundException::new);
        final QuestionOptions questionQuestionOptions = questionOptionsRepository.findFirstByQuestion(questions);
        if (questionQuestionOptions != null) {
            referencedWarning.setKey("questions.questionOptions.question.referenced");
            referencedWarning.addParam(questionQuestionOptions.getQuestionOptionId());
            return referencedWarning;
        }
        return null;
    }

}
