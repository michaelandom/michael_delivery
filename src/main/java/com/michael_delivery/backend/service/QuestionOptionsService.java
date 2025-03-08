package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.repos.RiderAnswersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionOptionsService extends BaseService<QuestionOptions, QuestionOptionsDTO,Long, QuestionOptionsRepository> {

    private final QuestionOptionsRepository questionOptionsRepository;
    private final QuestionsRepository questionsRepository;
    private final RiderAnswersRepository riderAnswersRepository;

    public QuestionOptionsService(final QuestionOptionsRepository questionOptionsRepository,
            final QuestionsRepository questionsRepository,
            final RiderAnswersRepository riderAnswersRepository) {
        super(questionOptionsRepository,"questionOptionId");

        this.questionOptionsRepository = questionOptionsRepository;
        this.questionsRepository = questionsRepository;
        this.riderAnswersRepository = riderAnswersRepository;
    }

    @Override
    public Page<QuestionOptionsDTO> search(Specification<QuestionOptions> query, Pageable pageable) {
        return this.questionOptionsRepository.findAll(query, pageable);
    }


    @Override
    protected QuestionOptionsDTO mapToDTO(final QuestionOptions questionOptions,
            final QuestionOptionsDTO questionOptionsDTO) {
        questionOptionsDTO.setQuestionOptionId(questionOptions.getQuestionOptionId());
        questionOptionsDTO.setQuestionOption(questionOptions.getQuestionOption());
        questionOptionsDTO.setDescription(questionOptions.getDescription());
        questionOptionsDTO.setIsCorrect(questionOptions.getIsCorrect());
        questionOptionsDTO.setQuestion(questionOptions.getQuestion() == null ? null : questionOptions.getQuestion().getQuestionId());
        return questionOptionsDTO;
    }

    @Override
    protected QuestionOptions mapToEntity(final QuestionOptionsDTO questionOptionsDTO,
            final QuestionOptions questionOptions) {
        questionOptions.setQuestionOption(questionOptionsDTO.getQuestionOption());
        questionOptions.setDescription(questionOptionsDTO.getDescription());
        questionOptions.setIsCorrect(questionOptionsDTO.getIsCorrect());
        final Questions question = questionOptionsDTO.getQuestion() == null ? null : questionsRepository.findById(questionOptionsDTO.getQuestion())
                .orElseThrow(() -> new NotFoundException("question not found"));
        questionOptions.setQuestion(question);
        return questionOptions;
    }

    @Override
    protected QuestionOptionsDTO createDTO() {
        return new QuestionOptionsDTO();
    }

    @Override
    protected QuestionOptions createEntity() {
        return new QuestionOptions();
    }

    public ReferencedWarning getReferencedWarning(final Long questionOptionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final QuestionOptions questionOptions = questionOptionsRepository.findById(questionOptionId)
                .orElseThrow(NotFoundException::new);
        final RiderAnswers optionRiderAnswers = riderAnswersRepository.findFirstByOption(questionOptions);
        if (optionRiderAnswers != null) {
            referencedWarning.setKey("questionOptions.riderAnswers.option.referenced");
            referencedWarning.addParam(optionRiderAnswers.getRiderAnswerId());
            return referencedWarning;
        }
        return null;
    }

}
