package ir.co.pna.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityRestExceptionHandler {

//	 Add an exception handler for EntityNotFoundException

	@ExceptionHandler
	public ResponseEntity<EntityErrorResponse> handleException(MyEntityNotFoundException exc) {

		// create CustomerErrorResponse

		EntityErrorResponse error = new EntityErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());

		// return ResponseEntity

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	
//	 Add another exception handler ... to catch any exception (catch all)

	@ExceptionHandler
	public ResponseEntity<EntityErrorResponse> handleException(Exception exc) {

		// create CustomerErrorResponse

		EntityErrorResponse error = new EntityErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis());

		// return ResponseEntity

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}





