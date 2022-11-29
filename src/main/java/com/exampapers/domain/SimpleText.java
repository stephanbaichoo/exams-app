package com.exampapers.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class SimpleText extends BaseEntityAudit {

    @NonNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionAnswer questionAnswer;

}
