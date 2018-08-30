package cn.neu.alpha.utl.constants;

/**
 * @author: CCM 20164969
 * @description: The class to save error type
 */
public enum ErrorEnum {
    /*
    * error message
    * */
    E_400("400", "请求异常，请稍后再试!"),
    E_501("501","参数异常！"),
    E_502("502","缴费金额异常");


    private String errorCode;

    private String errorMsg;

    ErrorEnum() {
    }

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
