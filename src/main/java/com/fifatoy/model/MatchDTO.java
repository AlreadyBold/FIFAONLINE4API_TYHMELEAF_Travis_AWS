package com.fifatoy.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class MatchDTO {
    private String matchId;
    private String matchDate;
    private int mathcType;
    private Object[] matchInfo;
}
