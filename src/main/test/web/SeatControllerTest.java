package web;

import entities.Seat;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import repositories.SeatRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Paths.get;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class SeatControllerTest extends TestCase {

    Seat seat;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    SeatRepository seatRepository;

    @Mock
    View mockView;

    @InjectMocks
    SeatController seatController;

    @BeforeEach
    void setup() throws ParseException {
        seat = new Seat();
        seat.setId(1L);
        seat.setName("John");

        seat.setSeatno("1A");
        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        seat.setTdate(date1);

        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(seatController).setSingleView(mockView).build();
    }

    public void testSeats() {
    }

    public void testDelete() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(seatRepository).deleteById(idCapture.capture());
        seatRepository.deleteById(1L);
        assertEquals(Optional.of(1L),idCapture.getValue());
        verify(seatRepository, times(1)).deleteById(1L);
    }

    public void testSave() {
    }

    @Test
    public void findAll_ListView() throws Exception {
        List<Seat> list = new ArrayList<Seat>();
        list.add(seat);
        list.add(seat);

        when(seatRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listSeats", list))
                .andExpect(view().name("seats"))
                .andExpect(model().attribute("listSeats", hasSize(2)));

        verify(seatRepository, times(1)).findAll();
        verifyNoMoreInteractions(seatRepository);
    }

}