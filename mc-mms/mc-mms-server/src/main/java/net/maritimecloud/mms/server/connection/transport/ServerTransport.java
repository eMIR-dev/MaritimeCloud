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
package net.maritimecloud.mms.server.connection.transport;

import static java.util.Objects.requireNonNull;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCode;
import javax.websocket.Session;

import net.maritimecloud.internal.mms.messages.spi.MmsMessage;
import net.maritimecloud.net.mms.MmsConnectionClosingCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kasper Nielsen
 */
public class ServerTransport {

    /** The logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ServerTransport.class);

    /** non-null while connecting. */
    final ServerTransportListener listener;

    /** The current session. */
    volatile Session wsSession;

    ServerTransport(Session session, ServerTransportListener listener) {
        this.listener = requireNonNull(listener);
        this.wsSession = requireNonNull(session);
    }

    public void close(MmsConnectionClosingCode reason) {
        Session wsSession = this.wsSession;
        if (wsSession != null) {
            CloseReason cr = new CloseReason(new CloseCode() {
                public int getCode() {
                    return reason.getId();
                }
            }, reason.getMessage());

            try {
                wsSession.close(cr);
            } catch (Exception e) {
                LOG.error("Failed to close connection", e);
            }
        }
    }

    void endpointOnClose(CloseReason closeReason) {
        wsSession = null;
        listener.onClose(this,
                MmsConnectionClosingCode.create(closeReason.getCloseCode().getCode(), closeReason.getReasonPhrase()));
    }

    void endpointOnOpen() {
        listener.onOpen(this);
    }

    void endpointOnTextMessage(String textMessage) {
        MmsMessage msg;
        try {
            msg = MmsMessage.parseTextMessage(textMessage);
        } catch (Exception e) {
            LOG.error("Failed to parse incoming message", e);
            close(MmsConnectionClosingCode.WRONG_MESSAGE.withMessage(e.getMessage()));
            return;
        }
        listener.onMessage(this, msg);
    }

    /**
     * Send the specified message with the transport.
     *
     * @param message
     *            the message to send
     */
    public void sendMessage(MmsMessage message) {
        Session wsSession = this.wsSession;
        if (wsSession != null) {
            String textToSend = message.toText();
            // connectionListener.textMessageSend(textToSend);
            wsSession.getAsyncRemote().sendText(textToSend);
        }
    }
}
