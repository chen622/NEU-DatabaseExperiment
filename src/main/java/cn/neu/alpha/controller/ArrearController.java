package cn.neu.alpha.controller;

import cn.neu.alpha.utl.CommonUtil;
import cn.neu.alpha.utl.constants.ErrorEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/arrear")
public class ArrearController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/get")
    public JSONObject getArrear() {
        String sql = "select * from arrear_record";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return CommonUtil.successJson(list);
    }

    @GetMapping("/refresh")
    public JSONObject refresh(){
        String sql = "call calculate()";
        jdbcTemplate.execute(sql);
        return CommonUtil.successJson();
    }

    @PostMapping("/pay")
    public JSONObject pay(@RequestBody JSONObject requestJsonObject){
        String param = (String) jdbcTemplate.execute(
                new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
                        String storedProc = "{call pay (?,?,?,?,?)}";// 调用的sql
                        CallableStatement cs = con.prepareCall(storedProc);
                        cs.setInt(1, requestJsonObject.getInteger("equipmentId"));// 设置输入参数的值
                        cs.setFloat(2, requestJsonObject.getFloat("amount"));// 设置输入参数的值
                        cs.setInt(3, requestJsonObject.getInteger("bankId"));// 设置输入参数的值
                        cs.setInt(4, requestJsonObject.getInteger("bankUserId"));// 设置输入参数的值
                        cs.registerOutParameter(5,Types.VARCHAR);// 注册输出参数的类型
                        return cs;
                    }
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getString(5);// 获取输出参数的值
                    }
                });
        if (param.equals("Fail")){
            return CommonUtil.errorJson(ErrorEnum.E_501);
        }else if(param.equals("Fail2")) {
            return CommonUtil.errorJson(ErrorEnum.E_502);
        }else{
            int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("arrearRecord", jdbcTemplate.queryForList("select * from arrear_record"));
            return CommonUtil.successJson(jsonObject);
        }
    }
}
