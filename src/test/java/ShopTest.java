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
    // T1: gültiger Auftrag
    @Test
    public void auftragMitErlaubtemTypSollAngenommenWerden() {
        Order order = mock(Order.class);
        when(order.getBicycleType()).thenReturn(Type.RACE);
        when(order.getCustomer()).thenReturn("Alice");

        boolean result = shop.accept(order);

        // Testausgabe zur Kontrolle
        System.out.println("Auftrag angenommen: " + result);

        assertTrue(result);
    }

    // T2: E-Bike → ablehnen
    @Test
    public void ebikeSollAbgelehntWerden() {
        Order order = mock(Order.class);
        when(order.getBicycleType()).thenReturn(Type.EBIKE);
        when(order.getCustomer()).thenReturn("Alice");

        boolean result = shop.accept(order);

        // Testausgabe zur Kontrolle
        System.out.println("Auftrag angenommen: " + result);

        assertFalse(result);
    }

    // T3: Gravel → ablehnen
    @Test
    public void gravelBikeSollAbgelehntWerden() {
        Order order = mock(Order.class);
        when(order.getBicycleType()).thenReturn(Type.GRAVEL);
        when(order.getCustomer()).thenReturn("Alice");

        boolean result = shop.accept(order);

        // Testausgabe zur Kontrolle
        System.out.println("Auftrag angenommen: " + result);

        assertFalse(result);
    }

    // T4: Kunde hat schon Auftrag
    @Test
    public void zweiterAuftragVomSelbenKundenSollAbgelehntWerden() {
        Order order1 = mock(Order.class);
        when(order1.getBicycleType()).thenReturn(Type.RACE);
        when(order1.getCustomer()).thenReturn("Alice");

        Order order2 = mock(Order.class);
        when(order2.getBicycleType()).thenReturn(Type.RACE);
        when(order2.getCustomer()).thenReturn("Alice");

        boolean result1 = shop.accept(order1); // erster Auftrag wird angenommen
        System.out.println("Auftrag1 angenommen: " + result1);
        assertTrue(result1); // muss true sein

        boolean result2 = shop.accept(order2); // zweiter wird abgelehnt
        System.out.println("Auftrag2 angenommen: " + result2);
        assertFalse(result2); // muss false sein
    }

    // T5: 4 offene → erlauben
    @Test
    public void auftragBeiVierOffenenSollAngenommenWerden() {
        for (int i = 0; i < 4; i++) {
            Order o = mock(Order.class);
            when(o.getBicycleType()).thenReturn(Type.RACE);
            when(o.getCustomer()).thenReturn("Kunde" + i);
            shop.accept(o);
        }

        Order o5 = mock(Order.class);
        when(o5.getBicycleType()).thenReturn(Type.RACE);
        when(o5.getCustomer()).thenReturn("Letzter");

        boolean result = shop.accept(o5);

        System.out.println("Auftrag angenommen: " + result);
        assertTrue(result);
    }

    // T6: 5 offene → ablehnen
    @Test
    public void auftragBeiFuenfOffenenSollAbgelehntWerden() {
        for (int i = 0; i < 5; i++) {
            Order o = mock(Order.class);
            when(o.getBicycleType()).thenReturn(Type.RACE);
            when(o.getCustomer()).thenReturn("Kunde" + i);
            shop.accept(o);
        }

        Order o6 = mock(Order.class);
        when(o6.getBicycleType()).thenReturn(Type.RACE);
        when(o6.getCustomer()).thenReturn("Zuviel");

        boolean result = shop.accept(o6);
        System.out.println("Auftrag angenommen: " + result);
        assertFalse(result);
    }

    // T7: Ungültiger Typ & Warteschlange voll
    @Test
    public void gravelBikeBeiVollemShopSollAbgelehntWerden() {
        for (int i = 0; i < 5; i++) {
            Order o = mock(Order.class);
            when(o.getBicycleType()).thenReturn(Type.RACE);
            when(o.getCustomer()).thenReturn("Kunde" + i);
            shop.accept(o);
        }

        Order o = mock(Order.class);
        when(o.getBicycleType()).thenReturn(Type.GRAVEL);
        when(o.getCustomer()).thenReturn("Zuviel");

        boolean result = shop.accept(o);
        System.out.println("Auftrag angenommen: " + result);

        assertFalse(result);
    }

    // T8: Nur Fahrradtyp gültig
    @Test
    public void auftragMitGueltigemTypAberSonstUngueltigSollAbgelehntWerden() {
        for (int i = 0; i < 5; i++) {
            Order o = mock(Order.class);
            when(o.getBicycleType()).thenReturn(Type.RACE);
            when(o.getCustomer()).thenReturn("Kunde" + i);
            shop.accept(o);
        }

        Order o = mock(Order.class);
        when(o.getBicycleType()).thenReturn(Type.RACE);
        when(o.getCustomer()).thenReturn("Zuviel");

        boolean result = shop.accept(o);
        System.out.println("Auftrgag angenommen: " + result);

        assertFalse(result);
    }
}
