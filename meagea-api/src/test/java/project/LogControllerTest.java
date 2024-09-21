package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.AnimalDto;
import project.dto.LogTotalDto;
import project.dto.PromotionDetailDto;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private PromotionDetailDto proDto;

    @BeforeEach
    public void setUp(){
        testRestTemplate.delete("/meagea/promotions/");
        testRestTemplate.delete("/meagea/animals/");
        testRestTemplate.delete("/meagea/logs/");

        MultiValueMap<String, Object> animalMap = new LinkedMultiValueMap<>();
        animalMap.add("name", "바보쥐");
        animalMap.add("age", 5);
        animalMap.add("gender", "암컷");
        animalMap.add("weight", 3.5);
        animalMap.add("neuter", true);
        animalMap.add("kind", "친칠라");
        animalMap.add("place", "동네");
        animalMap.add("healthState", 2);
        animalMap.add("activity", 1);
        animalMap.add("sociality", 2);
        animalMap.add("friendly", 1);
        String animalUrl = "/meagea/animals/";
        ResponseEntity<AnimalDto> animalRe = testRestTemplate.postForEntity(animalUrl, animalMap, AnimalDto.class);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", "제목");
        map.add("animalNo", Objects.requireNonNull(animalRe.getBody()).getNo());
        map.add("introduction", "귀여움");
        map.add("condition", "집 좋아하시는 분");
        for(int i = 0; i < 4; i++) {
            ClassPathResource resource = new ClassPathResource("file" + i + ".jpg");
            map.add("imageList", resource);
        }
        String url = "/meagea/promotions/";
        ResponseEntity<PromotionDetailDto> responseEntity = testRestTemplate.postForEntity(url, map, PromotionDetailDto.class);
        proDto = responseEntity.getBody();
    }

    @Test
    public void 로그_추가_테스트(){
        String url = "/meagea/logs/";

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("promotionNo", proDto.getProNo());
        map.add("body", "로그 내용");

        for (int i = 0; i < 4; i++) {
            ClassPathResource resource = new ClassPathResource("file" + i + ".jpg");
            map.add("imageList", resource);
        }

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map);
        List<LogTotalDto> dtoList = testRestTemplate.exchange(url, HttpMethod.POST, entity,
                    new ParameterizedTypeReference<List<LogTotalDto>>() {}).getBody();

        for(LogTotalDto dto : dtoList) {
            assertEquals(dto.getBody(), "로그 내용");
        }
    }

}
