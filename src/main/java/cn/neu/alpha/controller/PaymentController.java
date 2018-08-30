package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/get")
    public JSONObject getPayment(){
        String sql = "select * from payment_record join serial on payment_record.serial_number=serial.serial_id natural join bank;";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return CommonUtil.successJson(list);
    }

    @GetMapping("/serial")
    public JSONObject getSerial(){
        String sql = "select serial_id from payment_record join serial on payment_record.serial_number=serial.serial_id where is_edit = 0 and date(time) = date(now());";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return CommonUtil.successJson(list);
    }

    @PostMapping("/recovery")
    public JSONObject recovery(@RequestBody JSONObject jsonObject){
        String sql = "call error_recovery("+jsonObject.getInteger("serialId")+","+jsonObject.getInteger("equipmentId")+");";
        jdbcTemplate.execute(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from payment_record join serial on payment_record.serial_number=serial.serial_id natural join bank;");
        return CommonUtil.successJson(list);
    }
}
