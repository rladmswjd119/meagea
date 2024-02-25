package project;

import entity.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.SimplePromotionDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void 입양_홍보글_생성() throws IOException {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", "제목");
        map.add("name", "머핀");
        map.add("age", 4);
        map.add("weight", 3.5);
        map.add("neuter", true);
        map.add("kind", "고양이");
        map.add("detail", "삼색이");
        map.add("place", "인근 슈퍼 앞");
        map.add("healthState", 5);
        map.add("activity", 5);
        map.add("sociality", 3);
        map.add("friendly", 5);
        map.add("adoptionState", false);
        map.add("introduction", "귀여움");
        map.add("condition", "집 좋아하시는 분");
        for(int i = 0; i < 4; i++) {
            // path 경로에 있는 name.type 파일을 File 객체로 생성
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            FileSystemResource resource = new FileSystemResource(file);
            map.add("imageList", resource);
        }

        String url = "/meagea/promotion";
        ResponseEntity<Promotion> responseEntity = testRestTemplate.postForEntity(url, map, Promotion.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Promotion pro = responseEntity.getBody();
        assertThat(pro.getIntroduction()).isEqualTo("귀여움");
    }


    @Test
    public void 입양_홍보글_특정_조회(){
        String url = "/meagea/promotion/" + 10;
        ResponseEntity<Promotion> responseEntity = testRestTemplate.getForEntity(url, Promotion.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Promotion pro = responseEntity.getBody();
        assertThat(pro.getNo()).isEqualTo(10);
    }

    @Test
    public void 모든_입양_홍보글_간단_조회(){
        String url = "/meagea/all-promotion-title";
        ResponseEntity<List<SimplePromotionDto>> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SimplePromotionDto>>() {});
        List<SimplePromotionDto> dtoList = responseEntity.getBody();
        for(int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getNo()).isEqualTo(i);
        }
    }
}