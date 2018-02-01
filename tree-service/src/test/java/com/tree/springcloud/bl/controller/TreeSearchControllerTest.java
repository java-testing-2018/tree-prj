package com.tree.springcloud.bl.controller;

import com.google.common.collect.ImmutableList;
import com.tree.springcloud.bl.service.TreeService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.tree.springcloud.bl.controller.TestUtils.json;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TreeSearchControllerTest {

    @Mock
    private TreeService treeService;

    @InjectMocks
    private TreeSearchApiController treeCrudApiController = new TreeSearchApiController(treeService);

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(treeCrudApiController)
                .setControllerAdvice(new ApiExceptionHandler())

                .build();
    }

    @Test
    public void testFindChilds() throws Exception {
        long id = RandomUtils.nextLong();

        List<Long> expectedResult = ImmutableList.of(99L, 88L, 77L);

        when(treeService.findChildren(anyLong())).thenReturn(expectedResult);

        mvc.perform(get(TreeSearchApiController.URI + "/childs/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedResult)));

        verify(treeService, times(1)).findChildren(id);
    }

    @Test
    public void testFindParents() throws Exception {
        long id = RandomUtils.nextLong();

        List<Long> expectedResult = ImmutableList.of(99L, 88L, 77L);

        when(treeService.findParents(anyLong())).thenReturn(expectedResult);

        mvc.perform(get(TreeSearchApiController.URI + "/parents/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedResult)));

        verify(treeService, times(1)).findParents(id);
    }
}
