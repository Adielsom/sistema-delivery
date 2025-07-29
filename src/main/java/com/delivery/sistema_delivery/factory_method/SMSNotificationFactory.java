
package com.delivery.sistema_delivery.factory_method;

//fábrica específica para criar notificações por SMS
public class SMSNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return new SMSNotification();
    }
}