package com.example.minidoorayaccount.domain;

import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.time.LocalDateTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class AccountTeamBundleDtoImpl implements AccountTeamBundleDto {
    private AccountTeamBundle.Pk pk;

    private Account account;

    private TeamCode teamCode;

    private LocalDateTime registerDate;

}
