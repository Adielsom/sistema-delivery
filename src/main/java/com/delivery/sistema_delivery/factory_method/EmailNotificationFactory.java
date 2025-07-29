
package com.delivery.sistema_delivery.factory_method;

//fábrica específica para criar notificações por e-mail
public class EmailNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}