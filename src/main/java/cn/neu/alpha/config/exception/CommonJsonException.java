package cn.neu.alpha.config.exception;


import cn.neu.alpha.utl.CommonUtil;
import cn.neu.alpha.utl.constants.ErrorEnum;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: CCM 20164969
 * @description: An Exception class
 * When there is a exception has been throw,the class can send a error msg to front end.
 */
public class CommonJsonException extends RuntimeException {
    private JSONObject resultJson;

    public CommonJsonException(ErrorEnum errorEnum) {
        this.resultJson = CommonUtil.errorJson(errorEnum);
    }

    public CommonJsonException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }
}
