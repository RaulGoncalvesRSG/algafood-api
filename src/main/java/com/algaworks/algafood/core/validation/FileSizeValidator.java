package com.algaworks.algafood.core.validation;

import com.algaworks.algafood.core.validation.annotations.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {  //Anotação @FileSize valida MultipartFile

	private DataSize maxSize;			//Representa um tamanho de dado. È possível convertar tamanho de kb e mb, etc
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null || multipartFile.getSize() <= this.maxSize.toBytes();
	}

}
