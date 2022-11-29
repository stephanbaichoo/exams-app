package com.exampapers.domain;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class QuestionAnswerTest {

    @Test
    void getAllUUIDs() throws IOException {
        QuestionAnswer build = QuestionAnswer.builder()
                .simpleTexts(new ArrayList<>())
                .simpleImages(new ArrayList<>())
                .componentOrder(new HashSet<>())
                .build();

        File file = new File("C:\\Users\\steph\\Documents\\Projects\\ExamPapers\\src\\main\\resources\\image.svg");

        byte[] bytes = FileUtils.openInputStream(file).readAllBytes();

        build.simpleText(new SimpleText("Text"));
        build.simpleImage(new SimpleImage(bytes));
        build.simpleText(new SimpleText("Text1"));

        build.getAllUUIDs();

    }
}