package project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.AnimalDto;
import project.dto.AnimalForm;


import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {

    @Autowired
    public TestRestTemplate testRestTemplate;

    private AnimalForm form;

    @BeforeEach
    public void setUp(){
        testRestTemplate.delete("/meagea/promotions/");
        testRestTemplate.delete("/meagea/animals/");

        form = AnimalForm.builder()
                .name("후드")
                .age(4)
                .gender("암컷")
                .weight(3.5)
                .neuter(true)
                .kind("나몰빼미")
                .place("블루베리 아카데미")
                .healthState(2)
                .activity(3)
                .sociality(2)
                .friendly(1)
                .build();

    }

    @Test
    public void 유기동물_추가_테스트() {
        //given
        String url = "/meagea/animals/";

        //when
        ResponseEntity<AnimalDto> animalRe = testRestTemplate.postForEntity(url, form, AnimalDto.class);

        //then
        assertThat(animalRe.getStatusCode()).isEqualTo(HttpStatus.OK);
        AnimalDto animal2 = animalRe.getBody();
        assertThat(animal2.getName()).isEqualTo("후드");
    }

    @Test
    public void 유기동물_조회_테스트(){
        ResponseEntity<AnimalDto> animalRe = testRestTemplate.postForEntity("/meagea/animals/", form, AnimalDto.class);

        String url = "/meagea/animals/" + animalRe.getBody().getNo();
        AnimalDto animal = testRestTemplate.getForEntity(url, AnimalDto.class).getBody();

        assertThat(animal.getName()).isEqualTo(animalRe.getBody().getName());
    }
}
