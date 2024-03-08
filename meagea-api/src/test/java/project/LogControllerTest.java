package project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.dto.LogTatalDto;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void 로그_추가_테스트(){
        String url = "/meagea/log";

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("promotionNo", 4827);
        map.add("body", "로그 내용");
        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            FileSystemResource resource = new FileSystemResource(file);
            map.add("imageList", resource);
        }

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map);
        List<LogTatalDto> dtoList = testRestTemplate.exchange(url, HttpMethod.POST, entity,
                    new ParameterizedTypeReference<List<LogTatalDto>>() {}).getBody();

        for(LogTatalDto dto : dtoList) {
            assertEquals(dto.getBody(), "로그 내용");
        }
    }

}
