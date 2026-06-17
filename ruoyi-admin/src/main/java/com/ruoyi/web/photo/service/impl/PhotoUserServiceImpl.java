package com.ruoyi.web.photo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.photo.mapper.PhotoUserMapper;
import com.ruoyi.web.photo.domain.PhotoUser;
import com.ruoyi.web.photo.service.IPhotoUserService;

@Service
public class PhotoUserServiceImpl implements IPhotoUserService
{
    @Autowired
    private PhotoUserMapper photoUserMapper;

    @Override
    public PhotoUser selectByOpenid(String openid)
    {
        return photoUserMapper.selectByOpenid(openid);
    }

    @Override
    public PhotoUser selectById(Long id)
    {
        return photoUserMapper.selectById(id);
    }

    @Override
    public int createUser(PhotoUser user)
    {
        return photoUserMapper.insertUser(user);
    }

    @Override
    public int updateUser(PhotoUser user)
    {
        return photoUserMapper.updateUser(user);
    }

    @Override
    public int updateLastLoginTime(Long id)
    {
        return photoUserMapper.updateLastLoginTime(id);
    }
}
