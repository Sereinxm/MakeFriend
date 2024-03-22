package com.example.backed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backed.model.domain.UserTeam;
import com.example.backed.service.UserTeamService;
import com.example.backed.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author cao
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-03-22 10:25:23
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




