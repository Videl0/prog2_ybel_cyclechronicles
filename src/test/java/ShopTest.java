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
        when(order.getCustomer()).thenReturn("Tom");

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
        when(order.getCustomer()).thenReturn("Tom");

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
        when(order.getCustomer()).thenReturn("Tom");

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
        when(order1.getCustomer()).thenReturn("Tom");

        Order order2 = mock(Order.class);
        when(order2.getBicycleType()).thenReturn(Type.RACE);
        when(order2.getCustomer()).thenReturn("Tom");

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

    // MOCKING II

    @Test
    public void reparierenSollAeltestenAuftragBearbeiten() {
        Order auftrag1 = mock(Order.class);
        when(auftrag1.getCustomer()).thenReturn("Anna");
        when(auftrag1.getBicycleType()).thenReturn(Type.RACE);

        Order auftrag2 = mock(Order.class);
        when(auftrag2.getCustomer()).thenReturn("Ben");
        when(auftrag2.getBicycleType()).thenReturn(Type.RACE);

        boolean angenommen1 = shop.accept(auftrag1);
        System.out.println("Auftrag1 angenommen: " + angenommen1);

        boolean angenommen2 = shop.accept(auftrag2);
        System.out.println("Auftrag2 angenommen: " + angenommen2);

        System.out.println("Versuche Reparatur durchzuführen (sollte noch nicht funktionieren)");

        try {
            shop.repair();
            fail("Es wurde keine Exception geworfen"); // Test schlägt fehl, wenn keine Exception kommt
        } catch (UnsupportedOperationException e) {
            // Test bestanden
        }
    }

    @Test
    public void lieferungSollRepariertenAuftragFinden() {
        Order auftrag = mock(Order.class);
        when(auftrag.getCustomer()).thenReturn("Clara");
        when(auftrag.getBicycleType()).thenReturn(Type.RACE);

        boolean angenommen = shop.accept(auftrag);
        System.out.println("Auftragg angenommen: " + angenommen);

        System.out.println("Versuche Reparatur und Lieferung (sollte noch nicht funktionieren)");

        try {
            shop.repair();
            shop.deliver("Clara");
            fail("Es wurde keine Exception geworfen"); // Test schlägt fehl, wenn keine Exception kommt
        } catch (UnsupportedOperationException e) {
            // Test bestanden
        }
    }

    //(optional)
    @Test
    public void lieferungSollLeeresErgebnisGebenWennKeinPassenderAuftrag() {
        System.out.println("Kein Auftrag vorhanden, versuche direkte Lieferung");

        try {
            shop.deliver("Unbekannt");
            fail("Es wurde keine Exception geworfen"); // Test schlägt fehl, wenn keine Exception kommt
        } catch (UnsupportedOperationException e) {
            // Test bestanden
        }
    }


}
