package com.example.soleproject.dto;

import com.example.soleproject.entity.Scnu;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ScnuForm {
    private Long id;
    private String title;
    private String fesdate;
    private String starttime;
    private String endtime;
    private String location;
    private String info;

    public Scnu toEntity() {
        return new Scnu(id,title,fesdate,starttime,endtime,location,info);
    }
}
