package com.boco.TONY.auth.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * ��Ȩ�쳣����
 *
 * @author tony
 * @describe DefaultExceptionHandler
 * @date 2019-09-11
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    /**
     * û��Ȩ�� �쳣
     * <p/>
     * �������ݲ�ͬ�������Ƽ���
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        //��������쳣Ӧ��չʾ�Ľ���
        mv.setViewName("unauthorized");
        return mv;
    }
}
