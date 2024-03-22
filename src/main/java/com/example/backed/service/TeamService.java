package com.example.backed.service;

import com.example.backed.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backed.model.domain.User;

/**
*创建队伍
* @author cao
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-03-22 10:24:25
*/
public interface TeamService extends IService<Team> {
    long addTeam(Team team, User loginUser);
}
