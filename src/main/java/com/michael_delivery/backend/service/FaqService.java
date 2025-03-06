package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.ExtrFee;
import com.michael_delivery.backend.domain.Faq;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import com.michael_delivery.backend.model.FaqDTO;
import com.michael_delivery.backend.repos.ExtrFeeRepository;
import com.michael_delivery.backend.repos.FaqRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FaqService extends BaseService<Faq, FaqDTO,Long, FaqRepository>{

    private final FaqRepository faqRepository;

    public FaqService(final FaqRepository faqRepository) {

        super(faqRepository,"id");
        this.faqRepository = faqRepository;
    }

    @Override
    protected FaqDTO mapToDTO(final Faq faq, final FaqDTO faqDTO) {
        faqDTO.setId(faq.getId());
        faqDTO.setQuestion(faq.getQuestion());
        faqDTO.setAnswer(faq.getAnswer());
        faqDTO.setIsForRider(faq.getIsForRider());
        return faqDTO;
    }

    @Override
    protected Faq mapToEntity(final FaqDTO faqDTO, final Faq faq) {
        faq.setQuestion(faqDTO.getQuestion());
        faq.setAnswer(faqDTO.getAnswer());
        faq.setIsForRider(faqDTO.getIsForRider());
        return faq;
    }

    @Override
    protected FaqDTO createDTO() {
        return new FaqDTO();
    }

    @Override
    protected Faq createEntity() {
        return new Faq();
    }

}
