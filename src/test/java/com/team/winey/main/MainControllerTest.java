package com.team.winey.main;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.team.winey.main.model.WineSelDto;
import com.team.winey.main.model.WineTotalVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given; //메소드 임포트
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {MainController.class})
class MainControllerTest {

    @Autowired
    private MockMvc MVC;

    @MockBean
    private MainService SERVICE;

    @Test
    void getWines() throws Exception {
        //given
        WineSelDto selDto = new WineSelDto();
        selDto.setPage(1);
        selDto.setStartIdx(2);
        selDto.setRow(9);

        List<WineTotalVo> mockList = new ArrayList<>();
        mockList.add(new WineTotalVo(1L, 1L, 1L, 1L, 1L,
                "트라마리 로제 디 프리미티보", "Tramari Rosé di Primitivo", 11300, 7,
                "wine/1/qMwuRhM3Sl2mHZSfzDwwXg_pb_x960.png", 0, 0, 8, 0, 0));
        mockList.add(new WineTotalVo(2L, 2L, 2L, 2L, 2L,
                "러시아 리버 밸리 피노 누아", "Russian River Valley Pinot Noir", 12000, 11,
                "wine/2/4-vr4iXPT5eVsW46Yi6MnA_pb_x960.png", 0, 0, 10, 0, 0));
        given(SERVICE.selWine(any())).willReturn(mockList);

//        String gson = new Gson().toJson(mockList);

        //when
        ResultActions ra = MVC.perform(get("/api/main/wines")
                .param("page", String.valueOf(selDto.getPage()))
                .param("row", String.valueOf(selDto.getRow())));

        //then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockList.size())))
                .andExpect(jsonPath("$[*].productId").exists())
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].nmKor").value("트라마리 로제 디 프리미티보"))
                .andDo(print());

        verify(SERVICE).selWine(any());

    }


    @Test
    void getWinebyId() {
    }

    @Test
    void getWineByPrice2() {
    }
}