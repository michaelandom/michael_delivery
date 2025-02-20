package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.RiderAnswers;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderAnswersDTO;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.RiderAnswersRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RiderAnswersService {

    private final RiderAnswersRepository riderAnswersRepository;
    private final RidersRepository ridersRepository;
    private final QuestionOptionsRepository questionOptionsRepository;

    public RiderAnswersService(final RiderAnswersRepository riderAnswersRepository,
            final RidersRepository ridersRepository,
            final QuestionOptionsRepository questionOptionsRepository) {
        this.riderAnswersRepository = riderAnswersRepository;
        this.ridersRepository = ridersRepository;
        this.questionOptionsRepository = questionOptionsRepository;
    }

    public List<RiderAnswersDTO> findAll() {
        final List<RiderAnswers> riderAnswerses = riderAnswersRepository.findAll(Sort.by("riderAnswerId"));
        return riderAnswerses.stream()
                .map(riderAnswers -> mapToDTO(riderAnswers, new RiderAnswersDTO()))
                .toList();
    }

    public RiderAnswersDTO get(final Long riderAnswerId) {
        return riderAnswersRepository.findById(riderAnswerId)
                .map(riderAnswers -> mapToDTO(riderAnswers, new RiderAnswersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RiderAnswersDTO riderAnswersDTO) {
        final RiderAnswers riderAnswers = new RiderAnswers();
        mapToEntity(riderAnswersDTO, riderAnswers);
        return riderAnswersRepository.save(riderAnswers).getRiderAnswerId();
    }

    public void update(final Long riderAnswerId, final RiderAnswersDTO riderAnswersDTO) {
        final RiderAnswers riderAnswers = riderAnswersRepository.findById(riderAnswerId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(riderAnswersDTO, riderAnswers);
        riderAnswersRepository.save(riderAnswers);
    }

    public void delete(final Long riderAnswerId) {
        riderAnswersRepository.deleteById(riderAnswerId);
    }

    private RiderAnswersDTO mapToDTO(final RiderAnswers riderAnswers,
            final RiderAnswersDTO riderAnswersDTO) {
        riderAnswersDTO.setRiderAnswerId(riderAnswers.getRiderAnswerId());
        riderAnswersDTO.setQuizKey(riderAnswers.getQuizKey());
        riderAnswersDTO.setIsCorrect(riderAnswers.getIsCorrect());
        riderAnswersDTO.setRider(riderAnswers.getRider() == null ? null : riderAnswers.getRider().getRiderId());
        riderAnswersDTO.setOption(riderAnswers.getOption() == null ? null : riderAnswers.getOption().getQuestionOptionId());
        return riderAnswersDTO;
    }

    private RiderAnswers mapToEntity(final RiderAnswersDTO riderAnswersDTO,
            final RiderAnswers riderAnswers) {
        riderAnswers.setQuizKey(riderAnswersDTO.getQuizKey());
        riderAnswers.setIsCorrect(riderAnswersDTO.getIsCorrect());
        final Riders rider = riderAnswersDTO.getRider() == null ? null : ridersRepository.findById(riderAnswersDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        riderAnswers.setRider(rider);
        final QuestionOptions option = riderAnswersDTO.getOption() == null ? null : questionOptionsRepository.findById(riderAnswersDTO.getOption())
                .orElseThrow(() -> new NotFoundException("option not found"));
        riderAnswers.setOption(option);
        return riderAnswers;
    }

}
