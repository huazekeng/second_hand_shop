package com.mrli.second_shop.websocket;

import com.alibaba.fastjson.JSONObject;
import com.mrli.second_shop.entity.ShsFrontChat;
import com.mrli.second_shop.service.ShsFrontChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/chat/{userid}")
@Component
public class ChatWebSocket {

    private  static Map<String, ChatWebSocket> USER_SOCKET = new HashMap<>();
    private  static Map<String, Session> USER_SESSION = new HashMap<>();

    @Autowired
    private static ShsFrontChatService chatService;

    @Autowired
    public void setChatService(ShsFrontChatService chatService) {
        ChatWebSocket.chatService = chatService;
    }

    @OnOpen
    public void open(Session session, @PathParam("userid")String  userid) {
        System.out.println("用户"+userid+" 登录");
        USER_SOCKET.put(userid, this);
        USER_SESSION.put(userid, session);
    }

    @OnMessage
    public void message(Session session, String message, boolean last) throws IOException {
        System.out.println("sessionId:"+session.getId()+"-------"+message+"------"+last);
        // session.getBasicRemote().sendText("接受成功" + message);
        if (!message.equals("hello")) {
            ShsFrontChat chat = new ShsFrontChat();
            JSONObject jsonObject = JSONObject.parseObject(message);
            String userid = jsonObject.getString("sendId");
            String content = jsonObject.getString("content");
            chat.setTalkId(jsonObject.getString("talkId"));
            chat.setGuserId(jsonObject.getString("guserId"));
            chat.setSendId(userid);
            chat.setContent(content);
            chat.setPuchaserId(jsonObject.getString("puchaserId"));
            chatService.save(chat);
            Session sendSession = USER_SESSION.get(userid);
            sendSession.getBasicRemote().sendText(message);
        }
    }



}
