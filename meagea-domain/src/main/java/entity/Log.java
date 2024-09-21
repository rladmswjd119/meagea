package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Log implements Serializable{

    public Log(int promotionNo, String body) {
        this.no = (int)(Math.random()*10000);
        this.promotionNo = promotionNo;
        this.body = body;
        this.makeDate = LocalDateTime.now();
    }

    @Id
    private int no;
    private int promotionNo;
    private String body;
    @DateTimeFormat(pattern = "YYYY-mm-dd")
    private LocalDateTime makeDate;
}
