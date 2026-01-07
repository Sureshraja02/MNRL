package com.fisglobal.fsg.dip.core.expection;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Maps;

@ControllerAdvice
public class DefaultExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpRequestMethodNotSupportedException.class,
			UnsatisfiedServletRequestParameterException.class,
			ServletRequestBindingException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody Map<String, Object> handleRequestException(Exception ex) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Request Error");
		map.put("cause", ex.getMessage());
		return map;
	}
	
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
	public @ResponseBody Map<String, Object> ResourceNotFoundException(Exception ex) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Request Error");
		map.put("cause", ex.getMessage());
		return map;
	}
	

	@ExceptionHandler(RMSException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Map<String, Object> handleImpsTxnException(RMSException ex) throws IOException {

		Map<String, Object> map = Maps.newHashMap();
		map.put("error", ex.getCode());
		map.put("cause", ex.getMessage());
		return map;

		/*
		 * String errorCode = impsErrors.getMessage(ex.getCode(), null,
		 * ImpsErrors.ERROR_CODE_96, Locale.US); respVo.setRespCode(errorCode);
		 * respVo.setMsgId(impsTxnVo.getMsgId()); respVo.setRrn(impsTxnVo.getRrn());
		 * respVo.setStan(impsTxnVo.getStan()); respVo.setTxnId(impsTxnVo.getTxnId());
		 * respVo.setAuthCode(impsTxnVo.getAuthCode());
		 * 
		 * impsTxnVo.setTotalElaps(totalendtime - impsTxnVo.getTotalElaps());
		 * LOGGER.info(
		 * "eventType [IMPSTransaction] rrn [{}] Status [{}] txnType [{}] npciRespTime [{}] cbsRespTime [{}] respTime [{}] respCode [{}] npciRespCode [{}] cbsRespCode [{}] benIFSC [{}] extType [{}] procode [{}]"
		 * , impsTxnVo.getRrn(), impsTxnVo.getTxnStatus(), impsTxnVo.getTxnType(),
		 * impsTxnVo.getNpciElaps(), impsTxnVo.getCbsElaps(), impsTxnVo.getTotalElaps(),
		 * errorCode, impsTxnVo.getNpciRespCode(), impsTxnVo.getCbsRespCode(),
		 * impsTxnVo.getBenisfc(), impsTxnVo.getExtnFlag(), impsTxnVo.getpCode());
		 * return respVo;
		 */
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
			throws IOException {
		final String message = getFieldErrorAsString(ex.getBindingResult().getFieldErrors());

		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "101");
		map.put("object", ex.getBindingResult().getTarget());
		map.put("message", ex.getBindingResult().getFieldErrors().stream().map(m -> {
			return m.getCode() + "." + m.getField();
		}).toArray());

		LOGGER.info("Validation Error error [{}], Request [{}], Error message [{}]", map.get("error"),
				map.get("object"), message);
		return map;
	}

	private String getFieldErrorAsString(List<FieldError> fieldErrors) {

		StringBuilder fieldErrorBuilder = new StringBuilder(50);
		for (Iterator<FieldError> iterator = fieldErrors.iterator(); iterator.hasNext();) {
			FieldError fieldError = iterator.next();
			fieldErrorBuilder.append('{').append(fieldError.getCode()).append('}');
		}

		return fieldErrorBuilder.toString();

	}
	
	
	
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Map<String, Object> handleDataAccessException(DataAccessException ex) throws IOException {
		LOGGER.error("Unhandled data access exception caught!!! ", ex);
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Data Error");
		map.put("cause", ex.getCause().getMessage());
		return map;
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody Map<String, Object> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex)
			throws IOException {
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Unsupported Media Type");
		map.put("cause", ex.getLocalizedMessage());
		map.put("supported", ex.getSupportedMediaTypes());
		return map;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Map<String, Object> handleUncaughtException(Exception ex) throws IOException {
		LOGGER.error("Unhandled exception caught!!! ", ex);
		Map<String, Object> map = Maps.newHashMap();
		map.put("error", "Unknown Error");
		if (ex.getCause() != null) {
			map.put("cause", ex.getCause().getMessage());
		} else {
			map.put("cause", ex.getMessage());
		}
		return map;
	}

	
	
	/*@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Map<String, String> handleNpciException(HttpMessageNotReadableException ex) {
		Map<String, String> map = Maps.newHashMap();
		map.put("error", RMSConstants.INVALID_DIGITAL_SIGNATURE_CODE);
		map.put("message", RMSConstants.INVALID_DIGITAL_SIGNATURE_MSG);
		return map;
	}*/

}
