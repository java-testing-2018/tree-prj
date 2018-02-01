package com.tree.springcloud.bl.controller;

import com.google.common.base.VerifyException;
import com.tree.springcloud.bl.domain.Node;
import com.tree.springcloud.bl.dto.NodeDto;
import com.tree.springcloud.bl.service.TreeService;
import com.tree.springcloud.bl.service.exception.AlreadyExistsException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.google.common.collect.ImmutableMap.of;
import static com.tree.springcloud.bl.controller.TestUtils.json;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TreeCrudControllerTest {

    @Mock
    private TreeService treeService;

    @InjectMocks
    private TreeCrudApiController treeCrudApiController = new TreeCrudApiController(treeService);

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(treeCrudApiController)
                .setControllerAdvice(new ApiExceptionHandler())

                .build();
    }

    @Test
    public void testFindOrCreate() throws Exception {
        long id = RandomUtils.nextLong();
        long parentId = RandomUtils.nextLong();

        Node expectedNode = new Node(id, "test123", parentId);
        NodeDto expectedNodeDto = new NodeDto(expectedNode);

        when(treeService.findOrCreate(anyLong(), anyLong())).thenReturn(expectedNode);

        mvc.perform(post(TreeCrudApiController.URI)
                .content(json(expectedNodeDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedNodeDto)));

        verify(treeService, times(1)).findOrCreate(id, parentId);
    }

    @Test
    public void testFindOrCreateWithValidationError() throws Exception {
        long id = RandomUtils.nextLong();
        long parentId = RandomUtils.nextLong();

        Node expectedNode = new Node(id, "test123", parentId);
        NodeDto expectedNodeDto = new NodeDto(expectedNode);

        when(treeService.findOrCreate(anyLong(), anyLong())).thenThrow(VerifyException.class);

        mvc.perform(post(TreeCrudApiController.URI)
                .content(json(expectedNodeDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verify(treeService, times(1)).findOrCreate(id, parentId);
    }

    @Test
    public void testFindOrCreateWithAnyError() throws Exception {
        long id = RandomUtils.nextLong();
        long parentId = RandomUtils.nextLong();

        Node expectedNode = new Node(id, "test123", parentId);
        NodeDto expectedNodeDto = new NodeDto(expectedNode);

        when(treeService.findOrCreate(anyLong(), anyLong())).thenThrow(Exception.class);

        mvc.perform(post(TreeCrudApiController.URI)
                .content(json(expectedNodeDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());

        verify(treeService, times(1)).findOrCreate(id, parentId);
    }

    @Test
    public void testDelete() throws Exception {
        long id = RandomUtils.nextLong();

        when(treeService.delete(anyLong())).thenReturn(true);

        mvc.perform(delete(TreeCrudApiController.URI + "/" + id))
                .andExpect(status().isOk());

        verify(treeService, times(1)).delete(id);
    }

    @Test
    public void testDeleteAlreadyDeleted() throws Exception {
        long id = RandomUtils.nextLong();

        when(treeService.delete(anyLong())).thenReturn(false);

        mvc.perform(delete(TreeCrudApiController.URI + "/" + id))
                .andExpect(status().isUnprocessableEntity());

        verify(treeService, times(1)).delete(id);
    }

    @Test
    public void testUpdateId()
            throws Exception {
        long id = RandomUtils.nextLong();
        long newId = RandomUtils.nextLong();

        when(treeService.updateId(anyLong(), anyLong())).thenReturn(Boolean.TRUE);


        mvc.perform(patch(TreeCrudApiController.URI + "/" + id)
                .content(json(of("id", newId)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(treeService, times(1)).updateId(id, newId);
    }

    @Test
    public void testUpdateIdNotUpdatedResult()
            throws Exception {
        long id = RandomUtils.nextLong();
        long newId = RandomUtils.nextLong();

        when(treeService.updateId(anyLong(), anyLong())).thenReturn(Boolean.FALSE);

        mvc.perform(patch(TreeCrudApiController.URI + "/" + id)
                .content(json(of("id", newId)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnprocessableEntity());


        verify(treeService, times(1)).updateId(id, newId);
    }

    @Test
    public void testUpdateIdAlreadyExists()
            throws Exception {
        long id = RandomUtils.nextLong();
        long newId = RandomUtils.nextLong();

        when(treeService.updateId(anyLong(), anyLong())).thenThrow(AlreadyExistsException.class);

        mvc.perform(patch(TreeCrudApiController.URI + "/" + id)
                .content(json(of("id", newId)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verify(treeService, times(1)).updateId(id, newId);
    }
}
