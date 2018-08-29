package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/get")
    public JSONObject getCheck(){
        String sql = "select * from check_record";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return CommonUtil.successJson(list);
    }
}
