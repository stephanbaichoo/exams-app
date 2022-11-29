package com.exampapers.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class SimpleImage extends BaseEntityAudit{

    @Lob
    @NonNull
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionAnswer questionAnswer;
}
