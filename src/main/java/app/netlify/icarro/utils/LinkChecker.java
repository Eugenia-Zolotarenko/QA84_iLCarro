package app.netlify.icarro.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class LinkChecker {

    private static final int THREADS = 8;

    private static final String USER_AGENT =
            "Mozilla/5.0 (compatible; LinkChecker/1.0)";

    /** Статусы, при которых HEAD не поддерживается или блокируется: пробуем GET. */
    private static final Set<Integer> GET_FALLBACK_STATUSES = Set.of(403, 405, 501);

    /**
     * Статусы, которые не означают битую ссылку:
     * 429 - ограничение частоты запросов, 999 - антибот-защита (например, LinkedIn).
     */
    private static final Set<Integer> IGNORED_STATUSES = Set.of(418, 429, 999);

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private LinkChecker() {
    }

    /** Проверяет все ссылки параллельно. Возвращает список описаний проблем (пустой, если все рабочие). */
    public static List<String> checkAll(Collection<String> urls) {
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);

        try {
            List<CompletableFuture<String>> futures = urls.stream()
                    .map(url -> CompletableFuture.supplyAsync(() -> check(url), pool))
                    .toList();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .toList();
        } finally {
            pool.shutdown();
        }
    }

    /** Возвращает описание проблемы или null, если ссылка рабочая. */
    public static String check(String url) {
        try {
            int code = sendWithRetry(url, "HEAD");

            if (GET_FALLBACK_STATUSES.contains(code)) {
                code = sendWithRetry(url, "GET");
            }

            boolean broken = code >= 400 && !IGNORED_STATUSES.contains(code);

            return broken ? url + " -> " + code : null;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return url + " -> interrupted";

        } catch (Exception e) {
            return url + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    /** Одна повторная попытка при сетевой ошибке (таймаут, разрыв соединения). */
    private static int sendWithRetry(String url, String method)
            throws IOException, InterruptedException {
        try {
            return send(url, method);
        } catch (IOException first) {
            return send(url, method);
        }
    }

    private static int send(String url, String method)
            throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .header("User-Agent", USER_AGENT)
                .timeout(Duration.ofSeconds(10));

        if ("GET".equals(method)) {
            // не скачиваем тело целиком, нам нужен только статус
            builder.header("Range", "bytes=0-0");
        }

        return CLIENT.send(builder.build(), HttpResponse.BodyHandlers.discarding())
                .statusCode();
    }
}
