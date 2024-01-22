import org.example.AdminActionImpl;
import org.example.action.BookingProcessor;
import org.example.model.Show;
import org.example.storage.ShowStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class BookingImplTest {

    @InjectMocks
    private AdminActionImpl adminAction;

    @Mock
    private ShowStorage showStorageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddingShow() {
        String input = "1111, 5 , 5 , 2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        doNothing().when(showStorageMock).addNewShow(anyInt(), any());
        assertDoesNotThrow(() -> adminAction.setUpShow());
        verify(showStorageMock, times(1)).addNewShow(anyInt(), any());
    }

    @Test
    void testSeatInitialization() {
        int rows = 5;
        int seats = 5;
        Map<String, Boolean> seatMap = adminAction.initializeSeat(rows, seats);
        assertEquals(25, seatMap.size());
    }

    @Test
    void testProcessBooking() {
        Show mockShow = new Show(12345, 5, 5, 2);
        Map<String, Boolean> seatMap = new HashMap<>();
        seatMap.put("A1", true);
        seatMap.put("A2", true);
        seatMap.put("B1", true);
        mockShow.setSeatMap(seatMap);
        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(mockShow);

        BookingProcessor bookingProcessor = new BookingProcessor(showStorageMock);
        String result = bookingProcessor.processBooking(11, 987654321, Arrays.asList("A1", "A2"));
        assertEquals("Booking successful", result);

        verify(showStorageMock, times(1)).getPersistedShow(anyInt());
    }

}