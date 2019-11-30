package com.mrphayp.groupchatroom.config;

import com.alibaba.fastjson.JSON;
import com.mrphayp.groupchatroom.model.Message;
import org.springframework.stereotype.Component;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat")
public class WebSocketServer {
    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static HashMap<String, String> users = new HashMap<>();

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String user) {

        onlineSessions.put(session.getId(), session);
        // Getting username through path param.
        users.put(session.getId(), user);
        sendMessageToAll(Message.strToJson("ENTERED THE CHAT", user, onlineSessions.size(), "ENTER"));
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO: add send message.
        Message message = JSON.parseObject(jsonStr, Message.class);
        sendMessageToAll(Message.strToJson(message.getMsg(), message.getUsername(), onlineSessions.size(), "SPEAK"));
    }

    private static void sendMessageToAll(String msg) {
        //TODO: add send message method.
        onlineSessions.forEach((id, session) -> {
            try{
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
