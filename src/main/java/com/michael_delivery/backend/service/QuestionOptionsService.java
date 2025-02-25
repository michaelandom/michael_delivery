package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.domain.RiderAnswers;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.repos.RiderAnswersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionOptionsService {

    private final QuestionOptionsRepository questionOptionsRepository;
    private final QuestionsRepository questionsRepository;
    private final RiderAnswersRepository riderAnswersRepository;

    public QuestionOptionsService(final QuestionOptionsRepository questionOptionsRepository,
            final QuestionsRepository questionsRepository,
            final RiderAnswersRepository riderAnswersRepository) {
        this.questionOptionsRepository = questionOptionsRepository;
        this.questionsRepository = questionsRepository;
        this.riderAnswersRepository = riderAnswersRepository;
    }

    public List<QuestionOptionsDTO> findAll() {
        final List<QuestionOptions> questionOptionses = questionOptionsRepository.findAll(Sort.by("questionOptionId"));
        return questionOptionses.stream()
                .map(questionOptions -> mapToDTO(questionOptions, new QuestionOptionsDTO()))
                .toList();
    }

    public QuestionOptionsDTO get(final Long questionOptionId) {
        return questionOptionsRepository.findById(questionOptionId)
                .map(questionOptions -> mapToDTO(questionOptions, new QuestionOptionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final QuestionOptionsDTO questionOptionsDTO) {
        final QuestionOptions questionOptions = new QuestionOptions();
        mapToEntity(questionOptionsDTO, questionOptions);
        return questionOptionsRepository.save(questionOptions).getQuestionOptionId();
    }

    public void update(final Long questionOptionId, final QuestionOptionsDTO questionOptionsDTO) {
        final QuestionOptions questionOptions = questionOptionsRepository.findById(questionOptionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionOptionsDTO, questionOptions);
        questionOptionsRepository.save(questionOptions);
    }

    public void delete(final Long questionOptionId) {
        questionOptionsRepository.deleteById(questionOptionId);
    }

    private QuestionOptionsDTO mapToDTO(final QuestionOptions questionOptions,
            final QuestionOptionsDTO questionOptionsDTO) {
        questionOptionsDTO.setQuestionOptionId(questionOptions.getQuestionOptionId());
        questionOptionsDTO.setQuestionOption(questionOptions.getQuestionOption());
        questionOptionsDTO.setDescription(questionOptions.getDescription());
        questionOptionsDTO.setIsCorrect(questionOptions.getIsCorrect());
        questionOptionsDTO.setQuestion(questionOptions.getQuestion() == null ? null : questionOptions.getQuestion().getQuestionId());
        return questionOptionsDTO;
    }

    private QuestionOptions mapToEntity(final QuestionOptionsDTO questionOptionsDTO,
            final QuestionOptions questionOptions) {
        questionOptions.setQuestionOption(questionOptionsDTO.getQuestionOption());
        questionOptions.setDescription(questionOptionsDTO.getDescription());
        questionOptions.setIsCorrect(questionOptionsDTO.getIsCorrect());
        final Questions question = questionOptionsDTO.getQuestion() == null ? null : questionsRepository.findById(questionOptionsDTO.getQuestion())
                .orElseThrow(() -> new NotFoundException("question not found"));
        questionOptions.setQuestion(question);
        return questionOptions;
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
