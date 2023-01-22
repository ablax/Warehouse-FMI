package me.ablax.warehouse.models;

import lombok.Data;

@Data
public class EmailDetails {
 
    private String recipient;
    private String recipientName;
    private String subject;
    private String msgBody;

}