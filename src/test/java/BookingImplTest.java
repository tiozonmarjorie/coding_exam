import org.example.ConsoleOutputCapturer;
import org.example.action.AdminActionImpl;
import org.example.Utils;
import org.example.action.BookingProcessor;
import org.example.action.BuyerActionImpl;
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
        Map<String, Boolean> seatMap = Utils.initializeSeat(rows, seats);
        assertEquals(25, seatMap.size());
    }

    @Test
    void testPhoneValidation() {
        Show mockShow = mock(Show.class);
        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(mockShow);
        when(mockShow.getBookingsList()).thenReturn(Collections.singletonList(new Booking(123, UUID.randomUUID(), 987654321, Arrays.asList("A1","A2"))));

        ConsoleOutputCapturer.captureConsoleOutput();

        BookingProcessor bookingProcessor = new BuyerActionImpl();
        bookingProcessor.setShowStorage(showStorageMock);

        bookingProcessor.processBooking(123, 987654321, Arrays.asList("A2", "A3"));

        String expectedOutput = "====UNABLE TO BOOK A SHOW, PHONE NUMBER EXISTS!=====\n";
        assertEquals(expectedOutput, ConsoleOutputCapturer.getCapturedConsoleOutput());
    }

    @Test
    void testSeatCancellation() {
        Show mockShow = mock(Show.class);
        UUID randomUUID = UUID.fromString("bbcc4621-d88f-4a94-ae2f-b38072bf5087");
        when(showStorageMock.getPersistedShow(anyInt())).thenReturn(mockShow);
        when(mockShow.getBookingsList()).thenReturn(Collections.singletonList(new Booking(123, randomUUID,987654321, Arrays.asList("A1","A2"))));

        ConsoleOutputCapturer.captureConsoleOutput();

        BookingProcessor bookingProcessor = new BuyerActionImpl();
        bookingProcessor.setShowStorage(showStorageMock);
        bookingProcessor.removeBooking(mockShow, "bbcc4621-d88f-4a94-ae2f-b38072bf5087", 987654321);

        String expectedOutput = "======SEATS HAS BEEN SUCCESSFULLY CANCELLED========\n";
        assertEquals(expectedOutput, ConsoleOutputCapturer.getCapturedConsoleOutput());

        verify(mockShow).unMarkSeatMap(anyList());
    }

}