package com.kaengee.withhobby.service;

import com.kaengee.withhobby.model.Category;
import com.kaengee.withhobby.model.Team;
import com.kaengee.withhobby.model.TeamForm;
import com.kaengee.withhobby.model.User;
import jakarta.transaction.Transactional;

public interface TeamService {

    //동아리 등록
    Team saveTeam(Team team, User user);

    //teamform => team
    Team transToTeam(TeamForm teamForm);

    //동아리 카테고리 이동
    @Transactional
    void updateTeamCategory(Long teamId, Category newCategoryId);
}
