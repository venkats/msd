package game;

import java.util.List;

public record Response(int attempts, Status status, List<Match> matches, String message) {
}
