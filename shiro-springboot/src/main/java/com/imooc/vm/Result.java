package com.imooc.vm;

/**
 * 返回的结果对象模型
 */
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功， code=200，带数据
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return new Result(200,"成功",data);
    }

    /**
     * 成功，code=200
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 失败，code=500,带信息
     * @param message
     * @return
     */
    public static Result failure(String message){
        return new Result(500,message,null);
    }
    /**
     * 失败，code=500,
     * @return
     */
    public static Result failure(){
        return failure(null);
    }

    /**
     * 提示，带code，message,data
     * @return
     */
    public static Result info(Integer code,String message,Object data){
        return new Result(code,message,data);
    }

    /**
     * 提示，带code，message
     * @param code
     * @param message
     * @return
     */
    public static Result info(Integer code,String message){
        return info(code,message,null);
    }
}
