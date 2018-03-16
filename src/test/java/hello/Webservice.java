package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Webservice {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteRepository noteRepository;

//    @MockBean
//    NoteController noteController;

    @Test
    public void createNote() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/notes")
                .accept(MediaType.APPLICATION_JSON).content("{\"title\":\"Hello\",\"content\":\"hello\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void allNotes() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/notes");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteNote() throws Exception {
        Note note = new Note();
        note.setTitle("Hello");
        note.setContent("Content text");
        note.setId((long) 200);
        when(noteRepository.findById(note.getId())).thenReturn(java.util.Optional.of(note));
        doNothing().when(noteRepository).delete(note);
        mockMvc.perform(delete("/api/notes/{id}", note.getId())).andExpect(status().isOk());

        verify(noteRepository, times(1)).findById(note.getId());
        verify(noteRepository, times(1)).delete(note);
        verifyNoMoreInteractions(noteRepository);

    }

    @Test
    public void updateNote() throws Exception {
        
        Note note = new Note();
        note.setTitle("Hello");
        note.setContent("Content text");
        note.setId((long) 100);
        when(noteRepository.findById(note.getId())).thenReturn(java.util.Optional.of(note));
        doReturn(null).when(noteRepository).save(note);
        mockMvc.perform(put("/api/notes/{id}", note.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(note)))
                .andExpect(status().isOk());
        verify(noteRepository, times(1)).findById(note.getId());
        verify(noteRepository, times(1)).save(note);
        verifyNoMoreInteractions(noteRepository);
    }


}
