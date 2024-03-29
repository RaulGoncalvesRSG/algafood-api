package com.algaworks.algafood.infrasctrure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {      //Retorna a URL
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
        //Primeiro param informa qual é o bucket e segundo param qual é caminho do obj no bucket
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getFilename());

            ObjectMetadata metadata = new ObjectMetadata();           //Informações das propriedades de MetaDados do S3
            metadata.setContentType(novaFoto.getContentType());       //Add informação de contentType para ser possível abrir a img no S3 de forma inline
            metadata.setContentLength(novaFoto.getSize());

            //PutObjectRequest (payload para a chamada da API) é usado para submeter para API da Amazon q está sendo feita uma requisição para colocar um obj
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);     //CannedAccessControlList.PublicRead informa q a leitura do arq será pública

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    //Retorna a chave do obj no Bucket
    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
