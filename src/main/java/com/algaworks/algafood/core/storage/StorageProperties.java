package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

	private Local local = new Local();
	private S3 s3 = new S3();
	private TipoStorage tipo = TipoStorage.LOCAL;		//Propriedade q identifica o tipo de Storage q será usado. Se n configurar o tipo, o padrão será LOCAL
	
	public enum TipoStorage {
		LOCAL, S3
	}
	
	@Getter
	@Setter
	public class Local {					//Representa algafood.storage.local
		private Path diretorioFotos; 		//O spring consegue fazer o bind: algafood.storage.local.diretorio-fotos. O spring converte o - na letra maiúscula
	}
	
	@Getter
	@Setter
	public class S3 {
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		private Regions regiao;				//Tipo Regions do SDK da Amazon consegue converter a str do properties. Regions é um Enum
		private String diretorioFotos;
	}
}
