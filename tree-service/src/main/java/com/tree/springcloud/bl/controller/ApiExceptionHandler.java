package com.tree.springcloud.bl.controller;

import com.google.common.base.VerifyException;
import com.tree.springcloud.bl.dto.ResultResponseEntity;
import com.tree.springcloud.bl.service.exception.AlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.UnsupportedEncodingException;


@ResponseBody
@ControllerAdvice
public class ApiExceptionHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * @param e build error response if has been UnsupportedEncodingException
     *
     * @return ResultResponseEntity
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResultResponseEntity handleInvalidEncoding(UnsupportedEncodingException e)
    {
        return buildResponse(100, e);
    }

    /**
     * @param e build error response if has been IllegalArgumentException
     *
     * @return ResultResponseEntity
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultResponseEntity handleBadArgumentException(IllegalArgumentException e)
    {
        return buildResponse(101, e);
    }

    /**
     * @param e build error response if has been AlreadyExistsException
     *
     * @return ResultResponseEntity
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public ResultResponseEntity handleBadArgumentException(AlreadyExistsException e)
    {
        return buildResponse(101, e);
    }


    /**
     * @param e build error response if has been VerifyException
     *
     * @return ResultResponseEntity
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VerifyException.class)
    public ResultResponseEntity handleValidateException(VerifyException e)
    {
        return buildResponse(102, e);
    }

    /**
     * @param e build error response if has been any other
     *
     * @return ResultResponseEntity
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResultResponseEntity handleAllOtherExceptions(Exception e)
    {
        return buildResponse(500, e);
    }

    private ResultResponseEntity buildResponse(Integer code, Throwable e)
    {
        if (e instanceof NestedRuntimeException)
        {
            e = ((NestedRuntimeException) e).getMostSpecificCause();
        }

        LOG.error(e.getMessage(), e);
        return new ResultResponseEntity(code, e.getMessage());
    }
}
