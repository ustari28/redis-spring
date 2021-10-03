package com.alan.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessData implements Serializable {

    public String idTran;
    public String data;
    public Long inputTimestamp;
    public Long processTimestamp;
}
