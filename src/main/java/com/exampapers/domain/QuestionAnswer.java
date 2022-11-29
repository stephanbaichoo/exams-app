package com.exampapers.domain;

import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Table(name = "question_answer")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "question_answer_id"))
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer extends BaseEntityAudit {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_answer_id")
    @ToString.Exclude
    private List<SimpleText> simpleTexts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_answer_id")
    @ToString.Exclude
    private List<SimpleImage> simpleImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_answer_id")
    @ToString.Exclude
    private List<SimpleABCDTable> simpleABCDTables = new ArrayList<>();

    @ElementCollection
    private Set<UUID> componentOrder = new HashSet<>();

    public void simpleText(SimpleText simpleText) {
        simpleTexts.add(simpleText);
        componentOrder.add(simpleText.getId());
    }

    public void simpleImage(SimpleImage simpleImage) {
        simpleImages.add(simpleImage);
        componentOrder.add(simpleImage.getId());
    }

    public void simpleABCDTable(SimpleABCDTable simpleABCDTable) {
        simpleABCDTables.add(simpleABCDTable);
        componentOrder.add(simpleABCDTable.getId());
    }

    public LinkedHashMap<UUID, Class<? extends BaseEntity>> getAllUUIDs(PropertyDescriptor[] propertyDescriptors) {
        LinkedHashMap<UUID, Class<? extends BaseEntity>> hashMap = new LinkedHashMap<>();

        Arrays.stream(propertyDescriptors)
                .map(PropertyDescriptor::getReadMethod)
                .forEach(method -> {
                    try {
                        Class<?> declaringClass = method.getReturnType();
                        if (List.class.isAssignableFrom(declaringClass)) {
                            List<? extends BaseEntity> list = (List<? extends BaseEntity>) method.invoke(this);

                            list.forEach(x ->
                                            hashMap.put(((BaseEntity) x).getId(), (Class<? extends BaseEntity>) x.getClass())
                                    );
                        }
                        if (BaseEntity.class.isAssignableFrom(declaringClass) && !declaringClass.equals(this.getClass())) {
                            BaseEntity object = (BaseEntity) declaringClass.cast(method.invoke(this));
                            hashMap.put(object.getId(), (Class<? extends BaseEntity>) declaringClass);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });

        return hashMap;

    }


    public LinkedHashMap<UUID, Class<? extends BaseEntity>> getAllUUIDs() {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(this.getClass());

        return getAllUUIDs(propertyDescriptors);
    }

}
