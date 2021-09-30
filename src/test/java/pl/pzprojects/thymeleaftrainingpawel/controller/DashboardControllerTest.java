package pl.pzprojects.thymeleaftrainingpawel.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pzprojects.thymeleaftrainingpawel.Glass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DashboardController dashboardController;

    @Before
    public void setup()
    {
        dashboardController = new DashboardController();
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    @After
    public void after()
    {
        dashboardController.glasses = new ArrayList<>();
    }
    @Test
    public void testDashboard() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("styledPage"))
                .andExpect(model().attributeExists("glasses"))
                .andExpect(model().attributeExists("glass"))
                .andExpect(model().attributeExists("glassPour"));
    }

    @Test
    public void testShouldDashboardCreateGlass() throws Exception{

        mockMvc.perform(post("/add")
                .param("capacity","10")
                .param("quantity","10"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"))
                .andDo(print());
        assertTrue(dashboardController.glasses.size() == 1);
    }

    @Test
    public void testShouldDashboardNotCreateGlass() throws Exception{
        mockMvc.perform(post("/add")
                .param("capacity","a")
                .param("quantity","10"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"))
                .andDo(print());
        assertTrue(dashboardController.glasses.size() == 0);
    }

    @Test
    public void testShouldDashboardPourWater() throws Exception{

        dashboardController.glasses = createGlasses();

        mockMvc.perform(post("/put-water")
                .param("glassId","1")
                .param("targetId","2")
                .param("pourQuantity","1.5"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andDo(print());
        assertEquals(dashboardController.glasses.get(1).getQuantity(),"4.5");
    }

    @Test
    public void testShouldDashboardNotPourWater() throws Exception{

        dashboardController.glasses = createGlasses();

        mockMvc.perform(post("/put-water")
                .param("glassId","1")
                .param("targetId","a")
                .param("pourQuantity","1.5"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andDo(print());
        assertEquals(dashboardController.glasses.get(1).getQuantity(),"3.0");
    }

    @Test
    public void testShouldDashboardDeleteGlasses() throws Exception{

        dashboardController.glasses = createGlasses();

        mockMvc.perform(post("/delete-all"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"))
                .andDo(print());
        assertTrue(dashboardController.glasses.size()==0);
    }

    private List<Glass> createGlasses()
    {
        List<Glass> glasses;
        glasses = new ArrayList<>();
        glasses.add(new Glass("40","2.0"));
        glasses.add(new Glass("30","3.0"));
        glasses.add(new Glass("50","4.0"));
        return glasses;
    }
}
