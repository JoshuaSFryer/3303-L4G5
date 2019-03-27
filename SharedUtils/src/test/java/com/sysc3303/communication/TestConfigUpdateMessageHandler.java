package com.sysc3303.communication;

import com.sysc3303.commons.ConfigUpdateHandler;

public class TestConfigUpdateMessageHandler extends TestMessageHandler{

    public TestConfigUpdateMessageHandler(int port){
        super(port);
    }

    public void received(Message message){
        System.out.println("ReceivedMessage");
        switch(message.getOpcode()){
            case 16:
                (new Thread(new ConfigUpdateHandler((ConfigUpdateMessage) message))).start();
                break;
            default:
                System.out.println("Not the Config Update Opcode");
                break;
        }
    }

}
