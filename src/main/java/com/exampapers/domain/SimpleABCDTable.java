package com.exampapers.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SimpleABCDTable extends BaseEntityAudit {

    @OneToMany
    List<RowTableAnswer> rows;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionAnswer questionAnswer;

}
