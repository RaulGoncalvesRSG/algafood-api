package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@AllArgsConstructor
@Component      //Uma classe pode ter mais de um EventListener
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    //@EventListener informa q o método irá escutar evento do parâmetro qnd for disparado
    /*@TransactionalEventListener - pode especificar qual é a fase específica (parâmetro pashe = TransactionPhase.AFTER_COMMIT) da transação q o
    evento deve ser disparado. Por padrão, o evento é disparado depois q a transação é commitada, o @EventListener não garante isso*/
    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        Mensagem mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
