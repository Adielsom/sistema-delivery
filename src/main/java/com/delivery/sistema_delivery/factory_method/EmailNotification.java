
package com.delivery.sistema_delivery.factory_method;

//implementação de notificação por e-mail
public class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando notificação por E-mail: " + message);
    }
}