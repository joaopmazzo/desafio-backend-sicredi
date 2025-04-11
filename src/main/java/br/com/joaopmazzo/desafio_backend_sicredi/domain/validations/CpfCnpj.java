package br.com.joaopmazzo.desafio_backend_sicredi.domain.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {
    String message() default "Documento inválido. Deve ser um CPF ou CNPJ válido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
