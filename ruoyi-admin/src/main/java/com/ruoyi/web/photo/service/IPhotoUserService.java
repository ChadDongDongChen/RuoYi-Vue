package com.ruoyi.web.photo.service;

import com.ruoyi.web.photo.domain.PhotoUser;

public interface IPhotoUserService
{
    PhotoUser selectByOpenid(String openid);

    PhotoUser selectById(Long id);

    int createUser(PhotoUser user);

    int updateUser(PhotoUser user);

    int updateLastLoginTime(Long id);
}
