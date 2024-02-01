package com.project.meagea;

import controller.PromotionController;
import dto.PromotionForm;
import dto.SimplePromotionDto;
import entity.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class PromotionControllerTest {
    PromotionController controller = new PromotionController();
    @Test
    public void writePromotionTest() {
        PromotionForm form = new PromotionForm();
        form.setTitle("쫑이를 소개합니다.");
        form.setName("쫑이");
        form.setAge(5);
        form.setWeight(3.5);
        form.setNeuter(true);
        form.setKind("강아지");
        form.setDetail("믹스");
        form.setPlace("파주");
        form.setHealthState(5);
        form.setActivity(2);
        form.setSociality(3);
        form.setFriendly(3);
        form.setAdoptionState(false);
        form.setIntroduction("쫑이는 착해요");
        form.setCondition("평생 책임져야 합니다");

        Promotion pro = controller.writePromotion(form);
        assertThat(pro.getAnimal().getName()).isEqualTo("쫑이");
    }

    @Test
    public void getPromotionTest(){
        Promotion pro = controller.getPromotion(10);
        assertThat(pro.getNo()).isEqualTo(10);
    }

    @Test
    public void getAllPromotionTest(){
        List<SimplePromotionDto> list = controller.getAllPromotion();
        SimplePromotionDto simple = list.get(1);
        assertThat(simple.getName()).isEqualTo("초코1");
    }
}