package com.ruoyi.web.photo.service;

import java.util.List;
import com.ruoyi.web.photo.domain.PhotoSpec;

public interface IPhotoSpecService
{
    List<PhotoSpec> selectEnabledSpecs();

    PhotoSpec selectSpecById(Long id);
}
