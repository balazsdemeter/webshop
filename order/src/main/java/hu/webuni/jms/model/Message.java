package hu.webuni.jms.model;

import lombok.Data;

@Data
public class Message {
    private long shipmentId;
    private String status;
}