package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.Todo;
import org.apache.el.parser.Token;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @GetMapping
    public List<Todo> getTodos(@RequestParam("name") String todoName) {
        return Arrays.asList(
            new Todo("3f13bb4c-6d88-4cc5-97c8-868569ac2e94","todo 1","todo 1 description"),
            new Todo("e23d5839-1299-4334-ba35-2a9c62ef17a3","todo 2","todo 2 description")
        );
    }

    @GetMapping("/{id}")
    public Todo getTodoFromId(@PathVariable("id") String id) {
        return new Todo("3f13bb4c-6d88-4cc5-97c8-868569ac2e94","todo 1","todo 1 description");

    }

    @PostMapping()
    public Todo createTodo(@RequestBody Todo todo) {
        return new Todo("3f13bb4c-6d88-4cc5-97c8-868569ac2e94","todo 1","todo 1 description");
    }

}
