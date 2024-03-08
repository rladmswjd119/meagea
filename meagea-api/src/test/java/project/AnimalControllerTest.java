package project;

import entity.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Test
    public void 유기동물_추가_테스트() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "머핀");
        map.add("age", 5);
        map.add("gender", "암컷");
        map.add("weight", 3.5);
        map.add("neuter", true);
        map.add("kind", "친칠라");
        map.add("detail", "믹스");
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
    public void 유기동물_조회_테스트(){
        String url = "/meagea/animal/" + 8113;
        Animal animal = testRestTemplate.getForEntity(url, Animal.class).getBody();

        assertThat(animal.getName()).isEqualTo("머핀");
    }
}
