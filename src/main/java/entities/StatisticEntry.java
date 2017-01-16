package entities;

/**
 * Created by Nikolai_Shuvaev on 1/11/2017
 */
public class StatisticEntry {
    private Long accessedByNameCount = 0L;
    private Long priceQueriedCount = 0L;
    private Long ticketBookingCount = 0L;

    public Long getAccessedByNameCount() {
        return accessedByNameCount;
    }

    public void setAccessedByNameCount(Long accessedByNameCount) {
        this.accessedByNameCount = accessedByNameCount;
    }

    public Long getPriceQueriedCount() {
        return priceQueriedCount;
    }

    public void setPriceQueriedCount(Long priceQueriedCount) {
        this.priceQueriedCount = priceQueriedCount;
    }

    public Long getTicketBookingCount() {
        return ticketBookingCount;
    }

    public void setTicketBookingCount(Long ticketBookingCount) {
        this.ticketBookingCount = ticketBookingCount;
    }

    @Override
    public String toString() {
        return "StatisticEntry{" +
                "accessedByNameCount=" + accessedByNameCount +
                ", priceQueriedCount=" + priceQueriedCount +
                ", ticketBookingCount=" + ticketBookingCount +
                '}';
    }
}

