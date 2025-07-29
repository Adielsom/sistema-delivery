
package com.delivery.sistema_delivery.factory_method;

import com.delivery.sistema_delivery.factory_method.Notification;
import com.delivery.sistema_delivery.factory_method.NotificationFactory;
import com.delivery.sistema_delivery.factory_method.EmailNotificationFactory;
import com.delivery.sistema_delivery.factory_method.SMSNotificationFactory;

public class FactoryMethodDemo {

    public static void main(String[] args) {
        System.out.println("---Padrão Factory Method ---");

        //usando a fábrica de E-mail
        NotificationFactory emailFactory = new EmailNotificationFactory();
        Notification emailNotification = emailFactory.createNotification();
        emailNotification.send("Seu pedido #123 foi recebido!");

        System.out.println("----------------------------------------------");

        //usando a fábrica de SMS
        NotificationFactory smsFactory = new SMSNotificationFactory();
        Notification smsNotification = smsFactory.createNotification();
        smsNotification.send("Seu pedido #123 está a caminho!");

        System.out.println("----------------------------------------------");

        // apenas instanciando uma nova fábrica.
        System.out.println("Conceito: fácil adicionar novos tipos de notificação.");
    }
}