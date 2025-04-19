package spzx.common.exception;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler
	public Result error(Exception e){
		e.printStackTrace();
		return Result.build(null , ResultCodeEnum.SYSTEM_ERROR) ;
	}

	// 自定义异常处理
	@ExceptionHandler
	public Result guiguException(GuiguException e){
//		e.printStackTrace();
		return Result.build(null , e.getResultCodeEnum()) ;
	}
}