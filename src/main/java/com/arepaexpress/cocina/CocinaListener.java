package com.arepaexpress.cocina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CocinaListener {

    private static final Logger log = LoggerFactory.getLogger(CocinaListener.class);
    private final RabbitTemplate rabbit;

    public CocinaListener(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = "cocina.pedidos")
    public void preparar(PedidoCreadoEvent pedido) throws InterruptedException {
        log.info("🔥 Preparando {} para {} (pedido {})", pedido.arepas(), pedido.cliente(), pedido.pedidoId());
        Thread.sleep(3000);
        log.info("✅ Pedido {} listo", pedido.pedidoId());
        rabbit.convertAndSend("arepas.eventos", "pedido.listo", pedido);
    }
}
