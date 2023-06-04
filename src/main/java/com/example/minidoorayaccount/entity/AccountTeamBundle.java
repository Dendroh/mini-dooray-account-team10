package com.example.minidoorayaccount.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NamedEntityGraph(name = "bundlesWithAccountAndTeamCode", attributeNodes = {
        @NamedAttributeNode(value = "account"),
        @NamedAttributeNode(value = "teamCode")
})
@Entity
@Table(name = "account_team_bundle")
@Setter
@Getter
@EqualsAndHashCode
public class AccountTeamBundle {
    @EmbeddedId
    private Pk pk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId(value = "accountId")
    @JoinColumn(name = "account_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // account 가 삭제되면 해당 bundle 의 튜플(entity)도 delete cascade
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    @MapsId(value = "teamId")
    @OnDelete(action = OnDeleteAction.CASCADE) // teamCode "
    private TeamCode teamCode;

    @Column(name = "register_date")
    private LocalDateTime registerDate;


    @Getter
    @Setter
    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Pk implements Serializable {
        @Column(name = "team_id")
        private Integer teamId;

        @Column(name = "account_id")
        private Integer accountId;
    }

}
