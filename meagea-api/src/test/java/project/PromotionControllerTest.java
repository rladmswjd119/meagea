package project;

import entity.Animal;
import entity.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.PromotionDetailDto;
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
    public void 입양_홍보글_생성() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("title", "제목");
        map.add("animalNo", 5981);
        map.add("introduction", "귀여움");
        map.add("condition", "집 좋아하시는 분");
        for(int i = 0; i < 4; i++) {
            // path 경로에 있는 name.type 파일을 File 객체로 생성
            File file = new File("/Users/gim-eunjeong/IdeaProjects/meagea/meagea-api/src/main/java/project/image/"
                    + "file" + i + ".jpg");
            FileSystemResource resource = new FileSystemResource(file);
            map.add("imageList", resource);
        }

        String url = "/meagea/promotion";
        ResponseEntity<PromotionDetailDto> responseEntity = testRestTemplate.postForEntity(url, map, PromotionDetailDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PromotionDetailDto detailDto = responseEntity.getBody();
        assertThat(detailDto.getIntroduction()).isEqualTo("귀여움");
    }


    @Test
    public void 입양_홍보글_특정_조회(){
        String url = "/meagea/promotion/" + 5112;
        ResponseEntity<PromotionDetailDto> responseEntity = testRestTemplate.getForEntity(url, PromotionDetailDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo("제목");
        assertThat(responseEntity.getBody().getImageList().size()).isEqualTo(4);
    }

    @Test
    public void 모든_입양_홍보글_간단_조회(){
        String url = "/meagea/all-promotion-title";
        ResponseEntity<List<SimplePromotionDto>> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        List<SimplePromotionDto> dtoList = responseEntity.getBody();
        for(int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getName()).isEqualTo("머핀");
        }
    }

    @Test
    public void 홍보글_수정_테스트(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        String url = "/meagea/promotion";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("no", 2858);
        map.add("title", "수정된 제목");
        for(int i = 0; i < 4; i++) {
            File file = new File("/Users/gim-eunjeong/IdeaProjects/meagea/meagea-api/src/main/java/project/image/"
                    + "file" + i + ".jpg");
            FileSystemResource resource = new FileSystemResource(file);
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
        String url = "/meagea/promotion/" + 2858;
        testRestTemplate.delete(url);
    }
}