/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.za.absa.TestDataDashBoard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author ABKS580
 */

@ControllerAdvice
public class DataNotFoundExceptionMapper {
@ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> dataNotFound(DataNotFoundException dd)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(dd.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

}
