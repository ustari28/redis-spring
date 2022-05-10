package com.alan.example;

import java.io.Serializable;

public record BusinessData(String idTran, String data, Long inputTimestamp) implements Serializable {
}
