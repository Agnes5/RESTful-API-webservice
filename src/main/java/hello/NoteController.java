package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;


    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public List<Note> allNotes() {
        return noteRepository.findAll();
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable("id") Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        noteRepository.delete(note);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public Note updateNote(@PathVariable("id") Long id, @Valid @RequestBody Note updatedNote) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        return noteRepository.save(note);
    }

}
