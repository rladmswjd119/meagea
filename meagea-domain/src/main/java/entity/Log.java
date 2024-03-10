package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Log {

    public Log(int promotionNo, String body) {
        this.no = (int)(Math.random()*10000);
        this.promotionNo = promotionNo;
        this.body = body;
        this.makeDate = LocalDateTime.now();
        this.remove = 0;
    }

    @Id
    private int no;
    private int promotionNo;
    private String body;
    private LocalDateTime makeDate;
    private int remove;

}
