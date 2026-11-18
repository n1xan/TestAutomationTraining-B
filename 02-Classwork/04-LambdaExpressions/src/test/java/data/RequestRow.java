package data;

import java.util.Set;
import java.util.function.Predicate;

/** One row of the "My Requests" grid. */
public record RequestRow(String title, String priority, String status) {
}
