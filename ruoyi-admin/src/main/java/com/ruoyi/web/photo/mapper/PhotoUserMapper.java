package com.ruoyi.web.photo.mapper;

import com.ruoyi.web.photo.domain.PhotoUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoUserMapper
{
    PhotoUser selectByOpenid(String openid);

    PhotoUser selectById(Long id);

    int insertUser(PhotoUser user);

    int updateUser(PhotoUser user);

    int updateLastLoginTime(@Param("id") Long id);
}
