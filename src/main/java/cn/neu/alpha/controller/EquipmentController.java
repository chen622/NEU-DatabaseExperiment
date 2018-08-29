package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/get")
    public JSONObject getEquipment(){
        String sql = "select * from equipment natural join user";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return CommonUtil.successJson(list);
    }

    @PostMapping("/add")
    public JSONObject addEquipment(@RequestBody JSONObject requestJson){
        String sql = "insert equipment (user_id,category) values (" +requestJson.getInteger("userId") +",'"+ requestJson.getString("category")+"')";
        jdbcTemplate.update(sql);
        int id = jdbcTemplate.queryForObject("select last_insert_id()",Integer.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("equipments",jdbcTemplate.queryForList("select * from equipment natural join user"));
        return CommonUtil.successJson(jsonObject);
    }

}
