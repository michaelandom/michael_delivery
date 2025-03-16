package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.RiderAnswersDTO;
import com.michael_delivery.backend.repository.QuestionOptionsRepository;
import com.michael_delivery.backend.repository.RiderAnswersRepository;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RiderAnswersService extends BaseService<RiderAnswers, RiderAnswersDTO,Long, RiderAnswersRepository> {

    private final RiderAnswersRepository riderAnswersRepository;
    private final RidersRepository ridersRepository;
    private final QuestionOptionsRepository questionOptionsRepository;

    public RiderAnswersService(final RiderAnswersRepository riderAnswersRepository,
            final RidersRepository ridersRepository,
            final QuestionOptionsRepository questionOptionsRepository) {
        super(riderAnswersRepository,"riderAnswerId");
        this.riderAnswersRepository = riderAnswersRepository;
        this.ridersRepository = ridersRepository;
        this.questionOptionsRepository = questionOptionsRepository;
    }

    @Override
    public Page<RiderAnswersDTO> search(Specification<RiderAnswers> query, Pageable pageable) {
        return this.riderAnswersRepository.findAll(query, pageable);
    }


    @Override
    protected RiderAnswersDTO mapToDTO(final RiderAnswers riderAnswers,
            final RiderAnswersDTO riderAnswersDTO) {
        riderAnswersDTO.setRiderAnswerId(riderAnswers.getRiderAnswerId());
        riderAnswersDTO.setQuizKey(riderAnswers.getQuizKey());
        riderAnswersDTO.setIsCorrect(riderAnswers.getIsCorrect());
        riderAnswersDTO.setRider(riderAnswers.getRider() == null ? null : riderAnswers.getRider().getRiderId());
        riderAnswersDTO.setOption(riderAnswers.getOption() == null ? null : riderAnswers.getOption().getQuestionOptionId());
        return riderAnswersDTO;
    }

    @Override
    protected RiderAnswers mapToEntity(final RiderAnswersDTO riderAnswersDTO,
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

    @Override
    protected RiderAnswersDTO createDTO() {
        return new RiderAnswersDTO();
    }

    @Override
    protected RiderAnswers createEntity() {
        return new RiderAnswers();
    }

}
