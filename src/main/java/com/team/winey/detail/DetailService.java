package com.team.winey.detail;

import com.team.winey.detail.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class DetailService {
    private final DetailMapper MAPPER;
    private final SelReview selReview;

    public WineVo selWineDetail(Long productId) {


        WineDetailVo vo = MAPPER.selWineDetail(productId);
        switch (vo.getTemp()) {
            case 0:
                vo.setTemp(10);
                break;
            case 1:
                vo.setTemp(15);
                break;
            default:
                vo.setTemp(18);
        }



        List<String> selPairing = MAPPER.selPairing(productId);


        List<String> selCount = new ArrayList();

        for (int i = 1; i <= 3; i++) {
            SelCountVo selCountVo = new SelCountVo();
            selCountVo.setReviewLevel(i);
            selCountVo.setProductId(productId);
            MAPPER.selCount(selCountVo);
            selCount.add(MAPPER.selCount(selCountVo));
        }


        SelAroma selAroma = MAPPER.selAroma(productId);

        List<SelAroma> aromaList = new ArrayList<>();
        List<String> aroma = new ArrayList<>();
        aromaList.add(selAroma);

        for (SelAroma aromaItem : aromaList) {
            if (aromaItem.getFlower() == 1) {
                aroma.add("flower");
            }
            if (aromaItem.getPlant() == 1) {
                aroma.add("plant");
            }
            if (aromaItem.getFruit() == 1) {
                aroma.add("Fruit");
            }
            if (aromaItem.getSpicy() == 1) {
                aroma.add("Spicy");
            }
            if (aromaItem.getEarth() == 1) {
                aroma.add("Earth");
            }
            if (aromaItem.getOak() == 1) {
                aroma.add("Oak");
            }
            if (aromaItem.getNuts() == 1) {
                aroma.add("Nuts");
            }
            log.info("aroma : {}", aroma);

        }



        int level = 0;
        int sum = vo.getSweety() + vo.getAcidity() + vo.getBody();

        if (sum < 8) {
            level = 1;
        } else if (sum >= 8 && sum < 11) {
            level = 2;
        } else if (sum >= 11 && sum < 16) {
            level = 3;
        }

//        return -1;
//        if(vo.getSweety() <= 2 && vo.getAcidity() <= 2 && vo.getBody() <= 2 ){
//            level = 1;
//        } else if(vo.getSweety() == 5 && vo.getAcidity() == 5 && vo.getBody() == 5 ){
//            level = 3;
//        } else {
//            level = 2;
//        }


        SelSale selSale = MAPPER.selSale(productId);
        if(selSale !=null){
            selSale.setProductId(productId);

        } else if(selSale ==null) {
//            selSale = null;
//            log.info("할인상품아님"); 이게 원래 코드
            SelSale sale = new SelSale();
            sale.setProductId(productId);
            sale.setSalePrice(vo.getPrice());
            sale.setSale(0);
            selSale = sale; //수정후 - 세일상품아니면 세일프라이스에 원가를 넣고 할인율을 0으로 넣음

        }



        return WineVo.builder()
                .wineDetailVo(vo)
                .selPairing(selPairing)
                .selReview(selCount)
                .selAroma(aroma)
                .Level(level)
                .selSale(selSale)
                .build();

    }


    public SelWineKorNm selKorNm(Long productId) {
        return MAPPER.selKorNm(productId);
    }






}
