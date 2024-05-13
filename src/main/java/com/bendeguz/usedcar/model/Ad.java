package com.bendeguz.usedcar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_id_generator")
    @SequenceGenerator(name = "ad_id_generator", sequenceName = "ad_id_seq", allocationSize = 1)
    private Long id;

    @Length(max = 20)
    private String brand;

    @Length(max = 20)
    private String type;

    @Length(max = 200)
    private String description;

    @Min(value = 0)
    @Digits(integer = 10, fraction = 0)
    private Long price;

    @ManyToOne
    private Advertiser created_by;
}
