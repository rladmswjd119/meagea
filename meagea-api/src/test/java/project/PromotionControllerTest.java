package project;

import entity.Animal;
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
import project.dto.AnimalForm;
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
    public void 유기동물_추가() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "머핀");
        map.add("age", 5);
        map.add("gender", "암컷");
        map.add("weight", 3.5);
        map.add("neuter", true);
        map.add("kind", "친칠라");
        map.add("place", "동네");
        map.add("healthState", 2);
        map.add("activity", 1);
        map.add("sociality", 2);
        map.add("friendly", 1);

        String url = "/meagea/animal";
        ResponseEntity<Animal> animalRe = testRestTemplate.postForEntity(url, map, Animal.class);
        assertThat(animalRe.getStatusCode()).isEqualTo(HttpStatus.OK);
        Animal animal2 = animalRe.getBody();
        assertThat(animal2.getName()).isEqualTo("머핀");
    }

    @Test
    public void 입양_홍보글_생성() throws IOException {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", "제목");
        map.add("animalNo", 9213);
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
        String url = "/meagea/promotion/" + 9280;
        ResponseEntity<Promotion> responseEntity = testRestTemplate.getForEntity(url, Promotion.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Promotion pro = responseEntity.getBody();
        assertThat(pro.getTitle()).isEqualTo("제목");
    }

    @Test
    public void 모든_입양_홍보글_간단_조회(){
        String url = "/meagea/all-promotion-title";
        ResponseEntity<List<SimplePromotionDto>> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SimplePromotionDto>>() {});
        List<SimplePromotionDto> dtoList = responseEntity.getBody();
        for(int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getName()).isEqualTo("머핀");
        }
    }
}