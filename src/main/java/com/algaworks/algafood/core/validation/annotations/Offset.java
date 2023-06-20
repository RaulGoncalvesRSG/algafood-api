package com.algaworks.algafood.core.validation.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
//A expressão regular do @Pattern valida se segue o padrão: 'sinal' + 'número de 0 a 12 de dois dígitos' + 'dois-pontos' + '00
@Pattern(regexp = "[+-]([0][0-9]|[1][0-2]):00", message = "Padrão de offset inválido.")
public @interface Offset {

    String message() default "offset inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
