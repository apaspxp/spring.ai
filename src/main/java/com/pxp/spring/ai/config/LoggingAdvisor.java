package com.pxp.spring.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

public class LoggingAdvisor implements StreamAroundAdvisor {

    private static final Logger log = LoggerFactory.getLogger(LoggingAdvisor.class);

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        log.info("***************Before: {}", advisedRequest);
        Flux<AdvisedResponse> aroundStream = chain.nextAroundStream(advisedRequest);
        return new MessageAggregator().aggregateAdvisedResponse(aroundStream, chatResponse -> log.info("******************After: {}", chatResponse));
    }

    @Override
    public String getName() {
        return "Logging Advisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
