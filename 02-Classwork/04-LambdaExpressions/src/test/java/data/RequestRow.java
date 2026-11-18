package data;

import java.util.Set;
import java.util.function.Predicate;

/** One row of the "My Requests" grid. */
public record RequestRow(String title, String priority, String status) {
    private static final Set<String> URGENT_PRIORITIES = Set.of("high", "critical", "urgent");
    private static final Set<String> FINAL_STATUSES = Set.of("resolved", "closed", "rejected");

    public static final Predicate<RequestRow> IS_URGENT =
            row -> URGENT_PRIORITIES.contains(row.priority().toLowerCase());

    public static final Predicate<RequestRow> IS_OPEN =
            row -> !FINAL_STATUSES.contains(row.status().toLowerCase());
}
