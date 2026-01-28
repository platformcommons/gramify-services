package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.application.TagService;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.domain.facade.impl.TagFacadeImpl;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagFacadeImplTest {

    @Mock
    private TagService tagService;

    @Mock
    private TagDTOAssembler tagDTOAssembler;



    @InjectMocks
    private TagFacadeImpl tagFacade;


    private TagDTO tagDTO;
    private Tag tag;

    @Mock
    private TagHierarchyDTOAssembler tagHierarchyDTOAssembler;

    @BeforeEach
    void setUp() {
        tagDTO = TagDTO.builder().id(1L).code("testCode").name("testTag").build();
        tag = Tag.builder().id(1L).code("testCode").name("testTag").build();
    }


    //------ test cases for getTags

    @Test
    void getTags_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        String context = "testContext";
        Boolean isRoot = true;
        String type = "category";
        List<Tag> tagList = Collections.singletonList(tag);
        Page<Tag> tagPage = new PageImpl<>(tagList);

        when(tagService.getTags(page, size, context, isRoot, type)).thenReturn(tagPage);
        when(tagDTOAssembler.toDTO(tag)).thenReturn(tagDTO);

        // Act
        PageDTO<TagDTO> result = tagFacade.getTags(page, size, context, isRoot, type);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getElements().size());
        assertEquals(tagDTO, result.getElements().iterator().next());
        verify(tagService).getTags(page, size, context, isRoot, type);
        verify(tagDTOAssembler).toDTO(any(Tag.class));
    }

    @Test
    void getTags_NoResults() {
        // Arrange
        int page = 0;
        int size = 10;
        String context = "testContext";
        Boolean isRoot = true;
        String type = "category";

        Page<Tag> tagPage = Page.empty();

        when(tagService.getTags(page, size, context, isRoot, type)).thenReturn(tagPage);

        // Act
        PageDTO<TagDTO> result = tagFacade.getTags(page, size, context, isRoot, type);

        // Assert
        assertNotNull(result);
        assertTrue(result.getElements().isEmpty());
        verify(tagService).getTags(page, size, context, isRoot, type);
        verify(tagDTOAssembler, never()).toDTO(any(Tag.class));
    }
      @Test
    void getTags_MultipleResults() {
        // Arrange
        int page = 0;
        int size = 10;
        String context = "testContext";
        Boolean isRoot = true;
        String type = "category";
         Tag tag2 = Tag.builder().id(2L).code("testCode2").name("testTag2").build();
        TagDTO tagDTO2 = TagDTO.builder().id(2L).code("testCode2").name("testTag2").build();
        List<Tag> tagList = Arrays.asList(tag, tag2);
        Page<Tag> tagPage = new PageImpl<>(tagList);

        when(tagService.getTags(page, size, context, isRoot, type)).thenReturn(tagPage);
        when(tagDTOAssembler.toDTO(tag)).thenReturn(tagDTO);
          when(tagDTOAssembler.toDTO(tag2)).thenReturn(tagDTO2);

        // Act
        PageDTO<TagDTO> result = tagFacade.getTags(page, size, context, isRoot, type);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getElements().size());
        assertTrue(result.getElements().containsAll(Arrays.asList(tagDTO,tagDTO2)));
        verify(tagService).getTags(page, size, context, isRoot, type);
        verify(tagDTOAssembler,times(2)).toDTO(any(Tag.class));
    }
    //------ test cases for getSubTags
    @Test
    void getSubTags_Success() {
        // Arrange
        Set<Long> parentTagIds = Collections.singleton(1L);
        Long depth = 2L;
        String context = "testContext";
        String type = "category";

        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setTag(tag);
        TagHierarchyDTO tagHierarchyDTO = new TagHierarchyDTO();
        tagHierarchyDTO.setTag(tagDTO);


        Set<TagHierarchy> tagHierarchies = Collections.singleton(tagHierarchy);

        when(tagService.getSubTags(parentTagIds, depth, context, type)).thenReturn(tagHierarchies);
        when(tagHierarchyDTOAssembler.toDTOs(anySet())).thenReturn(Collections.singleton(tagHierarchyDTO));

        // Act
        Set<TagHierarchyDTO> result = tagFacade.getSubTags(parentTagIds, depth, context, type);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tagHierarchyDTO, result.iterator().next());

        verify(tagService).getSubTags(parentTagIds, depth, context, type);
        verify(tagHierarchyDTOAssembler).toDTOs(anySet());
    }
    @Test
    void getSubTags_NoResults() {
        // Arrange
        Set<Long> parentTagIds = Collections.singleton(1L);
        Long depth = 2L;
        String context = "testContext";
        String type = "category";

        Set<TagHierarchy> tagHierarchies = Collections.emptySet();
        when(tagService.getSubTags(parentTagIds, depth, context, type)).thenReturn(tagHierarchies);

        // Act
        Set<TagHierarchyDTO> result = tagFacade.getSubTags(parentTagIds, depth, context, type);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tagService).getSubTags(parentTagIds, depth, context, type);
//        verify(tagHierarchyDTOAssembler, never()).toDTOs(anySet());
    }
    @Test
    void getSubTags_MultipleResults() {
        // Arrange
        Set<Long> parentTagIds = Collections.singleton(1L);
        Long depth = 2L;
        String context = "testContext";
        String type = "category";
        Tag tag2 = Tag.builder().id(2L).code("testCode2").name("testTag2").build();
        TagDTO tagDTO2 = TagDTO.builder().id(2L).code("testCode2").name("testTag2").build();
        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setTag(tag);
        TagHierarchyDTO tagHierarchyDTO = new TagHierarchyDTO();
        tagHierarchyDTO.setTag(tagDTO);
        TagHierarchy tagHierarchy2 = new TagHierarchy();
        tagHierarchy2.setTag(tag2);
        TagHierarchyDTO tagHierarchyDTO2 = new TagHierarchyDTO();
        tagHierarchyDTO2.setTag(tagDTO2);

        Set<TagHierarchy> tagHierarchies = new HashSet<>(Arrays.asList(tagHierarchy, tagHierarchy2));
        Set<TagHierarchyDTO> tagHierarchyDTOS= new HashSet<>(Arrays.asList(tagHierarchyDTO,tagHierarchyDTO2));


        when(tagService.getSubTags(parentTagIds, depth, context, type)).thenReturn(tagHierarchies);
        when(tagHierarchyDTOAssembler.toDTOs(anySet())).thenReturn(tagHierarchyDTOS);

        // Act
        Set<TagHierarchyDTO> result = tagFacade.getSubTags(parentTagIds, depth, context, type);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(tagHierarchyDTOS));
        verify(tagService).getSubTags(parentTagIds, depth, context, type);
        verify(tagHierarchyDTOAssembler).toDTOs(anySet());
    }
//      @Test
//    void getSubTags_MultipleResults() {
//        // Arrange
//        int page = 0;
//        int size = 10;
//         String sortBy = "name";
//        String direction = "asc";
//        Set<Long> parentTagIds = Collections.singleton(1L);
//        Long depth = 2L;
//        String context = "testContext";
//        String type = "category";
//        Tag tag2 = Tag.builder().id(2L).code("testCode2").name("testTag2").build();
//        TagDTO tagDTO2 = TagDTO.builder().id(2L).code("testCode2").name("testTag2").build();
//        List<Tag> tagList = Arrays.asList(tag, tag2);
//        Page<Tag> tagPage = new PageImpl<>(tagList);
//
//        when(tagService.getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type)).thenReturn(tagPage);
//        when(tagDTOAssembler.toDTO(tag)).thenReturn(tagDTO);
//          when(tagDTOAssembler.toDTO(tag2)).thenReturn(tagDTO2);
//
//        // Act
//        PageDTO<TagDTO> result = tagFacade.getSubTags(direction, parentTagIds, depth, context, type);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.getElements().size());
//        assertTrue(result.getElements().containsAll(Arrays.asList(tagDTO,tagDTO2)));
//        verify(tagService).getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type);
//        verify(tagDTOAssembler,times(2)).toDTO(any(Tag.class));
//    }
    //------ test cases for getSubTags
//    @Test
//    void getSubTags_Success() {
//        // Arrange
//        int page = 0;
//        int size = 10;
//        String sortBy = "name";
//        String direction = "asc";
//        Set<Long> parentTagIds = Collections.singleton(1L);
//        Long depth = 2L;
//        String context = "testContext";
//        String type = "category";
//
//        List<Tag> tagList = Collections.singletonList(tag);
//        Page<Tag> tagPage = new PageImpl<>(tagList);
//        Set<TagDTO> tagDTOSet = Collections.singleton(tagDTO);
//
//
//        when(tagService.getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type)).thenReturn(tagPage);
//        when(tagDTOAssembler.toDTO(any(Tag.class))).thenReturn(tagDTO);
//
//        // Act
//        PageDTO<TagDTO> result = tagFacade.getSubTags(direction, parentTagIds, depth, context, type);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getElements().size());
//        assertEquals(tagDTO, result.getElements().iterator().next());
//
//        verify(tagService).getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type);
//        verify(tagDTOAssembler).toDTO(any(Tag.class));
//    }
//    @Test
//    void getSubTags_NoResults() {
//        // Arrange
//        int page = 0;
//        int size = 10;
//        String sortBy = "name";
//        String direction = "asc";
//        Set<Long> parentTagIds = Collections.singleton(1L);
//        Long depth = 2L;
//        String context = "testContext";
//        String type = "category";
//        Page<Tag> tagPage = Page.empty();
//
//        when(tagService.getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type)).thenReturn(tagPage);
//
//        // Act
//        PageDTO<TagDTO> result = tagFacade.getSubTags(direction, parentTagIds, depth, context, type);
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.getElements().isEmpty());
//        verify(tagService).getSubTags(page, size, sortBy, direction, parentTagIds, depth, context, type);
//        verify(tagDTOAssembler, never()).toDTO(any(Tag.class));
//    }
}