package project.controller;

import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.service.PromotionService;
import project.dto.PromotionForm;
import project.dto.SimplePromotionDto;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class PromotionController {

    private final PromotionService proService;

    @PostMapping("/promotion")
    public Promotion addPromotion(@ModelAttribute PromotionForm form) throws IOException {
        return proService.savePromotion(form);
    }

    @GetMapping("/promotion/{no}")
    public Promotion getPromotion(@PathVariable int no) {
        return proService.findByNo(no);
    }

    @GetMapping("/all-promotion-title")
    public List<SimplePromotionDto> getAllPromotionTitle() throws IOException  {
        return proService.findAllSimple();
    }
}