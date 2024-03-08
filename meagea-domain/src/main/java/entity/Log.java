package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Log {

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
    private LocalDateTime makeDate;
}
