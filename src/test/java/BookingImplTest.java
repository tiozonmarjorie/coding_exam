import org.example.AdminActionImpl;
import org.example.BuyerActionImpl;
import org.example.model.Booking;
import org.example.model.Show;
import org.example.storage.ShowStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class BookingImplTest {

    @InjectMocks
    private AdminActionImpl adminAction;

    @InjectMocks
    private BuyerActionImpl buyerAction;

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
    void testDisplayOfAvailableSeats() {
        String input = "12345\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(
                new Show(12345, 26, 10, 2));
        assertDoesNotThrow(() -> buyerAction.showAvailableSeats());
    }

    @Test
    void testBookASeat() {
        String input = "12345\n123456789\nA1,A2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Show mockShow = new Show(12345, 26, 10, 2);
        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(mockShow);
        assertDoesNotThrow(() -> buyerAction.bookASeat());
    }

    @Test
    void testSaveBooking() {
        Show mockShow = new Show(12345, 26, 10, 2);
        Booking mockBooking = new Booking(12345, UUID.randomUUID(),
                987654321, Arrays.asList("A1", "A2"));
        doNothing().when(showStorageMock).addBooking(anyInt(), any());
        assertDoesNotThrow(() -> buyerAction.saveBooking(mockShow, mockBooking));
        verify(showStorageMock, times(1)).addBooking(anyInt(), any());
    }

    @Test
    void testCancelASeat() {
        String input = "12345 ea335-adsffd 987654321\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Show mockShow = new Show(12345, 26, 10, 2);
        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(mockShow);
        assertDoesNotThrow(() -> buyerAction.cancelASeat());
        System.setIn(System.in);
    }



}