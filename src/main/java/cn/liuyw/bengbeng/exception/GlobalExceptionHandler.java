package cn.liuyw.bengbeng.exception;

import cn.liuyw.bengbeng.bean.Result;
import cn.liuyw.bengbeng.utils.StringUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.metadata.ConstraintDescriptor;
import java.text.MessageFormat;
import java.util.Set;

/**
 * Created by wujunjie on 2017-06-21.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result processBusinessException(BusinessException e) {
        String code = e.getCode();
        logger.error("biz error msg --->{}", code);
        String message = "";
        if (StringUtil.isNotEmpty(e.getExtMsg())) {
//            message = MessageFormat.format(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()), e.getExtMsg());
            message = messageSource.getMessage(code, new Object[]{e.getExtMsg()}, LocaleContextHolder.getLocale());
        } else {
            message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        }
        return new Result(false, code, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public Result processBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().get(0);

        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            String code;
            switch (fieldError.getCode()) {
                case "NotBlank":
                case "NotEmpty":
                case "NotNull":
                    code = "Validation.Param.NotNull";
                    break;
                case "Pattern":
                    code = "Validation.Param.NotPass";
                    break;
                case "Range":
                    code = "Validation.Param.Wrong";
                    break;
                default:
                    code = "Validation.Param.Fail";
                    break;
            }

            String message = "";
            if (!StringUtil.isEmpty(code)) {
                message = MessageFormat.format(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()), fieldError.getField());
            }
            return new Result(false, code, message);
        }
        return Result.error("Validation.Param.Error");
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result processConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        String code;
        if (descriptor.getAnnotation() instanceof NotBlank ||
                descriptor.getAnnotation() instanceof NotEmpty ||
                descriptor.getAnnotation() instanceof NotNull) {
            code = "Validation.Param.NotNull";
        } else if (descriptor.getAnnotation() instanceof Pattern) {
            code = "Validation.Param.NotPass";
        } else if (descriptor.getAnnotation() instanceof Range) {
            code = "Validation.Param.Wrong";
        } else {
            code = "Validation.Param.Fail";
        }

        String message = "";
        if (!StringUtil.isEmpty(code)) {
            message = MessageFormat.format(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()), "参数");
        }
        return new Result(false, code, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result processException(Exception e) {
        e.printStackTrace();
        logger.error("Returning HTTP 500 ，Exception Error Message --->{}", e.getMessage());
        return Result.error("Sys.Error.Unknow");
    }
}
