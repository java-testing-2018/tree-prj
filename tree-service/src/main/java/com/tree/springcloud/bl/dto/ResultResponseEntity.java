package com.tree.springcloud.bl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * General DTO reponse for output
 */
public class ResultResponseEntity
{
    @JsonProperty("code")
    private int _code;

    @JsonProperty("message")
    private String _message;

    public ResultResponseEntity()
    {
        _code = 0;
        _message = "ok";
    }

    public ResultResponseEntity(Integer code, String message)
    {
        this._code = code;
        this._message = message;
    }

    public int getCode()
    {
        return _code;
    }

    public void setCode(int code)
    {
        this._code = code;
    }

    public String getMessage()
    {
        return _message;
    }

    public void setMessage(String message)
    {
        this._message = message;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        ResultResponseEntity that = (ResultResponseEntity) o;

        return Objects.equals(_code, that._code)
               && Objects.equals(_message, that._message);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), _code, _message);
    }
}
