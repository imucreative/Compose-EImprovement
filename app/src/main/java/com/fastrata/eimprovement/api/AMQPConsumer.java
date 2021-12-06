package com.fastrata.eimprovement.api;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.fastrata.eimprovement.R;
import com.fastrata.eimprovement.di.ThisApplication;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AMQPConsumer {
    String host;
    int port;
    String userName;
    String userPassword;
    String vHost;
    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    AMQPConsumerListener listener;
    String consumerTag;
    Context context;
    NotificationManager notificationManager;
    Notification.Builder builder;


    public AMQPConsumer(String host,int port,String userName,String userPassword,String vHost,String consumerTag,Context context){
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        this.vHost = vHost;
        this.consumerTag = consumerTag;
        this.context = context;

        factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setPort(this.port);
        factory.setUsername(this.userName);
        factory.setPassword(this.userPassword);
        factory.setVirtualHost(this.vHost);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                String message = msg.getData().getString("message");
                if (listener != null)
                    listener.onReceive("", message);
                else
                    showNotify(message);
            }
            else {
                if (listener != null)
                    listener.onCancel();
            }
            return false;
        }
    });

    public void start(String queueName){
        this.listener = listener;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            DeliverCallback deliverCallback = new DeliverCallback() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                  String message = new String((delivery.getBody()), StandardCharsets.UTF_8);
                  Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("id","");
                    bundle.putString("message",message);
                    msg.arg1 = 1;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            };
            CancelCallback cancelCallback = new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 2;
                    handler.sendMessage(msg);
                }
            };
            channel.queueDeclare(queueName,true,false,false,null);
            channel.basicConsume(queueName,true,consumerTag,deliverCallback,cancelCallback);
        }catch (Exception e){
            if (listener != null)
                listener.onError(e.toString());
        }
    }

    public boolean isConnected() {
        try {
            return channel.isOpen();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stop() {
        try {
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api =  Build.VERSION_CODES.O)
    void showNotify(String message){
        Random random = new Random();
        Integer channelId = random.nextInt();

        Intent fullScreenIntent = new Intent(context, ThisApplication.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(context, ThisApplication.class);
        intent.putExtra("message", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivities(
                context,
                0,
                new Intent[]{intent},
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channelId.toString(),
                    message,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder = builder
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Eimprovement")
                .setTicker("Eimprovement")
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(random.nextInt(),builder.build());
    }

}
