
package com.delivery.sistema_delivery.factory_method;

//implementação de notificação por SMS
public class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando notificação por SMS: " + message);
    }
}