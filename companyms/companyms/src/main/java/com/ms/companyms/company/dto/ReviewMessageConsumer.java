package com.ms.companyms.company.dto;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.ms.companyms.company.CompanyService;
@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;
    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }
    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyService.updateCompanyRating(reviewMessage);
    }
}
