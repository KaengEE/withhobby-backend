package com.kaengee.withhobby.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamForm {

    private String teamname;
    private String teamTitle;
    private User teamHost;
    private String teamImg;
    private String category;
}
