package com.example.backed.service;

import com.example.backed.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backed.model.domain.User;
import com.example.backed.model.dto.TeamQuery;
import com.example.backed.model.request.TeamJoinRequest;
import com.example.backed.model.request.TeamQuitRequest;
import com.example.backed.model.request.TeamUpdateRequest;
import com.example.backed.model.vo.TeamUserVO;

import java.util.List;

/**
*
* @author cao
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-03-22 10:24:25
*/
public interface TeamService extends IService<Team> {
    /**
     * 添加队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 展示队伍列表
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 用户加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 用户退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 解散队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);






}
