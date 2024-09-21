package project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.AnimalDto;
import project.dto.LogTotalDto;
import project.dto.PromotionDetailDto;
import project.dto.SimplePromotionDto;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    private MultiValueMap<String, Object> map;

   @BeforeEach
    public void setUp(){
        testRestTemplate.delete("/meagea/promotions/");
        testRestTemplate.delete("/meagea/animals/");

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

        map = new LinkedMultiValueMap<>();
        map.add("title", "제목");
        map.add("animalNo", Objects.requireNonNull(animalRe.getBody()).getNo());
        map.add("introduction", "귀여움");
        map.add("condition", "집 좋아하시는 분");
        for(int i = 0; i < 4; i++) {
            ClassPathResource resource = new ClassPathResource("file" + i + ".jpg");
            map.add("imageList", resource);
        }
    }

    @Test
    public void 입양_홍보글_생성() {
        String url = "/meagea/promotions/";
        ResponseEntity<PromotionDetailDto> responseEntity = testRestTemplate.postForEntity(url, map, PromotionDetailDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PromotionDetailDto detailDto = responseEntity.getBody();
        assertThat(detailDto.getIntroduction()).isEqualTo("귀여움");
        assertThat(detailDto.getProImageList().size()).isEqualTo(4);
    }


    @Test
    public void 입양_홍보글_특정_조회(){
       String proUrl = "/meagea/promotions/";
       ResponseEntity<PromotionDetailDto> proResponseEntity = testRestTemplate.postForEntity(proUrl, map, PromotionDetailDto.class);
       PromotionDetailDto promotionDetailDto = proResponseEntity.getBody();

       String url = "/meagea/promotions/" + promotionDetailDto.getProNo();
       ResponseEntity<PromotionDetailDto> responseEntity = testRestTemplate.getForEntity(url, PromotionDetailDto.class);
       PromotionDetailDto dto = responseEntity.getBody();

       assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(Objects.requireNonNull(dto).getTitle()).isEqualTo("제목");
       assertThat(dto.getProImageList().size()).isEqualTo(4);
    }

    @Test
    public void 모든_입양_홍보글_간단_조회(){
        String url = "/meagea/promotions/";
        testRestTemplate.postForEntity(url, map, PromotionDetailDto.class);

        ResponseEntity<List<SimplePromotionDto>> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        List<SimplePromotionDto> dtoList = responseEntity.getBody();
        for(int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getName()).isEqualTo("바보쥐");
        }
    }

    @Test
    public void 홍보글_수정_테스트(){
        String proUrl = "/meagea/promotions/";
        ResponseEntity<PromotionDetailDto> proResponseEntity = testRestTemplate.postForEntity(proUrl, map, PromotionDetailDto.class);

        String url = "/meagea/promotions/";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("no", proResponseEntity.getBody().getProNo());
        map.add("title", "수정된 제목");
        for(int i = 0; i < 4; i++) {
            ClassPathResource resource = new ClassPathResource("file" + i + ".jpg");
            map.add("imageList", resource);
        }
        map.add("introduction", "수정된 설명");
        map.add("terms", "수정된 조건");
        HttpEntity<MultiValueMap<String, Object>> entityMap = new HttpEntity<>(map);

        ResponseEntity<PromotionDetailDto> modifyPro = testRestTemplate.exchange(url, HttpMethod.PATCH, entityMap, PromotionDetailDto.class);
        assertThat(modifyPro.getBody().getTitle()).isEqualTo("수정된 제목");
    }

    @Test
    public void 홍보글_삭제_테스트(){
        String proUrl = "/meagea/promotions/";
        ResponseEntity<PromotionDetailDto> proResponseEntity = testRestTemplate.postForEntity(proUrl, map, PromotionDetailDto.class);

        String url = "/meagea/promotions/" + proResponseEntity.getBody().getProNo();
        testRestTemplate.delete(url);
    }
}
