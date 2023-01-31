package com.fifatoy.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class queryM {
    private String accessId;
    private String nickname;
    private int level;
}
