package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.QuestionsDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionsService extends BaseService<Questions, QuestionsDTO,Long, QuestionsRepository> {

    private final QuestionsRepository questionsRepository;
    private final QuestionOptionsRepository questionOptionsRepository;

    public QuestionsService(final QuestionsRepository questionsRepository,
            final QuestionOptionsRepository questionOptionsRepository) {
        super(questionsRepository,"questionId");
        this.questionsRepository = questionsRepository;
        this.questionOptionsRepository = questionOptionsRepository;
    }

    @Override
    public Page<QuestionsDTO> search(Specification<Questions> query, Pageable pageable) {
        return this.questionsRepository.findAll(query, pageable);
    }

    @Override
    protected QuestionsDTO mapToDTO(final Questions questions, final QuestionsDTO questionsDTO) {
        questionsDTO.setQuestionId(questions.getQuestionId());
        questionsDTO.setImageUrl(questions.getImageUrl());
        questionsDTO.setQuestionText(questions.getQuestionText());
        questionsDTO.setDescription(questions.getDescription());
        return questionsDTO;
    }

    @Override
    protected Questions mapToEntity(final QuestionsDTO questionsDTO, final Questions questions) {
        questions.setImageUrl(questionsDTO.getImageUrl());
        questions.setQuestionText(questionsDTO.getQuestionText());
        questions.setDescription(questionsDTO.getDescription());
        return questions;
    }

    @Override
    protected QuestionsDTO createDTO() {
        return new QuestionsDTO();
    }

    @Override
    protected Questions createEntity() {
        return new Questions();
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
