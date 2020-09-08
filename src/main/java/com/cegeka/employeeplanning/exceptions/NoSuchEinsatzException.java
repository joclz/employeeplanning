package com.cegeka.employeeplanning.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchEinsatzException extends RuntimeException
{
    private static final long serialVersionUID = -1469903817218715717L;
}
