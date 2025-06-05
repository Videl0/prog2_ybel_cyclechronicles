import cyclechronicles.Order;
import cyclechronicles.Shop;
import cyclechronicles.Type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShopTest {

    private Shop shop;

    @BeforeEach // vor jedem testfall neuer shop
    public void start() {
        shop = new Shop();
    }
}
