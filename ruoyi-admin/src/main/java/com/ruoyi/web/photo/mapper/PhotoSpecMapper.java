package com.ruoyi.web.photo.mapper;

import java.util.List;
import com.ruoyi.web.photo.domain.PhotoSpec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoSpecMapper
{
    List<PhotoSpec> selectEnabledSpecs();

    PhotoSpec selectSpecById(Long id);

    int insertSpec(PhotoSpec spec);

    int updateSpec(PhotoSpec spec);

    int deleteSpecById(Long id);
}
