package hu.webuni.jms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private long shipmentId;
    private String status;
}