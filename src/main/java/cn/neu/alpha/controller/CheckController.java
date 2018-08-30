package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    public JSONObject getCheck() {
        String sql = "select * from check_record";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return CommonUtil.successJson(list);
    }

    @PostMapping("/add")
    public JSONObject addCheck(@RequestBody JSONObject requestJson) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "call record(" + requestJson.getInteger("equipmentId") + "," + requestJson.getFloat("degree") + ",'" + dateFormat.format(requestJson.getDate("time")) + "','" + requestJson.getString("recorder") + "');";
        System.out.println(sql);
        jdbcTemplate.execute(sql);
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("checkRecord", jdbcTemplate.queryForList("select * from check_record"));
        return CommonUtil.successJson(jsonObject);
    }
}
