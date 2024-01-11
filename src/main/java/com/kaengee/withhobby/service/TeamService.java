package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.TeamForm;
import com.kaengee.withhobby.model.User;

public interface TeamService {

    //동아리 등록
    Team saveTeam(Team team, User loggedInUser);

    //teamform => team
    Team transToTeam(TeamForm teamForm);
}
