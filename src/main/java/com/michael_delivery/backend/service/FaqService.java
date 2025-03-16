package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Faq;
import com.michael_delivery.backend.dto.FaqDTO;
import com.michael_delivery.backend.repository.FaqRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class FaqService extends BaseService<Faq, FaqDTO,Long, FaqRepository>{

    private final FaqRepository faqRepository;

    public FaqService(final FaqRepository faqRepository) {

        super(faqRepository,"id");
        this.faqRepository = faqRepository;
    }


    @Override
    public Page<FaqDTO> search(Specification<Faq> query, Pageable pageable) {
        return this.faqRepository.findAll(query, pageable);
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
