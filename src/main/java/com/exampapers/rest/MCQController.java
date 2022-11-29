package com.exampapers.rest;

import com.exampapers.domain.QuestionAnswer;
import com.exampapers.service.image.MCQService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class MCQController {

    private final MCQService mcqService;

    @Autowired
    public MCQController(MCQService mcqService) {
        this.mcqService = mcqService;
    }


    @GetMapping("/mcq/{id}")
    @SneakyThrows
    public ResponseEntity<QuestionAnswer> getCountry(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);
        QuestionAnswer questionAnswer = mcqService.getQuestionAnswerById();
        return ResponseEntity.ok(questionAnswer);
    }

}
