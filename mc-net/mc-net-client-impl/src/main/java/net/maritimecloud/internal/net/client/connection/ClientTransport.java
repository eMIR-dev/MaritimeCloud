/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.maritimecloud.internal.net.client.connection;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URI;

import net.maritimecloud.internal.net.messages.ConnectionMessage;
import net.maritimecloud.internal.net.messages.TransportMessage;
import net.maritimecloud.net.ClosingCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Kasper Nielsen
 */
public abstract class ClientTransport {

    /** The logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ClientTransport.class);

    /** The connection that is using the transport. */
    final ClientConnection connection;

    /** non-null while connecting. */
    ClientConnectFuture connectFuture;

    ClientTransport(ClientConnectFuture connectFuture, ClientConnection connection) {
        this.connectFuture = requireNonNull(connectFuture);
        this.connection = requireNonNull(connection);
    }

    abstract void doClose(final ClosingCode reason);

    public void onTextMessage(String textMessage) {
        TransportMessage msg;
        System.out.println("Received: " + textMessage);
        try {
            msg = TransportMessage.parseMessage(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Failed to parse incoming message", e);
            doClose(ClosingCode.WRONG_MESSAGE.withMessage(e.getMessage()));
            return;
        }
        if (connectFuture != null) {
            connectFuture.onMessage(msg);
        } else if (msg instanceof ConnectionMessage) {
            ConnectionMessage m = (ConnectionMessage) msg;
            connection.messageReceive(this, m);
        } else {
            String err = "Unknown messageType " + msg.getClass().getSimpleName();
            LOG.error(err);
            doClose(ClosingCode.WRONG_MESSAGE.withMessage(err));
        }
    }

    public abstract void sendText(String text);

    abstract void connect(URI uri) throws IOException;
}
