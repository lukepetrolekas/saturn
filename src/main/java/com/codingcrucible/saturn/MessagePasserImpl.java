package com.codingcrucible.saturn;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MessagePasserImpl<E> implements MessagePasser<E> {

    boolean echo;
    List<MessageConsumer> clients;

    public MessagePasserImpl(boolean echo) {
        this.echo = echo;
        clients = new CopyOnWriteArrayList<>();
    }

    public MessagePasserImpl(MessageConsumer c, boolean echo) {
        this.echo = echo;
        clients = new CopyOnWriteArrayList<>();
        clients.add(c);
    }

    public List<MessageConsumer> getClients() {
        return clients;
    }

    @Override
    public void sendToAll(Message<E> m) {
        for (MessageConsumer c : clients)
            c.consume(m);
    }

    /** If echo is set to true, echo message to all Nodes except for
     * the specified excluded Nodes
     * @param exclude
     * @param m 
     */
    @Override
    public void echoToAll(MessageConsumer<E> exclude, Message<E> m) {
        if (echo)
            for (MessageConsumer<E> c : clients)
                if (c.getGuid() != exclude.getGuid())
                    c.consume(m);
    }

}
