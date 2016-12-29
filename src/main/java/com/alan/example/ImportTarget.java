package com.alan.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Alan Dávila<br>
 *         e-mail ustargab@gmail.com<br>
 *         27 dic. 2016 20:15
 */
@Getter
@Setter
@ToString
public class ImportTarget implements Serializable {

    private Long serverid;
}
