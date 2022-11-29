package com.exampapers.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RowTableAnswer extends BaseEntityAudit {

    private Character character;

    private String firstColumn;

    private String secondColumn;

    private String thirdColumn;

    @ManyToOne
    private SimpleABCDTable simpleABCDTable;

}
