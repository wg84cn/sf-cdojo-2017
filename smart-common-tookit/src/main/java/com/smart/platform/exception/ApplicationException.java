package com.smart.platform.exception;

public class ApplicationException extends RuntimeException
{
    
    /** @Fields serialVersionUID: */
    private static final long serialVersionUID = 1L;
    
    private int errorCode;
    
    private String errorMsg;
    
    public ApplicationException()
    {
        
    }
    
    public ApplicationException(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public int getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}
