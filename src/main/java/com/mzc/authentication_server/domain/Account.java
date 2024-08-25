package com.mzc.authentication_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNT")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "ACCOUNT_ID", length = 30)
    private String accountId;

    @Column(name = "PASS_WD", length = 20)
    private String password;

    @Column(name = "TOKEN", length = 200)
    private String token;

}
