package com.algaworks.osworks.api.exptionhandle;

import com.algaworks.osworks.domain.exception.NegocioException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errosValidacao = new ArrayList<MensagemValidacao>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            var mensagemValidacao = MensagemValidacao.builder()
                    .campo(((FieldError) error).getField())
                    .mensagem(messageByError(error))
                    .build();

            errosValidacao.add(mensagemValidacao);
        }

        var problema = Problema.builder()
                .status(status.value())
                .titulo("Um ou mais campos estão inválidos")
                .dataHora(OffsetDateTime.now())
                .errosValidacao(errosValidacao).build();

        return super.handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException negocioException, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        var problema = Problema.builder()
                .dataHora(OffsetDateTime.now())
                .titulo(negocioException.getMessage())
                .status(status.value()).build();

        return super.handleExceptionInternal(negocioException, problema, new HttpHeaders(), status, request);
    }

    private String messageByError(MessageSourceResolvable error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
