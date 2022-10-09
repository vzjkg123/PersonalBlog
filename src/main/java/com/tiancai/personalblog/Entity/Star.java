package com.tiancai.personalblog.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Star {
    Integer page;
    String email;
    Star(Integer page){
        this.page=page;
    }
}
