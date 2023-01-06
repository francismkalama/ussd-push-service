package com.kcbgroup.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Channel",
        uniqueConstraints = {
                @UniqueConstraint(name = "channel_channelname_unique",columnNames = "channel_name")
        }
)
public class Channel {
    @Id
    @SequenceGenerator(
            name = "channel_sequence",
            sequenceName = "channel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "channel_sequence"
    )
    private Integer Id;
    @Column(
            name = "channel_name",
            nullable = false
    )
    private String channelName;
    @Column(
            name = "channel_status",
            nullable = false
    )
    private String channelStatus;
}
