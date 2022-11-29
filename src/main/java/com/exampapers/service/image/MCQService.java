package com.exampapers.service.image;

import com.exampapers.domain.QuestionAnswer;
import com.exampapers.domain.SimpleImage;
import com.exampapers.domain.SimpleText;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class MCQService {

    public QuestionAnswer getQuestionAnswerById() throws IOException {
        QuestionAnswer build = QuestionAnswer.builder()
                .simpleTexts(new ArrayList<>())
                .simpleImages(new ArrayList<>())
                .componentOrder(new HashSet<>())
                .build();

        File file = new File("C:\\Users\\steph\\Documents\\Projects\\ExamPapers\\src\\main\\resources\\image.svg");

        byte[] bytes = FileUtils.openInputStream(file).readAllBytes();

        build.simpleText(new SimpleText("The diagram shows a plant cell. The cell is stained with iodine solution."));
        build.simpleImage(new SimpleImage(bytes));
        build.simpleText(new SimpleText("After staining with iodine solution, what are the colours of the cell wall and the starch grain?"));

        build.getAllUUIDs();

        return build;
    }

}
