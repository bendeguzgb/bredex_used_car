package com.bendeguz.usedcar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "advertiser")
public class Advertiser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advertiser_id_generator")
    @SequenceGenerator(name = "advertiser_id_generator", sequenceName = "advertiser_id_seq", allocationSize = 1)
    private Long id;

    @Length(max = 50)
    @NotBlank(message = "Full name must be provided!")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "^.+@.+\\..+$", message = "Invalid email pattern! Should be: 'name@domain.com'")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and be at least 8 characters long.")
    private String password;

    private Instant lastLogout;
}
