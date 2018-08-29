package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.JDBCType;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JSONArray getEquipmentAmountAndDebit(List list){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObject.put("equipment_amount", jdbcTemplate.queryForObject("select ifnull(count(equipment_id),0) from equipment where user_id = " + jsonObject.getInteger("user_id") + ";", Integer.class));
            jsonObject.put("debt", jdbcTemplate.queryForObject("select ifnull(sum(amount),0) from arrear_record where equipment_id in (select equipment_id from equipment where user_id = " + jsonObject.getInteger("user_id") + ");", float.class));
            jsonArray.set(i, jsonObject);
        }
        return jsonArray;
    }

    @GetMapping("/id")
    public JSONObject getUserId() {
        String sql = "select user_id from user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return CommonUtil.successJson(list);
    }

    @GetMapping("/get")
    public JSONObject getUser() {
        String sql = "select * from user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        return CommonUtil.successJson(getEquipmentAmountAndDebit(list));
    }

    @GetMapping("/balance")
    public JSONObject getBalance() {
        String sql = "select * from balance_record";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        return CommonUtil.successJson(getEquipmentAmountAndDebit(list));
    }

    @PostMapping("/add")
    public JSONObject addUser(@RequestBody JSONObject requestJson){
        String sql = "insert user (name,address,balance) values ('" +requestJson.getString("name") +"','"+ requestJson.getString("address")+"',"+ requestJson.getFloat("balance")+")";
        jdbcTemplate.update(sql);
        int id = jdbcTemplate.queryForObject("select last_insert_id()",Integer.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("user",getEquipmentAmountAndDebit(jdbcTemplate.queryForList("select * from user")));

        return CommonUtil.successJson(jsonObject);
    }
}
