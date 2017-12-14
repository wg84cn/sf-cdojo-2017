package com.sf.iguess.response;

/**
 * ClassName: JsonResult <br/>
 * Function: 统一返回消息类. <br/>
 * date: 2017年12月14日 下午9:03:51 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public class JsonResult {  
    
    private String code;  
    private String message;  
    private Object data;  
      
    public JsonResult() {  
        this.setCode(ResponseCode.SUCCESS);  
        this.setMessage("成功！");  
    }  
      
    public JsonResult(ResponseCode code) {  
        this.setCode(code);  
        this.setMessage(code.msg());  
    }  
      
    public JsonResult(ResponseCode code, String message) {  
        this.setCode(code);  
        this.setMessage(message);  
    }  
      
    public JsonResult(ResponseCode code, String message, Object data) {  
        this.setCode(code);  
        this.setMessage(message);  
        this.setData(data);  
    }  
      
    public String getCode() {  
        return code;  
    }  
    public void setCode(ResponseCode code) {  
        this.code = code.val();  
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
}  
