package com.ruoyi.web.photo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.photo.mapper.PhotoSpecMapper;
import com.ruoyi.web.photo.domain.PhotoSpec;
import com.ruoyi.web.photo.service.IPhotoSpecService;

@Service
public class PhotoSpecServiceImpl implements IPhotoSpecService
{
    @Autowired
    private PhotoSpecMapper photoSpecMapper;

    @Override
    public List<PhotoSpec> selectEnabledSpecs()
    {
        return photoSpecMapper.selectEnabledSpecs();
    }

    @Override
    public PhotoSpec selectSpecById(Long id)
    {
        return photoSpecMapper.selectSpecById(id);
    }
}
