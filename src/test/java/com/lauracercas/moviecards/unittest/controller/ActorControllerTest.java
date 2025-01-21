package com.lauracercas.moviecards.unittest.controller;

import com.lauracercas.moviecards.controller.ActorController;
import com.lauracercas.moviecards.dto.ActorDTO;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.util.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ActorControllerTest {

    private ActorController controller;

    @Mock
    private ActorService actorServiceMock;

    private AutoCloseable closeable;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        controller = new ActorController(actorServiceMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    private ActorDTO createActorDTO(Integer id) {
        Date birthDate = new Date();
        Date deadDate = new Date();
        return new ActorDTO(id, "Sample Name", birthDate, deadDate, "Sample Country", new ArrayList<>());
    }

    private Actor createActor(Integer id) {
        Actor actor = new Actor();
        actor.setId(id);
        actor.setName("Sample Name");
        actor.setBirthDate(new Date());
        actor.setDeadDate(new Date());
        actor.setCountry("Sample Country");
        actor.setMovies(new ArrayList<>());
        return actor;
    }

    @Test
    void shouldGoListActorAndGetAllActors() {
        // Simular lista vacía
        List<Actor> actors = new ArrayList<>();
        when(actorServiceMock.getAllActors()).thenReturn(actors);

        String viewName = controller.getActorsList(model);

        assertEquals("actors/list", viewName);
        verify(model).addAttribute("actors", actors); // Validar que se agrega el atributo correcto
    }

    @Test
    void shouldInitializeActor() {
        String viewName = controller.newActor(model);

        assertEquals("actors/form", viewName);

        verify(model).addAttribute("actor", new Actor());
        verify(model).addAttribute("title", Messages.NEW_ACTOR_TITLE);
    }

    @Test
    void shouldSaveActorWithNoErrors() {
        ActorDTO actorDTO = createActorDTO(null);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        Actor savedActor = createActor(1);
        when(actorServiceMock.save(any(Actor.class))).thenReturn(savedActor);

        String viewName = controller.saveActor(actorDTO, result, model);

        assertEquals("redirect:/actors", viewName);

        verify(model).addAttribute(eq("actor"), any(Actor.class));
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
        verify(model).addAttribute("message", Messages.SAVED_ACTOR_SUCCESS);
    }

    @Test
    void shouldUpdateActorWithNoErrors() {
        ActorDTO actorDTO = createActorDTO(1);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        Actor updatedActor = createActor(1);
        when(actorServiceMock.save(any(Actor.class))).thenReturn(updatedActor);

        String viewName = controller.saveActor(actorDTO, result, model);

        assertEquals("redirect:/actors", viewName);

        verify(model).addAttribute(eq("actor"), any(Actor.class));
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
        verify(model).addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
    }

    @Test
    void shouldTrySaveActorWithErrors() {
        ActorDTO actorDTO = createActorDTO(null);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = controller.saveActor(actorDTO, result, model);

        assertEquals("actors/form", viewName);
        verifyNoInteractions(model); // Validar que no se interactúa con el modelo
    }

    @Test
    void shouldGoToEditActor() {
        Actor actor = createActor(1);
        List<Movie> movies = List.of(new Movie());
        actor.setMovies(movies);

        when(actorServiceMock.getActorById(actor.getId())).thenReturn(actor);

        String viewName = controller.editActor(actor.getId(), model);

        assertEquals("actors/form", viewName);

        verify(model).addAttribute("actor", actor);
        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute("title", Messages.EDIT_ACTOR_TITLE);
    }

    @Test
    void shouldThrowExceptionWhenActorNotFound() {
        when(actorServiceMock.getActorById(1)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            controller.editActor(1, model);
        });
    }

    @Test
    void shouldHandleSaveActorWithNullMovies() {
        ActorDTO actorDTO = createActorDTO(1);
        actorDTO.setMovies(null);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        Actor actor = createActor(1);
        when(actorServiceMock.save(any(Actor.class))).thenReturn(actor);

        String viewName = controller.saveActor(actorDTO, result, model);

        assertEquals("redirect:/actors", viewName);
        verify(model).addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
    }
}
