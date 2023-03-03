package com.example.project.mapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.Map;


@Mapper
public interface OrderMapper {

    Map<String, Object>orderList(Map<String, Object> param);
}
