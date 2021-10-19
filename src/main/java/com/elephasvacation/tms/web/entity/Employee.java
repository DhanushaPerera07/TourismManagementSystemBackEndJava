/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 26/04/2021
 * @since : 26/04/2021
 * @since : 26/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 26/04/2021
 */
package com.elephasvacation.tms.web.entity;

import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private String address;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private String nic;
    private String contact;
    @Column(unique = true)
    private String email;
    private GenderTypes gender;
    private String position;
    private String status;
//    private String password;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime addedDate;
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    /* custom constructor - without created, lastUpdated. */
    public Employee(String name,
                    String address,
                    Date dateOfBirth,
                    String nic,
                    String contact,
                    String email,
                    GenderTypes gender,
                    String position,
                    String status) {
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.position = position;
        this.status = status;
    }
}
