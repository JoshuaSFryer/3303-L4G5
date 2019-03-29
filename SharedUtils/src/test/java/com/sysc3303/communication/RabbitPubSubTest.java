package com.sysc3303.communication;

import com.sysc3303.commons.ConfigListener;
import org.junit.Test;

import java.util.Properties;

import static java.lang.Thread.sleep;

public class RabbitPubSubTest {

    @Test
    public void sender1_receiver3(){
        for(int i =0; i<3; i++){
            new Thread(
                    new RabbitSubscriber(
                            new TestMessageHandler(2000 + i),
                            "The Exchange"
                    )
            ).start();
            ;
        }

        new RabbitPublisher(
                "The Exchange",
                new ConfigUpdateMessage(
                        new Properties()
                )
        ).run();
        try{
            sleep(1000);
        } catch (InterruptedException e){

        }
    }


    @Test
    public void config_listener_flow(){
        ConfigListener listener = new ConfigListener();
        new Thread(listener).start();

        try{
            sleep(2000);
        } catch (InterruptedException e){
        }

        new RabbitPublisher(
                "The Exchange",
                new ConfigUpdateMessage(
                        new Properties()
                )
        ).run();

        try{
            sleep(2000);
        } catch (InterruptedException e){
        }
    }
}
