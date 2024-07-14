package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.AnimalDto;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    private MultiValueMap<String, Object> map;

    @BeforeEach
    public void setUp(){
        testRestTemplate.delete("/meagea/promotion");
        testRestTemplate.delete("/meagea/animal");

        map = new LinkedMultiValueMap<>();
        map.add("name", "후드");
        map.add("age", 4);
        map.add("gender", "암컷");
        map.add("weight", 3.5);
        map.add("neuter", true);
        map.add("kind", "포켓몬");
        map.add("detail", "나몰빼미");
        map.add("place", "블룸베리 아카데미");
        map.add("healthState", 2);
        map.add("activity", 1);
        map.add("sociality", 2);
        map.add("friendly", 1);

    }

    @Test
    public void 유기동물_추가_테스트() {
        //given
        String url = "/meagea/animal";

        //when
        ResponseEntity<AnimalDto> animalRe = testRestTemplate.postForEntity(url, map, AnimalDto.class);

        //then
        assertThat(animalRe.getStatusCode()).isEqualTo(HttpStatus.OK);
        AnimalDto animal2 = animalRe.getBody();
        assertThat(animal2.getName()).isEqualTo("후드");
    }

    @Test
    public void 유기동물_조회_테스트(){
        ResponseEntity<AnimalDto> animalRe = testRestTemplate.postForEntity("/meagea/animal", map, AnimalDto.class);

        String url = "/meagea/animal/" + animalRe.getBody().getNo();
        AnimalDto animal = testRestTemplate.getForEntity(url, AnimalDto.class).getBody();

        assertThat(animal.getName()).isEqualTo(animalRe.getBody().getName());
    }
}
